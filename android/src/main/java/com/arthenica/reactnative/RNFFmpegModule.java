/*
 * Copyright (c) 2018-2020 Taner Sener
 *
 * This file is part of ReactNativeFFmpeg.
 *
 * ReactNativeFFmpeg is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * ReactNativeFFmpeg is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with ReactNativeFFmpeg.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.arthenica.reactnative;

import android.os.AsyncTask;
import android.util.Log;

import com.arthenica.mobileffmpeg.AbiDetect;
import com.arthenica.mobileffmpeg.Config;
import com.arthenica.mobileffmpeg.ExecuteCallback;
import com.arthenica.mobileffmpeg.FFmpeg;
import com.arthenica.mobileffmpeg.FFmpegExecution;
import com.arthenica.mobileffmpeg.Level;
import com.arthenica.mobileffmpeg.LogCallback;
import com.arthenica.mobileffmpeg.LogMessage;
import com.arthenica.mobileffmpeg.MediaInformation;
import com.arthenica.mobileffmpeg.Statistics;
import com.arthenica.mobileffmpeg.StatisticsCallback;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableMapKeySetIterator;
import com.facebook.react.bridge.ReadableType;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class RNFFmpegModule extends ReactContextBaseJavaModule {

    public static final String LIBRARY_NAME = "react-native-ffmpeg";
    public static final String PLATFORM_NAME = "android";

    public static final String KEY_LOG_EXECUTION_ID = "executionId";
    public static final String KEY_LOG_MESSAGE = "message";
    public static final String KEY_LOG_LEVEL = "level";

    public static final String KEY_STAT_EXECUTION_ID = "executionId";
    public static final String KEY_STAT_TIME = "time";
    public static final String KEY_STAT_SIZE = "size";
    public static final String KEY_STAT_BITRATE = "bitrate";
    public static final String KEY_STAT_SPEED = "speed";
    public static final String KEY_STAT_VIDEO_FRAME_NUMBER = "videoFrameNumber";
    public static final String KEY_STAT_VIDEO_QUALITY = "videoQuality";
    public static final String KEY_STAT_VIDEO_FPS = "videoFps";

    public static final String KEY_EXECUTION_ID = "executionId";
    public static final String KEY_EXECUTION_START_TIME = "startTime";
    public static final String KEY_EXECUTION_COMMAND = "command";

    public static final String EVENT_LOG = "RNFFmpegLogCallback";
    public static final String EVENT_STAT = "RNFFmpegStatisticsCallback";
    public static final String EVENT_EXECUTE = "RNFFmpegExecuteCallback";

    private final ReactApplicationContext reactContext;

    public RNFFmpegModule(final ReactApplicationContext reactContext) {
        super(reactContext);

        this.reactContext = reactContext;
    }

    @Override
    public String getName() {
        return "RNFFmpegModule";
    }

    @ReactMethod
    public void getPlatform(final Promise promise) {
        final String abi = AbiDetect.getAbi();
        promise.resolve(PLATFORM_NAME + "-" + abi);
    }

    @ReactMethod
    public void getFFmpegVersion(final Promise promise) {
        final String version = Config.getFFmpegVersion();
        promise.resolve(version);
    }

    @ReactMethod
    public void executeFFmpegWithArguments(final ReadableArray readableArray, final Promise promise) {
        final RNFFmpegExecuteFFmpegAsyncArgumentsTask asyncTask = new RNFFmpegExecuteFFmpegAsyncArgumentsTask(promise, readableArray);
        asyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    @ReactMethod
    public void executeFFmpegAsyncWithArguments(final ReadableArray readableArray, final Promise promise) {

        /* PREPARING ARGUMENTS */
        String[] arguments = RNFFmpegModule.toArgumentsArray(readableArray);

        long executionId = FFmpeg.executeAsync(arguments, new ExecuteCallback() {

            @Override
            public void apply(long executionId, int returnCode) {
                final DeviceEventManagerModule.RCTDeviceEventEmitter jsModule = reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class);

                final WritableMap executeMap = Arguments.createMap();
                executeMap.putDouble("executionId", (double) executionId);
                executeMap.putInt("returnCode", returnCode);

                jsModule.emit(EVENT_EXECUTE, executeMap);
            }
        });

        promise.resolve((double) executionId);
    }

    @ReactMethod
    public void executeFFprobeWithArguments(final ReadableArray readableArray, final Promise promise) {
        final RNFFmpegExecuteFFprobeAsyncArgumentsTask asyncTask = new RNFFmpegExecuteFFprobeAsyncArgumentsTask(promise, readableArray);
        asyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    @ReactMethod
    public void cancel() {
        FFmpeg.cancel();
    }

    @ReactMethod
    public void cancelExecution(Double executionId) {
        FFmpeg.cancel(executionId.longValue());
    }

    @ReactMethod
    public void enableRedirection() {
        Config.enableRedirection();
    }

    @ReactMethod
    public void disableRedirection() {
        Config.disableRedirection();
    }

    @ReactMethod
    public void getLogLevel(final Promise promise) {
        final Level level = Config.getLogLevel();
        promise.resolve(levelToInt(level));
    }

    @ReactMethod
    public void setLogLevel(final Double level) {
        if (level != null) {
            Config.setLogLevel(Level.from(level.intValue()));
        }
    }

    @ReactMethod
    public void enableLogEvents() {
        Config.enableLogCallback(new LogCallback() {

            @Override
            public void apply(final LogMessage logMessage) {
                emitLogMessage(logMessage);
            }
        });
    }

    @ReactMethod
    public void disableLogEvents() {
        Config.enableLogCallback(null);
    }

    @ReactMethod
    public void enableStatisticsEvents() {
        Config.enableStatisticsCallback(new StatisticsCallback() {

            @Override
            public void apply(final Statistics statistics) {
                emitStatistics(statistics);
            }
        });
    }

    @ReactMethod
    public void disableStatisticsEvents() {
        Config.enableStatisticsCallback(null);
    }

    @ReactMethod
    public void getLastReceivedStatistics(final Promise promise) {
        WritableMap map = toMap(Config.getLastReceivedStatistics());
        promise.resolve(map);
    }

    @ReactMethod
    public void resetStatistics() {
        Config.resetStatistics();
    }

    @ReactMethod
    public void setFontconfigConfigurationPath(final String path) {
        Config.setFontconfigConfigurationPath(path);
    }

    @ReactMethod
    public void setFontDirectory(final String fontDirectoryPath, final ReadableMap fontNameMap) {
        Config.setFontDirectory(reactContext, fontDirectoryPath, toMap(fontNameMap));
    }

    @ReactMethod
    public void getPackageName(final Promise promise) {
        final String packageName = Config.getPackageName();
        promise.resolve(packageName);
    }

    @ReactMethod
    public void getExternalLibraries(final Promise promise) {
        final List<String> externalLibraries = Config.getExternalLibraries();
        promise.resolve(toStringArray(externalLibraries));
    }

    @ReactMethod
    public void getLastReturnCode(final Promise promise) {
        int lastReturnCode = Config.getLastReturnCode();
        promise.resolve(lastReturnCode);
    }

    @ReactMethod
    public void getLastCommandOutput(final Promise promise) {
        final String lastCommandOutput = Config.getLastCommandOutput();
        promise.resolve(lastCommandOutput);
    }

    @ReactMethod
    public void getMediaInformation(final String path, final Promise promise) {
        final RNFFmpegGetMediaInformationAsyncTask asyncTask = new RNFFmpegGetMediaInformationAsyncTask(path, promise);
        asyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    @ReactMethod
    public void registerNewFFmpegPipe(final Promise promise) {
        final String pipe = Config.registerNewFFmpegPipe(reactContext);
        promise.resolve(pipe);
    }

    @ReactMethod
    public void setEnvironmentVariable(final String variableName, final String variableValue) {
        Config.setEnvironmentVariable(variableName, variableValue);
    }

    @ReactMethod
    public void listExecutions(final Promise promise) {
        List<FFmpegExecution> ffmpegExecutionList = FFmpeg.listExecutions();
        promise.resolve(toExecutionsList(ffmpegExecutionList));
    }

    @ReactMethod
    public void writeToPipe(final String inputPath, final String namedPipePath, final Promise promise) {
        final RNFFmpegWriteToPipeAsyncTask asyncTask = new RNFFmpegWriteToPipeAsyncTask(inputPath, namedPipePath, promise);
        asyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    protected void emitLogMessage(final LogMessage logMessage) {
        final DeviceEventManagerModule.RCTDeviceEventEmitter jsModule = reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class);
        final WritableMap logMap = Arguments.createMap();

        logMap.putDouble(KEY_LOG_EXECUTION_ID, logMessage.getExecutionId());
        logMap.putInt(KEY_LOG_LEVEL, levelToInt(logMessage.getLevel()));
        logMap.putString(KEY_LOG_MESSAGE, logMessage.getText());

        jsModule.emit(EVENT_LOG, logMap);
    }

    protected void emitStatistics(final Statistics statistics) {
        final DeviceEventManagerModule.RCTDeviceEventEmitter jsModule = reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class);

        jsModule.emit(EVENT_STAT, toMap(statistics));
    }

    public static int levelToInt(final Level level) {
        return (level == null) ? Level.AV_LOG_TRACE.getValue() : level.getValue();
    }

    public static WritableArray toStringArray(final List<String> list) {
        final WritableArray array = Arguments.createArray();

        if (list != null) {
            for (String item : list) {
                array.pushString(item);
            }
        }

        return array;
    }

    public static Map<String, String> toMap(final ReadableMap readableMap) {
        final Map<String, String> map = new HashMap<>();

        if (readableMap != null) {
            final ReadableMapKeySetIterator iterator = readableMap.keySetIterator();
            while (iterator.hasNextKey()) {
                final String key = iterator.nextKey();
                final ReadableType type = readableMap.getType(key);

                if (type == ReadableType.String) {
                    map.put(key, readableMap.getString(key));
                }
            }
        }

        return map;
    }

    public static WritableMap toMap(final Statistics statistics) {
        final WritableMap statisticsMap = Arguments.createMap();

        if (statistics != null) {
            statisticsMap.putDouble(KEY_STAT_EXECUTION_ID, statistics.getExecutionId());
            statisticsMap.putInt(KEY_STAT_TIME, statistics.getTime());
            statisticsMap.putDouble(KEY_STAT_SIZE, statistics.getSize());
            statisticsMap.putDouble(KEY_STAT_BITRATE, statistics.getBitrate());
            statisticsMap.putDouble(KEY_STAT_SPEED, statistics.getSpeed());
            statisticsMap.putInt(KEY_STAT_VIDEO_FRAME_NUMBER, statistics.getVideoFrameNumber());
            statisticsMap.putDouble(KEY_STAT_VIDEO_QUALITY, statistics.getVideoQuality());
            statisticsMap.putDouble(KEY_STAT_VIDEO_FPS, statistics.getVideoFps());
        }

        return statisticsMap;
    }

    public static WritableArray toExecutionsList(final List<FFmpegExecution> ffmpegExecutions) {
        final WritableArray executions = Arguments.createArray();

        for (int i = 0; i < ffmpegExecutions.size(); i++) {
            FFmpegExecution execution = ffmpegExecutions.get(i);

            WritableMap executionMap = Arguments.createMap();
            executionMap.putDouble(KEY_EXECUTION_ID, execution.getExecutionId());
            executionMap.putDouble(KEY_EXECUTION_START_TIME, execution.getStartTime().getTime());
            executionMap.putString(KEY_EXECUTION_COMMAND, execution.getCommand());

            executions.pushMap(executionMap);
        }

        return executions;
    }

    public static WritableMap toMediaInformationMap(final MediaInformation mediaInformation) {
        WritableMap map = Arguments.createMap();

        if (mediaInformation != null) {
            JSONObject allProperties = mediaInformation.getAllProperties();
            if (allProperties != null) {
                map = toMap(allProperties);
            }
        }

        return map;
    }

    public static WritableMap toMap(final JSONObject jsonObject) {
        final WritableMap map = Arguments.createMap();

        if (jsonObject != null) {
            Iterator<String> keys = jsonObject.keys();
            while (keys.hasNext()) {
                String key = keys.next();
                Object value = jsonObject.opt(key);
                if (value != null) {
                    if (value instanceof JSONArray) {
                        map.putArray(key, toList((JSONArray) value));
                    } else if (value instanceof JSONObject) {
                        map.putMap(key, toMap((JSONObject) value));
                    } else if (value instanceof String) {
                        map.putString(key, (String) value);
                    } else if (value instanceof Number) {
                        if (value instanceof Integer) {
                            map.putInt(key, (Integer) value);
                        } else {
                            map.putDouble(key, ((Number) value).doubleValue());
                        }
                    } else if (value instanceof Boolean) {
                        map.putBoolean(key, (Boolean) value);
                    } else {
                        Log.i(LIBRARY_NAME, String.format("Can not map json key %s using value %s:%s", key, value.toString(), value.getClass().toString()));
                    }
                }
            }
        }

        return map;
    }

    public static WritableArray toList(final JSONArray array) {
        final WritableArray list = Arguments.createArray();

        for (int i = 0; i < array.length(); i++) {
            Object value = array.opt(i);
            if (value != null) {
                if (value instanceof JSONArray) {
                    list.pushArray(toList((JSONArray) value));
                } else if (value instanceof JSONObject) {
                    list.pushMap(toMap((JSONObject) value));
                } else if (value instanceof String) {
                    list.pushString((String) value);
                } else if (value instanceof Number) {
                    if (value instanceof Integer) {
                        list.pushInt((Integer) value);
                    } else {
                        list.pushDouble(((Number) value).doubleValue());
                    }
                } else if (value instanceof Boolean) {
                    list.pushBoolean((Boolean) value);
                } else {
                    Log.i(LIBRARY_NAME, String.format("Can not map json value %s:%s", value.toString(), value.getClass().toString()));
                }
            }
        }

        return list;
    }

    public static String[] toArgumentsArray(final ReadableArray readableArray) {
        final List<String> arguments = new ArrayList<>();
        for (int i = 0; i < readableArray.size(); i++) {
            final ReadableType type = readableArray.getType(i);

            if (type == ReadableType.String) {
                arguments.add(readableArray.getString(i));
            }
        }

        return arguments.toArray(new String[0]);
    }

}













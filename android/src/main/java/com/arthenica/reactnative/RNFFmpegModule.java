/*
 * MIT License
 *
 * Copyright (c) 2018 Taner Sener
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.arthenica.reactnative;

import com.arthenica.mobileffmpeg.AbiDetect;
import com.arthenica.mobileffmpeg.Config;
import com.arthenica.mobileffmpeg.FFmpeg;
import com.arthenica.mobileffmpeg.Level;
import com.arthenica.mobileffmpeg.LogCallback;
import com.arthenica.mobileffmpeg.LogMessage;
import com.arthenica.mobileffmpeg.MediaInformation;
import com.arthenica.mobileffmpeg.Statistics;
import com.arthenica.mobileffmpeg.StatisticsCallback;
import com.arthenica.mobileffmpeg.StreamInformation;
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
import com.facebook.react.bridge.WritableNativeMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class RNFFmpegModule extends ReactContextBaseJavaModule {

    public static final String LIBRARY_NAME = "react-native-ffmpeg";
    public static final String PLATFORM_NAME = "android";

    public static final String KEY_VERSION = "version";
    public static final String KEY_RC = "rc";
    public static final String KEY_PLATFORM = "platform";
    public static final String KEY_PACKAGE_NAME = "packageName";
    public static final String KEY_LAST_RC = "lastRc";
    public static final String KEY_LAST_COMMAND_OUTPUT = "lastCommandOutput";

    public static final String KEY_LOG_TEXT = "log";
    public static final String KEY_LOG_LEVEL = "level";

    public static final String KEY_STAT_TIME = "time";
    public static final String KEY_STAT_SIZE = "size";
    public static final String KEY_STAT_BITRATE = "bitrate";
    public static final String KEY_STAT_SPEED = "speed";
    public static final String KEY_STAT_VIDEO_FRAME_NUMBER = "videoFrameNumber";
    public static final String KEY_STAT_VIDEO_QUALITY = "videoQuality";
    public static final String KEY_STAT_VIDEO_FPS = "videoFps";

    public static final String EVENT_LOG = "RNFFmpegLogCallback";
    public static final String EVENT_STAT = "RNFFmpegStatisticsCallback";

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
        promise.resolve(toStringMap(KEY_PLATFORM, PLATFORM_NAME + "-" + abi));
    }

    @ReactMethod
    public void getFFmpegVersion(final Promise promise) {
        final String version = FFmpeg.getFFmpegVersion();
        promise.resolve(toStringMap(KEY_VERSION, version));
    }

    @ReactMethod
    public void executeWithArguments(final ReadableArray readableArray, final Promise promise) {
        final RNFFmpegExecuteAsyncArgumentsTask asyncTask = new RNFFmpegExecuteAsyncArgumentsTask(promise);
        asyncTask.execute(readableArray);
    }

    @ReactMethod
    public void execute(final String command, String delimiter, final Promise promise) {
        final RNFFmpegExecuteAsyncCommandTask asyncTask = new RNFFmpegExecuteAsyncCommandTask(delimiter, promise);
        asyncTask.execute(command);
    }

    @ReactMethod
    public void cancel() {
        FFmpeg.cancel();
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
        promise.resolve(toIntMap(KEY_LOG_LEVEL, levelToInt(level)));
    }

    @ReactMethod
    public void setLogLevel(final ReadableArray readableArray) {
        int logLevelInt = Level.AV_LOG_TRACE.getValue();

        for (int i = 0; i < readableArray.size(); i++) {
            final ReadableType type = readableArray.getType(i);

            if (type == ReadableType.Number) {
                logLevelInt = readableArray.getInt(i);
            }
        }

        Config.setLogLevel(Level.from(logLevelInt));
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
        promise.resolve(toStringMap(KEY_PACKAGE_NAME, packageName));
    }

    @ReactMethod
    public void getExternalLibraries(final Promise promise) {
        final List<String> externalLibraries = Config.getExternalLibraries();
        promise.resolve(toStringArray(externalLibraries));
    }

    @ReactMethod
    public void getLastReturnCode(final Promise promise) {
        int lastReturnCode = FFmpeg.getLastReturnCode();
        promise.resolve(toIntMap(KEY_LAST_RC, lastReturnCode));
    }

    @ReactMethod
    public void getLastCommandOutput(final Promise promise) {
        final String lastCommandOutput = FFmpeg.getLastCommandOutput();
        promise.resolve(toStringMap(KEY_LAST_COMMAND_OUTPUT, lastCommandOutput));
    }

    @ReactMethod
    public void getMediaInformation(final String path, final Double timeout, final Promise promise) {
        final RNFFmpegGetMediaInformationAsyncTask asyncTask = new RNFFmpegGetMediaInformationAsyncTask(timeout, promise);
        asyncTask.execute(path);
    }

    protected void emitLogMessage(final LogMessage logMessage) {
        final DeviceEventManagerModule.RCTDeviceEventEmitter jsModule = reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class);
        final WritableMap logMap = Arguments.createMap();
        logMap.putInt(KEY_LOG_LEVEL, levelToInt(logMessage.getLevel()));
        logMap.putString(KEY_LOG_TEXT, logMessage.getText());
        jsModule.emit(EVENT_LOG, logMap);
    }

    protected void emitStatistics(final Statistics statistics) {
        final DeviceEventManagerModule.RCTDeviceEventEmitter jsModule = reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class);
        jsModule.emit(EVENT_STAT, toMap(statistics));
    }

    public static int levelToInt(final Level level) {
        return (level == null) ? Level.AV_LOG_TRACE.getValue() : level.getValue();
    }

    public static WritableMap toStringMap(final String key, final String value) {
        final WritableMap map = new WritableNativeMap();
        map.putString(key, value);
        return map;
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

    public static WritableMap toIntMap(final String key, final int value) {
        final WritableMap map = new WritableNativeMap();
        map.putInt(key, value);
        return map;
    }

    public static Map<String, String> toMap(final ReadableMap readableMap) {
        final Map<String, String> map = new HashMap<>();

        if (readableMap != null) {
            final ReadableMapKeySetIterator iterator = readableMap.keySetIterator();
            while (iterator.hasNextKey()) {
                final String key = iterator.nextKey();
                final ReadableType type = readableMap.getType(key);

                switch (type) {
                    case String:
                        map.put(key, readableMap.getString(key));
                        break;
                }
            }
        }

        return map;
    }

    public static WritableMap toMap(final Statistics statistics) {
        final WritableMap statisticsMap = Arguments.createMap();

        if (statistics != null) {
            statisticsMap.putInt(KEY_STAT_TIME, statistics.getTime());
            statisticsMap.putInt(KEY_STAT_SIZE, (statistics.getSize() < Integer.MAX_VALUE) ? (int) statistics.getSize() : (int) (statistics.getSize() % Integer.MAX_VALUE));
            statisticsMap.putDouble(KEY_STAT_BITRATE, statistics.getBitrate());
            statisticsMap.putDouble(KEY_STAT_SPEED, statistics.getSpeed());

            statisticsMap.putInt(KEY_STAT_VIDEO_FRAME_NUMBER, statistics.getVideoFrameNumber());
            statisticsMap.putDouble(KEY_STAT_VIDEO_QUALITY, statistics.getVideoQuality());
            statisticsMap.putDouble(KEY_STAT_VIDEO_FPS, statistics.getVideoFps());
        }

        return statisticsMap;
    }

    public static WritableMap toMediaInformationMap(final MediaInformation mediaInformation) {
        final WritableMap map = Arguments.createMap();

        if (mediaInformation != null) {
            if (mediaInformation.getFormat() != null) {
                map.putString("format", mediaInformation.getFormat());
            }
            if (mediaInformation.getPath() != null) {
                map.putString("path", mediaInformation.getPath());
            }
            if (mediaInformation.getStartTime() != null) {
                map.putInt("startTime", mediaInformation.getStartTime().intValue());
            }
            if (mediaInformation.getDuration() != null) {
                map.putInt("duration", mediaInformation.getDuration().intValue());
            }
            if (mediaInformation.getBitrate() != null) {
                map.putInt("bitrate", mediaInformation.getBitrate().intValue());
            }
            if (mediaInformation.getRawInformation() != null) {
                map.putString("rawInformation", mediaInformation.getRawInformation());
            }

            final Set<Map.Entry<String, String>> metadata = mediaInformation.getMetadataEntries();
            if ((metadata != null) && (metadata.size() > 0)) {
                final WritableMap metadataMap = Arguments.createMap();

                for (Map.Entry<String, String> entry : metadata) {
                    metadataMap.putString(entry.getKey(), entry.getValue());
                }

                map.putMap("metadata", metadataMap);
            }

            final List<StreamInformation> streams = mediaInformation.getStreams();
            if ((streams != null) && (streams.size() > 0)) {
                final WritableArray array = Arguments.createArray();

                for (StreamInformation streamInformation : streams) {
                    array.pushMap(toStreamInformationMap(streamInformation));
                }

                map.putArray("streams", array);
            }
        }

        return map;
    }

    public static WritableMap toStreamInformationMap(final StreamInformation streamInformation) {
        final WritableMap map = Arguments.createMap();

        if (streamInformation != null) {
            if (streamInformation.getIndex() != null) {
                map.putInt("index", streamInformation.getIndex().intValue());
            }
            if (streamInformation.getType() != null) {
                map.putString("type", streamInformation.getType());
            }
            if (streamInformation.getCodec() != null) {
                map.putString("codec", streamInformation.getCodec());
            }
            if (streamInformation.getFullCodec() != null) {
                map.putString("fullCodec", streamInformation.getFullCodec());
            }
            if (streamInformation.getFormat() != null) {
                map.putString("format", streamInformation.getFormat());
            }
            if (streamInformation.getFullFormat() != null) {
                map.putString("fullFormat", streamInformation.getFullFormat());
            }
            if (streamInformation.getWidth() != null) {
                map.putInt("width", streamInformation.getWidth().intValue());
            }
            if (streamInformation.getHeight() != null) {
                map.putInt("height", streamInformation.getHeight().intValue());
            }
            if (streamInformation.getBitrate() != null) {
                map.putInt("bitrate", streamInformation.getBitrate().intValue());
            }
            if (streamInformation.getSampleRate() != null) {
                map.putInt("sampleRate", streamInformation.getSampleRate().intValue());
            }
            if (streamInformation.getSampleFormat() != null) {
                map.putString("sampleFormat", streamInformation.getSampleFormat());
            }
            if (streamInformation.getChannelLayout() != null) {
                map.putString("channelLayout", streamInformation.getChannelLayout());
            }
            if (streamInformation.getSampleAspectRatio() != null) {
                map.putString("sampleAspectRatio", streamInformation.getSampleAspectRatio());
            }
            if (streamInformation.getDisplayAspectRatio() != null) {
                map.putString("displayAspectRatio", streamInformation.getDisplayAspectRatio());
            }
            if (streamInformation.getAverageFrameRate() != null) {
                map.putString("averageFrameRate", streamInformation.getAverageFrameRate());
            }
            if (streamInformation.getRealFrameRate() != null) {
                map.putString("realFrameRate", streamInformation.getRealFrameRate());
            }
            if (streamInformation.getTimeBase() != null) {
                map.putString("timeBase", streamInformation.getTimeBase());
            }
            if (streamInformation.getCodecTimeBase() != null) {
                map.putString("codecTimeBase", streamInformation.getCodecTimeBase());
            }

            final Set<Map.Entry<String, String>> metadata = streamInformation.getMetadataEntries();
            if ((metadata != null) && (metadata.size() > 0)) {
                final WritableMap metadataMap = Arguments.createMap();

                for (Map.Entry<String, String> entry : metadata) {
                    metadataMap.putString(entry.getKey(), entry.getValue());
                }

                map.putMap("metadata", metadataMap);
            }

            final Set<Map.Entry<String, String>> sidedata = streamInformation.getSidedataEntries();
            if ((sidedata != null) && (sidedata.size() > 0)) {
                final WritableMap sidedataMap = Arguments.createMap();

                for (Map.Entry<String, String> entry : sidedata) {
                    sidedataMap.putString(entry.getKey(), entry.getValue());
                }

                map.putMap("sidedata", sidedataMap);
            }
        }

        return map;
    }

}













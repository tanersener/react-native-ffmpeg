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

import android.system.ErrnoException;
import android.util.Log;

import com.arthenica.mobileffmpeg.AbiDetect;
import com.arthenica.mobileffmpeg.Config;
import com.arthenica.mobileffmpeg.FFmpeg;
import com.arthenica.mobileffmpeg.Level;
import com.arthenica.mobileffmpeg.LogCallback;
import com.arthenica.mobileffmpeg.LogMessage;
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
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RNFFmpegModule extends ReactContextBaseJavaModule {

    public static final String LIBRARY_NAME = "react-native-ffmpeg";
    public static final String PLATFORM_NAME = "android";

    public static final String KEY_VERSION = "version";
    public static final String KEY_RC = "rc";
    public static final String KEY_PLATFORM = "platform";

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
    public void execute(final ReadableArray readableArray, final Promise promise) {

        // PREPARING ARGUMENTS
        final List<String> arguments = new ArrayList<>();
        for (int i = 0; i < readableArray.size(); i++) {
            final ReadableType type = readableArray.getType(i);

            if (type == ReadableType.String) {
                arguments.add(readableArray.getString(i));
            }
        }

        final String[] argumentsArray = arguments.toArray(new String[arguments.size()]);

        Log.d(LIBRARY_NAME, String.format("Running FFmpeg with arguments: %s", Arrays.toString(argumentsArray)));

        int rc = FFmpeg.execute(argumentsArray);

        Log.d(LIBRARY_NAME, String.format("FFmpeg exited with rc: %d", rc));

        promise.resolve(toIntMap(KEY_RC, rc));
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
    public void setFontconfigConfigurationPath(final ReadableArray readableArray) {
        String path = null;

        for (int i = 0; i < readableArray.size(); i++) {
            final ReadableType type = readableArray.getType(i);

            if (type == ReadableType.String) {
                path = readableArray.getString(i);
            }
        }

        try {
            Config.setFontconfigConfigurationPath(path);
        } catch (final ErrnoException e) {
            Log.w(LIBRARY_NAME, String.format("Setting fontconfig configuration path failed for %s", path), e);
        }
    }

    @ReactMethod
    public void setFontDirectory(final ReadableArray readableArray) {
        String path = null;
        Map<String, String> map = null;

        for (int i = 0; i < readableArray.size(); i++) {
            final ReadableType type = readableArray.getType(i);

            if (type == ReadableType.String) {
                path = readableArray.getString(i);
            } else if (type == ReadableType.Map) {
                map = toMap(readableArray.getMap(i));
            }
        }

        Config.setFontDirectory(reactContext, path, map);
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

    public static WritableMap toIntMap(final String key, final int value) {
        final WritableMap map = new WritableNativeMap();
        map.putInt(key, value);
        return map;
    }

    public static Map<String, String> toMap(final ReadableMap readableMap) {
        final Map<String, String> map = new HashMap<>();

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

}

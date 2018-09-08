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
import com.facebook.react.bridge.ReadableType;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RNFFmpegModule extends ReactContextBaseJavaModule {

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

        Log.d("react-native-ffmpeg", String.format("Calling execute with arguments %s", Arrays.toString(argumentsArray)));

        int rc = FFmpeg.execute(argumentsArray);
        if (rc == 0) {
            promise.resolve(toIntMap(KEY_RC, rc));
        } else {
            promise.reject(String.valueOf(rc), "Execute failed");
        }
    }

    @ReactMethod
    public void getPlatform(final Promise promise) {
        final String platform = AbiDetect.getAbi();
        promise.resolve(toStringMap(KEY_PLATFORM, platform));
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

    protected void emitLogMessage(final LogMessage logMessage) {
        final DeviceEventManagerModule.RCTDeviceEventEmitter jsModule = reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class);
        final WritableMap logMap = Arguments.createMap();
        logMap.putString(KEY_LOG_TEXT, logMessage.getText());
        logMap.putInt(KEY_LOG_LEVEL, (logMessage.getLevel() == null) ? Level.AV_LOG_TRACE.getValue() : logMessage.getLevel().getValue());
        jsModule.emit(EVENT_LOG, logMap);
    }

    protected void emitStatistics(final Statistics statistics) {
        final DeviceEventManagerModule.RCTDeviceEventEmitter jsModule = reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class);
        final WritableMap statisticsMap = Arguments.createMap();

        statisticsMap.putInt(KEY_STAT_TIME, statistics.getTime());
        statisticsMap.putInt(KEY_STAT_SIZE, (statistics.getSize() < Integer.MAX_VALUE) ? (int) statistics.getSize() : (int) (statistics.getSize() % Integer.MAX_VALUE));
        statisticsMap.putDouble(KEY_STAT_BITRATE, statistics.getBitrate());
        statisticsMap.putDouble(KEY_STAT_SPEED, statistics.getSpeed());

        statisticsMap.putInt(KEY_STAT_VIDEO_FRAME_NUMBER, statistics.getVideoFrameNumber());
        statisticsMap.putDouble(KEY_STAT_VIDEO_QUALITY, statistics.getVideoQuality());
        statisticsMap.putDouble(KEY_STAT_VIDEO_FPS, statistics.getVideoFps());

        jsModule.emit(EVENT_STAT, statisticsMap);
    }

    protected static WritableMap toStringMap(final String key, final String value) {
        final WritableMap map = new WritableNativeMap();
        map.putString(key, value);
        return map;
    }

    protected static WritableMap toIntMap(final String key, final int value) {
        final WritableMap map = new WritableNativeMap();
        map.putInt(key, value);
        return map;
    }

}

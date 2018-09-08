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

#import "RNFFmpegModule.h"
#import <React/RCTLog.h>
#import <React/RCTBridge.h>
#import <React/RCTEventDispatcher.h>

#import <mobileffmpeg/mobileffmpeg.h>
#import <mobileffmpeg/archdetect.h>

static NSString *const LIBRARY_NAME = @"react-native-ffmpeg";
static NSString *const PLATFORM_NAME = @"ios";

static NSString *const KEY_VERSION = @"version";
static NSString *const KEY_RC = @"rc";
static NSString *const KEY_PLATFORM = @"platform";

static NSString *const KEY_LOG_TEXT = @"log";
static NSString *const KEY_LOG_LEVEL = @"level";

static NSString *const KEY_STAT_TIME = @"time";
static NSString *const KEY_STAT_SIZE = @"size";
static NSString *const KEY_STAT_BITRATE = @"bitrate";
static NSString *const KEY_STAT_SPEED = @"speed";
static NSString *const KEY_STAT_VIDEO_FRAME_NUMBER = @"videoFrameNumber";
static NSString *const KEY_STAT_VIDEO_QUALITY = @"videoQuality";
static NSString *const KEY_STAT_VIDEO_FPS = @"videoFps";

static NSString *const EVENT_LOG = @"RNFFmpegLogCallback";
static NSString *const EVENT_STAT = @"RNFFmpegStatisticsCallback";

@implementation RNFFmpegModule
@synthesize bridge = _bridge;

RCT_EXPORT_MODULE();

RCT_EXPORT_METHOD(getPlatform:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject) {
    NSString *architecture = [ArchDetect getArch];
    resolve([self toStringDictionary(KEY_PLATFORM, [NSString stringWithFormat:@"%@-%@", PLATFORM_NAME, architecture])]);
}

RCT_EXPORT_METHOD(getFFmpegVersion:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject) {
    NSString *ffmpegVersion = [MobileFFmpeg getFFmpegVersion];
    resolve([self toStringDictionary(KEY_VERSION, ffmpegVersion)]);
}

RCT_EXPORT_METHOD(execute:(NSString*)arguments resolver:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject) {
    RCTLogInfo(@"Running FFmpeg with arguments: %s\n", command);

    dispatch_async(dispatch_get_main_queue(), ^{
        int rc = [MobileFFmpeg execute:arguments];

        RCTLogInfo(@"FFmpeg exited with rc: %d\n", rc);

        resolve([self toIntDictionary(KEY_RC, [NSNumber numberWithInt:rc])]);
    });
}

RCT_EXPORT_METHOD(cancel) {
    [MobileFFmpeg cancel];
}

RCT_EXPORT_METHOD(enableRedirection) {
    [MobileFFmpegConfig enableRedirection];
}

RCT_EXPORT_METHOD(disableRedirection) {
    [MobileFFmpegConfig disableRedirection];
}

RCT_EXPORT_METHOD(getLogLevel:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject) {
    int logLevel = [MobileFFmpegConfig getLogLevel];
    resolve([self toIntDictionary(KEY_LOG_LEVEL, [NSNumber numberWithInt:logLevel])]);
}

RCT_EXPORT_METHOD(setLogLevel:(int)logLevel) {
    [MobileFFmpegConfig setLogLevel:logLevel];
}

RCT_EXPORT_METHOD(enableLogEvents) {
    [MobileFFmpegConfig setLogDelegate:self];
}

RCT_EXPORT_METHOD(disableLogEvents) {
    [MobileFFmpegConfig setLogDelegate:nil];
}

RCT_EXPORT_METHOD(enableStatisticsEvents) {
    [MobileFFmpegConfig setStatisticsDelegate:self];
}

RCT_EXPORT_METHOD(disableStatisticsEvents) {
    [MobileFFmpegConfig setStatisticsDelegate:nil];
}

RCT_EXPORT_METHOD(getLastReceivedStatistics:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject) {
    (Statistics*) statistics = [MobileFFmpegConfig getLastReceivedStatistics];
    resolve([self toIntDictionary(KEY_LOG_LEVEL, [NSNumber numberWithInt:logLevel])]);
}

RCT_EXPORT_METHOD(resetStatistics) {
    [MobileFFmpegConfig resetStatistics];
}

RCT_EXPORT_METHOD(setFontconfigConfigurationPath:(NSString*)path) {
    [MobileFFmpegConfig setFontDirectory:path];
}

RCT_EXPORT_METHOD(setFontDirectory:(NSString*)fontDirectoryPath) {
    [MobileFFmpegConfig setFontDirectory:fontDirectoryPath];
}

- (void)logCallback: (int)level :(NSString*)message {
    dispatch_async(dispatch_get_main_queue(), ^{
        NSMutableDictionary *dictionary = [[NSMutableDictionary alloc] init];

        dictionary[KEY_LOG_LEVEL] = [NSNumber numberWithInt:level];
        dictionary[KEY_LOG_TEXT] = message;

        [self emitLogMessage: dictionary];
    });
}

- (void)statisticsCallback:(Statistics *)statistics {
    dispatch_async(dispatch_get_main_queue(), ^{
        [self emitStatistics: statistics];
    });
}

- (NSArray *) toStringDictionary:(NSString*)key :(NSString*)value {
    NSMutableArray *array = [NSMutableArray array];
    
    NSMutableDictionary *dictionary = [[NSMutableDictionary alloc] init];
    dictionary[key] = value;
    [array addObject:dictionary];
    
    return array;
}

- (NSArray *) toIntDictionary:(NSString*)key :(NSNumber*)value {
    NSMutableArray *array = [NSMutableArray array];
    
    NSMutableDictionary *dictionary = [[NSMutableDictionary alloc] init];
    dictionary[key] = value;
    [array addObject:dictionary];
    
    return array;
}

- (NSArray *) toStatisticsDictionary:(Statistics*)statistics {
    NSMutableArray *array = [NSMutableArray array];
    
    NSMutableDictionary *dictionary = [[NSMutableDictionary alloc] init];

    if (statistics != nil) {
        dictionary[KEY_STAT_TIME] = [NSNumber numberWithInt: [statistics getTime]];
        dictionary[KEY_STAT_SIZE] = [NSNumber numberWithLong: [statistics getSize]];

        dictionary[KEY_STAT_BITRATE] = [NSNumber numberWithDouble: [statistics getBitrate]];
        dictionary[KEY_STAT_SPEED] = [NSNumber numberWithDouble: [statistics getSpeed]];
        
        dictionary[KEY_STAT_VIDEO_FRAME_NUMBER] = [NSNumber numberWithInt: [statistics getVideoFrameNumber]];
        dictionary[KEY_STAT_VIDEO_QUALITY] = [NSNumber numberWithFloat: [statistics getVideoQuality]];
        dictionary[KEY_STAT_VIDEO_FPS] = [NSNumber numberWithFloat: [statistics getVideoFps]];
    }
    
    [array addObject:dictionary];
    
    return array;
}

- (void) emitLogMessage:(NSDictionary*)logMessage{
    [self.bridge.eventDispatcher sendAppEventWithName:EVENT_LOG body:logMessage];
}

- (void) emitStatistics:(Statistics*)statistics{
    (NSDictionary*)dictionary = [self toStatistics:statistics]
    [self.bridge.eventDispatcher sendAppEventWithName:EVENT_STAT body:dictionary];
}

@end
  

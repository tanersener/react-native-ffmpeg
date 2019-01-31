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

#import <mobileffmpeg/MobileFFmpeg.h>
#import <mobileffmpeg/ArchDetect.h>
#import <mobileffmpeg/MediaInformation.h>

static NSString *const PLATFORM_NAME = @"ios";

static NSString *const KEY_VERSION = @"version";
static NSString *const KEY_RC = @"rc";
static NSString *const KEY_PLATFORM = @"platform";
static NSString *const KEY_PACKAGE_NAME = @"packageName";
static NSString *const KEY_LAST_RC = @"lastRc";
static NSString *const KEY_LAST_COMMAND_OUTPUT = @"lastCommandOutput";

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

RCT_EXPORT_MODULE(RNFFmpegModule);

- (NSArray<NSString*> *)supportedEvents {
    NSMutableArray *array = [NSMutableArray array];

    [array addObject:EVENT_LOG];
    [array addObject:EVENT_STAT];

    return array;
}

RCT_EXPORT_METHOD(getPlatform:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject) {
    NSString *architecture = [ArchDetect getArch];
    resolve([RNFFmpegModule toStringDictionary:KEY_PLATFORM :[NSString stringWithFormat:@"%@-%@", PLATFORM_NAME, architecture]]);
}

RCT_EXPORT_METHOD(getFFmpegVersion:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject) {
    NSString *ffmpegVersion = [MobileFFmpeg getFFmpegVersion];
    resolve([RNFFmpegModule toStringDictionary:KEY_VERSION :ffmpegVersion]);
}

RCT_EXPORT_METHOD(executeWithArguments:(NSArray*)arguments resolver:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject) {
    RCTLogInfo(@"Running FFmpeg with arguments: %@.\n", arguments);

    dispatch_async(dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_DEFAULT, 0), ^{
        int rc = [MobileFFmpeg executeWithArguments:arguments];

        RCTLogInfo(@"FFmpeg exited with rc: %d\n", rc);

        resolve([RNFFmpegModule toIntDictionary:KEY_RC :[NSNumber numberWithInt:rc]]);
    });
}

RCT_EXPORT_METHOD(execute:(NSString*)command delimiter:(NSString*)delimiter resolver:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject) {
    if (delimiter == nil) {
        delimiter = @" ";
    }

    RCTLogInfo(@"Running FFmpeg command: %@ with delimiter %@.\n", command, delimiter);

    dispatch_async(dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_DEFAULT, 0), ^{
        int rc = [MobileFFmpeg execute:command delimiter:delimiter];

        RCTLogInfo(@"FFmpeg exited with rc: %d\n", rc);

        resolve([RNFFmpegModule toIntDictionary:KEY_RC :[NSNumber numberWithInt:rc]]);
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
    resolve([RNFFmpegModule toIntDictionary:KEY_LOG_LEVEL :[NSNumber numberWithInt:logLevel]]);
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
    Statistics *statistics = [MobileFFmpegConfig getLastReceivedStatistics];
    resolve([RNFFmpegModule toStatisticsDictionary:statistics]);
}

RCT_EXPORT_METHOD(resetStatistics) {
    [MobileFFmpegConfig resetStatistics];
}

RCT_EXPORT_METHOD(setFontconfigConfigurationPath:(NSString*)path) {
    [MobileFFmpegConfig setFontconfigConfigurationPath:path];
}

RCT_EXPORT_METHOD(setFontDirectory:(NSString*)fontDirectoryPath with:(NSDictionary*)fontNameMap) {
    [MobileFFmpegConfig setFontDirectory:fontDirectoryPath with:fontNameMap];
}

RCT_EXPORT_METHOD(getPackageName:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject) {
    NSString *packageName = [MobileFFmpegConfig getPackageName];
    resolve([RNFFmpegModule toStringDictionary:KEY_PACKAGE_NAME :packageName]);
}

RCT_EXPORT_METHOD(getExternalLibraries:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject) {
    NSArray *externalLibraries = [MobileFFmpegConfig getExternalLibraries];
    resolve(externalLibraries);
}

RCT_EXPORT_METHOD(getLastReturnCode:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject) {
    int lastReturnCode = [MobileFFmpeg getLastReturnCode];
    resolve([RNFFmpegModule toIntDictionary:KEY_LAST_RC :[NSNumber numberWithInt:lastReturnCode]]);
}

RCT_EXPORT_METHOD(getLastCommandOutput:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject) {
    NSString *lastCommandOutput = [MobileFFmpeg getLastCommandOutput];
    resolve([RNFFmpegModule toStringDictionary:KEY_LAST_COMMAND_OUTPUT :lastCommandOutput]);
}

RCT_EXPORT_METHOD(getMediaInformation:(NSString*)path timeout:(NSNumber*_Nonnull)timeout resolver:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject) {
    RCTLogInfo(@"Getting media information for %@ with timeout %d.\n", path, [timeout intValue]);

    dispatch_async(dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_DEFAULT, 0), ^{
        MediaInformation *mediaInformation = [MobileFFmpeg getMediaInformation:path timeout:[timeout longLongValue]];
        resolve([RNFFmpegModule toMediaInformationDictionary:mediaInformation]);
    });
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

- (void)emitLogMessage:(NSDictionary*)logMessage{
    [self sendEventWithName:EVENT_LOG body:logMessage];
}

- (void)emitStatistics:(Statistics*)statistics{
    NSDictionary *dictionary = [RNFFmpegModule toStatisticsDictionary:statistics];
    [self sendEventWithName:EVENT_STAT body:dictionary];
}

+ (NSDictionary *)toStringDictionary:(NSString*)key :(NSString*)value {
    NSMutableDictionary *dictionary = [[NSMutableDictionary alloc] init];
    dictionary[key] = value;

    return dictionary;
}

+ (NSDictionary *)toIntDictionary:(NSString*)key :(NSNumber*)value {
    NSMutableDictionary *dictionary = [[NSMutableDictionary alloc] init];
    dictionary[key] = value;

    return dictionary;
}

+ (NSDictionary *)toStatisticsDictionary:(Statistics*)statistics {
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

    return dictionary;
}

+ (NSDictionary *)toMediaInformationDictionary:(MediaInformation*)mediaInformation {
    NSMutableDictionary *dictionary = [[NSMutableDictionary alloc] init];

    if (mediaInformation != nil) {
        if ([mediaInformation getFormat] != nil) {
            dictionary[@"format"] =  [mediaInformation getFormat];
        }
        if ([mediaInformation getPath] != nil) {
            dictionary[@"path"] = [mediaInformation getPath];
        }
        if ([mediaInformation getStartTime] != nil) {
            dictionary[@"startTime"] = [mediaInformation getStartTime];
        }
        if ([mediaInformation getDuration] != nil) {
            dictionary[@"duration"] = [mediaInformation getDuration];
        }
        if ([mediaInformation getBitrate] != nil) {
            dictionary[@"bitrate"] = [mediaInformation getBitrate];
        }
        if ([mediaInformation getRawInformation] != nil) {
            dictionary[@"rawInformation"] = [mediaInformation getRawInformation];
        }

        NSDictionary *metadataDictionary = [mediaInformation getMetadataEntries];
        if (metadataDictionary != nil && ([metadataDictionary count] > 0)) {
            dictionary[@"metadata"] = metadataDictionary;
        }

        NSArray *streams = [mediaInformation getStreams];
        if (streams != nil && ([streams count] > 0)) {
            NSMutableArray *array = [[NSMutableArray alloc] init];

            for (int i=0; i < [streams count]; i++) {
                StreamInformation *streamInformation= [streams objectAtIndex:i];
                [array addObject: [RNFFmpegModule toStreamInformationDictionary:streamInformation]];
            }

            dictionary[@"streams"] = array;
        }
    }

    return dictionary;
}

+ (NSDictionary *)toStreamInformationDictionary:(StreamInformation*)streamInformation {
    NSMutableDictionary *dictionary = [[NSMutableDictionary alloc] init];

    if (streamInformation != nil) {
        if ([streamInformation getIndex] != nil) {
            dictionary[@"index"] = [streamInformation getIndex];
        }
        if ([streamInformation getType] != nil) {
            dictionary[@"type"] = [streamInformation getType];
        }
        if ([streamInformation getCodec] != nil) {
            dictionary[@"codec"] = [streamInformation getCodec];
        }
        if ([streamInformation getFullCodec] != nil) {
            dictionary[@"fullCodec"] = [streamInformation getFullCodec];
        }
        if ([streamInformation getFormat] != nil) {
            dictionary[@"format"] = [streamInformation getFormat];
        }
        if ([streamInformation getFullFormat] != nil) {
            dictionary[@"fullFormat"] = [streamInformation getFullFormat];
        }
        if ([streamInformation getWidth] != nil) {
            dictionary[@"width"] = [streamInformation getWidth];
        }
        if ([streamInformation getHeight] != nil) {
            dictionary[@"height"] = [streamInformation getHeight];
        }
        if ([streamInformation getBitrate] != nil) {
            dictionary[@"bitrate"] = [streamInformation getBitrate];
        }
        if ([streamInformation getSampleRate] != nil) {
            dictionary[@"sampleRate"] = [streamInformation getSampleRate];
        }
        if ([streamInformation getSampleFormat] != nil) {
            dictionary[@"sampleFormat"] = [streamInformation getSampleFormat];
        }
        if ([streamInformation getChannelLayout] != nil) {
            dictionary[@"channelLayout"] = [streamInformation getChannelLayout];
        }
        if ([streamInformation getSampleAspectRatio] != nil) {
            dictionary[@"sampleAspectRatio"] = [streamInformation getSampleAspectRatio];
        }
        if ([streamInformation getDisplayAspectRatio] != nil) {
            dictionary[@"displayAspectRatio"] = [streamInformation getDisplayAspectRatio];
        }
        if ([streamInformation getAverageFrameRate] != nil) {
            dictionary[@"averageFrameRate"] = [streamInformation getAverageFrameRate];
        }
        if ([streamInformation getRealFrameRate] != nil) {
            dictionary[@"realFrameRate"] = [streamInformation getRealFrameRate];
        }
        if ([streamInformation getTimeBase] != nil) {
            dictionary[@"timeBase"] = [streamInformation getTimeBase];
        }
        if ([streamInformation getCodecTimeBase] != nil) {
            dictionary[@"codecTimeBase"] = [streamInformation getCodecTimeBase];
        }

        NSDictionary *metadataDictionary = [streamInformation getMetadataEntries];
        if (metadataDictionary != nil && ([metadataDictionary count] > 0)) {
            dictionary[@"metadata"] = metadataDictionary;
        }
    }

    return dictionary;
}

@end

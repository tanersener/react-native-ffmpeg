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

#import "RNExecuteDelegate.h"
#import "RNFFmpegModule.h"
#import <React/RCTLog.h>
#import <React/RCTBridge.h>
#import <React/RCTEventDispatcher.h>

#import <mobileffmpeg/MobileFFmpeg.h>
#import <mobileffmpeg/MobileFFprobe.h>
#import <mobileffmpeg/ArchDetect.h>
#import <mobileffmpeg/MediaInformation.h>

static NSString *const PLATFORM_NAME = @"ios";

static NSString *const KEY_LOG_EXECUTION_ID = @"executionId";
static NSString *const KEY_LOG_MESSAGE = @"message";
static NSString *const KEY_LOG_LEVEL = @"level";

static NSString *const KEY_STAT_EXECUTION_ID = @"executionId";
static NSString *const KEY_STAT_TIME = @"time";
static NSString *const KEY_STAT_SIZE = @"size";
static NSString *const KEY_STAT_BITRATE = @"bitrate";
static NSString *const KEY_STAT_SPEED = @"speed";
static NSString *const KEY_STAT_VIDEO_FRAME_NUMBER = @"videoFrameNumber";
static NSString *const KEY_STAT_VIDEO_QUALITY = @"videoQuality";
static NSString *const KEY_STAT_VIDEO_FPS = @"videoFps";

static NSString *const KEY_EXECUTION_ID = @"executionId";
static NSString *const KEY_EXECUTION_START_TIME = @"startTime";
static NSString *const KEY_EXECUTION_COMMAND = @"command";

static NSString *const EVENT_LOG = @"RNFFmpegLogCallback";
static NSString *const EVENT_STAT = @"RNFFmpegStatisticsCallback";
static NSString *const EVENT_EXECUTE = @"RNFFmpegExecuteCallback";

@implementation RNFFmpegModule

RCT_EXPORT_MODULE(RNFFmpegModule);

- (NSArray<NSString*> *)supportedEvents {
    NSMutableArray *array = [NSMutableArray array];

    [array addObject:EVENT_LOG];
    [array addObject:EVENT_STAT];
    [array addObject:EVENT_EXECUTE];

    return array;
}

RCT_EXPORT_METHOD(getPlatform:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject) {
    NSString *architecture = [ArchDetect getArch];
    resolve([NSString stringWithFormat:@"%@-%@", PLATFORM_NAME, architecture]);
}

RCT_EXPORT_METHOD(getFFmpegVersion:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject) {
    NSString *ffmpegVersion = [MobileFFmpegConfig getFFmpegVersion];
    resolve(ffmpegVersion);
}

RCT_EXPORT_METHOD(executeFFmpegWithArguments:(NSArray*)arguments resolver:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject) {
    dispatch_async(dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_DEFAULT, 0), ^{
        int rc = [MobileFFmpeg executeWithArguments:arguments];
        resolve([NSNumber numberWithInt:rc]);
    });
}

RCT_EXPORT_METHOD(executeFFmpegAsyncWithArguments:(NSArray*)arguments resolver:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject) {
    RNExecuteDelegate* executeDelegate = [[RNExecuteDelegate alloc] initWithEventEmitter:self];
    long executionId = [MobileFFmpeg executeWithArgumentsAsync:arguments withCallback:executeDelegate];
    resolve([NSNumber numberWithLong:executionId]);
}

RCT_EXPORT_METHOD(executeFFprobeWithArguments:(NSArray*)arguments resolver:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject) {
    dispatch_async(dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_DEFAULT, 0), ^{
        int rc = [MobileFFprobe executeWithArguments:arguments];
        resolve([NSNumber numberWithInt:rc]);
    });
}

RCT_EXPORT_METHOD(cancel) {
    [MobileFFmpeg cancel];
}

RCT_EXPORT_METHOD(cancelExecution:(int)executionId) {
    [MobileFFmpeg cancel:executionId];
}

RCT_EXPORT_METHOD(enableRedirection) {
    [MobileFFmpegConfig enableRedirection];
}

RCT_EXPORT_METHOD(disableRedirection) {
    [MobileFFmpegConfig disableRedirection];
}

RCT_EXPORT_METHOD(getLogLevel:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject) {
    int logLevel = [MobileFFmpegConfig getLogLevel];
    resolve([NSNumber numberWithInt:logLevel]);
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
    resolve(packageName);
}

RCT_EXPORT_METHOD(getExternalLibraries:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject) {
    NSArray *externalLibraries = [MobileFFmpegConfig getExternalLibraries];
    resolve(externalLibraries);
}

RCT_EXPORT_METHOD(getLastReturnCode:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject) {
    int lastReturnCode = [MobileFFmpegConfig getLastReturnCode];
    resolve([NSNumber numberWithInt:lastReturnCode]);
}

RCT_EXPORT_METHOD(getLastCommandOutput:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject) {
    NSString *lastCommandOutput = [MobileFFmpegConfig getLastCommandOutput];
    resolve(lastCommandOutput);
}

RCT_EXPORT_METHOD(getMediaInformation:(NSString*)path resolver:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject) {
    dispatch_async(dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_DEFAULT, 0), ^{
        MediaInformation *mediaInformation = [MobileFFprobe getMediaInformation:path];
        resolve([RNFFmpegModule toMediaInformationDictionary:mediaInformation]);
    });
}

RCT_EXPORT_METHOD(registerNewFFmpegPipe:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject) {
    NSString *pipe = [MobileFFmpegConfig registerNewFFmpegPipe];
    resolve(pipe);
}

RCT_EXPORT_METHOD(setEnvironmentVariable:(NSString*)variableName with:(NSString*)variableValue) {
    setenv([variableName UTF8String], [variableValue UTF8String], true);
}

RCT_EXPORT_METHOD(listExecutions:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject) {
    NSArray* executionsArray = [RNFFmpegModule toExecutionsArray:[MobileFFmpeg listExecutions]];
    resolve(executionsArray);
}

RCT_EXPORT_METHOD(writeToPipe:(NSString*)inputPath onPipe:(NSString*)namedPipePath:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject) {
    dispatch_async(dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_DEFAULT, 0), ^{

        NSLog(@"Starting copy %@ to pipe %@ operation.\n", inputPath, namedPipePath);

        NSFileHandle *fileHandle = [NSFileHandle fileHandleForReadingAtPath: inputPath];
        if (fileHandle == nil) {
            NSLog(@"Failed to open file %@.\n", inputPath);
            reject(@"Copy failed", [NSString stringWithFormat:@"Failed to open file %@.", inputPath], nil);
            return;
        }

        NSFileHandle *pipeHandle = [NSFileHandle fileHandleForWritingAtPath: namedPipePath];
        if (pipeHandle == nil) {
            NSLog(@"Failed to open pipe %@.\n", namedPipePath);
            reject(@"Copy failed", [NSString stringWithFormat:@"Failed to open pipe %@.", namedPipePath], nil);
            [fileHandle closeFile];
            return;
        }

        int BUFFER_SIZE = 4096;
        unsigned long readBytes = 0;
        unsigned long totalBytes = 0;
        double startTime = CACurrentMediaTime();

        @try {
            [fileHandle seekToFileOffset: 0];

            do {
                NSData *data = [fileHandle readDataOfLength:BUFFER_SIZE];
                readBytes = [data length];
                if (readBytes > 0) {
                    totalBytes += readBytes;
                    [pipeHandle writeData:data];
                }
            } while (readBytes > 0);

            double endTime = CACurrentMediaTime();

            NSLog(@"Completed copy %@ to pipe %@ operation. %lu bytes copied in %f seconds.\n", inputPath, namedPipePath, totalBytes, (endTime - startTime));

            resolve(0);

        } @catch (NSException *e) {
            NSLog(@"Copy failed %@.\n", [e reason]);
            reject(@"Copy failed", [NSString stringWithFormat:@"Copy %@ to %@ failed with error.", inputPath, namedPipePath], e);
        } @finally {
            [fileHandle closeFile];
            [pipeHandle closeFile];
        }
    });
}

- (void)logCallback:(long)executionId :(int)level :(NSString*)message {
    dispatch_async(dispatch_get_main_queue(), ^{
        NSMutableDictionary *dictionary = [[NSMutableDictionary alloc] init];
        dictionary[KEY_LOG_EXECUTION_ID] = [NSNumber numberWithLong:executionId];
        dictionary[KEY_LOG_LEVEL] = [NSNumber numberWithInt:level];
        dictionary[KEY_LOG_MESSAGE] = message;

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

+ (NSDictionary *)toStatisticsDictionary:(Statistics*)statistics {
    NSMutableDictionary *dictionary = [[NSMutableDictionary alloc] init];

    if (statistics != nil) {
        dictionary[KEY_STAT_EXECUTION_ID] = [NSNumber numberWithLong: [statistics getExecutionId]];

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

+ (NSArray *)toExecutionsArray:(NSArray*)ffmpegExecutions {
    NSMutableArray *executions = [[NSMutableArray alloc] init];

    for (int i = 0; i < [ffmpegExecutions count]; i++) {
        FFmpegExecution* execution = [ffmpegExecutions objectAtIndex:i];

        NSMutableDictionary *executionDictionary = [[NSMutableDictionary alloc] init];
        executionDictionary[KEY_EXECUTION_ID] = [NSNumber numberWithLong: [execution getExecutionId]];
        executionDictionary[KEY_EXECUTION_START_TIME] = [NSNumber numberWithDouble:[[execution getStartTime] timeIntervalSince1970]*1000];
        executionDictionary[KEY_EXECUTION_COMMAND] = [execution getCommand];

        [executions addObject: executionDictionary];
    }

    return executions;
}

+ (NSDictionary *)toMediaInformationDictionary:(MediaInformation*)mediaInformation {
    NSMutableDictionary *dictionary = [[NSMutableDictionary alloc] init];

    if (mediaInformation != nil) {
        NSDictionary* allProperties = [mediaInformation getAllProperties];
        if (allProperties != nil) {
            for(NSString *key in [allProperties allKeys]) {
                dictionary[key] = [allProperties objectForKey:key];
            }
        }
    }

    return dictionary;
}

@end

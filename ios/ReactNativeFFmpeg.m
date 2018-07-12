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

#import "ReactNativeFFmpeg.h"
#import <mobileffmpeg/mobileffmpeg.h>
#import <mobileffmpeg/archdetect.h>

@implementation ReactNativeFFmpeg

- (dispatch_queue_t)methodQueue
{
    return dispatch_get_main_queue();
}

RCT_EXPORT_MODULE(ReactNativeFFmpegModule)

RCT_EXPORT_METHOD(getFFmpegVersion: resolver:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject) {
    NSString *ffmpegVersion = [NSString stringWithUTF8String:mobileffmpeg_get_ffmpeg_version()];
    resolve(ffmpegVersion);
}

RCT_EXPORT_METHOD(execute: (NSArray*)commandArray resolver:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject) {

    NSMutableString* command = [NSMutableString stringWithString:@""];
    char **arguments = (char **)malloc(sizeof(char*) * ([commandArray count]));
    for (int i=0; i < [commandArray count]; i++) {
        NSString *argument = [commandArray objectAtIndex:i];
        arguments[i] = (char *) [argument UTF8String];
        
        [command appendString:@" "];
        [command appendString:argument];
    }
    
    RCTLogInfo(@"Running synchronously with arguments '%@'\n", command);
    
    // EXECUTING
    int result = mobileffmpeg_execute((int) [commandArray count], arguments);
    
    RCTLogInfo(@"Process exited with rc %d\n", result);
    
    // CLEANING ARGUMENTS
    free(arguments);
    
    resolve([NSNumber numberWithInt:result]);
}

RCT_EXPORT_METHOD(getPlatform: resolver:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject) {
    NSString *platform = [NSString stringWithUTF8String:mobileffmpeg_get_arch()];
    resolve(platform);
}

@end
  

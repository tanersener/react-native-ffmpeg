/*
 * Copyright (c) 2020 Taner Sener
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

static NSString *const EVENT_EXECUTE = @"RNFFmpegExecuteCallback";

/**
 * Execute delegate for async executions.
 */
@implementation RNExecuteDelegate {
    RCTEventEmitter* _eventEmitter;
}

- (instancetype)initWithEventEmitter:(RCTEventEmitter*)eventEmitter {
    self = [super init];
    if (self) {
        _eventEmitter = eventEmitter;
    }

    return self;
}

- (void)executeCallback:(long)executionId :(int)returnCode {
    NSMutableDictionary *executeDictionary = [[NSMutableDictionary alloc] init];
    executeDictionary[@"executionId"] = [NSNumber numberWithLong: executionId];
    executeDictionary[@"returnCode"] = [NSNumber numberWithInt: returnCode];

    [_eventEmitter sendEventWithName:EVENT_EXECUTE body:executeDictionary];
}

@end

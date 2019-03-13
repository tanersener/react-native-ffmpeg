declare module 'react-native-ffmpeg' {

    export class LogLevel {
        static AV_LOG_QUIET: number;
        static AV_LOG_PANIC: number;
        static AV_LOG_FATAL: number;
        static AV_LOG_ERROR: number;
        static AV_LOG_WARNING: number;
        static AV_LOG_INFO: number;
        static AV_LOG_VERBOSE: number;
        static AV_LOG_DEBUG: number;
        static AV_LOG_TRACE: number;
    }

    export interface LogMessage {
        level: LogLevel;
        log: string;
    }

    export interface Statistics {
        videoFrameNumber: number;
        videoFps: number;
        videoQuality: number;
        size: number;
        time: number;
        bitrate: number;
        speed: number;
    }

    export interface StreamInformation {
        index?: number;
        type?: string;
        codec?: string;
        fullCodec?: string;
        format?: string;
        fullFormat?: string;
        width?: number;
        height?: number;
        bitrate?: number;
        sampleRate?: number;
        sampleFormat?: string;
        channelLayout?: string;
        sampleAspectRatio?: string;
        displayAspectRatio?: string;
        averageFrameRate?: string;
        realFrameRate?: string;
        timeBase?: string;
        codecTimeBase?: string;
        metadata?: [string, string];
    }

    export interface MediaInformation {
        format?: string;
        path?: string
        duration?: number;
        startTime?: number;
        bitrate?: number;
        metadata?: [string, string];
        streams?: StreamInformation[];
        rawInformation?: string;
    }

    export class RNFFmpeg {
        static getFFmpegVersion(): Promise<{version: string}>;
        static getPlatform(): Promise<{platform: string}>;
        static executeWithArguments(arguments: string[]): Promise<{rc: number}>;
        static execute(command: string, delimiter: string): Promise<{rc: number}>;
        static cancel(): void;
        static disableRedirection(): void;
        static getLogLevel(): Promise<{level: number}>;
        static setLogLevel(level: number): void;
        static disableLogs(): void;
        static disableStatistics(): void;
        static enableLogCallback(newCallback: (log: LogMessage) => void): void;
        static enableStatisticsCallback(newCallback: (statistics: Statistics) => void): void;
        static getLastReceivedStatistics(): Promise<Statistics>;
        static resetStatistics(): void;
        static setFontconfigConfigurationPath(path: string): void;
        static setFontDirectory(path: string, mapping?: { [key: string]: string }): void;
        static getPackageName(): Promise<{packageName: string}>;
        static getExternalLibraries(): Promise<string[]>;
        static getLastReturnCode(): Promise<{lastRc: number}>;
        static getLastCommandOutput(): Promise<{lastCommandOutput: string}>;
        static getMediaInformation(path: string, timeout?: number): Promise<MediaInformation>;
    }

}

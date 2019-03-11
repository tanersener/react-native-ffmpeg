declare module 'react-native-ffmpeg' {

    enum Level {
        AV_LOG_QUIET = -8,
        AV_LOG_PANIC = 0,
        AV_LOG_FATAL = 8,
        AV_LOG_ERROR = 16,
        AV_LOG_WARNING = 24,
        AV_LOG_INFO = 32,
        AV_LOG_VERBOSE = 40,
        AV_LOG_DEBUG = 48,
        AV_LOG_TRACE = 56
    }

    export interface LogMessage {
        level: Level;
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
        getFFmpegVersion(): Promise<[string, string]>;
        getPlatform(): Promise<[string, string]>;
        executeWithArguments(arguments: string[]): Promise<[string, string]>;
        execute(command: string, delimiter: string): Promise<[string, string]>;
        cancel(): void;
        disableRedirection(): void;
        getLogLevel(): Promise<[string, string]>;
        setLogLevel(level: number): void;
        disableLogs(): void;
        disableStatistics(): void;
        enableLogCallback(newCallback: (log: LogMessage) => void): void;
        enableStatisticsCallback(newCallback: (statistics: Statistics) => void): void;
        getLastReceivedStatistics(): Promise<Statistics>;
        resetStatistics(): void;
        setFontconfigConfigurationPath(path: string): void;
        setFontDirectory(path: string, mapping: [string, string]): void;
        getPackageName(): Promise<[string, string]>;
        getExternalLibraries(): Promise<string[]>;
        getLastReturnCode(): Promise<[string, string]>;
        getLastCommandOutput(): Promise<[string, string]>;
        getMediaInformation(path: string, timeout: number): Promise<MediaInformation>;
    }
}

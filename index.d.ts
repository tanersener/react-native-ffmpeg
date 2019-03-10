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
        text: string;
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

    export function getFFmpegVersion(): Promise<[string, string]>;
    export function getPlatform(): Promise<[string, string]>;
    export function executeWithArguments(arguments: string[]): Promise<[string, string]>;
    export function execute(command: string, delimiter: string): Promise<[string, string]>;
    export function cancel(): void;
    export function disableRedirection(): void;
    export function getLogLevel(): Promise<[string, string]>;
    export function setLogLevel(level: number): void;
    export function disableLogs(): void;
    export function disableStatistics(): void;
    export function enableLogCallback(newCallback:(log: LogMessage) => void): void;
    export function enableStatisticsCallback(newCallback:(statistics: Statistics) => void): void;
    export function getLastReceivedStatistics(): Promise<Statistics>;
    export function resetStatistics(): void;
    export function setFontconfigConfigurationPath(path: string): void;
    export function setFontDirectory(path: string, mapping: [string, string]): void;
    export function getPackageName(): Promise<[string, string]>;
    export function getExternalLibraries(): Promise<string[]>;
    export function getLastReturnCode(): Promise<[string, string]>;
    export function getLastCommandOutput(): Promise<[string, string]>;
    export function getMediaInformation(path: string, timeout: number): Promise<MediaInformation>;

}

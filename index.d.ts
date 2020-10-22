declare module 'react-native-ffmpeg' {

    export class LogLevel {
        static AV_LOG_STDERR: number;
        static AV_LOG_QUIET: number;
        static AV_LOG_PANIC: number;
        static AV_LOG_FATAL: number;
        static AV_LOG_ERROR: number;
        static AV_LOG_WARNING: number;
        static AV_LOG_INFO: number;
        static AV_LOG_VERBOSE: number;
        static AV_LOG_DEBUG: number;
        static AV_LOG_TRACE: number;

        static logLevelToString(number): string;
    }

    export interface Log {
        executionId: number;
        level: LogLevel;
        message: string;
    }

    export interface Statistics {
        executionId: number;
        videoFrameNumber: number;
        videoFps: number;
        videoQuality: number;
        size: number;
        time: number;
        bitrate: number;
        speed: number;
    }

    export interface Execution {
        executionId: number;
        startTime: number;
        command: string;
    }

    export interface CompletedExecution {
        executionId: number;
        returnCode: number;
    }

    export class StreamInformation {
        getAllProperties(): Record<string, string>;
    }

    export class MediaInformation {
        getStreams(): StreamInformation[];

        getMediaProperties(): Record<string, string>;

        getAllProperties(): Record<string, string>;
    }

    export class RNFFmpegConfig {
        static getFFmpegVersion(): Promise<string>;

        static getPlatform(): Promise<string>;

        static disableRedirection(): void;

        static getLogLevel(): Promise<number>;

        static setLogLevel(level: number): void;

        static disableLogs(): void;

        static disableStatistics(): void;

        static enableLogCallback(newCallback: (log: Log) => void): void;

        static enableStatisticsCallback(newCallback: (statistics: Statistics) => void): void;

        static getLastReceivedStatistics(): Promise<Statistics>;

        static resetStatistics(): void;

        static setFontconfigConfigurationPath(path: string): void;

        static setFontDirectory(path: string, mapping?: { [key: string]: string }): void;

        static getPackageName(): Promise<string>;

        static getExternalLibraries(): Promise<string[]>;

        static getLastReturnCode(): Promise<number>;

        static getLastCommandOutput(): Promise<string>;

        static registerNewFFmpegPipe(): Promise<string>;

        static setEnvironmentVariable(name: string, value: string);

        static writeToPipe(inputPath: string, pipePath: string): Promise<number>;
    }

    export class RNFFmpeg {
        static executeWithArguments(arguments: string[]): Promise<number>;

        static execute(command: string): Promise<number>;

        static executeAsyncWithArguments(arguments: string[], callback: (execution: CompletedExecution) => void): Promise<number>;

        static executeAsync(command: string, callback: (execution: CompletedExecution) => void): Promise<number>;

        static cancel(): void;

        static cancelExecution(executionId: number): void;

        static listExecutions(): Promise<Execution[]>;

        static parseArguments(command: string): string[];
    }

    export class RNFFprobe {
        static executeWithArguments(arguments: string[]): Promise<number>;

        static execute(command: string): Promise<number>;

        static getMediaInformation(path: string): Promise<MediaInformation>;
    }

}

import {NativeEventEmitter, NativeModules} from 'react-native';

const {RNFFmpegModule} = NativeModules;

const eventLog = "RNFFmpegLogCallback";
const eventStatistics = "RNFFmpegStatisticsCallback";
const eventExecute = "RNFFmpegExecuteCallback";
const executeCallbackMap = new Map()

class LogLevel {

    /**
     * This log level is used to specify logs printed to stderr by ffmpeg.
     * Logs that has this level are not filtered and always redirected.
     *
     * @since 0.4.1
     */
    static AV_LOG_STDERR = -16;

    /**
     * Print no output.
     */
    static AV_LOG_QUIET = -8;

    /**
     * Something went really wrong and we will crash now.
     */
    static AV_LOG_PANIC = 0;

    /**
     * Something went wrong and recovery is not possible.
     * For example, no header was found for a format which depends
     * on headers or an illegal combination of parameters is used.
     */
    static AV_LOG_FATAL = 8;

    /**
     * Something went wrong and cannot losslessly be recovered.
     * However, not all future data is affected.
     */
    static AV_LOG_ERROR = 16;

    /**
     * Something somehow does not look correct. This may or may not
     * lead to problems. An example would be the use of '-vstrict -2'.
     */
    static AV_LOG_WARNING = 24;

    /**
     * Standard information.
     */
    static AV_LOG_INFO = 32;

    /**
     * Detailed information.
     */
    static AV_LOG_VERBOSE = 40;

    /**
     * Stuff which is only useful for libav* developers.
     */
    static AV_LOG_DEBUG = 48;

    /**
     * Extremely verbose debugging, useful for libav* development.
     */
    static AV_LOG_TRACE = 56;

    /**
     * Returns log level string.
     *
     * @param level log level integer
     * @returns log level string
     */
    static logLevelToString(level) {
        switch (level) {
            case LogLevel.AV_LOG_TRACE:
                return "TRACE";
            case LogLevel.AV_LOG_DEBUG:
                return "DEBUG";
            case LogLevel.AV_LOG_VERBOSE:
                return "VERBOSE";
            case LogLevel.AV_LOG_INFO:
                return "INFO";
            case LogLevel.AV_LOG_WARNING:
                return "WARNING";
            case LogLevel.AV_LOG_ERROR:
                return "ERROR";
            case LogLevel.AV_LOG_FATAL:
                return "FATAL";
            case LogLevel.AV_LOG_PANIC:
                return "PANIC";
            case LogLevel.AV_LOG_STDERR:
                return "STDERR";
            case LogLevel.AV_LOG_QUIET:
            default:
                return "";
        }
    }

}

/**
 * This class is used to configure ReactNativeFFmpeg library utilities/tools.
 *
 * @author Taner Sener
 * @since v0.4.1
 */
class ReactNativeFFmpegConfig {

    constructor() {
        const reactNativeFFmpegModuleEvents = new NativeEventEmitter(RNFFmpegModule);
        reactNativeFFmpegModuleEvents.addListener(eventLog, log => {
            if (this.logCallback === undefined) {
                console.log(log.message);
            } else {
                this.logCallback(log);
            }
        });
        reactNativeFFmpegModuleEvents.addListener(eventStatistics, statistics => {
            if (this.statisticsCallback !== undefined) {
                this.statisticsCallback(statistics);
            }
        });
        reactNativeFFmpegModuleEvents.addListener(eventExecute, completedExecution => {
            let executeCallback = executeCallbackMap.get(completedExecution.executionId);
            if (executeCallback !== undefined) {
                executeCallback(completedExecution);
            } else {
                console.log(`Async execution with id ${completedExecution.executionId} completed but no callback is found for it.`);
            }
        });

        console.log("Loading react-native-ffmpeg.");

        RNFFmpegModule.enableLogEvents();
        RNFFmpegModule.enableStatisticsEvents();
        RNFFmpegModule.enableRedirection();

        this.getPlatform().then(platform => {
            console.log(`Loaded react-native-ffmpeg-${platform}.`);
        });
    }

    /**
     * Returns FFmpeg version bundled within the library.
     *
     * @returns FFmpeg version
     */
    getFFmpegVersion() {
        return RNFFmpegModule.getFFmpegVersion();
    }

    /**
     * Returns platform name where library is loaded.
     *
     * @returns platform name
     */
    getPlatform() {
        return RNFFmpegModule.getPlatform();
    }

    /**
     * Disables log and statistics redirection. By default redirection is enabled in constructor.
     * When redirection is enabled FFmpeg logs are printed to console and can be routed further to a callback function.
     * By disabling redirection, logs are redirected to stderr.
     * Statistics redirection behaviour is similar. Statistics are not printed at all if redirection is not enabled.
     * If it is enabled then it is possible to define a statistics callback function but if you don't, they are not
     * printed anywhere and only saved as <code>lastReceivedStatistics</code> data which can be polled with
     * {@link #getLastReceivedStatistics()}.
     */
    disableRedirection() {
        RNFFmpegModule.disableRedirection();
    }

    /**
     * Returns log level.
     *
     * @returns log level
     */
    getLogLevel() {
        return RNFFmpegModule.getLogLevel();
    }

    /**
     * Sets log level.
     *
     * @param logLevel log level
     */
    setLogLevel(logLevel) {
        RNFFmpegModule.setLogLevel(logLevel);
    }

    /**
     * Disables log functionality of the library. Logs will not be printed to console and log callback will be disabled.
     * Note that log functionality is enabled by default.
     */
    disableLogs() {
        RNFFmpegModule.disableLogEvents();
    }

    /**
     * Disables statistics functionality of the library. Statistics callback will be disabled but the last received
     * statistics data will be still available.
     * Note that statistics functionality is enabled by default.
     */
    disableStatistics() {
        RNFFmpegModule.disableStatisticsEvents();
    }

    /**
     * Sets a callback to redirect FFmpeg logs.
     *
     * @param newCallback new log callback function or undefined to disable a previously defined callback
     */
    enableLogCallback(newCallback) {
        this.logCallback = newCallback;
    }

    /**
     * Sets a callback function to redirect FFmpeg statistics.
     *
     * @param newCallback new statistics callback function or undefined to disable a previously defined callback
     */
    enableStatisticsCallback(newCallback) {
        this.statisticsCallback = newCallback;
    }

    /**
     * Returns the last received statistics data.
     *
     * @returns last received statistics data stored in bitrate, size, speed, time, videoFps, videoFrameNumber and
     * videoQuality fields
     */
    getLastReceivedStatistics() {
        return RNFFmpegModule.getLastReceivedStatistics();
    }

    /**
     * Resets last received statistics. It is recommended to call it before starting a new execution.
     */
    resetStatistics() {
        RNFFmpegModule.resetStatistics();
    }

    /**
     * Sets and overrides fontconfig configuration directory.
     *
     * @param path directory which contains fontconfig configuration (fonts.conf)
     */
    setFontconfigConfigurationPath(path) {
        RNFFmpegModule.setFontconfigConfigurationPath(path);
    }

    /**
     * Registers fonts inside the given path, so they are available to use in FFmpeg filters.
     *
     * @param path which contains fonts (.ttf, .otf files)
     * @param mapping font name mapping like {"my_easy_font_name":"my complex font name"}
     */
    setFontDirectory(path, mapping) {
        RNFFmpegModule.setFontDirectory(path, mapping);
    }

    /**
     * Returns MobileFFmpeg package name.
     *
     * @return guessed package name according to supported external libraries
     */
    getPackageName() {
        return RNFFmpegModule.getPackageName();
    }

    /**
     * Returns supported external libraries.
     *
     * @return list of supported external libraries
     */
    getExternalLibraries() {
        return RNFFmpegModule.getExternalLibraries();
    }

    /**
     * Returns return code of last executed command.
     *
     * @return return code of last executed command
     */
    getLastReturnCode() {
        return RNFFmpegModule.getLastReturnCode();
    }

    /**
     * <p>Returns log output of last executed command.
     * <p>This method does not support executing multiple concurrent commands. If you execute multiple commands at the same time, this method will return output from all executions.
     * <p>Please note that disabling redirection using disableRedirection() method also disables this functionality.
     *
     * @return output of last executed command
     */
    getLastCommandOutput() {
        return RNFFmpegModule.getLastCommandOutput();
    }

    /**
     * Creates a new FFmpeg pipe.
     *
     * @return the path of created FFmpeg pipe. A pipe can be read/written like a file
     */
    registerNewFFmpegPipe() {
        return RNFFmpegModule.registerNewFFmpegPipe();
    }

    /**
     * Sets an environment variable
     *
     * @param name environment variable name
     * @param value environment variable value
     */
    setEnvironmentVariable(name, value) {
        return RNFFmpegModule.setEnvironmentVariable(name, value);
    }

    /**
     * Writes input file path to the pipe path.
     *
     * @param inputPath input file path
     * @param pipePath pipe path
     */
    writeToPipe(inputPath, pipePath) {
        return RNFFmpegModule.writeToPipe(inputPath, pipePath);
    }

}

/**
 * Main class for FFmpeg operations. Provides execute() method to execute FFmpeg commands.
 *
 * @author Taner Sener
 * @since v0.4.1
 */
class ReactNativeFFmpeg {

    /**
     * Executes FFmpeg with arguments provided.
     *
     * @param commandArguments FFmpeg command options/arguments as string array
     * @returns return code
     */
    executeWithArguments(commandArguments) {
        return RNFFmpegModule.executeFFmpegWithArguments(commandArguments);
    }

    /**
     * Executes FFmpeg command provided.
     *
     * @param command FFmpeg command
     * @returns return code
     */
    execute(command) {
        return RNFFmpegModule.executeFFmpegWithArguments(this.parseArguments(command));
    }

    /**
     * Asynchronously executes FFmpeg with arguments provided.
     *
     * @param commandArguments FFmpeg command options/arguments as string array
     * @param executeCallback callback to receive execution result
     * @returns returns a unique id that represents this execution
     */
    async executeAsyncWithArguments(commandArguments, executeCallback) {
        let executionId = await RNFFmpegModule.executeFFmpegAsyncWithArguments(commandArguments);
        executeCallbackMap.set(executionId, executeCallback);
        return executionId;
    }

    /**
     * Asynchronously executes FFmpeg command provided.
     *
     * @param command FFmpeg command
     * @param executeCallback callback to receive execution result
     * @returns returns a unique id that represents this execution
     */
    async executeAsync(command, executeCallback) {
        let executionId = await RNFFmpegModule.executeFFmpegAsyncWithArguments(this.parseArguments(command));
        executeCallbackMap.set(executionId, executeCallback);
        return executionId;
    }

    /**
     * Cancels all ongoing operations.
     */
    cancel() {
        RNFFmpegModule.cancel();
    }

    /**
     * Cancels an ongoing operation.
     */
    cancelExecution(executionId) {
        RNFFmpegModule.cancelExecution(executionId);
    }

    /**
     * Lists ongoing executions.
     *
     * @return list of ongoing executions
     */
    listExecutions() {
        return RNFFmpegModule.listExecutions();
    }

    /**
     * Parses the given command into arguments.
     *
     * @param command string command
     * @return array of arguments
     */
    parseArguments(command) {
        var argumentList = [];
        var currentArgument = "";

        var singleQuoteStarted = 0;
        var doubleQuoteStarted = 0;

        for (var i = 0; i < command.length; i++) {
            let previousChar;
            if (i > 0) {
                previousChar = command.charAt(i - 1);
            } else {
                previousChar = null;
            }
            let currentChar = command.charAt(i);

            if (currentChar === ' ') {
                if (singleQuoteStarted === 1 || doubleQuoteStarted === 1) {
                    currentArgument += currentChar;
                } else if (currentArgument.length > 0) {
                    argumentList.push(currentArgument);
                    currentArgument = "";
                }
            } else if (currentChar === '\'' && (previousChar == null || previousChar !== '\\')) {
                if (singleQuoteStarted === 1) {
                    singleQuoteStarted = 0;
                } else if (doubleQuoteStarted === 1) {
                    currentArgument += currentChar;
                } else {
                    singleQuoteStarted = 1;
                }
            } else if (currentChar === '\"' && (previousChar == null || previousChar !== '\\')) {
                if (doubleQuoteStarted === 1) {
                    doubleQuoteStarted = 0;
                } else if (singleQuoteStarted === 1) {
                    currentArgument += currentChar;
                } else {
                    doubleQuoteStarted = 1;
                }
            } else {
                currentArgument += currentChar;
            }
        }

        if (currentArgument.length > 0) {
            argumentList.push(currentArgument);
        }

        return argumentList;
    }

}

/**
 * Main class for FFprobe operations. Provides execute() method to execute FFprobe commands.
 *
 * @author Taner Sener
 * @since v0.4.1
 */
class ReactNativeFFprobe {

    /**
     * Executes FFprobe with arguments provided.
     *
     * @param commandArguments FFprobe command options/arguments as string array
     * @returns return code
     */
    executeWithArguments(commandArguments) {
        return RNFFmpegModule.executeFFprobeWithArguments(commandArguments);
    }

    /**
     * Executes FFprobe command provided.
     *
     * @param command FFprobe command
     * @returns return code
     */
    execute(command) {
        return RNFFmpegModule.executeFFprobeWithArguments(RNFFmpeg.parseArguments(command));
    }

    /**
     * Returns media information for given file.
     *
     * @param path path or uri of media file
     * @return media information class
     */
    getMediaInformation(path) {
        return RNFFmpegModule.getMediaInformation(path).then(properties => new MediaInformation(properties));
    }

}

class MediaInformation {
    #allProperties;

    constructor(properties) {
        this.#allProperties = properties;
    }

    /**
     * Returns the streams found as array.
     *
     * @returns StreamInformation[]
     */
    getStreams() {
        let list = [];
        let streamList;

        if (this.#allProperties === undefined) {
            streamList = [];
        } else {
            streamList = this.#allProperties.streams;
        }

        if (streamList !== undefined) {
            streamList.forEach((stream) => {
                list.push(new StreamInformation(stream));
            });
        }

        return list;
    }

    /**
     * Returns all media properties.
     *
     * @returns an object where media properties can be accessed by property names
     */
    getMediaProperties() {
        if (this.#allProperties == null) {
            return new Map();
        } else {
            return this.#allProperties.format;
        }
    }

    /**
     * Returns all properties found, including stream properties too.
     *
     * @returns an object in which properties can be accessed by property names
     */
    getAllProperties() {
        return this.#allProperties;
    }
}

class StreamInformation {
    #allProperties;

    constructor(properties) {
        this.#allProperties = properties;
    }

    /**
     * Returns all properties found.
     *
     * @returns an object in which properties can be accessed by property names
     */
    getAllProperties() {
        return this.#allProperties;
    }
}

export {
    LogLevel, MediaInformation
}

export const RNFFmpegConfig = new ReactNativeFFmpegConfig();
export const RNFFmpeg = new ReactNativeFFmpeg();
export const RNFFprobe = new ReactNativeFFprobe();

export default RNFFmpeg;

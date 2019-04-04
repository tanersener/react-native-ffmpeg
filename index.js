import { NativeEventEmitter, NativeModules } from 'react-native';

const { RNFFmpegModule } = NativeModules;

const eventLog = "RNFFmpegLogCallback";
const statisticsLog = "RNFFmpegStatisticsCallback";

class LogLevel {

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

}

/**
 * Main class for FFmpeg operations. Provides execute() method to execute FFmpeg commands.
 */
class ReactNativeFFmpeg {

    constructor() {
        const reactNativeFFmpegModuleEvents = new NativeEventEmitter(RNFFmpegModule);
        reactNativeFFmpegModuleEvents.addListener(eventLog, event => {
            if (this.logCallback === undefined) {
                console.log(event.log);
            } else {
                this.logCallback(event);
            }
        });
        reactNativeFFmpegModuleEvents.addListener(statisticsLog, statistics => {
            if (this.statisticsCallback !== undefined) {
                this.statisticsCallback(statistics);
            }
        });

        console.log("Loading react-native-ffmpeg.");

        RNFFmpegModule.enableLogEvents();
        RNFFmpegModule.enableStatisticsEvents();
        RNFFmpegModule.enableRedirection();

        RNFFmpegModule.getPlatform().then((result) => {
            console.log("Loaded react-native-ffmpeg-" + result.platform + ".");
        });
    }

    /**
     * Returns FFmpeg version bundled within the library.
     *
     * @returns FFmpeg version stored in version field
     */
    getFFmpegVersion() {
        return RNFFmpegModule.getFFmpegVersion();
    }

    /**
     * Returns platform name where library is loaded.
     *
     * @returns platform name stored in platform field
     */
    getPlatform() {
        return RNFFmpegModule.getPlatform();
    }

    /**
     * Executes FFmpeg with arguments provided.
     *
     * @param commandArguments FFmpeg command options/arguments as string array
     * @returns return code stored in rc field
     */
    executeWithArguments(commandArguments) {
        return RNFFmpegModule.executeWithArguments(commandArguments);
    }

    /**
     * Executes FFmpeg command provided. Command is split into arguments using provided delimiter.
     *
     * @param command FFmpeg command
     * @param delimiter delimiter used between arguments, default value is space character
     * @returns return code stored in rc field
     */
    execute(command, delimiter) {
        return RNFFmpegModule.execute(command, delimiter);
    }

    /**
     * Cancels an ongoing operation.
     */
    cancel() {
        RNFFmpegModule.cancel();
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
     * @returns log level stored in level field
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
     * <p>Returns log output of last executed command. Please note that disabling redirection using
     * disableRedirection() method also disables this functionality.
     *
     * @return output of last executed command
     */
    getLastCommandOutput() {
        return RNFFmpegModule.getLastCommandOutput();
    }

    /**
     * Returns media information for given file.
     *
     * @param path path or uri of media file
     * @param timeout complete timeout in ms, default value is 10000
     * @return media information
     */
    getMediaInformation(path, timeout) {
        if (timeout) {
            return RNFFmpegModule.getMediaInformation(path, timeout);
        } else {
            return RNFFmpegModule.getMediaInformation(path, Number(10000));
        }
    }

    /**
     * Returns log level string.
     *
     * @param level log level integer
     * @returns log level string
     */
    logLevelToString(level) {
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
            case LogLevel.AV_LOG_QUIET:
            default:
                return "";
        }
    }

}

export const RNFFmpeg = new ReactNativeFFmpeg();

export default RNFFmpeg;
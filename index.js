import { DeviceEventEmitter, NativeModules } from 'react-native';

const { RNFFmpegModule } = NativeModules;

const eventLog = "reactNativeFFmpegLogCallback";

class ReactNativeFFmpeg {

    constructor() {
        DeviceEventEmitter.addListener(eventLog, event => {
            console.log(event.log);
        });
    }

    getPlatform() {
        return RNFFmpegModule.getPlatform();
    }

    getFFmpegVersion() {
        return RNFFmpegModule.getFFmpegVersion();
    }

    execute(parameters) {
        return RNFFmpegModule.execute(parameters);
    }

    cancel() {
        RNFFmpegModule.cancel();
    }

    disableRedirection() {
        RNFFmpegModule.disableRedirection();
    }

    getLogLevel() {
        return RNFFmpegModule.getLogLevel();
    }

    setLogLevel(logLevel) {
        return RNFFmpegModule.setLogLevel(logLevel);
    }

    getLastReceivedStatistics() {
        return RNFFmpegModule.getLastReceivedStatistics();
    }

    resetStatistics() {
        RNFFmpegModule.resetStatistics();
    }

    setFontconfigConfigurationPath(path) {
        RNFFmpegModule.setFontconfigConfigurationPath(path);
    }

    setFontDirectory() {
        RNFFmpegModule.setFontDirectory(path);
    }

}

export const RNFFmpeg = new ReactNativeFFmpeg();

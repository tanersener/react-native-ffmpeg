# React Native FFmpeg

FFmpeg for React Native

<img src="https://github.com/tanersener/react-native-ffmpeg/blob/master/docs/assets/react-native-ffmpeg-logo-v1.jpeg" width="280">

### 1. Features
- Based on MobileFFmpeg
- Supports both Android and IOS

### 2. Installation

`$ npm install react-native-ffmpeg --save`

#### 2.1 iOS

Add react-native-ffmpeg pod to your `Podfile`
`pod 'react-native-ffmpeg', :podspec => '../node_modules/react-native-ffmpeg/ios/react-native-ffmpeg.podspec'`

#### 2.2 Android

`$ react-native link react-native-ffmpeg`

### 3. Using

1. Create and execute commands.
    ```
    import { RNFFmpeg } from 'react-native-ffmpeg';
    
    RNFFmpeg.execute('-i file1.mp4 -c:v mpeg4 file2.mp4').then(data => {
        console.log("FFmpeg process exited with rc " + data.rc);
    });
    ```

2. Stop an ongoing operation.
    ```
    RNFFmpeg.cancel();
    ```

3. Enable log callback.
    ```
    logCallback = (logData) => {
        console.log(logData.log);
    };
    ...
    RNFFmpeg.enableLogCallback(this.logCallback);
    ```

4. Enable statistics callback.
    ```
    statisticsCallback = (statisticsData) => {
        console.log('Statistics; frame: ' + statisticsData.videoFrameNumber.toFixed(1) + ', fps: ' + statisticsData.videoFps.toFixed(1) + ', quality: ' + statisticsData.videoQuality.toFixed(1) +
        ', size: ' + statisticsData.size + ', time: ' + statisticsData.time);
    };
    ...
    RNFFmpeg.enableStatisticsCallback(this.statisticsCallback);
    ```
    
#### 4. Test Application

You can see how `React Native FFmpeg` is used inside an application by running test application provided under `test-app` folder.

### 5. License

This project is licensed under the LGPL v3.0.

Digital assets used in test applications are published in the public domain.

### 6. Contributing

Feel free to submit issues or pull requests.

### 7. See Also

- [MobileFFmpeg](https://github.com/tanersener/mobile-ffmpeg)
- [FFmpeg License and Legal Considerations](https://ffmpeg.org/legal.html)
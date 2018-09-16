# React Native FFmpeg [![Join the chat at https://gitter.im/react-native-ffmpeg/Lobby](https://badges.gitter.im/react-native-ffmpeg/Lobby.svg)](https://gitter.im/react-native-ffmpeg/Lobby?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge) ![GitHub release](https://img.shields.io/badge/release-v0.1.1-blue.svg) [![npm](https://img.shields.io/npm/v/react-native-ffmpeg.svg)](react-native-ffmpeg)


FFmpeg for React Native

<img src="https://github.com/tanersener/react-native-ffmpeg/raw/master/docs/assets/react-native-ffmpeg-logo-v2.png" width="280">

### 1. Features
- Based on MobileFFmpeg
- Supports both Android and IOS
- Includes eight different packages with different external libraries enabled in FFmpeg

| min | min-gpl | https | https-gpl | audio | video | full | full-gpl |
| :----: | :----: | :----: | :----: | :----: | :----: | :----: | :----: |
|  -  |  vid.stab <br/> x264 <br/> x265 <br/> xvidcore  |  gnutls  |  gnutls <br/> vid.stab <br/> x264 <br/> x265 <br/> xvidcore  |  chromaprint <br/> lame <br/> libilbc <br/> libvorbis <br/> opencore-amr <br/> opus <br/> shine <br/> soxr <br/> speex <br/> wavpack  |  fontconfig <br/> freetype <br/> fribidi <br/> kvazaar <br/> libaom <br/> libass <br/> libiconv <br/> libtheora <br/> libvpx <br/> snappy  |  chromaprint <br/> fontconfig <br/> freetype <br/> fribidi <br/> gmp <br/> gnutls <br/> kvazaar <br/> lame <br/> libaom <br/> libass <br/> libiconv <br/> libilbc <br/> libtheora <br/> libvorbis <br/> libvpx <br/> libwebp <br/> libxml2 <br/> opencore-amr <br/> opus <br/> shine <br/> snappy <br/> soxr <br/> speex <br/> wavpack  |  chromaprint <br/> fontconfig <br/> freetype <br/> fribidi <br/> gmp <br/> gnutls <br/> kvazaar <br/> lame <br/> libaom <br/> libass <br/> libiconv <br/> libilbc <br/> libtheora <br/> libvorbis <br/> libvpx <br/> libwebp <br/> libxml2 <br/> opencore-amr <br/> opus <br/> shine <br/> snappy <br/> soxr <br/> speex <br/> vid.stab <br/> wavpack <br/> x264 <br/> x265 <br/> xvidcore  |

### 2. Installation

`$ yarn add react-native-ffmpeg`

#### 2.1 Android

    $ react-native link react-native-ffmpeg
    
#### 2.2 iOS

- Add `react-native-ffmpeg` pod to your `Podfile`

    ```
    pod 'react-native-ffmpeg', :podspec => '../node_modules/react-native-ffmpeg/ios/react-native-ffmpeg.podspec'
    ```

- Run `pod install`

#### 2.3 Using Packages

Default installation of `ReactNativeFFmpeg` using instructions in `2.1` and `2.2` enables the default package, which is based 
on `https` package. It is possible to enable other installed packages using following steps.  

#### 2.3.1 Android

- Edit `android/settings.gradle` file and modify `projectDir` for `react-native-ffmpeg` by appending package name at
the end of the path.
    ```
    project(':react-native-ffmpeg').projectDir = new File(rootProject.projectDir, '../node_modules/react-native-ffmpeg/android/<package name>')
    ```

#### 2.3.2 IOS

- Edit `ios/Podfile` file and modify `podspec` path for `react-native-ffmpeg` by appending package name at the end of 
the name. After that run `pod install` again.
    ```
    pod 'react-native-ffmpeg-<package name>', :podspec => '../node_modules/react-native-ffmpeg/ios/react-native-ffmpeg-<package name>.podspec'
    ```

### 3. Using

1. Create and execute commands.
    ```
    import { LogLevel, RNFFmpeg } from 'react-native-ffmpeg';
    
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
    
5. Set log level.
    ```
    RNFFmpeg.setLogLevel(LogLevel.AV_LOG_WARNING);
    ```

6. Register custom fonts directory.
    ```
    RNFFmpeg.setFontDirectory('<folder with fonts>');
    ```
    
#### 4. Test Application

You can see how `React Native FFmpeg` is used inside an application by running test application provided under `test-app` folder. Current implementation performs command execution and video encoding operations.

<img src="https://github.com/tanersener/react-native-ffmpeg/raw/master/docs/assets/ios_test_app.gif" width="240">

### 5. License

This project is licensed under the LGPL v3.0. However, if installation is customized to use a package with `-gpl` 
postfix (min-gpl, https-gpl, full-gpl) then ReactNativeFFmpeg is subject to the GPL v3.0 license.

Digital assets used in test applications are published in the public domain.

### 6. Contributing

Feel free to submit issues or pull requests.

### 7. See Also

- [FFmpeg](https://www.ffmpeg.org)
- [MobileFFmpeg Wiki](https://github.com/tanersener/mobile-ffmpeg/wiki)
- [FFmpeg License and Legal Considerations](https://ffmpeg.org/legal.html)
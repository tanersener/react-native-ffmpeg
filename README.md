# React Native FFmpeg [![Join the chat at https://gitter.im/react-native-ffmpeg/Lobby](https://badges.gitter.im/react-native-ffmpeg/Lobby.svg)](https://gitter.im/react-native-ffmpeg/Lobby?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge) ![GitHub release](https://img.shields.io/badge/release-v0.1.2-blue.svg) [![npm](https://img.shields.io/npm/v/react-native-ffmpeg.svg)](react-native-ffmpeg)


FFmpeg for React Native

<img src="https://github.com/tanersener/react-native-ffmpeg/raw/master/docs/assets/react-native-ffmpeg-logo-v2.png" width="280">

### 1. Features
- Based on Mobile FFmpeg
- Supports both Android and IOS
- Includes eight different packages with different external libraries enabled in FFmpeg

| min | min-gpl | https | https-gpl | audio | video | full | full-gpl |
| :----: | :----: | :----: | :----: | :----: | :----: | :----: | :----: |
|  -  |  <sup>vid.stab</sup></sup> <br/> <sup>x264</sup> <br/> <sup>x265</sup> <br/> <sup>xvidcore</sup>  |  <sup>gnutls</sup>  |  <sup>gnutls</sup> <br/> <sup>vid.stab</sup> <br/> <sup>x264</sup> <br/> <sup>x265</sup> <br/> <sup>xvidcore</sup>  |  <sup>chromaprint</sup> <br/> <sup>lame</sup> <br/> <sup>libilbc</sup> <br/> <sup>libvorbis</sup> <br/> <sup>opencore-amr</sup> <br/> <sup>opus</sup> <br/> <sup>shine</sup> <br/> <sup>soxr</sup> <br/> <sup>speex</sup> <br/> <sup>wavpack</sup>  |  <sup>fontconfig</sup> <br/> <sup>freetype</sup> <br/> <sup>fribidi</sup> <br/> <sup>kvazaar</sup> <br/> <sup>libaom</sup> <br/> <sup>libass</sup> <br/> <sup>libiconv</sup> <br/> <sup>libtheora</sup> <br/> <sup>libvpx</sup> <br/> <sup>snappy</sup>  |  <sup>chromaprint</sup> <br/> <sup>fontconfig</sup> <br/> <sup>freetype</sup> <br/> <sup>fribidi</sup> <br/> <sup>gmp</sup> <br/> <sup>gnutls</sup> <br/> <sup>kvazaar</sup> <br/> <sup>lame</sup> <br/> <sup>libaom</sup> <br/> <sup>libass</sup> <br/> <sup>libiconv</sup> <br/> <sup>libilbc</sup> <br/> <sup>libtheora</sup> <br/> <sup>libvorbis</sup> <br/> <sup>libvpx</sup> <br/> <sup>libwebp</sup> <br/> <sup>libxml2</sup> <br/> <sup>opencore-amr</sup> <br/> <sup>opus</sup> <br/> <sup>shine</sup> <br/> <sup>snappy</sup> <br/> <sup>soxr</sup> <br/> <sup>speex</sup> <br/> <sup>wavpack</sup>  |  <sup>chromaprint</sup> <br/> <sup>fontconfig</sup> <br/> <sup>freetype</sup> <br/> <sup>fribidi</sup> <br/> <sup>gmp</sup> <br/> <sup>gnutls</sup> <br/> <sup>kvazaar</sup> <br/> <sup>lame</sup> <br/> <sup>libaom</sup> <br/> <sup>libass</sup> <br/> <sup>libiconv</sup> <br/> <sup>libilbc</sup> <br/> <sup>libtheora</sup> <br/> <sup>libvorbis</sup> <br/> <sup>libvpx</sup> <br/> <sup>libwebp</sup> <br/> <sup>libxml2</sup> <br/> <sup>opencore-amr</sup> <br/> <sup>opus</sup> <br/> <sup>shine</sup> <br/> <sup>snappy</sup> <br/> <sup>soxr</sup> <br/> <sup>speex</sup> <br/> <sup>vid.stab</sup> <br/> <sup>wavpack</sup> <br/> <sup>x264</sup> <br/> <sup>x265</sup> <br/> <sup>xvidcore</sup>  |

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
    
### 4. Versions

- `master` includes latest released version `v0.1.2`
- `development` branch includes new features and unreleased fixes

### 5. Updates

Refer to [Changelog](CHANGELOG.md) for updates.

### 6. Tips

Apply provided solutions if you encounter one of the following issues.

- Sometimes `react-native run-ios` fails with weird build errors. Execute commands below and try again.

    ```
    rm -rf ios/Pods ios/build ios/Podfile.lock
    cd ios
    pod install
    ```

- Add `"postinstall": "sed -i '' 's\/#import <RCTAnimation\\/RCTValueAnimatedNode.h>\/#import \"RCTValueAnimatedNode.h\"\/' ./node_modules/react-native/Libraries/NativeAnimation/RCTNativeAnimatedNodesManager.h"` 
line to the scripts section of your package.json as recommended in [react-native issue # 13198](https://github.com/facebook/react-native/issues/13198#issuecomment-302917321), 
if your build receives the following error for IOS.

    ```
    ../node_modules/react-native/Libraries/NativeAnimation/RCTNativeAnimatedNodesManager.h:10:9: fatal error: 'RCTAnimation/RCTValueAnimatedNode.h' file not found
    #import <RCTAnimation/RCTValueAnimatedNode.h>
            ^~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    1 error generated.
    ```

- When `pod install` is not successful with the following message, delete `Podfile.lock` file and run `pod install` again.

    ```
    [!] Unable to find a specification for 'react-native-ffmpeg'.
    ```

- If `react-native link` is used for IOS linking, building may fail with this error. Running `pod install` again 
fixes this issue.

    ```
    ../node_modules/react-native-ffmpeg/ios/Pods/Target Support Files/Pods-ReactNativeFFmpeg/Pods-ReactNativeFFmpeg.debug.xcconfig: unable to open file (in target "ReactNativeFFmpeg" in project "ReactNativeFFmpeg") (in target 'ReactNativeFFmpeg')
    ```

### 7. Test Application

You can see how `React Native FFmpeg` is used inside an application by running test applications provided under 
[react-native-ffmpeg-test](https://github.com/tanersener/react-native-ffmpeg-test) repository. All applications provide
the same functionality; performs command execution and video encoding operations. The difference between them is IOS 
dependency management mechanism applied.

<img src="https://github.com/tanersener/react-native-ffmpeg/raw/master/docs/assets/ios_test_app.gif" width="240">

### 8. License

This project is licensed under the LGPL v3.0. However, if installation is customized to use a package with `-gpl` 
postfix (min-gpl, https-gpl, full-gpl) then `React Native FFmpeg` is subject to the GPL v3.0 license.

Digital assets used in test applications are published in the public domain.

### 9. Contributing

Feel free to submit issues or pull requests.

### 10. See Also

- [FFmpeg](https://www.ffmpeg.org)
- [Mobile FFmpeg Wiki](https://github.com/tanersener/mobile-ffmpeg/wiki)
- [FFmpeg License and Legal Considerations](https://ffmpeg.org/legal.html)

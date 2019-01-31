# React Native FFmpeg [![Join the chat at https://gitter.im/react-native-ffmpeg/Lobby](https://badges.gitter.im/react-native-ffmpeg/Lobby.svg)](https://gitter.im/react-native-ffmpeg/Lobby?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge) ![GitHub release](https://img.shields.io/badge/release-v0.3.1-blue.svg) [![npm](https://img.shields.io/npm/v/react-native-ffmpeg.svg)](react-native-ffmpeg)

FFmpeg for React Native

<img src="https://github.com/tanersener/react-native-ffmpeg/raw/master/docs/assets/react-native-ffmpeg-logo-v2.png" width="280">

### 1. Features
- Based on MobileFFmpeg
- Supports
    - Both Android and IOS
    - FFmpeg `v4.1` and `v4.2-dev-x` (master) releases
    - `arm-v7a`, `arm-v7a-neon`, `arm64-v8a`, `x86` and `x86_64` architectures on Android
    - `armv7`, `armv7s`, `arm64`, `arm64e`, `i386` and `x86_64` architectures on IOS
    - 24 external libraries
    
        `fontconfig`, `freetype`, `fribidi`, `gmp`, `gnutls`, `kvazaar`, `lame`, `libaom`, `libass`, `libiconv`, `libilbc`, `libtheora`, `libvorbis`, `libvpx`, `libwebp`, `libxml2`, `opencore-amr`, `opus`, `shine`, `snappy`, `soxr`, `speex`, `twolame`, `wavpack`
    
    - 4 external libraries with GPL license
    
        `vid.stab`, `x264`, `x265`, `xvidcore`
        
    - `zlib` and `MediaCodec` Android system libraries
    - `bzip2`, `zlib` IOS system libraries and `AudioToolbox`, `CoreImage`, `VideoToolbox`, `AVFoundation` IOS system frameworks

- Licensed under LGPL 3.0, can be customized to support GPL v3.0
- Includes eight different packages with different external libraries enabled in FFmpeg

<table>
<thead>
<tr>
<th align="center"></th>
<th align="center">min</th>
<th align="center">min-gpl</th>
<th align="center">https</th>
<th align="center">https-gpl</th>
<th align="center">audio</th>
<th align="center">video</th>
<th align="center">full</th>
<th align="center">full-gpl</th>
</tr>
</thead>
<tbody>
<tr>
<td align="center"><sup>external libraries</sup></td>
<td align="center">-</td>
<td align="center"><sup>vid.stab</sup><br><sup>x264</sup><br><sup>x265</sup><br><sup>xvidcore</sup></td>
<td align="center"><sup>gmp</sup><br><sup>gnutls</sup></td>
<td align="center"><sup>gmp</sup><br><sup>gnutls</sup><br><sup>vid.stab</sup><br><sup>x264</sup><br><sup>x265</sup><br><sup>xvidcore</sup></td>
<td align="center"><sup>lame</sup><br><sup>libilbc</sup><br><sup>libvorbis</sup><br><sup>opencore-amr</sup><br><sup>opus</sup><br><sup>shine</sup><br><sup>soxr</sup><br><sup>speex</sup><br><sup>twolame</sup><br><sup>wavpack</sup></td>
<td align="center"><sup>fontconfig</sup><br><sup>freetype</sup><br><sup>fribidi</sup><br><sup>kvazaar</sup><br><sup>libaom</sup><br><sup>libass</sup><br><sup>libiconv</sup><br><sup>libtheora</sup><br><sup>libvpx</sup><br><sup>libwebp</sup><br><sup>snappy</sup></td>
<td align="center"><sup>fontconfig</sup><br><sup>freetype</sup><br><sup>fribidi</sup><br><sup>gmp</sup><br><sup>gnutls</sup><br><sup>kvazaar</sup><br><sup>lame</sup><br><sup>libaom</sup><br><sup>libass</sup><br><sup>libiconv</sup><br><sup>libilbc</sup><br><sup>libtheora</sup><br><sup>libvorbis</sup><br><sup>libvpx</sup><br><sup>libwebp</sup><br><sup>libxml2</sup><br><sup>opencore-amr</sup><br><sup>opus</sup><br><sup>shine</sup><br><sup>snappy</sup><br><sup>soxr</sup><br><sup>speex</sup><br><sup>twolame</sup><br><sup>wavpack</sup></td>
<td align="center"><sup>fontconfig</sup><br><sup>freetype</sup><br><sup>fribidi</sup><br><sup>gmp</sup><br><sup>gnutls</sup><br><sup>kvazaar</sup><br><sup>lame</sup><br><sup>libaom</sup><br><sup>libass</sup><br><sup>libiconv</sup><br><sup>libilbc</sup><br><sup>libtheora</sup><br><sup>libvorbis</sup><br><sup>libvpx</sup><br><sup>libwebp</sup><br><sup>libxml2</sup><br><sup>opencore-amr</sup><br><sup>opus</sup><br><sup>shine</sup><br><sup>snappy</sup><br><sup>soxr</sup><br><sup>speex</sup><br><sup>twolame</sup><br><sup>vid.stab</sup><br><sup>wavpack</sup><br><sup>x264</sup><br><sup>x265</sup><br><sup>xvidcore</sup></td>
</tr>
<tr>
<td align="center"><sup>android system libraries</sup></td>
<td align="center" colspan=8><sup>zlib</sup><br><sup>MediaCodec</sup></td>
</tr>
<tr>
<td align="center"><sup>ios system libraries</sup></td>
<td align="center" colspan=8><sup>zlib</sup><br><sup>AudioToolbox</sup><br><sup>AVFoundation</sup><br><sup>CoreImage</sup><br><sup>VideoToolbox</sup><br><sup>bzip2</sup></td>
</tr>
</tbody>
</table>


### 2. Installation

`$ yarn add react-native-ffmpeg`

#### 2.1 Android

    $ react-native link react-native-ffmpeg
    
#### 2.2 iOS

##### 2.2.1 Basic
  - Add `react-native-ffmpeg` pod to your `Podfile` and run `pod install`

    ```
    pod 'react-native-ffmpeg', :podspec => '../node_modules/react-native-ffmpeg/ios/react-native-ffmpeg.podspec'
    ```
    
  - **DO NOT USE** `react-native link` **on IOS.** `react-native link` breaks Cocoapods dependencies.

##### 2.2.2 Advanced
  - See [react-native-ffmpeg-test](https://github.com/tanersener/react-native-ffmpeg-test) for linking alternatives

#### 2.3 Packages

Installation of `react-native-ffmpeg` using instructions in `2.1` and `2.2` enables the default package, which is based 
on `https` package. It is possible to enable other installed packages using the following steps.  

##### 2.3.1 Android

- Edit `android/settings.gradle` file and modify `projectDir` for `react-native-ffmpeg` by appending package name at
the end of the path.
    ```
    project(':react-native-ffmpeg').projectDir = new File(rootProject.projectDir, '../node_modules/react-native-ffmpeg/android/<package name>')
    ```

##### 2.3.2 IOS

- Edit `ios/Podfile` file and modify `podspec` path for `react-native-ffmpeg` by appending package name at the end of 
the name. After that run `pod install` again.
    ```
    pod 'react-native-ffmpeg-<package name>', :podspec => '../node_modules/react-native-ffmpeg/ios/react-native-ffmpeg-<package name>.podspec'
    ```

### 3. Using

1. Execute commands. 

    - Use execute() method with a single command line and an argument delimiter  

    ```
    import { LogLevel, RNFFmpeg } from 'react-native-ffmpeg';
    
    RNFFmpeg.execute('-i file1.mp4 -c:v mpeg4 file2.mp4', ' ').then(result => console.log("FFmpeg process exited with rc " + result.rc));
    ```

    - Use executeWithArguments() method with an array of arguments  

    ```
    import { LogLevel, RNFFmpeg } from 'react-native-ffmpeg';
    
    RNFFmpeg.executeWithArguments(["-i", "file1.mp4", "-c:v", "mpeg4", "file2.mp4"]).then(result => console.log("FFmpeg process exited with rc " + result.rc));
    ```

2. Check execution output. Zero represents successful execution, non-zero values represent failure.
    ```
    RNFFmpeg.getLastReturnCode().then(result => {
        console.log("Last return code: " + result.lastRc);
    });

    RNFFmpeg.getLastCommandOutput().then(result => {
        console.log("Last command output: " + result.lastCommandOutput);
    });
    ```

3. Stop an ongoing operation. Note that this function does not wait for termination to complete and returns immediately.
    ```
    RNFFmpeg.cancel();
    ```

4. Get media information for a file.
    - Print all fields
    ```
    RNFFmpeg.getMediaInformation('<file path or uri>').then(info => {
        console.log('Result: ' + JSON.stringify(info));
    });
    ```
    - Print selected fields
    ```
    RNFFmpeg.getMediaInformation('<file path or uri>').then(info => {
        console.log('Result: ' + JSON.stringify(info));
        console.log('Media Information');
        console.log('Path: ' + info.path);
        console.log('Format: ' + info.format);
        console.log('Duration: ' + info.duration);
        console.log('Start time: ' + info.startTime);
        console.log('Bitrate: ' + info.bitrate);
        if (info.streams) {
            for (var i = 0; i < info.streams.length; i++) {
                console.log('Stream id: ' + info.streams[i].index);
                console.log('Stream type: ' + info.streams[i].type);
                console.log('Stream codec: ' + info.streams[i].codec);
                console.log('Stream full codec: ' + info.streams[i].fullCodec);
                console.log('Stream format: ' + info.streams[i].format);
                console.log('Stream full format: ' + info.streams[i].fullFormat);
                console.log('Stream width: ' + info.streams[i].width);
                console.log('Stream height: ' + info.streams[i].height);
                console.log('Stream bitrate: ' + info.streams[i].bitrate);
                console.log('Stream sample rate: ' + info.streams[i].sampleRate);
                console.log('Stream sample format: ' + info.streams[i].sampleFormat);
                console.log('Stream channel layout: ' + info.streams[i].channelLayout);
                console.log('Stream sar: ' + info.streams[i].sampleAspectRatio);
                console.log('Stream dar: ' + info.streams[i].displayAspectRatio);
                console.log('Stream average frame rate: ' + info.streams[i].averageFrameRate);
                console.log('Stream real frame rate: ' + info.streams[i].realFrameRate);
                console.log('Stream time base: ' + info.streams[i].timeBase);
                console.log('Stream codec time base: ' + info.streams[i].codecTimeBase);
            }
        }
    });
    ```

5. Enable log callback and redirect all `FFmpeg` logs to a console/file/widget.
    ```
    logCallback = (logData) => {
        console.log(logData.log);
    };
    ...
    RNFFmpeg.enableLogCallback(this.logCallback);
    ```

6. Enable statistics callback and follow the progress of an ongoing operation.
    ```
    statisticsCallback = (statisticsData) => {
        console.log('Statistics; frame: ' + statisticsData.videoFrameNumber.toFixed(1) + ', fps: ' + statisticsData.videoFps.toFixed(1) + ', quality: ' + statisticsData.videoQuality.toFixed(1) +
        ', size: ' + statisticsData.size + ', time: ' + statisticsData.time);
    };
    ...
    RNFFmpeg.enableStatisticsCallback(this.statisticsCallback);
    ```

7. Poll statistics without implementing statistics callback.
    ```
    RNFFmpeg.getLastReceivedStatistics().then(stats => console.log('Stats: ' + JSON.stringify(stats)));
    ```

8. Reset statistics before starting a new operation.
    ```
    RNFFmpeg.resetStatistics();
    ```

9. Set log level.
    ```
    RNFFmpeg.setLogLevel(LogLevel.AV_LOG_WARNING);
    ```

10. Register your own fonts by specifying a custom fonts directory, so they are available to use in `FFmpeg` filters. Please note that this function can not work on relative paths, you need to provide full file system path.
    - Without any font name mappings
    ```
    RNFFmpeg.setFontDirectory('<folder with fonts>', null);
    ```
    - Apply custom font name mappings. This functionality is very useful if your font name includes ' ' (space) characters in it.
    ```
    RNFFmpeg.setFontDirectory('<folder with fonts>', { my_easy_font_name: "my complex font name" });
    ```

11. Use your own `fontconfig` configuration.
    ```
    RNFFmpeg.setFontconfigConfigurationPath('<fontconfig configuration directory>');
    ```

12. Disable log functionality of the library. Logs will not be printed to console and log callback will be disabled.
    ```
    RNFFmpeg.disableLogs();
    ```

13. Disable statistics functionality of the library. Statistics callback will be disabled but the last received statistics data will be still available.
    ```
    RNFFmpeg.disableStatistics();
    ```

14. List enabled external libraries.
    ```
    RNFFmpeg.getExternalLibraries().then(externalLibraries => {
        console.log(externalLibraries);
    });
    ```
    
### 4. Versions

#### 4.1 Releases

- `0.1.x` releases are based on `FFmpeg v4.0.2` and `MobileFFmpeg v2.x` 
- `0.2.x` releases are based on `FFmpeg v4.1-dev` and `MobileFFmpeg v3.x`
- `0.3.0` and `0.3.1` releases are based on `FFmpeg v4.2-dev` and `MobileFFmpeg v4.2`

#### 4.2 Source Code

- `master` includes the latest released version `v0.3.1`
- `development` branch includes new features and unreleased fixes

### 5. MobileFFmpeg

`react-native-ffmpeg` uses [MobileFFmpeg](https://github.com/tanersener/mobile-ffmpeg) in its core. 

Starting from `v4.2`, `MobileFFmpeg` binaries are published in two different variants: `Main Release` and `LTS Release`. 

- Main releases include complete functionality of the library and support the latest SDK/API features

- LTS releases are customized to support a wide range of devices. They are built using older API/SDK versions, so some features are not available for them

By default, `react-native-ffmpeg` releases depend on `LTS` releases, to be backward compatible with its earlier versions. But you can change `MobileFFmpeg` variant and/or version used to support a specific feature/architecture.

- To do that on Android, set a different value to `mobileFFmpegVersion` variable inside `build.gradle` file imported, under `../node_modules/react-native-ffmpeg/android` path.

- On IOS, specify a different version for `mobile-ffmpeg` dependency inside imported `.podspec` file under `../node_modules/react-native-ffmpeg/ios` path.

#### 5.1 Main Release vs LTS Release

|        | Main Release | LTS Release |
| :----: | :----: | :----: |
| Android API Level | 24 | 21 | 
| Android Camera Access | x | - |
| Android Architectures | arm-v7a-neon<br/>arm64-v8a<br/>x86<br/>x86-64</br> | arm-v7a<br/>arm-v7a-neon<br/>arm64-v8a<br/>x86<br/>x86-64</br> |
| IOS SDK | 12.1 | 9.3 |
| Xcode Support | 10.1 | 7.3.1 |
| IOS Architectures | arm64<br/>arm64e<br/>x86-64</br> | armv7<br/>arm64<br/>i386<br/>x86-64</br> | 

### 6. Tips

Apply provided solutions if you encounter one of the following issues.

- You should not use double quotes (") to define your complex filters or map definitions.
    ```
     -filter_complex [0:v]scale=1280:-1[v] -map [v]
    ```
    
- If your commands include unnecessary quotes or space characters your command will fail with `No such filter: ' '` errors. Please check your command and remove them.

- Sometimes `react-native run-ios` fails with weird build errors. Execute the following commands and try again.

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
    
- Using `cocoapods` for IOS dependency management may produce duplicate symbols for `libReact.a` and `libyoga.a`. 
Add the following block to your `Podfile` and run `pod install` again.
 
    ```
    post_install do |installer|
        installer.pods_project.targets.each do |target|
          targets_to_ignore = %w(React yoga)
          if targets_to_ignore.include? target.name
            target.remove_from_project
          end
        end
    end

    ```

### 7. Test Application

You can see how `React Native FFmpeg` is used inside an application by running test applications provided under [react-native-ffmpeg-test](https://github.com/tanersener/react-native-ffmpeg-test) repository. All applications provide the same functionality; performs command execution and video encoding operations. The difference between them is IOS dependency management mechanism applied.

<img src="https://github.com/tanersener/react-native-ffmpeg/raw/master/docs/assets/ios_test_app.gif" width="240">

### 8. Updates

Refer to [Changelog](CHANGELOG.md) for updates.

### 9. License

This project is licensed under the LGPL v3.0. However, if installation is customized to use a package with `-gpl` postfix (min-gpl, https-gpl, full-gpl) then `React Native FFmpeg` is subject to the GPL v3.0 license.

Digital assets used in test applications are published in the public domain.

### 10. Contributing

Feel free to submit issues or pull requests.

### 11. See Also

- [FFmpeg](https://www.ffmpeg.org)
- [Mobile FFmpeg Wiki](https://github.com/tanersener/mobile-ffmpeg/wiki)
- [FFmpeg License and Legal Considerations](https://ffmpeg.org/legal.html)

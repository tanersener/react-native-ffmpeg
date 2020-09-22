# React Native FFmpeg ![GitHub release](https://img.shields.io/badge/release-v0.5.0-blue.svg) [![npm](https://img.shields.io/npm/v/react-native-ffmpeg.svg)](react-native-ffmpeg)

FFmpeg for React Native

<img src="https://github.com/tanersener/react-native-ffmpeg/raw/master/docs/assets/react-native-ffmpeg-logo-v2.png" width="280">

### 1. Features
- Based on `MobileFFmpeg`
- Includes both `FFmpeg` and `FFprobe`
- Supports
    - Both `Android` and `iOS`
    - FFmpeg `v4.1`, `v4.2`, `v4.3` and `v4.4-dev` releases
    - `arm-v7a`, `arm-v7a-neon`, `arm64-v8a`, `x86` and `x86_64` architectures on Android
    - `Android API Level 16` or later 
    - `armv7`, `armv7s`, `arm64`, `arm64e`, `i386` and `x86_64` architectures on iOS
    - `iOS SDK 9.3` or later
    - 25 external libraries
    
        `fontconfig`, `freetype`, `fribidi`, `gmp`, `gnutls`, `kvazaar`, `lame`, `libaom`, `libass`, `libiconv`, `libilbc`, `libtheora`, `libvorbis`, `libvpx`, `libwebp`, `libxml2`, `opencore-amr`, `opus`, `shine`, `snappy`, `soxr`, `speex`, `twolame`, `vo-amrwbenc`, `wavpack`
    
    - 4 external libraries with GPL license
    
        `vid.stab`, `x264`, `x265`, `xvidcore`
        
    - Concurrent execution
    - `zlib` and `MediaCodec` Android system libraries
    - `bzip2`, `iconv`, `libuuid`, `zlib` system libraries and `AudioToolbox`, `VideoToolbox`, `AVFoundation` system frameworks

- Includes Typescript definitions
- Licensed under LGPL 3.0, can be customized to support GPL v3.0

### 2. Installation

`$ yarn add react-native-ffmpeg`

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
<td align="center" colspan=8><sup>zlib</sup><br><sup>AudioToolbox</sup><br><sup>AVFoundation</sup><br><sup>CoreImage</sup><br><sup>iconv</sup><br><sup>VideoToolbox</sup><br><sup>bzip2</sup></td>
</tr>
</tbody>
</table>

#### 2.1 Android

On React Native < 0.60, manually link the module by running the following command.

    $ react-native link react-native-ffmpeg
    
#### 2.2 iOS

##### 2.2.1 Basic

On React Native >= 0.60,
  
  - Add `use_native_modules!` to your `Podfile` and run `pod install`  

On React Native < 0.60,
  
  - Add `react-native-ffmpeg` pod to your `Podfile` and run `pod install`

    ```
    pod 'react-native-ffmpeg', :podspec => '../node_modules/react-native-ffmpeg/react-native-ffmpeg.podspec'
    ```
    
  - **DO NOT USE** `react-native link` **on iOS.** `react-native link` breaks Cocoapods dependencies.

##### 2.2.2 Advanced
  - See [react-native-ffmpeg-test](https://github.com/tanersener/react-native-ffmpeg-test) for linking alternatives

#### 2.3 Packages

Installation of `react-native-ffmpeg` using instructions in `2.1` and `2.2` enables the default package, which is based on `https` package. 
It is possible to enable other installed packages using the following steps.  

##### 2.3.1 Android

- Edit `android/build.gradle` file and define package name in `ext.reactNativeFFmpegPackage` variable.

    ```
    ext {
        reactNativeFFmpegPackage = "<package name>"
    }

    ```

##### 2.3.2 iOS

- Edit `ios/Podfile` file and add package name as `subspec`. After that run `pod install` again.

    ```
    pod 'react-native-ffmpeg/<package name>', :podspec => '../node_modules/react-native-ffmpeg/react-native-ffmpeg.podspec'
    ```
  
- Note that if you have `use_native_modules!` in your `Podfile`, specifying a `subspec` may cause the following error. 
You can fix it by defining `react-native-ffmpeg` dependency before `use_native_modules!` in your `Podfile`. 

    ```
    [!] There are multiple dependencies with different sources for `react-native-ffmpeg` in `Podfile`:

    - react-native-ffmpeg (from `../node_modules/react-native-ffmpeg`)
    - react-native-ffmpeg/<package name> (from `../node_modules/react-native-ffmpeg/react-native-ffmpeg.podspec`)
    ```  
    
#### 2.4 LTS Release

When a package name is not specified, `react-native-ffmpeg` is installed in the `Main Release` variant described in [LTS Releases](#5-lts-releases) section.

In order to install the `LTS` variant, install the `https-lts` package using instructions in `2.3` or append `-lts` to the package name you are using. 

### 3. Using

1. Execute FFmpeg commands.

    - Use execute() method with a single command

    ```
    import { LogLevel, RNFFmpeg } from 'react-native-ffmpeg';
    
    RNFFmpeg.execute('-i file1.mp4 -c:v mpeg4 file2.mp4').then(result => console.log("FFmpeg process exited with rc " + result.rc));
    ```

    - Use executeWithArguments() method with an array of arguments  

    ```
    import { LogLevel, RNFFmpeg } from 'react-native-ffmpeg';
    
    RNFFmpeg.executeWithArguments(["-i", "file1.mp4", "-c:v", "mpeg4", "file2.mp4"]).then(result => console.log("FFmpeg process exited with rc " + result.rc));
    ```

2. Execute FFprobe commands.

    - Use execute() method with a single command

    ```
    import { LogLevel, RNFFprobe } from 'react-native-ffmpeg';
    
    RNFFprobe.execute('-i file1.mp4').then(result => console.log("FFprobe process exited with rc " + result.rc));
    ```

    - Use executeWithArguments() method with an array of arguments  

    ```
    import { LogLevel, RNFFprobe } from 'react-native-ffmpeg';
    
    RNFFprobe.executeWithArguments(["-i", "file1.mp4"]).then(result => console.log("FFprobe process exited with rc " + result.rc));
    ```

3. Check execution output. Zero represents successful execution, non-zero values represent failure.
    ```
    RNFFmpegConfig.getLastReturnCode().then(result => {
        console.log("Last return code: " + result.lastRc);
    });

    RNFFmpegConfig.getLastCommandOutput().then(result => {
        console.log("Last command output: " + result.lastCommandOutput);
    });
    ```

4. Stop an ongoing FFmpeg operation. Note that this function does not wait for termination to complete and returns immediately.
    ```
    RNFFmpeg.cancel();
    ```

5. Get media information for a file.
    - Print all fields
    ```
    RNFFprobe.getMediaInformation('<file path or uri>').then(info => {
        console.log('Result: ' + JSON.stringify(info));
    });
    ```
    - Print selected fields
    ```
    RNFFprobe.getMediaInformation('<file path or uri>').then(info => {
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
                
                if (info.streams[i].metadata) {
                    console.log('Stream metadata encoder: ' + info.streams[i].metadata.encoder);
                    console.log('Stream metadata rotate: ' + info.streams[i].metadata.rotate);
                    console.log('Stream metadata creation time: ' + info.streams[i].metadata.creation_time);
                    console.log('Stream metadata handler name: ' + info.streams[i].metadata.handler_name);
                }
    
                if (info.streams[i].sidedata) {
                    console.log('Stream side data displaymatrix: ' + info.streams[i].sidedata.displaymatrix);
                }
            }
        }
    });
    ```

6. Enable log callback and redirect all `FFmpeg`/`FFprobe` logs to a console/file/widget.
    ```
    logCallback = (logData) => {
        console.log(logData.log);
    };
    ...
    RNFFmpegConfig.enableLogCallback(this.logCallback);
    ```

7. Enable statistics callback and follow the progress of an ongoing `FFmpeg` operation.
    ```
    statisticsCallback = (statisticsData) => {
        console.log('Statistics; frame: ' + statisticsData.videoFrameNumber.toFixed(1) + ', fps: ' + statisticsData.videoFps.toFixed(1) + ', quality: ' + statisticsData.videoQuality.toFixed(1) +
        ', size: ' + statisticsData.size + ', time: ' + statisticsData.time);
    };
    ...
    RNFFmpegConfig.enableStatisticsCallback(this.statisticsCallback);
    ```

8. Poll statistics without implementing statistics callback.
    ```
    RNFFmpegConfig.getLastReceivedStatistics().then(stats => console.log('Stats: ' + JSON.stringify(stats)));
    ```

9. Reset statistics before starting a new operation.
    ```
    RNFFmpegConfig.resetStatistics();
    ```

10. Set log level.
    ```
    RNFFmpegConfig.setLogLevel(LogLevel.AV_LOG_WARNING);
    ```

11. Register your own fonts by specifying a custom fonts directory, so they are available to use in `FFmpeg` filters. Please note that this function can not work on relative paths, you need to provide full file system path.
    - Without any font name mappings
    ```
    RNFFmpegConfig.setFontDirectory('<folder with fonts>', null);
    ```
    - Apply custom font name mappings. This functionality is very useful if your font name includes ' ' (space) characters in it.
    ```
    RNFFmpegConfig.setFontDirectory('<folder with fonts>', { my_easy_font_name: "my complex font name" });
    ```

12. Use your own `fontconfig` configuration.
    ```
    RNFFmpegConfig.setFontconfigConfigurationPath('<fontconfig configuration directory>');
    ```

13. Disable log functionality of the library. Logs will not be printed to console and log callback will be disabled.
    ```
    RNFFmpegConfig.disableLogs();
    ```

14. Disable statistics functionality of the library. Statistics callback will be disabled but the last received statistics data will be still available.
    ```
    RNFFmpegConfig.disableStatistics();
    ```

15. List enabled external libraries.
    ```
    RNFFmpegConfig.getExternalLibraries().then(externalLibraries => {
        console.log(externalLibraries);
    });
    ```
    
### 4. Versions

#### 4.1 Releases

- `0.1.x` releases are based on `FFmpeg v4.0.2` and `MobileFFmpeg v2.x` 
- `0.2.x` releases are based on `FFmpeg v4.1-dev` and `MobileFFmpeg v3.x`
- `0.3.x` releases are based on `FFmpeg v4.2-dev` and `MobileFFmpeg v4.2.x`
- `0.4.x` releases are based on `FFmpeg v4.3-dev` and `MobileFFmpeg v4.3.x`

#### 4.2 Source Code

- `master` includes the latest released version `v0.4.4`
- `development` branch includes new features and unreleased fixes

### 5. LTS Releases

Starting from `v3.0`, `react-native-ffmpeg` packages are published in two different variants: `Main Release` and `LTS Release`. 

- Main releases include complete functionality of the library and support the latest SDK/API features

- LTS releases are customized to support a wide range of devices. They are built using older API/SDK versions, so some features are not available for them

Packages from LTS variant includes `-lts` postfix in their names. So if you want use a package from LTS release, you need to append `-lts` to package name. For example, to use `full-gpl` package of a LTS release you need to use `full-gpl-lts`.

#### 5.1 Main Release vs LTS Release

|        | Main Release | LTS Release |
| :----: | :----: | :----: |
| Android API Level | 24 | 16 | 
| Android Camera Access | Yes | - |
| Android Architectures | arm-v7a-neon<br/>arm64-v8a<br/>x86<br/>x86-64 | arm-v7a<br/>arm-v7a-neon<br/>arm64-v8a<br/>x86<br/>x86-64 |
| Xcode Support | 10.1 | 7.3.1 |
| iOS SDK | 12.1 | 9.3 |
| iOS Architectures | arm64<br/>arm64e<br/>x86-64 | armv7<br/>arm64<br/>i386<br/>x86-64 |

### 6. Tips

Apply provided solutions if you encounter one of the following issues.

- `react-native-ffmpeg` uses file system paths, it does not know what an `assets` folder or a `project` folder is. So you can't use resources on those folders directly, you need to provide full paths of those resources.

- Enabling `ProGuard` on releases older than `v0.3.3` causes linking errors. Please add the following rule inside your `proguard-rules.pro` file to preserve necessary method names and prevent linking errors.
                                                        
    ```
    -keep class com.arthenica.mobileffmpeg.Config {
        native <methods>;
        void log(int, byte[]);
        void statistics(int, float, float, long , int, double, double);
    }
    ```
    
- `ffmpeg` requires a valid `fontconfig` configuration to render subtitles. Unfortunately, Android does not include a default `fontconfig` configuration. 
So if you do not register a font or specify a `fontconfig` configuration under Android, then the burning process will not produce any errors but subtitles won't be burned in your file. 
You can overcome this situation by registering a font using `RNFFmpeg.setFontDirectory` method or specifying your own `fontconfig` configuration using `RNFFmpeg.setFontconfigConfigurationPath` method.

- By default, Xcode compresses `PNG` files during packaging. If you use `.png` files in your commands make sure you set the following two settings to `NO`. If one of them is set to `YES`, your operations may fail with `Error while decoding stream #0:0: Generic error in an external library` error.

    <img width="720" alt="png_settings" src="https://user-images.githubusercontent.com/10158439/45798948-794c9f80-bcb4-11e8-8881-8c61789b283c.png">

- Sometimes `react-native run-ios` fails with weird build errors. Execute the following commands and try again.

    ```
    rm -rf ios/Pods ios/build ios/Podfile.lock
    cd ios
    pod install
    ```

- Add `"postinstall": "sed -i '' 's\/#import <RCTAnimation\\/RCTValueAnimatedNode.h>\/#import \"RCTValueAnimatedNode.h\"\/' ./node_modules/react-native/Libraries/NativeAnimation/RCTNativeAnimatedNodesManager.h"` 
line to the `scripts` section of your `package.json` as recommended in [react-native issue # 13198](https://github.com/facebook/react-native/issues/13198#issuecomment-302917321), 
if your build receives the following error for iOS.

    ```
    ../node_modules/react-native/Libraries/NativeAnimation/RCTNativeAnimatedNodesManager.h:10:9: fatal error: 'RCTAnimation/RCTValueAnimatedNode.h' file not found
    #import <RCTAnimation/RCTValueAnimatedNode.h>
            ^~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    1 error generated.
    ```

- When `pod install` fails with the following message, delete `Podfile.lock` file and run `pod install` again.

    ```
    [!] Unable to find a specification for 'react-native-ffmpeg'.
    ```

- If `react-native link` is used for iOS, build may fail with the error below. Running `pod install` again fixes this issue.

    ```
    ../node_modules/react-native-ffmpeg/ios/Pods/Target Support Files/Pods-ReactNativeFFmpeg/Pods-ReactNativeFFmpeg.debug.xcconfig: unable to open file (in target "ReactNativeFFmpeg" in project "ReactNativeFFmpeg") (in target 'ReactNativeFFmpeg')
    ```
    
- On React Native < 0.60, using `cocoapods` for iOS dependency management may produce duplicate symbols for `libReact.a` and `libyoga.a`. 
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
    
- Some `react-native-ffmpeg` packages include `libc++_shared.so` native library. If a second library which also includes `libc++_shared.so` is added as a dependency, `gradle` fails with `More than one file was found with OS independent path 'lib/x86/libc++_shared.so'` error message.

  You can fix this error by adding the following block into your `build.gradle`.
  ```
  android {
      packagingOptions {
          pickFirst 'lib/x86/libc++_shared.so'
          pickFirst 'lib/x86_64/libc++_shared.so'
          pickFirst 'lib/armeabi-v7a/libc++_shared.so'
          pickFirst 'lib/arm64-v8a/libc++_shared.so'
      }
  }
  ```

### 7. Test Application

You can see how `React Native FFmpeg` is used inside an application by running test applications provided under [react-native-ffmpeg-test](https://github.com/tanersener/react-native-ffmpeg-test) repository.

<img src="https://github.com/tanersener/react-native-ffmpeg/raw/master/docs/assets/ios_test_app.gif" width="240">

### 8. Updates

Refer to [Changelog](CHANGELOG.md) for updates.

### 9. License

This project is licensed under the LGPL v3.0. However, if installation is customized to use a package with `-gpl` 
postfix (min-gpl, https-gpl, full-gpl) then `React Native FFmpeg` is subject to the GPL v3.0 license.

Digital assets used in test application are published in the public domain.

### 8. Patents

It is not clearly explained in their documentation but it is believed that `FFmpeg`, `kvazaar`, `x264` and `x265`
include algorithms which are subject to software patents. If you live in a country where software algorithms are
patentable then you'll probably need to pay royalty fees to patent holders. We are not lawyers though, so we recommend
that you seek legal advice first. See [FFmpeg Patent Mini-FAQ](https://ffmpeg.org/legal.html).

### 10. Contributing

Feel free to submit issues or pull requests.

Please note that `master` branch includes only the latest released source code. Changes planned for the next release 
are implemented under the `development` branch. Therefore, if you want to create a pull request, please open it against
the `development`.

### 11. See Also

- [FFmpeg](https://www.ffmpeg.org)
- [Mobile FFmpeg Wiki](https://github.com/tanersener/mobile-ffmpeg/wiki)
- [FFmpeg License and Legal Considerations](https://ffmpeg.org/legal.html)

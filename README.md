# React Native FFmpeg ![GitHub release](https://img.shields.io/badge/release-v0.5.1-blue.svg) [![npm](https://img.shields.io/npm/v/react-native-ffmpeg.svg)](react-native-ffmpeg)

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

#### 2.3 Packages

`ffmpeg` includes built-in encoders for some popular formats. However, there are certain external libraries that needs 
to be enabled in order to encode specific formats/codecs. For example, to encode an `mp3` file you need `lame` or 
`shine` library enabled. You have to install a `react-native-ffmpeg` package that has at least one of them inside. 
To encode an `h264` video, you need to install a package with `x264` inside. To encode `vp8` or `vp9` videos, you need 
a `react-native-ffmpeg` package with `libvpx` inside.

`react-native-ffmpeg` provides eight packages that include different sets of external libraries. These packages are 
named according to the external libraries included in them. Below you can see which libraries are enabled in each 
package. 

<table>
<thead>
<tr>
<th align="center"></th>
<th align="center"><sup>min</sup></th>
<th align="center"><sup>min-gpl</sup></th>
<th align="center"><sup>https</sup></th>
<th align="center"><sup>https-gpl</sup></th>
<th align="center"><sup>audio</sup></th>
<th align="center"><sup>video</sup></th>
<th align="center"><sup>full</sup></th>
<th align="center"><sup>full-gpl</sup></th>
</tr>
</thead>
<tbody>
<tr>
<td align="center"><sup>external libraries</sup></td>
<td align="center">-</td>
<td align="center"><sup>vid.stab</sup><br><sup>x264</sup><br><sup>x265</sup><br><sup>xvidcore</sup></td>
<td align="center"><sup>gmp</sup><br><sup>gnutls</sup></td>
<td align="center"><sup>gmp</sup><br><sup>gnutls</sup><br><sup>vid.stab</sup><br><sup>x264</sup><br><sup>x265</sup><br><sup>xvidcore</sup></td>
<td align="center"><sup>lame</sup><br><sup>libilbc</sup><br><sup>libvorbis</sup><br><sup>opencore-amr</sup><br><sup>opus</sup><br><sup>shine</sup><br><sup>soxr</sup><br><sup>speex</sup><br><sup>twolame</sup><br><sup>vo-amrwbenc</sup><br><sup>wavpack</sup></td>
<td align="center"><sup>fontconfig</sup><br><sup>freetype</sup><br><sup>fribidi</sup><br><sup>kvazaar</sup><br><sup>libaom</sup><br><sup>libass</sup><br><sup>libiconv</sup><br><sup>libtheora</sup><br><sup>libvpx</sup><br><sup>libwebp</sup><br><sup>snappy</sup></td>
<td align="center"><sup>fontconfig</sup><br><sup>freetype</sup><br><sup>fribidi</sup><br><sup>gmp</sup><br><sup>gnutls</sup><br><sup>kvazaar</sup><br><sup>lame</sup><br><sup>libaom</sup><br><sup>libass</sup><br><sup>libiconv</sup><br><sup>libilbc</sup><br><sup>libtheora</sup><br><sup>libvorbis</sup><br><sup>libvpx</sup><br><sup>libwebp</sup><br><sup>libxml2</sup><br><sup>opencore-amr</sup><br><sup>opus</sup><br><sup>shine</sup><br><sup>snappy</sup><br><sup>soxr</sup><br><sup>speex</sup><br><sup>twolame</sup><br><sup>vo-amrwbenc</sup><br><sup>wavpack</sup></td>
<td align="center"><sup>fontconfig</sup><br><sup>freetype</sup><br><sup>fribidi</sup><br><sup>gmp</sup><br><sup>gnutls</sup><br><sup>kvazaar</sup><br><sup>lame</sup><br><sup>libaom</sup><br><sup>libass</sup><br><sup>libiconv</sup><br><sup>libilbc</sup><br><sup>libtheora</sup><br><sup>libvorbis</sup><br><sup>libvpx</sup><br><sup>libwebp</sup><br><sup>libxml2</sup><br><sup>opencore-amr</sup><br><sup>opus</sup><br><sup>shine</sup><br><sup>snappy</sup><br><sup>soxr</sup><br><sup>speex</sup><br><sup>twolame</sup><br><sup>vid.stab</sup><br><sup>vo-amrwbenc</sup><br><sup>wavpack</sup><br><sup>x264</sup><br><sup>x265</sup><br><sup>xvidcore</sup></td>
</tr>
<tr>
<td align="center"><sup>android system libraries</sup></td>
<td align="center" colspan=8><sup>zlib</sup><br><sup>MediaCodec</sup></td>
</tr>
<tr>
<td align="center"><sup>ios system libraries</sup></td>
<td align="center" colspan=8><sup>zlib</sup><br><sup>AudioToolbox</sup><br><sup>AVFoundation</sup><br><sup>iconv</sup><br><sup>VideoToolbox</sup><br><sup>bzip2</sup></td>
</tr>
</tbody>
</table>

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
    
##### 2.3.3 Package Names

The following table shows all package names defined for `react-native-ffmpeg`.
    
| Package | Main Release | LTS Release |
| :----: | :----: | :----: |
| min | min  | min-lts |
| min-gpl | min-gpl | min-gpl-lts |
| https | https | https-lts |
| https-gpl | https-gpl | https-gpl-lts |
| audio | audio | audio-lts |
| video | video | video-lts |
| full | full | full-lts |
| full-gpl | full-gpl | full-gpl-lts |
    
#### 2.4 LTS Release

`react-native-ffmpeg` is published in two different variants: `Main Release` and `LTS Release`. Both releases share the 
same source code but is built with different settings. Below you can see the differences between the two.

In order to install the `LTS` variant, install the `https-lts` package using instructions in `2.3` or append `-lts` to 
the package name you are using.

|        | Main Release | LTS Release |
| :----: | :----: | :----: |
| Android API Level | 24 | 16 | 
| Android Camera Access | Yes | - |
| Android Architectures | arm-v7a-neon<br/>arm64-v8a<br/>x86<br/>x86-64 | arm-v7a<br/>arm-v7a-neon<br/>arm64-v8a<br/>x86<br/>x86-64 |
| Xcode Support | 10.1 | 7.3.1 |
| iOS SDK | 11.0 | 9.3 |
| iOS AVFoundation | Yes | - |
| iOS Architectures | arm64<br/>x86-64<br/>x86-64-mac-catalyst | armv7<br/>arm64<br/>i386<br/>x86-64 |

### 3. Using

1. Execute synchronous FFmpeg commands.

    - Use execute() method with a single command

    ```
    import { LogLevel, RNFFmpeg } from 'react-native-ffmpeg';
    
    RNFFmpeg.execute('-i file1.mp4 -c:v mpeg4 file2.mp4').then(result => console.log(`FFmpeg process exited with rc=${result}.`));
    ```

    - Use executeWithArguments() method with an array of arguments  

    ```
    import { LogLevel, RNFFmpeg } from 'react-native-ffmpeg';
    
    RNFFmpeg.executeWithArguments(["-i", "file1.mp4", "-c:v", "mpeg4", "file2.mp4"]).then(result => console.log(`FFmpeg process exited with rc=${result}.`));
    ```
   
2. Execute asynchronous FFmpeg commands.

    ```
    import { LogLevel, RNFFmpeg } from 'react-native-ffmpeg';
    
    RNFFmpeg.executeAsync('-i file1.mp4 -c:v mpeg4 file2.mp4', completedExecution => {
        if (completedExecution.returnCode === 0) {
           console.log("FFmpeg process completed successfully");
        } else {
           console.log(`FFmpeg process failed with rc=${completedExecution.returnCode}.`);
        }
    }).then(executionId => console.log(`Async FFmpeg process started with executionId ${executionId}.`));
    ```

3. Execute FFprobe commands.

    - Use execute() method with a single command

    ```
    import { LogLevel, RNFFprobe } from 'react-native-ffmpeg';
    
    RNFFprobe.execute('-i file1.mp4').then(result => console.log(`FFprobe process exited with rc=${result}.`));
    ```

    - Use executeWithArguments() method with an array of arguments  

    ```
    import { LogLevel, RNFFprobe } from 'react-native-ffmpeg';
    
    RNFFprobe.executeWithArguments(["-i", "file1.mp4"]).then(result => console.log(`FFmpeg process exited with rc=${result}.`));
    ```

4. Check execution output. Zero represents successful execution, non-zero values represent failure.
    ```
    RNFFmpegConfig.getLastReturnCode().then(returnCode => {
        console.log(`Last return code: ${returnCode}`);
    });

    RNFFmpegConfig.getLastCommandOutput().then(output => {
        console.log(`Last command output: ${output}`);
    });
    ```

5. Stop ongoing FFmpeg operations. Note that these two functions do not wait for termination to complete and return immediately.
    - Stop all executions
        ```
        RNFFmpeg.cancel();
        ```
    - Stop a specific execution
        ```
        RNFFmpeg.cancelExecution(executionId);
        ```

6. Get media information for a file.
    - Print all fields
    ```
    RNFFprobe.getMediaInformation('<file path or uri>').then(information => {
        console.log('Result: ' + JSON.stringify(information));
    });
    ```
    - Print selected fields
    ```
    RNFFprobe.getMediaInformation('<file path or uri>').then(information => {
        if (information.getMediaProperties() !== undefined) {
            if (information.getMediaProperties().filename !== undefined) {
                console.log(`Path: ${information.getMediaProperties().filename}`);
            }
            if (information.getMediaProperties().format_name !== undefined) {
                console.log(`Format: ${information.getMediaProperties().format_name}`);
            }
            if (information.getMediaProperties().bit_rate !== undefined) {
                console.log(`Bitrate: ${information.getMediaProperties().bit_rate}`);
            }
            if (information.getMediaProperties().duration !== undefined) {
                console.log(`Duration: ${information.getMediaProperties().duration}`);
            }
            if (information.getMediaProperties().start_time !== undefined) {
                console.log(`Start time: ${information.getMediaProperties().start_time}`);
            }
            if (information.getMediaProperties().nb_streams !== undefined) {
                console.log(`Number of streams: ${information.getMediaProperties().nb_streams.toString()}`);
            }
            let tags = information.getMediaProperties().tags;
            if (tags !== undefined) {
                Object.keys(tags).forEach((key) => {
                    console.log(`Tag: ${key}:${tags[key]}`);
                });
            }
   
            let streams = information.getStreams();
            if (streams !== undefined) {
                for (let i = 0; i < streams.length; ++i) {
                   let stream = streams[i];
                   console.log("---");
                   if (stream.getAllProperties().index !== undefined) {
                       console.log(`Stream index: ${stream.getAllProperties().index.toString()}`);
                   }
                   if (stream.getAllProperties().codec_type !== undefined) {
                       console.log(`Stream type: ${stream.getAllProperties().codec_type}`);
                   }
                   if (stream.getAllProperties().codec_name !== undefined) {
                       console.log(`Stream codec: ${stream.getAllProperties().codec_name}`);
                   }
                }
            }
        }
    });
    ```

7. Enable log callback and redirect all `FFmpeg`/`FFprobe` logs to a console/file/widget.
    ```
    logCallback = (log) => {
        this.appendLog(`${log.executionId}:${log.message}`);
    };
    ...
    RNFFmpegConfig.enableLogCallback(this.logCallback);
    ```

8. Enable statistics callback and follow the progress of an ongoing `FFmpeg` operation.
    ```
    statisticsCallback = (statistics) => {
        console.log(`Statistics; executionId: ${statistics.executionId}, video frame number: ${statistics.videoFrameNumber}, video fps: ${statistics.videoFps}, video quality: ${statistics.videoQuality}, size: ${statistics.size}, time: ${statistics.time}, bitrate: ${statistics.bitrate}, speed: ${statistics.speed}`);
    };
    ...
    RNFFmpegConfig.enableStatisticsCallback(this.statisticsCallback);
    ```

9. Poll statistics without implementing statistics callback.
    ```
    RNFFmpegConfig.getLastReceivedStatistics().then(statistics => console.log('Stats: ' + JSON.stringify(statistics)));
    ```

10. List ongoing executions.
    ```
    RNFFmpeg.listExecutions().then(executionList => {
        executionList.forEach(execution => {
            console.log(`Execution id is ${execution.executionId}`);
            console.log(`Execution start time is ` + new Date(execution.startTime));
            console.log(`Execution command is ${execution.command}`);
        });
    });
    ```

11. Set log level.
    ```
    RNFFmpegConfig.setLogLevel(LogLevel.AV_LOG_WARNING);
    ```

12. Register your own fonts by specifying a custom fonts directory, so they are available to use in `FFmpeg` filters. Please note that this function can not work on relative paths, you need to provide full file system path.
    - Without any font name mappings
    ```
    RNFFmpegConfig.setFontDirectory('<folder with fonts>', null);
    ```
    - Apply custom font name mappings. This functionality is very useful if your font name includes ' ' (space) characters in it.
    ```
    RNFFmpegConfig.setFontDirectory('<folder with fonts>', { my_easy_font_name: "my complex font name" });
    ```

13. Use your own `fontconfig` configuration.
    ```
    RNFFmpegConfig.setFontconfigConfigurationPath('<fontconfig configuration directory>');
    ```

14. Disable log functionality of the library. Logs will not be printed to console and log callback will be disabled.
    ```
    RNFFmpegConfig.disableLogs();
    ```

15. Disable statistics functionality of the library. Statistics callback will be disabled but the last received statistics data will be still available.
    ```
    RNFFmpegConfig.disableStatistics();
    ```

16. Create new `FFmpeg` pipe.
    ```
    RNFFmpegConfig.registerNewFFmpegPipe().then(pipe => {
        console.log("New ffmpeg pipe: " + pipe1);
    });
    ```
### 4. Test Application
You can see how `react-native-ffmpeg` is used inside an application by running test application provided under the 
[react-native-ffmpeg-test](https://github.com/tanersener/react-native-ffmpeg-test) repository. It supports command 
execution, video encoding, accessing https, encoding audio, burning subtitles, video stabilisation, pipe operations 
and concurrent command execution.

<img src="https://github.com/tanersener/react-native-ffmpeg/raw/development/docs/assets/ios_test_app.gif" width="240">

### 5. Tips

Apply provided solutions if you encounter one of the following issues.

- `react-native-ffmpeg` uses file system paths, it does not know what an `assets` folder or a `project` folder is. So you can't use resources on those folders directly, you need to provide full paths of those resources.

- If you receive the following error on Android, please add the `ProGuard` rule given below to your `proguard-rules.pro` file.

    ```
    OnLoad thread failed to GetStaticMethodID for log.
    JNI DETECTED ERROR IN APPLICATION: JNI NewGlobalRef called with pending exception java.lang.NoSuchMethodError: no static method "Lcom/arthenica/mobileffmpeg/Config;.log(JI[B)V"
    ```

    `proguard-rules.pro`

    ```
    -keep class com.arthenica.mobileffmpeg.Config {
        native <methods>;
        void log(long, int, byte[]);
        void statistics(long, int, float, float, long , int, double, double);
    }

    -keep class com.arthenica.mobileffmpeg.AbiDetect {
        native <methods>;
    }
    ```

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

- When `pod install` fails with the following message, delete the `Podfile.lock` file and run `pod install` again.

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
  
### 6. Updates

Refer to [Changelog](CHANGELOG.md) for updates.

### 7. License

This project is licensed under the LGPL v3.0. However, if installation is customized to use a package with `-gpl` 
postfix (min-gpl, https-gpl, full-gpl) then `ReactNativeFFmpeg` is subject to the GPL v3.0 license.

In test application; embedded fonts are licensed under the
[SIL Open Font License](https://opensource.org/licenses/OFL-1.1), other digital assets are published in the public
domain.

### 8. Patents

It is not clearly explained in their documentation, but it is believed that `FFmpeg`, `kvazaar`, `x264` and `x265`
include algorithms which are subject to software patents. If you live in a country where software algorithms are
patentable then you'll probably need to pay royalty fees to patent holders. We are not lawyers though, so we recommend
that you seek legal advice first. See [FFmpeg Patent Mini-FAQ](https://ffmpeg.org/legal.html).

### 9. Contributing

Feel free to submit issues or pull requests.

Please note that `master` branch includes only the latest released source code. Changes planned for the next release 
are implemented under the `development` branch. Therefore, if you want to create a pull request, please open it against
the `development`.

### 10. See Also

- [FFmpeg](https://www.ffmpeg.org)
- [Mobile FFmpeg Wiki](https://github.com/tanersener/mobile-ffmpeg/wiki)
- [FFmpeg License and Legal Considerations](https://ffmpeg.org/legal.html)

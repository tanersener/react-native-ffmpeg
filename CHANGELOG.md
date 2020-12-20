# Changelog
All notable changes to this project will be documented in this file.

## 0.5.1 - 2020-12-20
- Fixes issue #210

## 0.5.0 - 2020-10-22
- Depends on mobile-ffmpeg v4.4
- Implements async FFmpeg execution methods
- Allows modifying mobile-ffmpeg version for android
- Adds listExecutions API method
- Updates getMediaInformation implementation
- Adds setEnvironmentVariable API method
- LogCallback and StatisticsCallback functions updated with executionId
- Includes an updated example application
- Fixes issue #156, #164, #188 and #196

## 0.4.4 - 2020-03-09
- Fixes issue #118

## 0.4.3 - 2020-01-19
- Fixes not visible cancel() function

## 0.4.1 - 2020-01-17
- Implements FFprobe
- Add concurrent execution support
- Re-organises plugin classes
- iOS releases depend on iconv system library instead of iconv external library

## 0.4.0 - 2019-10-27
- Implements registerNewFFmpegPipe API method

## 0.3.6 - 2019-09-13
- Fixes LogLevel class exports

## 0.3.5 - 2019-07-17
- Adds support for single quotes and double quotes in command strings

## 0.3.4 - 2019-07-05
- Implements side data information parsing

## 0.3.3 - 2019-07-04
- Adds support for Android devices with API Level 16+
- Removes conflicting attributes from AndroidManifest.xml
- Includes ProGuard configuration file inside

## 0.3.2 - 2019-04-03
- Includes Typescript declarations
- Adds React Native 0.59 support
- Simpler version and package selection

## 0.3.1 - 2019-01-31
- Includes fixes and enhancements for issues #41, #42 and #43.

## 0.3.0 - 2018-12-29
- Using mobile-ffmpeg-v4.2.LTS

## 0.2.1 - 2018-11-02
- Fixes issue #10: UI freezes while executing ffmpeg command 

## 0.2.0 - 2018-10-25
- Applied mobile-ffmpeg-v3.0 API changes

## 0.1.3 - 2018-09-28
- React.project deleted from Xcode workspace
- React dependency added, header search path customizations removed for .podspec files
- google() method replaced with maven repository declaration in build.gradle files
- test application removed

## 0.1.1 - 2018-09-16
- Test application updated
- Documentation revised

## 0.1.0 - 2018-09-16
- First stable release

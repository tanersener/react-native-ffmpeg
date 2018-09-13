# react-native-ffmpeg

FFmpeg for React Native

<img src="https://github.com/tanersener/react-native-ffmpeg/blob/master/docs/assets/react-native-ffmpeg-logo-v1.jpeg" width="240">

### 1. Features
- Based on [MobileFFmpeg](https://github.com/tanersener/mobile-ffmpeg)
- Supports both Android and IOS
- Provides eight different packages

| min | min-gpl | https | https-gpl | audio | video | full | full-gpl |
| :----: | :----: | :----: | :----: | :----: | :----: | :----: | :----: |
|  -  |  vid.stab <br/> x264 <br/> x265 <br/> xvidcore  |  gnutls  |  gnutls <br/> vid.stab <br/> x264 <br/> x265 <br/> xvidcore  |  chromaprint <br/> lame <br/> libilbc <br/> libvorbis <br/> opencore-amr <br/> opus <br/> shine <br/> soxr <br/> speex <br/> wavpack  |  fontconfig <br/> freetype <br/> fribidi <br/> kvazaar <br/> libaom <br/> libass <br/> libiconv <br/> libtheora <br/> libvpx <br/> snappy  |  chromaprint <br/> fontconfig <br/> freetype <br/> fribidi <br/> gmp <br/> gnutls <br/> kvazaar <br/> lame <br/> libaom <br/> libass <br/> libiconv <br/> libilbc <br/> libtheora <br/> libvorbis <br/> libvpx <br/> libwebp <br/> libxml2 <br/> opencore-amr <br/> opus <br/> shine <br/> snappy <br/> soxr <br/> speex <br/> wavpack  |  chromaprint <br/> fontconfig <br/> freetype <br/> fribidi <br/> gmp <br/> gnutls <br/> kvazaar <br/> lame <br/> libaom <br/> libass <br/> libiconv <br/> libilbc <br/> libtheora <br/> libvorbis <br/> libvpx <br/> libwebp <br/> libxml2 <br/> opencore-amr <br/> opus <br/> shine <br/> snappy <br/> soxr <br/> speex <br/> vid.stab <br/> wavpack <br/> x264 <br/> x265 <br/> xvidcore  |

## Getting started

`$ npm install react-native-ffmpeg --save`

### Mostly automatic installation

`$ react-native link react-native-ffmpeg`

### Manual installation


#### iOS

1. In XCode, in the project navigator, right click `Libraries` ➜ `Add Files to [your project's name]`
2. Go to `node_modules` ➜ `react-native-ffmpeg` and add `ReactNativeFfmpeg.xcodeproj`
3. In XCode, in the project navigator, select your project. Add `libReactNativeFfmpeg.a` to your project's `Build Phases` ➜ `Link Binary With Libraries`
4. Run your project (`Cmd+R`)<

#### Android

1. Open up `android/app/src/main/java/[...]/MainActivity.java`
  - Add `import com.arthenica.reactnative.ReactNativeFFmpegPackage;` to the imports at the top of the file
  - Add `new ReactNativeFfmpegPackage()` to the list returned by the `getPackages()` method
2. Append the following lines to `android/settings.gradle`:
  	```
  	include ':react-native-ffmpeg'
  	project(':react-native-ffmpeg').projectDir = new File(rootProject.projectDir, 	'../node_modules/react-native-ffmpeg/android')
  	```
3. Insert the following lines inside the dependencies block in `android/app/build.gradle`:
  	```
      compile project(':react-native-ffmpeg')
  	```


## Usage
```javascript
import ReactNativeFFpeg from 'react-native-ffmpeg';

// TODO: What to do with the module?
ReactNativeFfmpeg;
```
  
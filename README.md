
# react-native-ffmpeg

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
  
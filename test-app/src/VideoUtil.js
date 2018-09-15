import { Platform } from 'react-native';
import RNFS from 'react-native-fs';

async function androidResourcePath(resourceName) {
    var destinationPath = RNFS.CachesDirectoryPath + '/' + resourceName;

    await RNFS.copyFileAssets(resourceName, destinationPath).catch((err) => {
        console.log('Failed to copy android resource: ' + resourceName + ', err message: ' + err.message + ', err code: ' + err.code);
        return undefined;
    });

    return destinationPath;
}

function iosResourcePath(resourceName) {
    return new Promise(function(resolve, reject){
        resolve(RNFS.MainBundlePath + '/' + resourceName);
    });
}

class VideoClass {

    generateEncodeVideoScript(image1Path, image2Path, image3Path, videoFilePath, videoCodec, customOptions) {
        return "-hide_banner -y -loop 1 -i " + image1Path + " " +
            "-loop 1 -i " + image2Path + " " +
            "-loop 1 -i " + image3Path + " " +
            "-filter_complex " +
            "[0:v]setpts=PTS-STARTPTS,scale=w=\'if(gte(iw/ih,640/427),min(iw,640),-1)\':h=\'if(gte(iw/ih,640/427),-1,min(ih,427))\',scale=trunc(iw/2)*2:trunc(ih/2)*2,setsar=sar=1/1,split=2[stream1out1][stream1out2];" +
            "[1:v]setpts=PTS-STARTPTS,scale=w=\'if(gte(iw/ih,640/427),min(iw,640),-1)\':h=\'if(gte(iw/ih,640/427),-1,min(ih,427))\',scale=trunc(iw/2)*2:trunc(ih/2)*2,setsar=sar=1/1,split=2[stream2out1][stream2out2];" +
            "[2:v]setpts=PTS-STARTPTS,scale=w=\'if(gte(iw/ih,640/427),min(iw,640),-1)\':h=\'if(gte(iw/ih,640/427),-1,min(ih,427))\',scale=trunc(iw/2)*2:trunc(ih/2)*2,setsar=sar=1/1,split=2[stream3out1][stream3out2];" +
            "[stream1out1]pad=width=640:height=427:x=(640-iw)/2:y=(427-ih)/2:color=#00000000,trim=duration=3,select=lte(n\\,90)[stream1overlaid];" +
            "[stream1out2]pad=width=640:height=427:x=(640-iw)/2:y=(427-ih)/2:color=#00000000,trim=duration=1,select=lte(n\\,30)[stream1ending];" +
            "[stream2out1]pad=width=640:height=427:x=(640-iw)/2:y=(427-ih)/2:color=#00000000,trim=duration=2,select=lte(n\\,60)[stream2overlaid];" +
            "[stream2out2]pad=width=640:height=427:x=(640-iw)/2:y=(427-ih)/2:color=#00000000,trim=duration=1,select=lte(n\\,30),split=2[stream2starting][stream2ending];" +
            "[stream3out1]pad=width=640:height=427:x=(640-iw)/2:y=(427-ih)/2:color=#00000000,trim=duration=2,select=lte(n\\,60)[stream3overlaid];" +
            "[stream3out2]pad=width=640:height=427:x=(640-iw)/2:y=(427-ih)/2:color=#00000000,trim=duration=1,select=lte(n\\,30)[stream3starting];" +
            "[stream2starting][stream1ending]blend=all_expr=\'if(gte(X,(W/2)*T/1)*lte(X,W-(W/2)*T/1),B,A)\':shortest=1[stream2blended];" +
            "[stream3starting][stream2ending]blend=all_expr=\'if(gte(X,(W/2)*T/1)*lte(X,W-(W/2)*T/1),B,A)\':shortest=1[stream3blended];" +
            "[stream1overlaid][stream2blended][stream2overlaid][stream3blended][stream3overlaid]concat=n=5:v=1:a=0,scale=w=640:h=424,format=yuv420p[video]" +
            " -map [video] -vsync 2 -async 1 " + customOptions + "-c:v " + videoCodec.toLowerCase() + " -r 30 " + videoFilePath;
    }

    resourcePath(resourceName) {
        if (Platform.OS === 'ios') {
            return iosResourcePath(resourceName);
        } else {
            return androidResourcePath(resourceName);
        }
    }

}

export const VideoUtil = new VideoClass();
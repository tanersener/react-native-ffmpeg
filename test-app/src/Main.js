import React from 'react';
import { Platform, ScrollView, StyleSheet, Text, TextInput, TouchableOpacity, View } from 'react-native';
import { createBottomTabNavigator } from 'react-navigation';
import { RNFFmpeg } from 'react-native-ffmpeg';

// import RNFS from 'react-native-fs';
/*
RNFS.readDir(RNFS.DocumentDirectoryPath) // On Android, use "RNFS.DocumentDirectoryPath" (MainBundlePath is not defined)
    .then((result) => {
        console.log('GOT RESULT', result);

        // stat the first file
        return Promise.all([RNFS.stat(result[0].path), result[0].path]);
    })
    .then((statResult) => {
        if (statResult[0].isFile()) {
            // if we have a file, read it
            return RNFS.readFile(statResult[1], 'utf8');
        }

        return 'no file';
    })
    .then((contents) => {
        // log the file contents
        console.log(contents);
    })
    .catch((err) => {
        console.log(err.message, err.code);
    });
*/

class CommandScreen extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            command: '',
            commandOutput: ''
        };

        RNFFmpeg.enableLogCallback(this.logCallback);
    }

    render() {
        return (
            <View style={commandScreenStyles.screenStyle}>
                <View style={commandScreenStyles.headerViewStyle}>
                    <Text
                        style={commandScreenStyles.headerTextStyle}>
                        ReactNativeFFmpegTest
                    </Text>
                </View>
                <View style={commandScreenStyles.commandTextViewStyle}>
                    <TextInput
                        style={commandScreenStyles.commandTextInputStyle}
                        autoCapitalize='none'
                        autoCorrect={false}
                        placeholder="Enter command"
                        underlineColorAndroid="transparent"
                        onChangeText={(command) => this.setState({command})}
                        value={this.state.command}
                    />
                </View>
                <View style={commandScreenStyles.runViewStyle}>
                    <TouchableOpacity
                        style={commandScreenStyles.runButtonStyle}
                        onPress={this.run}>
                        <Text style={commandScreenStyles.buttonTextStyle}>RUN</Text>
                    </TouchableOpacity>
                </View>
                <View style={commandScreenStyles.commandOutputViewStyle}>
                    <ScrollView style={commandScreenStyles.commandOutputScrollViewStyle}>
                        <Text style={commandScreenStyles.commandOutputTextStyle}>{this.state.commandOutput}</Text>
                    </ScrollView>
                </View>
            </View>
        );
    };

    logCallback = (logData) => {
        this.setState({commandOutput: this.state.commandOutput + logData.log});
    };

    run = () => {

        // CLEAR COMMAND OUTPUT FIRST
        this.setState({commandOutput:''});

        console.log("Testing COMMAND.");

        console.log("FFmpeg process started with arguments");
        console.log(this.state.command);

        if ((this.state.command !== undefined) && (this.state.command.length > 0)) {
            RNFFmpeg.execute(this.state.command)
                .then(data => {
                    console.log("FFmpeg process exited with rc " + data.rc);
                });
        }
    };

}

class VideoScreen extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            videoCodec: 'mpeg4'
        };
    }

    render() {
        return (
            <View style={videoScreenStyles.screenStyle}>
                <View style={videoScreenStyles.headerViewStyle}>
                    <Text
                        style={videoScreenStyles.headerTextStyle}>
                        ReactNativeFFmpegTest
                    </Text>
                </View>
                <View style={videoScreenStyles.videoCodecTextViewStyle}>
                    <TextInput
                        style={videoScreenStyles.videoCodecTextInputStyle}
                        autoCapitalize='none'
                        autoCorrect={false}
                        placeholder="video codec"
                        underlineColorAndroid="transparent"
                        onChangeText={(videoCodec) => this.setState({videoCodec})}
                        value={this.state.videoCodec}
                    />
                </View>
                <View style={videoScreenStyles.createViewStyle}>
                    <TouchableOpacity
                        style={videoScreenStyles.createButtonStyle}
                        onPress={this.createVideo}>
                        <Text style={videoScreenStyles.buttonTextStyle}>CREATE</Text>
                    </TouchableOpacity>
                </View>
                <View style={videoScreenStyles.logTextViewStyle}>
                    <TextInput
                        style={videoScreenStyles.logTextInputStyle}
                        autoCapitalize='none'
                        autoCorrect={false}
                        dataDetectorTypes='none'
                        underlineColorAndroid="transparent"
                        multiline={true}
                        editable={false}
                    />
                </View>
            </View>
        );
    }

    createVideo = () => {
        RNFFmpeg.execute(this.state.command)
            .then(() => {
                // this.setState({output: 'Process exited with rc 0.'});
            })
            .catch(rc => {
                // this.setState({output: 'Process exited with rc ' + rc + "."});
            });
    };

    playVideo = () => {
    };

}

const TabNavigator = createBottomTabNavigator(
    {
        COMMAND: CommandScreen,
        VIDEO: VideoScreen,
    },
    {
        tabBarOptions: {
            activeTintColor: 'dodgerblue',
            inactiveTintColor: 'gray',
            showIcon: 'false',
            labelStyle: {
                fontSize: 12,
                fontWeight: 'bold',
                flex: 1,
                textAlign: 'center',
                marginBottom: 12
            }
        },
    }
);

export default class Main extends React.Component {
    render() {
        return <TabNavigator/>;
    }
}

const commandScreenStyles = StyleSheet.create({
    screenStyle: {
        flex: 1,
        justifyContent: 'flex-start',
        alignItems: 'stretch',
        marginTop: Platform.select({ios: 20, android: 0})
    },
    headerViewStyle: {
        paddingTop: 16,
        paddingBottom: 10,
        backgroundColor: '#F46842'
    },
    headerTextStyle: {
        alignSelf: "center",
        height: 32,
        fontSize: 18,
        fontWeight: 'bold',
        color: '#fff',
        borderColor: 'lightgray',
        borderRadius: 5,
        borderWidth: 0
    },
    commandTextViewStyle: {
        paddingTop: 40,
        paddingBottom: 40,
        paddingRight: 20,
        paddingLeft: 20
    },
    commandTextInputStyle: {
        height: 36,
        fontSize: 12,
        borderColor: '#3498db',
        borderRadius: 5,
        borderWidth: 1
    },
    runViewStyle: {
        alignSelf: "center",
        paddingBottom: 20
    },
    runButtonStyle: {
        justifyContent: 'center',
        width: 100,
        height: 38,
        backgroundColor: '#2ecc71',
        borderRadius: 5
    },
    buttonTextStyle: {
        textAlign: "center",
        fontSize: 14,
        fontWeight: 'bold',
        color: '#fff'
    },
    commandOutputViewStyle: {
        padding: 20
    },
    commandOutputScrollViewStyle: {
        padding: 4,
        backgroundColor: '#f1c40f',
        borderColor: '#f39c12',
        borderRadius: 5,
        borderWidth: 1,
        height: 200,
        maxHeight: 200
    },
    commandOutputTextStyle: {
        color: 'black'
    }
});

const videoScreenStyles = StyleSheet.create({
    screenStyle: {
        flex: 1,
        justifyContent: 'flex-start',
        alignItems: 'stretch',
        marginTop: Platform.select({ios: 20, android: 0})
    },
    headerViewStyle: {
        paddingTop: 16,
        paddingBottom: 10,
        backgroundColor: '#F46842'
    },
    headerTextStyle: {
        alignSelf: "center",
        height: 32,
        fontSize: 18,
        fontWeight: 'bold',
        color: '#fff',
        borderColor: 'lightgray',
        borderRadius: 5,
        borderWidth: 0
    },
    videoCodecTextViewStyle: {
        paddingTop: 40,
        paddingBottom: 40,
        width: 100,
        alignSelf: "center"
    },
    videoCodecTextInputStyle: {
        height: 36,
        fontSize: 12,
        borderColor: '#3498db',
        borderRadius: 5,
        borderWidth: 1,
        textAlign: 'center'
    },
    createViewStyle: {
        alignSelf: "center",
        paddingBottom: 20
    },
    createButtonStyle: {
        justifyContent: 'center',
        width: 100,
        height: 38,
        backgroundColor: '#2ecc71',
        borderRadius: 5
    },
    buttonTextStyle: {
        textAlign: "center",
        fontSize: 14,
        fontWeight: 'bold',
        color: '#fff'
    },
    logTextViewStyle: {
        padding: 20
    },
    logTextInputStyle: {
        backgroundColor: '#ecf0f1',
        textAlignVertical: 'top',
        color: 'black',
        borderColor: '#bdc3c7',
        borderRadius: 5,
        borderWidth: 1,
        height: 200,
        maxHeight: 200
    }
});

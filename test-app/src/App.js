/*!
 * MIT License
 *
 * Copyright (c) 2018 Taner Sener
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

import React from 'react';
import { Platform, StyleSheet, Text, TextInput, TouchableOpacity, View } from 'react-native';
import { createBottomTabNavigator } from 'react-navigation';
import { reactNativeFFmpeg } from 'react-native-ffmpeg';

class CommandScreen extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            command: '',
            output: ''
        };

        reactNativeFFmpeg.enableLogEvents();
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
                <View style={commandScreenStyles.logTextViewStyle}>
                    <TextInput
                        style={commandScreenStyles.logTextInputStyle}
                        autoCapitalize='none'
                        autoCorrect={false}
                        dataDetectorTypes='none'
                        underlineColorAndroid="transparent"
                        multiline={true}
                        editable={false}
                        onChangeText={(output) => this.setState({output})}
                        value={this.state.output}
                    />
                </View>
            </View>
        );
    }

    run = () => {
        if ((this.state.command !== undefined) && (this.state.command.length > 0)) {
            reactNativeFFmpeg.execute(this.state.command.split(' '))
                .then(() => {
                    this.setState({output: 'Process exited with rc 0.'});
                })
                .catch(rc => {
                    this.setState({output: 'Process exited with rc ' + rc + "."});
                });
        }
    };

}

class SlideshowScreen extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            playDisabled: true
        };
    }

    render() {
        return (
            <View style={slideshowScreenStyles.screenStyle}>
                <View style={slideshowScreenStyles.headerViewStyle}>
                    <Text
                        style={slideshowScreenStyles.headerTextStyle}>
                        ReactNativeFFmpegTest
                    </Text>
                </View>
                <View style={slideshowScreenStyles.createViewStyle}>
                    <TouchableOpacity
                        style={slideshowScreenStyles.createButtonStyle}
                        onPress={this.createSlideshow}>
                        <Text style={slideshowScreenStyles.buttonTextStyle}>CREATE</Text>
                    </TouchableOpacity>
                </View>
                <View style={slideshowScreenStyles.playViewStyle}>
                    <TouchableOpacity
                        style={slideshowScreenStyles.playButtonStyle}
                        disabled={this.state.playDisabled}
                        onPress={this.playSlideshow}>
                        <Text style={slideshowScreenStyles.buttonTextStyle}>PLAY</Text>
                    </TouchableOpacity>
                </View>
                <View style={slideshowScreenStyles.logTextViewStyle}>
                    <TextInput
                        style={slideshowScreenStyles.logTextInputStyle}
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

    createSlideshow = () => {
        reactNativeFFmpeg.execute(this.state.command.split(' '))
            .then(() => {
                this.setState({output: 'Process exited with rc 0.'});
            })
            .catch(rc => {
                this.setState({output: 'Process exited with rc ' + rc + "."});
            });
    };

    playSlideshow = () => {
    };

}

const TabNavigator = createBottomTabNavigator(
    {
        COMMAND: CommandScreen,
        SLIDESHOW: SlideshowScreen,
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

export default class App extends React.Component {
    render() {
        return <TabNavigator/>;
    }
}

const slideshowScreenStyles = StyleSheet.create({
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
    createViewStyle: {
        paddingTop: 40,
        alignSelf: "center",
        paddingBottom: 20
    },
    createButtonStyle: {
        justifyContent: 'center',
        width: 100,
        height: 38,
        backgroundColor: '#E3E3E3',
        borderRadius: 5
    },
    playViewStyle: {
        paddingTop: 20,
        alignSelf: "center",
        paddingBottom: 20
    },
    playButtonStyle: {
        justifyContent: 'center',
        width: 100,
        height: 38,
        backgroundColor: '#E3E3E3',
        borderRadius: 5
    },
    buttonTextStyle: {
        textAlign: "center",
        fontSize: 14
    },
    logTextViewStyle: {
        padding: 20
    },
    logTextInputStyle: {
        backgroundColor: '#E3E3E3',
        textAlignVertical: 'top',
        color: 'black',
        borderColor: 'lightgray',
        borderRadius: 5,
        borderWidth: 1,
        height: 200,
        maxHeight: 200
    }
});

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
        borderColor: 'lightgray',
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
        backgroundColor: '#E3E3E3',
        borderRadius: 5
    },
    buttonTextStyle: {
        textAlign: "center",
        fontSize: 14
    },
    logTextViewStyle: {
        padding: 20
    },
    logTextInputStyle: {
        backgroundColor: '#E3E3E3',
        textAlignVertical: 'top',
        color: 'black',
        borderColor: 'lightgray',
        borderRadius: 5,
        borderWidth: 1,
        height: 200,
        maxHeight: 200
    }
});

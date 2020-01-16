/*
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

package com.arthenica.reactnative;

import android.os.AsyncTask;
import android.util.Log;

import com.arthenica.mobileffmpeg.FFprobe;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RNFFmpegExecuteFFprobeAsyncArgumentsTask extends AsyncTask<String, Integer, Integer> {

    private final Promise promise;
    private final String[] argumentsArray;

    RNFFmpegExecuteFFprobeAsyncArgumentsTask(final Promise promise, final ReadableArray... readableArrays) {
        this.promise = promise;

        /* PREPARING ARGUMENTS */
        final List<String> arguments = new ArrayList<>();
        if ((readableArrays != null) && (readableArrays.length > 0)) {
            final ReadableArray readableArray = readableArrays[0];

            for (int i = 0; i < readableArray.size(); i++) {
                final ReadableType type = readableArray.getType(i);

                if (type == ReadableType.String) {
                    arguments.add(readableArray.getString(i));
                }
            }
        }

        this.argumentsArray = arguments.toArray(new String[0]);
    }

    @Override
    protected Integer doInBackground(final String... unusedArgs) {
        Log.d(RNFFmpegModule.LIBRARY_NAME, String.format("Running FFprobe with arguments: %s.", Arrays.toString(argumentsArray)));

        int rc = FFprobe.execute(argumentsArray);

        Log.d(RNFFmpegModule.LIBRARY_NAME, String.format("FFprobe exited with rc: %d", rc));

        return rc;
    }

    @Override
    protected void onPostExecute(final Integer rc) {
        promise.resolve(RNFFmpegModule.toIntMap(RNFFmpegModule.KEY_RC, rc));
    }

}

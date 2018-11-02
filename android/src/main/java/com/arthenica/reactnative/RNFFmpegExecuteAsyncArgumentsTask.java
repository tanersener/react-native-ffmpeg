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

import com.arthenica.mobileffmpeg.FFmpeg;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RNFFmpegExecuteAsyncArgumentsTask extends AsyncTask<ReadableArray, Integer, Integer> {

    private final Promise promise;

    public RNFFmpegExecuteAsyncArgumentsTask(final Promise promise) {
        this.promise = promise;
    }

    @Override
    protected Integer doInBackground(final ReadableArray... readableArrays) {
        int rc = -1;

        if ((readableArrays != null) && (readableArrays.length > 0)) {
            final ReadableArray readableArray = readableArrays[0];

            /* PREPARING ARGUMENTS */
            final List<String> arguments = new ArrayList<>();
            for (int i = 0; i < readableArray.size(); i++) {
                final ReadableType type = readableArray.getType(i);

                if (type == ReadableType.String) {
                    arguments.add(readableArray.getString(i));
                }
            }

            final String[] argumentsArray = arguments.toArray(new String[arguments.size()]);

            Log.d(RNFFmpegModule.LIBRARY_NAME, String.format("Running FFmpeg with arguments: %s.", Arrays.toString(argumentsArray)));

            rc = FFmpeg.execute(argumentsArray);

            Log.d(RNFFmpegModule.LIBRARY_NAME, String.format("FFmpeg exited with rc: %d", rc));
        }

        return rc;
    }

    @Override
    protected void onPostExecute(final Integer rc) {
        promise.resolve(RNFFmpegModule.toIntMap(RNFFmpegModule.KEY_RC, rc));
    }

}

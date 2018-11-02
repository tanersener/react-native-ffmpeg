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

public class RNFFmpegExecuteAsyncCommandTask extends AsyncTask<String, Integer, Integer> {

    private String delimiter;
    private final Promise promise;

    public RNFFmpegExecuteAsyncCommandTask(final String delimiter, final Promise promise) {
        if (delimiter == null) {
            this.delimiter = " ";
        } else {
            this.delimiter = delimiter;
        }

        this.promise = promise;
    }

    @Override
    protected Integer doInBackground(final String... strings) {
        int rc = -1;

        if ((strings != null) && (strings.length > 0)) {
            final String command = strings[0];

            Log.d(RNFFmpegModule.LIBRARY_NAME, String.format("Running FFmpeg command: %s with delimiter %s.", command, delimiter));

            rc = FFmpeg.execute(command, delimiter);

            Log.d(RNFFmpegModule.LIBRARY_NAME, String.format("FFmpeg exited with rc: %d", rc));
        }

        return rc;
    }

    @Override
    protected void onPostExecute(final Integer rc) {
        promise.resolve(RNFFmpegModule.toIntMap(RNFFmpegModule.KEY_RC, rc));
    }

}

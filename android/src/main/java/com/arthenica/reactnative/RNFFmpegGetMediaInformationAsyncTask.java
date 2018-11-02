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
import com.arthenica.mobileffmpeg.MediaInformation;
import com.facebook.react.bridge.Promise;

public class RNFFmpegGetMediaInformationAsyncTask extends AsyncTask<String, Integer, MediaInformation> {

    private Double timeout;
    private final Promise promise;

    public RNFFmpegGetMediaInformationAsyncTask(final Double timeout, final Promise promise) {
        this.timeout = timeout;
        this.promise = promise;
    }

    @Override
    protected MediaInformation doInBackground(final String... strings) {
        MediaInformation mediaInformation = null;

        if ((strings != null) && (strings.length > 0)) {
            final String path = strings[0];

            if (timeout == null) {
                Log.d(RNFFmpegModule.LIBRARY_NAME, String.format("Getting media information for %s", path));
                mediaInformation = FFmpeg.getMediaInformation(path);
            } else {
                Log.d(RNFFmpegModule.LIBRARY_NAME, String.format("Getting media information for %s with timeout %d.", path, timeout.longValue()));
                mediaInformation = FFmpeg.getMediaInformation(path, timeout.longValue());
            }
        }

        return mediaInformation;
    }

    @Override
    protected void onPostExecute(final MediaInformation mediaInformation) {
        promise.resolve(RNFFmpegModule.toMediaInformationMap(mediaInformation));
    }

}

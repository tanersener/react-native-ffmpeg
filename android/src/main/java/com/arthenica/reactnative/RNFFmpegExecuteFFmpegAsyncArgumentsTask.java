/*
 * Copyright (c) 2018-2020 Taner Sener
 *
 * This file is part of ReactNativeFFmpeg.
 *
 * ReactNativeFFmpeg is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * ReactNativeFFmpeg is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with ReactNativeFFmpeg.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.arthenica.reactnative;

import android.os.AsyncTask;

import com.arthenica.mobileffmpeg.FFmpeg;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReadableArray;

public class RNFFmpegExecuteFFmpegAsyncArgumentsTask extends AsyncTask<String, Integer, Integer> {

    private final Promise promise;
    private final String[] argumentsArray;

    RNFFmpegExecuteFFmpegAsyncArgumentsTask(final Promise promise, final ReadableArray... readableArrays) {
        this.promise = promise;

        /* PREPARING ARGUMENTS */
        if ((readableArrays != null) && (readableArrays.length > 0)) {
            this.argumentsArray = RNFFmpegModule.toArgumentsArray(readableArrays[0]);
        } else {
            this.argumentsArray = new String[0];
        }
    }

    @Override
    protected Integer doInBackground(final String... unusedArgs) {
        return FFmpeg.execute(argumentsArray);
    }

    @Override
    protected void onPostExecute(final Integer rc) {
        promise.resolve(rc);
    }

}

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

import com.arthenica.mobileffmpeg.FFprobe;
import com.arthenica.mobileffmpeg.MediaInformation;
import com.facebook.react.bridge.Promise;

public class RNFFmpegGetMediaInformationAsyncTask extends AsyncTask<String, Integer, MediaInformation> {

    private final String path;
    private final Promise promise;

    RNFFmpegGetMediaInformationAsyncTask(final String path, final Promise promise) {
        this.path = path;
        this.promise = promise;
    }

    @Override
    protected MediaInformation doInBackground(final String... unusedArgs) {
        return FFprobe.getMediaInformation(path);
    }

    @Override
    protected void onPostExecute(final MediaInformation mediaInformation) {
        promise.resolve(RNFFmpegModule.toMediaInformationMap(mediaInformation));
    }

}

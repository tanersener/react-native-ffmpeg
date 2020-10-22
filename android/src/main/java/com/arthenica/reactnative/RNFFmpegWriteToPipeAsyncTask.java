/*
 * Copyright (c) 2020 Taner Sener
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
import android.util.Log;

import com.facebook.react.bridge.Promise;

import java.io.IOException;

import static com.arthenica.reactnative.RNFFmpegModule.LIBRARY_NAME;

public class RNFFmpegWriteToPipeAsyncTask extends AsyncTask<String, Integer, Integer> {

    private final String inputPath;
    private final String namedPipePath;
    private final Promise promise;

    RNFFmpegWriteToPipeAsyncTask(final String inputPath, final String namedPipePath, final Promise promise) {
        this.inputPath = inputPath;
        this.namedPipePath = namedPipePath;
        this.promise = promise;
    }

    @Override
    protected Integer doInBackground(final String... unusedArgs) {
        try {
            final String asyncCommand = "cat " + inputPath + " > " + namedPipePath;
            Log.d(LIBRARY_NAME, String.format("Starting copy %s to pipe %s operation.", inputPath, namedPipePath));

            final Process process = Runtime.getRuntime().exec(new String[]{"sh", "-c", asyncCommand});
            int rc = process.waitFor();

            Log.d(LIBRARY_NAME, String.format("Copying %s to pipe %s operation completed with rc %d.", inputPath, namedPipePath, rc));

            return rc;
        } catch (final IOException | InterruptedException e) {
            Log.e(LIBRARY_NAME, String.format("Copy %s to %s failed with error.", inputPath, namedPipePath), e);
            return -1;
        }
    }

    @Override
    protected void onPostExecute(final Integer integer) {
        promise.resolve(integer);
    }

}

/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.assignmentmanagerapp.utilities;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * A class to generate URL to literature API
 */
public class NetworkUtils {

    // API Used: https://www.gbif.org/developer/literature
    final static String LITERATURE_BASE_URL =
            "https://api.gbif.org/v1/literature/search";

    final static String PARAM_QUERY = "q";

    /**
     * This method constructs the Url to be searched
     * @param literatureSearchQuery The user's entered query
     * @return The url to be searched
     */
    public static URL buildUrl(String literatureSearchQuery) {
        Uri builtUri = Uri.parse(LITERATURE_BASE_URL).buildUpon()
                .appendQueryParameter(PARAM_QUERY, literatureSearchQuery)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    /**
     * This method fetches the response from the Http connecions
     * @param url the url to be searched
     * @return the output from the url
     * @throws IOException
     */
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}
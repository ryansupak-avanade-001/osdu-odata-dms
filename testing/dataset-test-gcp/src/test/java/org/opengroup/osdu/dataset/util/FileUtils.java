/*
 * Copyright 2021 Google LLC
 * Copyright 2021 EPAM Systems, Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.opengroup.osdu.dataset.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import org.springframework.util.StreamUtils;

public class FileUtils {

	public static String readFileFromResources(String filePath) throws IOException {
		ClassLoader classloader = Thread.currentThread().getContextClassLoader();
		InputStream inStream = classloader.getResourceAsStream(filePath);
		BufferedReader br = new BufferedReader(new InputStreamReader(inStream));
		StringBuilder stringBuilder = new StringBuilder();

		String eachLine = "";
		while ((eachLine = br.readLine()) != null) {
			stringBuilder.append(eachLine);
		}

		return stringBuilder.toString();
	}

	public static String readFileFromUrl(URL fileURL) throws IOException {
		URLConnection conn = fileURL.openConnection();
		return StreamUtils.copyToString(conn.getInputStream(), StandardCharsets.UTF_8);
	}
}

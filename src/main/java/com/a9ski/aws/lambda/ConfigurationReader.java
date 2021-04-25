/*-
 * #%L
 * LambdaHandlers
 * %%
 * Copyright (C) 2021 Kiril Arabadzhiyski
 * %%
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
 * #L%
 */
package com.a9ski.aws.lambda;

import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * Reads lambda configuration from map.
 */
public class ConfigurationReader {
	/**
	 * Key in configuration map that contains the stage name.
	 */
	public static final String STAGE_ENVIRONMENT_VARIABLE_KEY = "STAGE";
	/**
	 * Key in configuration map that contains the stage configuration
	 */
	public static final String CONFIGURATION_ENVIRONMENT_VARIABLE_KEY = "CONFIGURATION";

	/**
	 * Name for the common stage.
	 */
	public static final String COMMON_STAGE = "all";

	private final Gson gson;

	/**
	 * Creates a new reader with default Gson.
	 */
	public ConfigurationReader() {
		this(new GsonBuilder().create());
	}

	/**
	 * Creates new reader with given gson.
	 * @param gson the Gson.
	 */
	public ConfigurationReader(final Gson gson) {
		super();
		this.gson = gson;
	}

	/**
	 * Reads the stage name from the map.
	 *
	 * @param map
	 *            with variables.
	 * @return stage name.
	 */
	public String readStageName(final Map<String, String> map) {
		return map.getOrDefault(STAGE_ENVIRONMENT_VARIABLE_KEY, "dev");
	}

	/**
	 * Read the configuration from environment variables.
	 *
	 * @return the configuration as json object.
	 */
	public JsonObject readConfiguration() {
		return readConfiguration(System.getenv());
	}

	/**
	 * Reads the configuration from map.
	 *
	 * @param map
	 *            map with variables
	 * @return the configuration as json object.
	 */
	public JsonObject readConfiguration(final Map<String, String> map) {
		final String stageName = readStageName(map);
		return readConfiguration(map, stageName);
	}

	/**
	 * Reads the configuration from map.
	 *
	 * @param map
	 *            map with variables
	 * @param stageName
	 *            the stage name
	 * @return the configuration as json object.
	 */
	public JsonObject readConfiguration(final Map<String, String> map, final String stageName) {
		final String val = map.getOrDefault(CONFIGURATION_ENVIRONMENT_VARIABLE_KEY, "{}");
		final JsonObject config = gson.fromJson(val, JsonObject.class);
		final JsonObject result = getValue(config, COMMON_STAGE);
		final JsonObject stage = getValue(config, stageName);

		for (final Map.Entry<String, JsonElement> entry : stage.entrySet()) {
			result.add(entry.getKey(), entry.getValue().deepCopy());
		}
		return result;

	}

	private JsonObject getValue(final JsonObject j, final String key) {
		if (j.has(key) && j.get(key).isJsonObject()) {
			return j.get(key).getAsJsonObject();
		} else {
			return new JsonObject();
		}
	}
}

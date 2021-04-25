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
	public static final String STAGE_ENVIRONMENT_VARIABLE_KEY = "STAGE";
	public static final String CONFIGURATION_ENVIRONMENT_VARIABLE_KEY = "CONFIGURATION";

	public static final String COMMON_STAGE = "all";

	private final Gson gson;

	public ConfigurationReader() {
		this(new GsonBuilder().create());
	}

	public ConfigurationReader(Gson gson) {
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
	public String readStageName(Map<String, String> map) {
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
	public JsonObject readConfiguration(Map<String, String> map) {
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
	public JsonObject readConfiguration(Map<String, String> map, String stageName) {
		final String val = map.getOrDefault(CONFIGURATION_ENVIRONMENT_VARIABLE_KEY, "{}");
		final JsonObject config = gson.fromJson(val, JsonObject.class);
		final JsonObject result = getValue(config, COMMON_STAGE);
		final JsonObject stage = getValue(config, stageName);

		for (Map.Entry<String, JsonElement> entry : stage.entrySet()) {
			result.add(entry.getKey(), entry.getValue().deepCopy());
		}
		return result;

	}

	private JsonObject getValue(JsonObject j, String key) {
		if (j.has(key) && j.get(key).isJsonObject()) {
			return j.get(key).getAsJsonObject();
		} else {
			return new JsonObject();
		}
	}
}

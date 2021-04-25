package com.a9ski.aws.lambda;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

public class ConfigurationReaderTest {

	@Test
	public void testReadConfigurationMapOfStringString() {
		final Map<String, String> env = new HashMap<>();
		env.put("STAGE", "production");
		env.put("CONFIGURATION", "{\"all\":{\"field1\":\"f1\", \"field2\":42, \"field3\":\"f3\"}, \"production\":{\"field3\":\"p3\"} }");
		
		final ConfigurationReader r = new ConfigurationReader(new GsonBuilder().create());
		final JsonObject config = r.readConfiguration(env);
		assertEquals("f1", config.get("field1").getAsString());
		assertEquals("p3", config.get("field3").getAsString());
		assertEquals(42, config.get("field2").getAsInt());
	}

}

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

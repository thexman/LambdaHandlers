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
package com.a9ski.aws.lambda.gson;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

import com.a9ski.aws.lambda.gson.pojo.TestPojo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonOutputWriterTest {

private final Gson gson = new GsonBuilder().create();

	@Test
	public void testWrite() throws IOException {
		final TestPojo expected = TestPojo.builder().field1("field1").field2(42).build();
		final GsonOutputWriter<TestPojo> w = new GsonOutputWriter<>();
		final ByteArrayOutputStream bos = new ByteArrayOutputStream();
		w.write(expected, bos);
		final String json = new String(bos.toByteArray(), StandardCharsets.UTF_8);
		assertEquals(expected, gson.fromJson(json, TestPojo.class));
	}
}

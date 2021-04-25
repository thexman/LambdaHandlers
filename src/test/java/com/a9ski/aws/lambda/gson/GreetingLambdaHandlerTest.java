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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

import com.amazonaws.services.lambda.runtime.Context;

public class GreetingLambdaHandlerTest {
	@Test
	public void testInvokeLambda() throws IOException {
		GreetingLambdaHandler h = new GreetingLambdaHandler();
		Context ctx = null;
		String input = "{\"firstName\":\"John\", \"lastName\": \"Doe\"}";
		ByteArrayInputStream bis = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		h.handleRequest(bis, bos, ctx);
		assertEquals("{\"greeting\":\"Hello John Doe!\",\"language\":\"English\"}", bos.toString(StandardCharsets.UTF_8));
	}
}

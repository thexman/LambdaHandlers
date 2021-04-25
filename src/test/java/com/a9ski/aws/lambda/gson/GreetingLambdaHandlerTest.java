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

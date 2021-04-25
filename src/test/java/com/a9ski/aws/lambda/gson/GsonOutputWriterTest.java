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

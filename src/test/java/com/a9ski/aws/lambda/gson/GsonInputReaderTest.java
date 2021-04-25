package com.a9ski.aws.lambda.gson;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

import com.a9ski.aws.lambda.gson.pojo.TestPojo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonInputReaderTest {

	private final Gson gson = new GsonBuilder().create();
	
	@Test
	public void testRead() throws IOException {
		final TestPojo expected = TestPojo.builder().field1("field1").field2(42).build();
		final GsonInputReader<TestPojo> r = new GsonInputReader<TestPojo>(TestPojo.class);
		final ByteArrayInputStream is = new ByteArrayInputStream(gson.toJson(expected).getBytes(StandardCharsets.UTF_8));
		TestPojo actual = r.read(is);
		assertEquals(expected, actual);
	}

}

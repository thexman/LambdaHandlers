package com.a9ski.aws.lambda.gson.pojo;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TestPojo {
	private final String field1;
	private final int field2;
}

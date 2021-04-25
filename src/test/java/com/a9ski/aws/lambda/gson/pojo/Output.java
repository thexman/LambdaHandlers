package com.a9ski.aws.lambda.gson.pojo;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Output {
	private final String greeting;
	private final String language;
}

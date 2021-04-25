package com.a9ski.aws.lambda.gson.pojo;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Input {
	private final String firstName;
	private final String lastName;
}
package com.a9ski.aws.lambda.gson;

import com.a9ski.aws.lambda.gson.pojo.Input;
import com.a9ski.aws.lambda.gson.pojo.Output;

public class GreetingLambdaHandler extends GsonLambdaHandler<Input, Output> {
	public GreetingLambdaHandler() {
		super(Input.class,
				(input, context) -> Output.builder()
					.language("English")
					.greeting(String.format("Hello %s %s!", input.getFirstName(), input.getLastName()))
					.build());
	}
}
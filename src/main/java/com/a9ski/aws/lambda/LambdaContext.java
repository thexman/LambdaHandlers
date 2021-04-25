package com.a9ski.aws.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.google.gson.JsonObject;

import lombok.Data;
import lombok.Builder;

/**
 * Extended object for storing useful data like lambda runtime context, user configuration, stage name.
 * 
 */
@Builder(toBuilder = true)
@Data
public class LambdaContext {
	private final Context context;
	private final JsonObject configuration;
	private final String stage;
}

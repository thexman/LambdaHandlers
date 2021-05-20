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

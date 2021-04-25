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

import com.a9ski.aws.lambda.DelegatingLambdaHandler;
import com.a9ski.aws.lambda.LambdaRequestHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Abstract class for handlers that use Gson for reading/writing input/output data.
 *
 * @param <I>
 *            the input class.
 * @param <O>
 *            the output class.
 */
public class GsonLambdaHandler<I, O> extends DelegatingLambdaHandler<I, O> {

	/**
	 * Creates a new handler for given <code>inputClass</code>. Uses default Gson object.
	 *
	 * @param inputClass
	 *            the input class.
	 * @param delegate
	 *            the request handler.
	 */
	public GsonLambdaHandler(final Class<I> inputClass, final LambdaRequestHandler<I, O> delegate) {
		this(inputClass, delegate, new GsonBuilder().create());
	}

	/**
	 * Creates a new handler.
	 *
	 * @param inputClass
	 *            the input class.
	 * @param delegate
	 *            the request handler.
	 * @param gson
	 *            the Gson object.
	 */
	public GsonLambdaHandler(final Class<I> inputClass, final LambdaRequestHandler<I, O> delegate, final Gson gson) {
		super(new GsonInputReader<>(inputClass, gson), new GsonOutputWriter<O>(gson), delegate);
	}
}

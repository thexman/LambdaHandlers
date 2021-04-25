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
package com.a9ski.aws.lambda;

import java.io.IOException;

import com.amazonaws.services.lambda.runtime.Context;

/**
 * Lambda handler that uses delegates for handling requests.
 *
 * @param <I>
 *            the input class.
 * @param <O>
 *            the output class.
 */
public class DelegatingLambdaHandler<I, O> extends LambdaHandler<I, O> {

	private final InputReader<I> inputReader;
	private final OutputWriter<O> outputWriter;
	private final LambdaRequestHandler<I, O> delegate;

	/**
	 * Creates new handler with given input reader, output writer and request handler delegate.
	 *
	 * @param inputReader
	 *            the input reader.
	 * @param outputWriter
	 *            the output writer.
	 * @param delegate
	 *            the request delegate.
	 */
	public DelegatingLambdaHandler(final InputReader<I> inputReader, final OutputWriter<O> outputWriter, final LambdaRequestHandler<I, O> delegate) {
		super();
		this.inputReader = inputReader;
		this.outputWriter = outputWriter;
		this.delegate = delegate;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected OutputWriter<O> createOutputWriter(final Context context) {
		return outputWriter;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected InputReader<I> createInputReader(final Context context) {
		return inputReader;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public O handleRequest(final I input, final LambdaContext context) throws IOException {
		return delegate.handleRequest(input, context);
	}

}

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

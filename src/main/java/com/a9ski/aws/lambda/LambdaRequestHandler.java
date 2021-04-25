package com.a9ski.aws.lambda;

import java.io.IOException;

/**
 * Interface for implementing lambda request handler.
 *
 * @param <I>
 *            the input class.
 * @param <O>
 *            the output class.
 */
@FunctionalInterface
public interface LambdaRequestHandler<I, O> {
	/**
	 * Handle lambda requests.
	 *
	 * @param input
	 *            the lambda input.
	 * @param context
	 *            the context object.
	 * @return output fromthe lambda invocation.
	 * @throws IOException
	 *             thrown if an error occurs.
	 */
	public O handleRequest(I input, LambdaContext context) throws IOException;

}

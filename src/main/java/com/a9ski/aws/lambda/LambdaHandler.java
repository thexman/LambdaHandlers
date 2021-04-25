package com.a9ski.aws.lambda;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.google.gson.GsonBuilder;

/**
 * Handler for lambda functions.
 *
 * @param <I>
 *            the class of the input object.
 * @param <O>
 *            the class of the outptu object.
 */
public abstract class LambdaHandler<I, O> implements RequestStreamHandler, LambdaRequestHandler<I, O> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void handleRequest(InputStream is, OutputStream os, Context context) throws IOException {
		try {
			final ConfigurationReader configReader = createConfigurationReader(context);
			final InputReader<I> reader = createInputReader(context);
			final OutputWriter<O> writer = createOutputWriter(context);
			final I input = reader.read(is);

			final LambdaContext ctx = LambdaContext.builder().configuration(configReader.readConfiguration(readEnvironmentVariables(context))).context(context).build();

			final O output = handleRequest(input, ctx);
			writer.write(output, os);
		} catch (final Exception ex) {
			handleException(ex);
		}
	}

	/**
	 * Read lambda environment variables.
	 *
	 * @param context
	 *            the lambda context.
	 * @return map with environment variables.
	 */
	protected Map<String, String> readEnvironmentVariables(Context context) {
		return System.getenv();
	}

	/**
	 * Creates a new configuration reader.
	 *
	 * @param context
	 *            the lambda context.
	 * @return a configuration reader
	 */
	protected ConfigurationReader createConfigurationReader(Context context) {
		return new ConfigurationReader(new GsonBuilder().create());
	}

	/**
	 * Handler for unexpected exceptions.
	 *
	 * @param ex
	 *            exception being thrown.
	 * @throws IOException
	 *             throws an IOException to Lambda runtime.
	 */
	protected void handleException(Exception ex) throws IOException {
		throw new IOException("Error occurred while handling lambda request", ex);
	}

	/**
	 * Creates a new output writer.
	 *
	 * @param context
	 *            the lambda context
	 * @return new output writer.
	 */
	protected abstract OutputWriter<O> createOutputWriter(Context context);

	/**
	 * Creates a new input reader.
	 *
	 * @param context
	 *            the lambda context.
	 * @return new input reader.
	 */
	protected abstract InputReader<I> createInputReader(Context context);

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
	public abstract O handleRequest(I input, LambdaContext context) throws IOException;

}

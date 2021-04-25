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
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
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
	public void handleRequest(final InputStream is, final OutputStream os, final Context context) throws IOException {
		try {
			final ConfigurationReader configReader = createConfigurationReader(context);
			final InputReader<I> reader = createInputReader(context);
			final OutputWriter<O> writer = createOutputWriter(context);
			final I input = reader.read(is);

			log(context, "Lambda input: " + input);

			//@formatter:off
			final LambdaContext ctx = LambdaContext.builder()
					.configuration(configReader.readConfiguration(readEnvironmentVariables(context)))
					.context(context)
					.build();
			//@formatter:on
			final O output = handleRequest(input, ctx);
			writer.write(output, os);

			log(context, "Lambda output: " + output);
		} catch (final Exception ex) {
			log(context, ex);
			handleException(ex);
		}
	}

	/**
	 * Logs message with LambdaLoggger from the context.
	 * @param context the lambda context.
	 * @param message the message to be logged.
	 */
	protected void log(final Context context, final String message) {
		if (context != null && context.getLogger() != null) {
			context.getLogger().log(message);
		}
	}

	/**
	 * Logs exception with LambdaLogger from the context.
	 * @param context the lambda context.
	 * @param ex the exception to be logged.
	 */
	protected void log(final Context context, final Exception ex) {
		try (StringWriter sw = new StringWriter()) {
			try (PrintWriter pw = new PrintWriter(sw)) {
				ex.printStackTrace(pw);
			}
			log(context, sw.toString());
		} catch (final IOException iex) {
			// should never happen, since StringWriter doesn't really close anything.
		}

	}

	/**
	 * Read lambda environment variables.
	 *
	 * @param context
	 *            the lambda context.
	 * @return map with environment variables.
	 */
	protected Map<String, String> readEnvironmentVariables(final Context context) {
		return System.getenv();
	}

	/**
	 * Creates a new configuration reader.
	 *
	 * @param context
	 *            the lambda context.
	 * @return a configuration reader
	 */
	protected ConfigurationReader createConfigurationReader(final Context context) {
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
	protected void handleException(final Exception ex) throws IOException {
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
	@Override
	public abstract O handleRequest(I input, LambdaContext context) throws IOException;

}

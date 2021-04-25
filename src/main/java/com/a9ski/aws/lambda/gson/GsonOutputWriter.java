package com.a9ski.aws.lambda.gson;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

import com.a9ski.aws.lambda.OutputWriter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Writes output to stream using Gson.
 *
 * @param <O>
 *            the output class.
 */
public class GsonOutputWriter<O> implements OutputWriter<O> {

	private final Gson gson;

	/**
	 * Creates a new writer using default Gson.
	 */
	public GsonOutputWriter() {
		this(new GsonBuilder().create());
	}

	/**
	 * Creates a new writer.
	 *
	 * @param gson
	 *            the Gson object.
	 */
	public GsonOutputWriter(final Gson gson) {
		super();
		this.gson = gson;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void write(final O output, final OutputStream os) throws IOException {
		final String json = gson.toJson(output);
		final byte[] bytes = json.getBytes(StandardCharsets.UTF_8);
		os.write(bytes);
	}

}

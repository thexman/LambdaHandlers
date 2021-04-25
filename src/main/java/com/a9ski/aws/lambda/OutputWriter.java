package com.a9ski.aws.lambda;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Writes output to stream.
 *
 * @param <O>
 *            the output class.
 */
public interface OutputWriter<O> {
	public void write(O output, OutputStream os) throws IOException;
}

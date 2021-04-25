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
	/**
	 * Writes object to the output stream.
	 *
	 * @param output
	 *            the object to be written.
	 * @param os
	 *            the output stream.
	 * @throws IOException
	 *             thrown if an error occurs.
	 */
	public void write(O output, OutputStream os) throws IOException;
}

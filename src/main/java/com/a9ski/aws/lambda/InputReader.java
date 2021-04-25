package com.a9ski.aws.lambda;

import java.io.IOException;
import java.io.InputStream;

/**
 * Reads input stream into object.
 *
 * @param <I>
 *            the input class.
 */
public interface InputReader<I> {
	/**
	 * Reads input stream into object
	 * 
	 * @param is
	 *            input stream to be read.
	 * @return parsed object.
	 * @throws IOException
	 *             thrown if an error occurs.
	 */
	public I read(InputStream is) throws IOException;
}

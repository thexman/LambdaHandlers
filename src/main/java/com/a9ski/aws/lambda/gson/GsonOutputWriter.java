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

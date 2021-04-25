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
import java.io.InputStream;
import java.io.InputStreamReader;

import com.a9ski.aws.lambda.InputReader;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Implementation of input reader with Gson.
 *
 * @param <I>
 *            the input class.
 */
public class GsonInputReader<I> implements InputReader<I> {

	private final Class<I> inputClass;
	private final Gson gson;

	/**
	 * Creates new reader for given input class using default Gson.
	 *
	 * @param inputClass
	 *            the input class.
	 */
	public GsonInputReader(final Class<I> inputClass) {
		this(inputClass, new GsonBuilder().create());
	}

	/**
	 * Creates a new reader.
	 *
	 * @param inputClass
	 *            the input class.
	 * @param gson
	 *            Gson object.
	 */
	public GsonInputReader(final Class<I> inputClass, final Gson gson) {
		super();
		this.inputClass = inputClass;
		this.gson = gson;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public I read(final InputStream is) throws IOException {
		try (var r = new InputStreamReader(is, "UTF-8")) {
			return gson.fromJson(r, inputClass);
		}
	}

}

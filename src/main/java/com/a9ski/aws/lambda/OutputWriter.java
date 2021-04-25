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

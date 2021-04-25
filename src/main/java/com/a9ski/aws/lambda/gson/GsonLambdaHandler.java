package com.a9ski.aws.lambda.gson;

import com.a9ski.aws.lambda.DelegatingLambdaHandler;
import com.a9ski.aws.lambda.LambdaRequestHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Abstract class for handlers that use Gson for reading/writing input/output data.
 *
 * @param <I>
 *            the input class.
 * @param <O>
 *            the output class.
 */
public class GsonLambdaHandler<I, O> extends DelegatingLambdaHandler<I, O> {

	/**
	 * Creates a new handler for given <tt>inputClass</tt>. Uses default Gson object.
	 * 
	 * @param inputClass
	 *            the input class.
	 * @param delegate
	 *            the request handler.
	 */
	public GsonLambdaHandler(Class<I> inputClass, LambdaRequestHandler<I, O> delegate) {
		this(inputClass, delegate, new GsonBuilder().create());
	}

	/**
	 * Creates a new handler.
	 * 
	 * @param inputClass
	 *            the input class.
	 * @param delegate
	 *            the request handler.
	 * @param gson
	 *            the Gson object.
	 */
	public GsonLambdaHandler(Class<I> inputClass, LambdaRequestHandler<I, O> delegate, Gson gson) {
		super(new GsonInputReader<I>(inputClass, gson), new GsonOutputWriter<O>(gson), delegate);
	}
}

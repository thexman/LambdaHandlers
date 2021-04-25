open module com.a9ski.aws.lambda {
	exports com.a9ski.aws.lambda;
	exports com.a9ski.aws.lambda.gson;

	requires transitive aws.lambda.java.core;
	requires transitive com.google.gson;

	requires lombok;
}
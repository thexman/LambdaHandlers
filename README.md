# LambdaHandlers

Library that provides convenient handler for implementing AWS Lambda functions with Java.

## Lambda handlers

`LambdaHandler` is an abstract class that requires 3 methofs to be implemented
- `createInputReader(context)`: responsible for creating the reader that converts input streams to java objects.
- `createOutputWriter(context)`: responsible for creating the writer that converts output objects to streams.
- `handleRequest(input, context)`: the actual lambda request handler.

`DelegatingLambdaHandler` is class that delegates the reading/writing to the reader and writer passes as parameters to the constructor. The requests are forwarded to `LambdaRequestHandler` passed as parameter to the constructor

`GsonLambdaHandler` is an class that uses Gson for reading and writing the inputs/outputs. All the requests are forwarded to the delegated handler.

## When to use what

Use `GsonLambdaHandler` when the input and output can be serialized with standard Gson object and the requests can be forwarded to a delegate. Use `DelegatingLambdaHandler` when the standard Gson cannot serialize the input/output objects correctly. Use `LambdaHandler` in cases when creation of the reader and writer depends on the lambda runtime `Context`. 

## Example

Consider following example of lambda that greets people

```
@Builder
@Data
public class Input {
	private final String firstName;
	private final String lastName;
}
...
@Builder
@Data
public class Output {
	private final String greeting;
	private final String language;
}
...
public class GreetingLambdaHandler extends GsonLambdaHandler<Input, Output> {
	public GreetingLambdaHandler() {
		super(Input.class,
				(input, context) -> Output.builder()
					.language("English")
					.greeting(String.format("Hello %s %s!", input.getFirstName(), input.getLastName()))
					.build());
	}
}
```
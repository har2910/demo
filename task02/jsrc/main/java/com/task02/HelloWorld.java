////package com.task02;
////
////import com.amazonaws.services.lambda.runtime.Context;
////import com.amazonaws.services.lambda.runtime.RequestHandler;
////import com.syndicate.deployment.annotations.lambda.LambdaHandler;
////import com.syndicate.deployment.model.RetentionSetting;
////
////import java.util.HashMap;
////import java.util.Map;
////
////@LambdaHandler(
////    lambdaName = "hello_world",
////	roleName = "hello_world-role",
////	isPublishVersion = true,
////	aliasName = "learn",
////	logsExpiration = RetentionSetting.SYNDICATE_ALIASES_SPECIFIED
////)
//@LambdaUrlConfig(
//		authType = AuthType.NONE,
//		invokeMode = InvokeMode.BUFFERED
//)
////public class HelloWorld implements RequestHandler<Object, Map<String, Object>> {
////
////	public Map<String, Object> handleRequest(Object request, Context context) {
////		System.out.println("Hello from lambda");
////		Map<String, Object> resultMap = new HashMap<String, Object>();
////		resultMap.put("statusCode", 200);
////		resultMap.put("message", "Hello from Lambda");
////		return resultMap;
////	}
////}
//
//import com.amazonaws.services.lambda.runtime.Context;
//import com.amazonaws.services.lambda.runtime.RequestHandler;
//import com.amazonaws.services.lambda.runtime.events.APIGatewayV2HTTPEvent;
//import com.amazonaws.services.lambda.runtime.events.APIGatewayV2HTTPResponse;
//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
//import com.syndicate.deployment.annotations.lambda.LambdaHandler;
//import com.syndicate.deployment.annotations.lambda.LambdaLayer;
//import com.syndicate.deployment.annotations.lambda.LambdaUrlConfig;
//import com.syndicate.deployment.model.Architecture;
//import com.syndicate.deployment.model.ArtifactExtension;
//import com.syndicate.deployment.model.DeploymentRuntime;
//import com.syndicate.deployment.model.RetentionSetting;
//import com.syndicate.deployment.model.lambda.url.AuthType;
//import com.syndicate.deployment.model.lambda.url.InvokeMode;
//
//import java.util.Map;
//import java.util.Optional;
//import java.util.function.Function;
//
//@LambdaHandler(
//		lambdaName = "hello-world",
//		roleName = "hello-world-role",
//		layers = {"sdk-layer"},
//		runtime = DeploymentRuntime.JAVA11,
//		architecture = Architecture.ARM64,
//		logsExpiration = RetentionSetting.SYNDICATE_ALIASES_SPECIFIED
//)
////@LambdaLayer(
////		layerName = "sdk-layer",
////		libraries = {"lib/commons-lang3-3.14.0.jar", "lib/gson-2.10.1.jar"},
////		runtime = DeploymentRuntime.JAVA11,
////		architectures = {Architecture.ARM64},
////		artifactExtension = ArtifactExtension.ZIP
////)
//@LambdaUrlConfig(
//		authType = AuthType.NONE,
//		invokeMode = InvokeMode.BUFFERED
//)
//public class HelloWorld implements RequestHandler<APIGatewayV2HTTPEvent, APIGatewayV2HTTPResponse> {
//
//	private static final int SC_OK = 200;
//	private static final int SC_BAD_REQUEST = 400;
//	private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
//	private final Map<String, String> responseHeaders = Map.of("Content-Type", "application/json");
//
//	private final Map<RouteKey, Function<APIGatewayV2HTTPEvent, APIGatewayV2HTTPResponse>> routeHandlers = Map.of(
//			new RouteKey("GET", "/hello"), this::handleGetHello
//	);
//
//	@Override
//	public APIGatewayV2HTTPResponse handleRequest(APIGatewayV2HTTPEvent requestEvent, Context context) {
//		RouteKey routeKey = new RouteKey(getMethod(requestEvent), getPath(requestEvent));
//		return routeHandlers.getOrDefault(routeKey, this::badRequestResponse).apply(requestEvent);
//	}
//
//	private APIGatewayV2HTTPResponse handleGetHello(APIGatewayV2HTTPEvent requestEvent) {
//		return buildResponse(SC_OK, Body.ok("Hello from Lambda"));
//	}
//
//	private APIGatewayV2HTTPResponse badRequestResponse(APIGatewayV2HTTPEvent requestEvent) {
//		return buildResponse(SC_BAD_REQUEST, Body.error(
//				"Bad request syntax or unsupported method. Request path: %s. HTTP method: %s"
//						.formatted(getPath(requestEvent), getMethod(requestEvent))
//		));
//	}
//
//	private APIGatewayV2HTTPResponse buildResponse(int statusCode, Object body) {
//		return APIGatewayV2HTTPResponse.builder()
//				.withStatusCode(statusCode)
//				.withHeaders(responseHeaders)
//				.withBody(gson.toJson(body))
//				.build();
//	}
//
//	private String getMethod(APIGatewayV2HTTPEvent requestEvent) {
//		return requestEvent.getRequestContext().getHttp().getMethod();
//	}
//
//	private String getPath(APIGatewayV2HTTPEvent requestEvent) {
//		return requestEvent.getRequestContext().getHttp().getPath();
//	}
//
//	private record RouteKey(String method, String path) {
//	}
//
//	private record Body(String message, String error) {
//		static Body ok(String message) {
//			return new Body(message, null);
//		}
//
//		static Body error(String error) {
//			return new Body(null, error);
//		}
//	}
//
//}


import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayV2HTTPEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayV2HTTPResponse;
import com.syndicate.deployment.annotations.lambda.LambdaHandler;
import com.syndicate.deployment.annotations.lambda.LambdaUrlConfig;
import com.syndicate.deployment.model.RetentionSetting;
import com.syndicate.deployment.model.lambda.url.AuthType;
import com.syndicate.deployment.model.lambda.url.InvokeMode;

import java.util.HashMap;
import java.util.Map;

@LambdaHandler(
		lambdaName = "hello_world",
		roleName = "hello_world-role",
		isPublishVersion = true,
		aliasName = "learn",
		logsExpiration = RetentionSetting.SYNDICATE_ALIASES_SPECIFIED
)
@LambdaUrlConfig(
		authType = AuthType.NONE, // This means no authentication is required to access the Function URL
		invokeMode = InvokeMode.BUFFERED // This can be set to BUFFERED or STREAMING based on your requirements
)
public class HelloWorld implements RequestHandler<APIGatewayV2HTTPEvent, APIGatewayV2HTTPResponse> {

	@Override
	public APIGatewayV2HTTPResponse handleRequest(APIGatewayV2HTTPEvent requestEvent, Context context) {
		String path = requestEvent.getRequestContext().getHttp().getPath();
		String method = requestEvent.getRequestContext().getHttp().getMethod();

		// Check if the request is for the /hello endpoint
		if ("GET".equals(method) && "/hello".equals(path)) {
			return buildResponse(200, "Hello from Lambda");
		} else {
			// Handle all other paths with a 400 error
			return buildResponse(400, String.format("Bad request syntax or unsupported method. Request path: %s. HTTP method: %s", path, method));
		}
	}

	private APIGatewayV2HTTPResponse buildResponse(int statusCode, String message) {
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("statusCode", statusCode);
		resultMap.put("message", message);

		return APIGatewayV2HTTPResponse.builder()
				.withStatusCode(statusCode)
				.withHeaders(Map.of("Content-Type", "application/json"))
				.withBody(resultMap.toString()) // You might want to use a JSON library to convert this properly
				.build();
	}
}

package com.restapi.qaautomation.webservices.test.base;

import static io.restassured.RestAssured.given;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.restapi.qaautomation.webservices.test.exception.RestAssuredException;

import java.lang.reflect.Method;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ApiBaseTest {
	private static Logger logger = LogManager.getLogger();
	
	public <T> T deleteRequest(Object requestObject, String url, Class<T> responseClass) throws Exception {
		try {
			logger.info("*** Delete request sent ***");
			Response response = 
					given()
						.log().all()
//						.spec(getDeleteSpec(requestObject))
						.body(requestObject)
					.when()
						.log().all()
						.delete(url)
					.then()
						.log().all()
						.extract()
						.response();
			return objectMapper().readValue(response.asString(), responseClass);
		} catch(Exception e) {
			throw new RestAssuredException("!!! Error getting response, request url = " + url);
		}
	}
	
	public <T> T patchRequest(Object requestObject, String url, Class<T> responseClass) throws Exception {
		try {
			logger.info("*** Patch request sent ***");
			Response response = 
					given()
						.log().all()
//						.spec(getPatchSpec(requestObject))
						.body(requestObject)
					.when()
						.log().all()
						.patch(url)
					.then()
						.log().all()
						.extract()
						.response();
			return objectMapper().readValue(response.asString(), responseClass);
		} catch(Exception e) {
			throw new RestAssuredException("!!! Error getting response, request url = " + url);
		}
	}
	
	public <T> T putRequest(Object requestObject, String url, Class<T> responseClass) throws Exception {
		try {
			logger.info("*** Put request sent ***");
			Response response = 
					given()
						.log().all()
//						.spec(getPutSpec(requestObject))
						.body(requestObject)
					.when()
						.log().all()
						.put(url)
					.then()
						.log().all()
						.extract()
						.response();
			return objectMapper().readValue(response.asString(), responseClass);
		} catch(Exception e) {
			throw new RestAssuredException("!!! Error getting response, request url = " + url);
		}
	}
	
	public <T> T postRequest(Object requestObject, String url, Class<T> responseClass) throws Exception {
		try {
			logger.info("*** Post request sent ***");
			Response response = 
					given()
						.log().all()
//						.spec(getPostSpec(requestObject))
						.body(requestObject)
					.when()
						.log().all()
						.post(url)
					.then()
						.log().all()
						.extract()
						.response();
			return objectMapper().readValue(response.asString(), responseClass);
		} catch(Exception e) {
			throw new RestAssuredException("!!! Error getting response, request url = " + url);
		}
	}
	
	public <T> T getRequest(Object requestObject, String url, Class<T> responseClass) throws Exception {
		try {
			logger.info("*** Get request sent ***");
			Response response = 
				given()
					.log()
					.all()
					.spec(getRequestSpec(requestObject))
				.when()
					.log()
					.all()
					.get(url)
				.then()
					.log()
					.all()
					.extract()
					.response();
		
			return objectMapper().readValue(response.asString(), responseClass);
		} catch (Exception e) {
			throw new RestAssuredException("!!! Error getting response, request url = " + url);
		}
	}
	
	private ObjectMapper objectMapper() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.getSerializationConfig().with(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES);
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		return mapper;
	}
	
	private RequestSpecification getRequestSpec(Object params) throws Exception {
		String token = ""; //Call API get token
		Map map = convertObjectToMap(params);
		return new RequestSpecBuilder().setContentType(ContentType.JSON)
				.addHeader("Accept", "application/json")
				.addHeader("Authorization", token)
				.addQueryParams(map)
				.build();
	}
	
	private Map convertObjectToMap(Object object) throws Exception {
		Map<String, Object> hashMap = new HashMap<String, Object>();
		try {
			 Class<?> c = object.getClass();
			 Method[] m = c.getMethods();
			 String paramName;
			 for (Method method : m) {
				 if (!method.getName().contains("Class")) {
					 paramName = method.getName().toLowerCase().substring(3, 4) + method.getName().substring(4);
					 if (method.getName().indexOf("get") == 0) {
						hashMap.put(paramName, method.invoke(object));
					 }
				 }
			 }
		} catch (Exception e) {
			// log 
		}
		
		return hashMap;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}

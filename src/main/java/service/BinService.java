package service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import dto.Bin;
import dto.Delivery;
import dto.Request;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Contains Requestbin service apis
 * <p>
 *
 * @since 2022-10-30
 */
public class BinService {
    public final String REQUESTBIN_BASE_URL = "https://floating-brushlands-81939.herokuapp.com/";
    private ObjectMapper mapper = new ObjectMapper();

    public String createBin() {
        RestAssured.baseURI = REQUESTBIN_BASE_URL + "api/v1/bins";
        RequestSpecification requestSpecification = RestAssured.given();
        Response response = requestSpecification.post();
        ResponseBody body = response.getBody();
        Bin bin = null;
        try {
            bin = mapper.readValue(body.asString(), Bin.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bin.getName();
    }

    public Response sendWebhookToBin(String binName, Delivery delivery) {
        RestAssured.baseURI = REQUESTBIN_BASE_URL + binName;
        RequestSpecification requestSpecification = RestAssured.given();
        String requestBody;
        try {
            requestBody = mapper.writeValueAsString(delivery);
        } catch (IOException e) {
            throw new RuntimeException("Delivery object cannot be serialized.", e);
        }
        requestSpecification.body(requestBody);
        return requestSpecification.get();
    }

    public List<Request> getRequests(String binName) {
        RestAssured.baseURI = REQUESTBIN_BASE_URL + "api/v1/bins/" + binName + "/requests";
        RequestSpecification requestSpecification = RestAssured.given();
        Response response = requestSpecification.get();
        ResponseBody body = response.getBody();
        List<Request> requests = null;
        try {
            CollectionType listType =
                    mapper.getTypeFactory().constructCollectionType(ArrayList.class, Request.class);
            requests = mapper.readValue(body.asString(), listType);
        } catch (Exception e) {
            throw new RuntimeException("Requests list cannot be serialized.", e);
        }
        return requests;
    }

    public Request getRequest(String binName, String requestName) {
        RestAssured.baseURI = REQUESTBIN_BASE_URL + "api/v1/bins/" + binName + "/requests/" + requestName;
        RequestSpecification requestSpecification = RestAssured.given();
        Response response = requestSpecification.get();
        ResponseBody body = response.getBody();
        Request request = null;
        try {
            request = mapper.readValue(body.asString(), Request.class);
        } catch (Exception e) {
            throw new RuntimeException("Request object cannot be serialized.", e);
        }
        return request;
    }
}

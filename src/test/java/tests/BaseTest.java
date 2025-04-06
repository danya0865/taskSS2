package tests;

import io.qameta.allure.Epic;
import io.restassured.RestAssured;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;

@Epic("API Tests")
public class BaseTest {
    protected static final String BASE_URL = "http://localhost:8080";
    protected static final String API_PATH = "/api";

    protected ResponseSpecification notFoundResponseSpec;
    protected ResponseSpecification serverErrorResponseSpec;
    protected ResponseSpecification noContentResponseSpec;
    protected ResponseSpecification textPlainSuccessResponseSpec;
    protected ResponseSpecification jsonSuccessResponseSpec;

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = BASE_URL;
        RestAssured.basePath = API_PATH;

        textPlainSuccessResponseSpec = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType("text/plain; charset=utf-8")
                .build();

        jsonSuccessResponseSpec = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType(ContentType.JSON)
                .build();

        noContentResponseSpec = new ResponseSpecBuilder()
                .expectStatusCode(204)
                .build();

        notFoundResponseSpec = new ResponseSpecBuilder()
                .expectStatusCode(404)
                .build();

        serverErrorResponseSpec = new ResponseSpecBuilder()
                .expectStatusCode(500)
                .build();
    }
}
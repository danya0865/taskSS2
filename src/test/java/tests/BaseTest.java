package tests;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;

public class BaseTest {
    protected static final String BASE_URL = "http://localhost:8080";
    protected static final String API_PATH = "/api";

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = BASE_URL;
        RestAssured.basePath = API_PATH;
    }
}
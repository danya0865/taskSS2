package tests;

import dto.AdditionRequest;
import dto.EntityRequest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qameta.allure.internal.shadowed.jackson.databind.ObjectMapper;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import io.restassured.http.ContentType;

import java.util.Arrays;

import static io.restassured.RestAssured.given;

@Epic("API Tests")
@Feature("Entity Management")
public class EntityCreationTests extends BaseTest {

    @Test(description = "Запрос на добавление новой сущности")
    @Story("Create a new entity")
    public void testCreateEntity() {
        AdditionRequest addition = new AdditionRequest("Дополнительные сведения", 123);
        EntityRequest entityRequest = new EntityRequest(
                addition,
                Arrays.asList(42, 87, 15),
                "Заголовок сущности",
                true
        );

        allureLogApiResponse("Request Body", serializeToJson(entityRequest));

        Response response = given()
                .contentType(ContentType.JSON)
                .body(entityRequest)
                .when()
                .post("/create");

        String entityId = response.then()
                .extract()
                .asString();

        Response getResponse = given()
                .when()
                .get("/get/" + entityId);

        allureLogApiResponse("Full Entity Response", getResponse.getBody().asString());

        response.then()
                .statusCode(200);
    }

    private void allureLogApiResponse(String title, String content) {
        io.qameta.allure.Allure.addAttachment(title, "application/json", content);
    }

    private String serializeToJson(Object object) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException("Failed to serialize object to JSON", e);
        }
    }
}
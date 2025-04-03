package tests;

import dto.AdditionRequest;
import dto.EntityRequest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.Arrays;

import static io.restassured.RestAssured.given;

@Epic("API Tests")
@Feature("Entity Management")
public class EntityUpdateTests extends BaseTest {

    @Test(description = "Запрос на обновление сущности и ее дополнений")
    @Story("Update entity")

    public void testUpdateEntity() {
        String entityId = createTestEntity();

        Response getResponseBeforeUpdate = given()
                .when()
                .get("/get/" + entityId);

        allureLogApiResponse("Created Entity", getResponseBeforeUpdate.getBody().asString());

        AdditionRequest updatedAddition = new AdditionRequest("Updated info", 999);
        EntityRequest updatedEntity = new EntityRequest(
                updatedAddition,
                Arrays.asList(49, 87, 15),
                "Updated title",
                true
        );

        Response updateResponse = given()
                .contentType(ContentType.JSON)
                .body(updatedEntity)
                .when()
                .patch("/patch/" + entityId);

        updateResponse.then()
                .statusCode(204);

        Response getResponseAfterUpdate = given()
                .when()
                .get("/get/" + entityId);

        allureLogApiResponse("Updated Entity", getResponseAfterUpdate.getBody().asString());
    }

    private String createTestEntity() {
        AdditionRequest addition = new AdditionRequest("Test info", 111);
        EntityRequest entityRequest = new EntityRequest(
                addition,
                Arrays.asList(1, 2, 3),
                "Test title",
                false
        );

        return given()
                .contentType(ContentType.JSON)
                .body(entityRequest)
                .when()
                .post("/create")
                .then()
                .extract()
                .asString();
    }

    private void allureLogApiResponse(String title, String responseBody) {
        io.qameta.allure.Allure.addAttachment(title, "application/json", responseBody);
    }
}
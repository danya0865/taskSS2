package tests;

import dto.AdditionRequest;
import dto.EntityRequest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import io.restassured.http.ContentType;

import java.util.Arrays;

import static io.restassured.RestAssured.given;

@Epic("API Tests")
@Feature("Entity Management")
public class EntityDeletionTests extends BaseTest {

    @Test(description = "Запрос на удаление сущности и ее дополнений")
    @Story("Delete an existing entity")
    public void testDeleteEntity() {
        String entityId = createTestEntity();

        Response getResponse = given()
                .when()
                .get("/get/" + entityId);

        allureLogApiResponse("Create Response", getResponse.getBody().asString());

        Response deleteResponse = given()
                .when()
                .delete("/delete/" + entityId);

        if (deleteResponse.statusCode() == 204) {
            allureLogApiResponse("Delete Response", "Удаление проведено успешно");
        } else {
            allureLogApiResponse("Delete Response", deleteResponse.getBody().asString());
        }

        deleteResponse.then()
                .statusCode(204);
    }

    private String createTestEntity() {
        AdditionRequest addition = new AdditionRequest("Test info", 111);
        EntityRequest entityRequest = new EntityRequest(
                addition,
                Arrays.asList(1, 2, 3),
                "Test title",
                false
        );

        Response createResponse = given()
                .contentType(ContentType.JSON)
                .body(entityRequest)
                .when()
                .post("/create");

        return createResponse.then()
                .extract()
                .asString();
    }

    private void allureLogApiResponse(String title, String responseBody) {
        io.qameta.allure.Allure.addAttachment(title, "application/json", responseBody);
    }
}
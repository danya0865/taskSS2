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

    @Test
    @Story("Update entity")
    public void testUpdateEntity() {
        String entityId = "56";

        AdditionRequest updatedAddition = new AdditionRequest("Updated info", 999);
        EntityRequest updatedEntity = new EntityRequest(
                updatedAddition,
                Arrays.asList(49, 87, 15),
                "Updated title",
                true
        );

        Response response = given()
                .contentType(ContentType.JSON)
                .body(updatedEntity)
                .when()
                .patch("/patch/" + entityId);

        response.then()
                .statusCode(204);
    }
}
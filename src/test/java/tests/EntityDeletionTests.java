package tests;

import models.Addition;
import models.Entity;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qameta.allure.restassured.AllureRestAssured;
import org.testng.annotations.Test;
import io.restassured.http.ContentType;
import java.util.Arrays;
import static io.restassured.RestAssured.given;

@Feature("Управление сущностями")
public class EntityDeletionTests extends BaseTest {

    @Test(description = "Запрос на удаление сущности и ее дополнений")
    @Story("Удаление сущности")
    public void testDeleteEntity() {
        Entity entityRequest = Entity.builder()
                .addition(Addition.builder()
                        .additional_info("Test info")
                        .additional_number(111)
                        .build())
                .important_numbers(Arrays.asList(1, 2, 3))
                .title("Test title")
                .verified(false)
                .build();

        String entityId = given()
                .filter(new AllureRestAssured())
                .contentType(ContentType.JSON)
                .body(entityRequest)
                .when()
                .post("/create")
                .then()
                .spec(textPlainSuccessResponseSpec)
                .extract()
                .asString();

        given()
                .filter(new AllureRestAssured())
                .when()
                .get("/get/" + entityId)
                .then()
                .spec(jsonSuccessResponseSpec);

        given()
                .filter(new AllureRestAssured())
                .when()
                .delete("/delete/" + entityId)
                .then()
                .spec(noContentResponseSpec);

        given()
                .filter(new AllureRestAssured())
                .when()
                .get("/get/" + entityId)
                .then()
                .spec(serverErrorResponseSpec);
    }
}
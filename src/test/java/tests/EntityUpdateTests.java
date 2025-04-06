package tests;

import models.Addition;
import models.Entity;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.http.ContentType;
import org.testng.annotations.Test;
import java.util.Arrays;
import static io.restassured.RestAssured.given;

@Feature("Управление сущностями")
public class EntityUpdateTests extends SoftAssertBaseTest {

    @Test(description = "Запрос на обновление сущности и ее дополнений")
    @Story("Обновление сущности")
    public void testUpdateEntity() {
        Entity originalEntity = Entity.builder()
                .addition(Addition.builder()
                        .additional_info("Original info")
                        .additional_number(111)
                        .build())
                .important_numbers(Arrays.asList(1, 2, 3))
                .title("Original title")
                .verified(false)
                .build();

        String entityId = given()
                .filter(new AllureRestAssured())
                .contentType(ContentType.JSON)
                .body(originalEntity)
                .when()
                .post("/create")
                .then()
                .spec(textPlainSuccessResponseSpec)
                .extract()
                .asString();

        Entity updatedEntity = Entity.builder()
                .addition(Addition.builder()
                        .additional_info("Updated info")
                        .additional_number(999)
                        .build())
                .important_numbers(Arrays.asList(4, 5, 6))
                .title("Updated title")
                .verified(true)
                .build();

        given()
                .filter(new AllureRestAssured())
                .contentType(ContentType.JSON)
                .body(updatedEntity)
                .when()
                .patch("/patch/" + entityId)
                .then()
                .spec(noContentResponseSpec);

        Entity responseEntity = given()
                .filter(new AllureRestAssured())
                .when()
                .get("/get/" + entityId)
                .then()
                .spec(jsonSuccessResponseSpec)
                .extract()
                .as(Entity.class);

        softAssert.assertEquals(responseEntity.getTitle(), updatedEntity.getTitle(), "Заголовок должен обновиться");
        softAssert.assertEquals(responseEntity.isVerified(), updatedEntity.isVerified(), "Статус verified должен обновиться");
        softAssert.assertEquals(responseEntity.getImportant_numbers(), updatedEntity.getImportant_numbers(), "Числа должны обновиться");

        Addition responseAddition = responseEntity.getAddition();
        softAssert.assertEquals(responseAddition.getAdditional_info(), updatedEntity.getAddition().getAdditional_info(),
                "Дополнительная информация должна обновиться");
        softAssert.assertEquals(responseAddition.getAdditional_number(), updatedEntity.getAddition().getAdditional_number(),
                "Число должно обновиться");

        assertAll();
    }
}
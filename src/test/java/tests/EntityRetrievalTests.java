package tests;

import models.Addition;
import models.Entity;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qameta.allure.restassured.AllureRestAssured;
import org.testng.annotations.Test;
import java.util.Arrays;
import static io.restassured.RestAssured.given;

@Feature("Управление сущностями")
public class EntityRetrievalTests extends SoftAssertBaseTest {

    @Test(description = "Запрос на получение одной сущности")
    @Story("Получение сущности")
    public void testGetEntity() {
        Entity entityRequest = Entity.builder()
                .addition(Addition.builder()
                        .additional_info("Тестовая информация")
                        .additional_number(111)
                        .build())
                .important_numbers(Arrays.asList(1, 2, 3))
                .title("Тестовый заголовок")
                .verified(false)
                .build();

        String entityId = given()
                .filter(new AllureRestAssured())
                .contentType("application/json")
                .body(entityRequest)
                .when()
                .post("/create")
                .then()
                .spec(textPlainSuccessResponseSpec)
                .extract()
                .asString();

        Entity entityResponse = given()
                .filter(new AllureRestAssured())
                .when()
                .get("/get/" + entityId)
                .then()
                .spec(jsonSuccessResponseSpec)
                .extract()
                .as(Entity.class);

        // Исправленные проверки с явным приведением типов
        softAssert.assertEquals(entityResponse.getId().intValue(), Integer.parseInt(entityId), "ID сущности должен совпадать");
        softAssert.assertEquals(entityResponse.getTitle(), entityRequest.getTitle(), "Заголовок должен совпадать");
        softAssert.assertEquals(entityResponse.isVerified(), entityRequest.isVerified(), "Статус verified должен совпадать");
        softAssert.assertEquals(entityResponse.getImportant_numbers(), entityRequest.getImportant_numbers(), "Числа должны совпадать");

        Addition responseAddition = entityResponse.getAddition();
        softAssert.assertNotNull(responseAddition, "Дополнение не должно быть null");
        softAssert.assertEquals(responseAddition.getAdditional_info(), entityRequest.getAddition().getAdditional_info(),
                "Дополнительная информация должна совпадать");
        softAssert.assertEquals(responseAddition.getAdditional_number(), entityRequest.getAddition().getAdditional_number(),
                "Дополнительное число должно совпадать");
        softAssert.assertNotNull(responseAddition.getId(), "ID дополнения должен присутствовать");

        assertAll();
    }
}
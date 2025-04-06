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
public class EntityCreationTests extends SoftAssertBaseTest {

    @Test(description = "Запрос на добавление новой сущности")
    @Story("Создание новой сущности")
    public void testCreateEntity() {
        Entity entityRequest = Entity.builder()
                .addition(Addition.builder()
                        .additional_info("Дополнительные сведения")
                        .additional_number(123)
                        .build())
                .important_numbers(Arrays.asList(42, 87, 15))
                .title("Заголовок сущности")
                .verified(true)
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

        softAssert.assertTrue(entityId != null && !entityId.isEmpty(), "ID сущности не должен быть пустым");

        Entity entityResponse = given()
                .filter(new AllureRestAssured())
                .when()
                .get("/get/" + entityId)
                .then()
                .spec(jsonSuccessResponseSpec)
                .extract()
                .as(Entity.class);

        softAssert.assertEquals(entityResponse.getId().intValue(), Integer.parseInt(entityId), "ID сущности должен соответствовать");
        softAssert.assertEquals(entityResponse.getTitle(), entityRequest.getTitle(), "Заголовок должен соответствовать");
        softAssert.assertEquals(entityResponse.isVerified(), entityRequest.isVerified(), "Статус verified должен соответствовать");
        softAssert.assertEquals(entityResponse.getImportant_numbers(), entityRequest.getImportant_numbers(), "Числа должны соответствовать");

        Addition responseAddition = entityResponse.getAddition();
        softAssert.assertNotNull(responseAddition, "Дополнение не должно быть null");
        softAssert.assertEquals(responseAddition.getAdditional_info(), entityRequest.getAddition().getAdditional_info(),
                "Дополнительная информация должна соответствовать");
        softAssert.assertEquals(responseAddition.getAdditional_number(), entityRequest.getAddition().getAdditional_number(),
                "Число должно соответствовать");
        softAssert.assertNotNull(responseAddition.getId(), "ID дополнения должен присутствовать");

        assertAll();
    }
}
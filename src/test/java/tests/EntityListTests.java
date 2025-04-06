package tests;

import models.Entity;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.http.ContentType;
import org.testng.annotations.Test;
import java.util.List;
import static io.restassured.RestAssured.given;

@Feature("Управление сущностями")
public class EntityListTests extends SoftAssertBaseTest {

    @Test(description = "Запрос на получение списка сущностей")
    @Story("Получение всех сущностей")
    public void testGetAllEntities() {
        List<Entity> entities = given()
                .filter(new AllureRestAssured())
                .contentType(ContentType.JSON)
                .when()
                .get("/getAll")
                .then()
                .spec(jsonSuccessResponseSpec)
                .extract()
                .jsonPath()
                .getList("entity", Entity.class);

        softAssert.assertFalse(entities.isEmpty(), "Список сущностей не должен быть пустым");

        entities.forEach(entity -> {
            softAssert.assertTrue(entity.getId() > 0, "ID сущности должен присутствовать");
            softAssert.assertNotEquals(entity.getTitle(), "", "Заголовок сущности не должен быть пустым");
            softAssert.assertNotNull(entity.getImportant_numbers(), "Числа не должны быть null");
            softAssert.assertNotNull(entity.getAddition(), "Дополнение должно присутствовать");
        });

        assertAll();
    }
}
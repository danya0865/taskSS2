package tests;

import models.Entity;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.http.ContentType;
import org.testng.annotations.Test;
import java.util.List;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.emptyString;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;

@Feature("Управление сущностями")
public class EntityListTests extends BaseTest {

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

        assertThat("Список сущностей не должен быть пустым", entities, not(empty()));

        entities.forEach(entity -> {
            assertThat("ID сущности должен присутствовать", entity.getId(), greaterThan(0));
            assertThat("Заголовок сущности не должен быть пустым", entity.getTitle(), not(emptyString()));
            assertThat("Числа не должны быть null",
                    entity.getImportant_numbers(), notNullValue());
            assertThat("Дополнение должно присутствовать", entity.getAddition(), notNullValue());
        });
    }
}
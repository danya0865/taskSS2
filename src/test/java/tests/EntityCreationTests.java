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
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.emptyString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;

@Feature("Управление сущностями")
public class EntityCreationTests extends BaseTest {

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

        assertThat("ID сущности не должен быть пустым", entityId, not(emptyString()));

        Entity entityResponse = given()
                .filter(new AllureRestAssured())
                .when()
                .get("/get/" + entityId)
                .then()
                .spec(jsonSuccessResponseSpec)
                .extract()
                .as(Entity.class);

        assertThat("ID сущности должен соответствовать",
                entityResponse.getId(), equalTo(Integer.parseInt(entityId)));
        assertThat("Заголовок должен соответствовать",
                entityResponse.getTitle(), equalTo(entityRequest.getTitle()));
        assertThat("Статус verified должен соответствовать",
                entityResponse.isVerified(), equalTo(entityRequest.isVerified()));
        assertThat("Список важных чисел не должен быть пустым",
                entityResponse.getImportant_numbers(), not(empty()));
        assertThat("Числа должны соответствовать",
                entityResponse.getImportant_numbers(), equalTo(entityRequest.getImportant_numbers()));

        Addition responseAddition = entityResponse.getAddition();
        assertThat("Дополнение не должно быть null", responseAddition, notNullValue());
        assertThat("Дополнительная информация должна соответствовать",
                responseAddition.getAdditional_info(), equalTo(entityRequest.getAddition().getAdditional_info()));
        assertThat("Число должно соответствовать",
                responseAddition.getAdditional_number(), equalTo(entityRequest.getAddition().getAdditional_number()));
        assertThat("ID дополнения должен присутствовать",
                responseAddition.getId(), notNullValue());
    }
}
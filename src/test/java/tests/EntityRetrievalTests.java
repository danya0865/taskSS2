package tests;

import models.Addition;
import models.Entity;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qameta.allure.restassured.AllureRestAssured;
import org.testng.annotations.Test;
import java.util.Arrays;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@Feature("Управление сущностями")
public class EntityRetrievalTests extends BaseTest {

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

        assertThat("ID сущности должен совпадать",
                entityResponse.getId(), equalTo(Integer.parseInt(entityId)));
        assertThat("Заголовок должен совпадать",
                entityResponse.getTitle(), equalTo(entityRequest.getTitle()));
        assertThat("Статус verified должен совпадать",
                entityResponse.isVerified(), equalTo(entityRequest.isVerified()));
        assertThat("Числа должны совпадать",
                entityResponse.getImportant_numbers(), equalTo(entityRequest.getImportant_numbers()));

        Addition responseAddition = entityResponse.getAddition();
        assertThat("Дополнение не должно быть null", responseAddition, notNullValue());
        assertThat("Дополнительная информация должна совпадать",
                responseAddition.getAdditional_info(), equalTo(entityRequest.getAddition().getAdditional_info()));
        assertThat("Дополнительное число должно совпадать",
                responseAddition.getAdditional_number(), equalTo(entityRequest.getAddition().getAdditional_number()));
        assertThat("ID дополнения должен присутствовать",
                responseAddition.getId(), notNullValue());
    }
}
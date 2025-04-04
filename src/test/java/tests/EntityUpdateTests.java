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
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@Feature("Управление сущностями")
public class EntityUpdateTests extends BaseTest {

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

        assertThat("Заголовок должен обновиться", responseEntity.getTitle(), equalTo(updatedEntity.getTitle()));
        assertThat("Статус verified должен обновиться", responseEntity.isVerified(), equalTo(updatedEntity.isVerified()));
        assertThat("Числа должны обновиться", responseEntity.getImportant_numbers(), equalTo(updatedEntity.getImportant_numbers()));

        Addition responseAddition = responseEntity.getAddition();
        assertThat("Дополнительная информация должна обновиться",
                responseAddition.getAdditional_info(), equalTo(updatedEntity.getAddition().getAdditional_info()));
        assertThat("Число должно обновиться",
                responseAddition.getAdditional_number(), equalTo(updatedEntity.getAddition().getAdditional_number()));
    }
}
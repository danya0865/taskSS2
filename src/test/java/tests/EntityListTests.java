package tests;

import dto.EntityResponse;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@Epic("API Tests")
@Feature("Entity Management")
public class EntityListTests extends BaseTest {

    @Test(description = "Запрос на получение списка сущностей")
    @Story("Get all entities")
    public void testGetAllEntities() {
        Response response = given()
                .contentType(ContentType.JSON)
                .when()
                .get("/getAll");

        String responseBody = response.getBody().asString();
        allureLogApiResponse(responseBody);

        response.then().statusCode(200);

        JsonPath jsonPath = response.jsonPath();
        List<EntityResponse> entities = jsonPath.getList("entity", EntityResponse.class);

        assertThat("Entities list should not be empty", entities, not(empty()));

        if (!entities.isEmpty()) {
            EntityResponse firstEntity = entities.get(0);
            assertThat("Entity ID should not be null", firstEntity.getId(), greaterThan(0));
            assertThat("Entity title should not be empty", firstEntity.getTitle(), not(emptyString()));
            assertThat("Important numbers should not be empty",
                    firstEntity.getImportant_numbers(), not(empty()));
        }
    }

    private void allureLogApiResponse(String responseBody) {
        io.qameta.allure.Allure.addAttachment("API Response", "application/json", responseBody);
    }
}
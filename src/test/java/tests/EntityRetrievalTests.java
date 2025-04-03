package tests;

import dto.AdditionRequest;
import dto.EntityRequest;
import dto.EntityResponse;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import java.util.Arrays;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;

@Epic("API Tests")
@Feature("Entity Management")
public class EntityRetrievalTests extends BaseTest {

    @Test(description = "Запрос на получение одной сущности")
    @Story("Get a single entity")
    public void testGetEntity() {
        String entityId = createTestEntity();

        Response response = given()
                .when()
                .get("/get/" + entityId);

        allureLogApiResponse(response.getBody().asString());

        response.then()
                .statusCode(200);

        EntityResponse entityResponse = response.then()
                .extract()
                .as(EntityResponse.class);

        assertThat("Entity ID should match", entityResponse.getId(), equalTo(Integer.parseInt(entityId)));
        assertThat("Entity title should not be null", entityResponse.getTitle(), notNullValue());
        assertThat("Important numbers should not be empty", entityResponse.getImportant_numbers(), not(empty()));
    }

    private String createTestEntity() {
        AdditionRequest addition = new AdditionRequest("Test info", 111);
        EntityRequest entityRequest = new EntityRequest(
                addition,
                Arrays.asList(1, 2, 3),
                "Test title",
                false
        );

        return given()
                .contentType("application/json")
                .body(entityRequest)
                .when()
                .post("/create")
                .then()
                .extract()
                .asString();
    }

    private void allureLogApiResponse(String responseBody) {
        io.qameta.allure.Allure.addAttachment("API Response", "application/json", responseBody);
    }
}
package tests;

import dto.EntityResponse;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@Epic("API Tests")
@Feature("Entity Management")
public class EntityRetrievalTests extends BaseTest {

    @Test
    @Story("Get a single entity")
    public void testGetEntity() {
        String entityId = "92";

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

    private void allureLogApiResponse(String responseBody) {
        io.qameta.allure.Allure.addAttachment("API Response", "application/json", responseBody);
    }
}
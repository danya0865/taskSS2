package tests;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;

@Epic("API Tests")
@Feature("Entity Management")
public class EntityDeletionTests extends BaseTest {

    @Test
    @Story("Delete an existing entity")
    public void testDeleteEntity() {
        String entityId = "88";

        Response response = given()
                .when()
                .delete("/delete/" + entityId);

        response.then()
                .statusCode(204);
    }
}
package tests;

import dto.AdditionRequest;
import dto.EntityRequest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import io.restassured.http.ContentType;
import java.util.Arrays;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

@Epic("API Tests")
@Feature("Entity Management")
public class EntityCreationTests extends BaseTest {

    @Test
    @Story("Create a new entity")
    public void testCreateEntity() {
        AdditionRequest addition = new AdditionRequest("Дополнительные сведения", 123);
        EntityRequest entityRequest = new EntityRequest(
                addition,
                Arrays.asList(42, 87, 15),
                "Заголовок сущности",
                true
        );
        Response response = given()
                .contentType(ContentType.JSON)
                .body(entityRequest)
                .when()
                .post("/create");
        response.then()
                .statusCode(200)
                .body(notNullValue());
    }
}
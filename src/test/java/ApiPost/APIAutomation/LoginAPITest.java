package ApiPost.APIAutomation;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginAPITest {

    private static final String BASE_URL = "https://reqres.in/api/login";

    @Test(description = "Positive Test Case: Valid Login")
    public void testValidLogin() {
        String requestBody = "{ \"email\": \"eve.holt@reqres.in\", \"password\": \"cityslicka\" }";

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .post(BASE_URL);

        // Verify HTTP Status Code
        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200");

        // Verify Response Body
        String token = response.jsonPath().getString("token");
        Assert.assertNotNull(token, "Token should not be null");
    }

    @Test(description = "Negative Test Case: Invalid Login - Missing Password")
    public void testInvalidLoginMissingPassword() {
        String requestBody = "{ \"email\": \"eve.holt@reqres.in\" }";

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .post(BASE_URL);

        // Verify HTTP Status Code
        Assert.assertEquals(response.getStatusCode(), 400, "Expected status code 400");

        // Verify Error Message
        String errorMessage = response.jsonPath().getString("error");
        Assert.assertEquals(errorMessage, "Missing password", "Expected error message 'Missing password'");
    }

    @Test(description = "Negative Test Case: Invalid Login - Invalid Email")
    public void testInvalidLoginInvalidEmail() {
        String requestBody = "{ \"email\": \"invalid@reqres.in\", \"password\": \"cityslicka\" }";

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .post(BASE_URL);

        // Verify HTTP Status Code
        Assert.assertEquals(response.getStatusCode(), 400, "Expected status code 400");

        // Verify Error Message
        String errorMessage = response.jsonPath().getString("error");
        Assert.assertEquals(errorMessage, "user not found", "Expected error message 'user not found'");
    }
}
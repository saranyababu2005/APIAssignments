import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;

/*import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class WiremockApi {

    public void createStub()
    {
        stubFor(get("/myownapi/services")
                .willReturn(aResponse().withStatus(200)
                .withBody("This is new API")
                .withHeader("Content-Type","application/json")));
    }

    @Test
    public void verifyStub()
    {
        createStub();
        RestAssured.baseURI="http://localhost:8080";

        Response response=RestAssured.given()
                .log().all()
                .when()
                .get("/myownapi/services")
                .then()
                .extract().response();
        System.out.println(response.asString());
    }

}*/

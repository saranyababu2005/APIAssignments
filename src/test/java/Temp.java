/*import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class Temp {
    import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;

    public class LibraryApiTest {
        @Test
        public void verifyAddBook()
        {
            RestAssured.baseURI="http://216.10.245.166";
            Response response=given().header("Content-Type","application/json")
                    .body("{\"name\":\"Learn Appium Automation with Java\",\n" +
                            "\"isbn\":\"sonal1111222\",\n" +
                            "\"aisle\":\"227344567\",\n" +
                            "\"author\":\"John foeeee\"\n" +
                            "}")
                    .when().post("Library/Addbook.php")
                    .then().statusCode(200).extract().response();
            System.out.println(response.asString());
            //Assert.assertEquals(response.asString(),"{\"Msg\":\"successfully added\",\"ID\":\"sonal1111222227344567\"}");
        }
        @Test
        public void getBookByID()
        {
            RestAssured.baseURI="http://216.10.245.166";
            Response response=given().queryParam("ID","sonal111122222734")
                    .header("Content-Type","application/json")
                    .when().get("Library/GetBook.php")
                    .then().statusCode(200).extract().response();
            System.out.println(response);
            System.out.println(response.asString());
            Assert.assertEquals(response.asString(),"[{\"book_name\":\"Learn Appium Automation with Java\",\"isbn\":\"sonal1111222\",\"aisle\":\"22734\",\"author\":\"John foe2\"}]");
        }

        @Test
        public void getBookByIdwithDeserialization()
        {
            RestAssured.baseURI="http://216.10.245.166";
            Response response=given().queryParam("ID","sonal111122222734")
                    .header("Content-Type","application/json")
                    .when().get("Library/GetBook.php")
                    .then().statusCode(200).extract().response();

            BookResponse[] book = response.as(BookResponse[].class);
            System.out.println(book[0].getAuthor());
        }

        @Test
        public void addBookwithSerialization()
        {
            BookResponse addbook=new BookResponse();
            addbook.setName("Rest Assured");
            addbook.setAisle("12345");
            addbook.setIsbn("A12345");
            addbook.setAuthor("Saranyaaa");

            RestAssured.baseURI="http://216.10.245.166";
            Response response=given().body(addbook)
                    .when().post("Library/Addbook.php")
                    .then()
                    .statusCode(200)
                    .extract().response();
            System.out.println(response.asString());

        }



    }

}*/

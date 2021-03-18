import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Random;

import static io.restassured.RestAssured.given;

public class ReusableFunctions {

    public String addBook(BookResponse addBook, int status, String message) {
        RestAssured.baseURI = "http://216.10.245.166";
        Response response = given().body(addBook)
                .when().post("Library/Addbook.php")
                .then()
                .assertThat().statusCode(status)
                .extract().response();
        System.out.println(response.asString());
        JsonPath jsonPathEvaluator = response.jsonPath();
        String msg;
        if (status == 200) {
            msg = jsonPathEvaluator.getString("Msg");
        } else {
            msg = jsonPathEvaluator.getString("msg");
        }
        Assert.assertEquals(msg, message);
        String id = "";
        if (status == 200)
            id = jsonPathEvaluator.getString("ID");
        return id;
    }

    public void verifyGetBook(String id, BookResponse addBook, int status) {
        RestAssured.baseURI = "http://216.10.245.166";
        Response response = given().queryParam("ID", id)
                .when().get("Library/GetBook.php")
                .then().statusCode(status).extract().response();
        if (status == 200) {
            GetBookResponse[] book = response.as(GetBookResponse[].class);
            System.out.println(book[0].getAuthor());
            // Assert.assertEquals(book[0].name,addBook.name);
            Assert.assertEquals(book[0].getAisle(), addBook.getAisle());
            Assert.assertEquals(book[0].getAuthor(), addBook.getAuthor());
            Assert.assertEquals(book[0].getIsbn(), addBook.getIsbn());
            Assert.assertEquals(book[0].getName(), addBook.getName());
        } else if (status == 404) {
            //The book by requested bookid / author name does not exists!
            JsonPath jsonPathEvaluator = response.jsonPath();
            String msg = jsonPathEvaluator.getString("msg");
            Assert.assertEquals(msg, "The book by requested bookid / author name does not exists!");
        }
    }

    public void deleteBook(String id, int status, String message) {
        RestAssured.baseURI = "http://216.10.245.166";
        Response response = given()
                .body("{\"ID\" : \"" + id + "\"}")
                .when()
                .delete("Library/DeleteBook.php")
                .then()
                .assertThat().statusCode(status)
                .extract().response();
        JsonPath jsonPathEvaluator = response.jsonPath();
        String msg = jsonPathEvaluator.getString("msg");
        System.out.println(response.asString());
        Assert.assertEquals(msg, message);

    }

    public static String randomIsbn()
    {
        int number = (int) (Math.random() * 9999);
        if (number <= 1000) {
            number = number + 1000;
            //System.out.println(number);
        }
        return String.valueOf(number);
    }

    public GetBookResponse[] getBookByAuthorName(String author, int status, int count)
    {
        RestAssured.baseURI="http://216.10.245.166";
        Response response=given().queryParam("AuthorName",author)
                .when()
                .get("Library/GetBook.php")
                .then().assertThat().statusCode(status)
                .extract().response();
        GetBookResponse[] books=response.as(GetBookResponse[].class);
        System.out.println(books.length);
        Assert.assertEquals(books.length,count,"Number of books are equal");

        return books;
    }

    public void displayBooksByAuthorName(GetBookResponse[] books)
    {
        for(int i=0;i<books.length;i++)
            System.out.println(books[i].getName());
    }

    public void deleteOneBookByAuthorName(GetBookResponse[] books)
    {
        String id=books[0].getIsbn()+books[0].getAisle();
        deleteBook(id,200,"book is successfully deleted");
    }

    public BookResponse newBook()
    {
        BookResponse addnewBook = new BookResponse();
        
        addnewBook.setName("Rest Assured");
        addnewBook.setAisle("1123");
        addnewBook.setIsbn(ReusableFunctions.randomIsbn());
        addnewBook.setAuthor("SaranyaaaaBabu");

        return addnewBook;
    }

    public String newBooks()
    {
        BookResponse bookOne=new BookResponse();
        Random rn = new Random();
        int answer = rn.nextInt(100000) + 1;
        String author="boxom"+answer;
        System.out.println(author);
        bookOne.setAuthor(author);
        bookOne.setName("BookA");
        bookOne.setIsbn(ReusableFunctions.randomIsbn());
        bookOne.setAisle("1");

        BookResponse bookTwo=new BookResponse();
        bookTwo.setAuthor(author);
        bookTwo.setName("BookB");
        bookTwo.setIsbn(ReusableFunctions.randomIsbn());
        bookTwo.setAisle("2");

        BookResponse bookThree=new BookResponse();
        bookThree.setAuthor(author);
        bookThree.setName("BookC");
        bookThree.setIsbn(ReusableFunctions.randomIsbn());
        bookThree.setAisle("3");

        addBook(bookOne,200,"successfully added");
        addBook(bookTwo,200,"successfully added");
        addBook(bookThree,200,"successfully added");

        return author;

    }

}

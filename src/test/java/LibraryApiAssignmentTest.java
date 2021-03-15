import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Random;

import static io.restassured.RestAssured.given;

public class LibraryApiAssignmentTest {

    public String addBookwithSerialization(BookResponse addBook, int status, String message) {
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

    @DataProvider
    public Object[][] addBookData() {
        Object[][] data = new Object[2][3];

        BookResponse addBook = new BookResponse();
        addBook.setName("Rest Assured");
        addBook.setAisle("2");
        addBook.setIsbn(LibraryApiAssignmentTest.randomIsbn());
        addBook.setAuthor("SaranyaaaaBabu");

        BookResponse addBook1 = new BookResponse();
        addBook1.setName("Appium");
        addBook1.setAisle("2");
        addBook1.setIsbn("3");
        addBook1.setAuthor("Johfnfoe2");

        data[0][0] = addBook;
        data[0][1] = 200;
        data[0][2] = "successfully added";

        data[1][0] = addBook1;
        data[1][1] = 404;
        data[1][2] = "Add Book operation failed, looks like the book already exists";

        return data;
    }

    @Test(dataProvider = "addBookData")
    public void verifyAddBook(BookResponse addBook, int status, String message) {
        LibraryApiAssignmentTest lib = new LibraryApiAssignmentTest();
        String id = lib.addBookwithSerialization(addBook, status, message);
        System.out.println(id);
        if (id != "") lib.verifyGetBook(id, addBook, 200);
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

    @Test
    public void verifyDelete() {
        BookResponse addnewBook = new BookResponse();
        addnewBook.setName("Rest Assured");
        addnewBook.setAisle("1123");
        addnewBook.setIsbn(LibraryApiAssignmentTest.randomIsbn());
        addnewBook.setAuthor("SaranyaaaaBabu");

        int status = 200;
        String message = "successfully added";

        LibraryApiAssignmentTest lib1 = new LibraryApiAssignmentTest();
        String id = lib1.addBookwithSerialization(addnewBook, status, message);

        verifyGetBook(id, addnewBook, 200);

        deleteBook(id, 200, "book is successfully deleted");

        verifyGetBook(id, addnewBook, 404);

        id = lib1.addBookwithSerialization(addnewBook, status, message);


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
            System.out.println(number);
        }
        return String.valueOf(number);
    }

    @Test
    public void verifyBookByAuthorName()
    {
        BookResponse book1=new BookResponse();
        Random rn = new Random();
        int answer = rn.nextInt(100000) + 1;
        String author="boxom"+answer;
        book1.setAuthor(author);
        book1.setName("BookA");
        book1.setIsbn(LibraryApiAssignmentTest.randomIsbn());
        book1.setAisle("1");

        BookResponse book2=new BookResponse();
        book2.setAuthor(author);
        book2.setName("BookB");
        book2.setIsbn(LibraryApiAssignmentTest.randomIsbn());
        book2.setAisle("2");

        BookResponse book3=new BookResponse();
        book3.setAuthor(author);
        book3.setName("BookC");
        book3.setIsbn(LibraryApiAssignmentTest.randomIsbn());
        book3.setAisle("3");

        addBookwithSerialization(book1,200,"successfully added");
        addBookwithSerialization(book2,200,"successfully added");
        addBookwithSerialization(book3,200,"successfully added");

        GetBookResponse[] books=getBookByAuthorName(author,200,3);
        System.out.println("After adding books");
        displayBooksByAuthorName(books);
        deleteOneBookByAuthorName(books);
        GetBookResponse[] booksAfterDeletion=getBookByAuthorName(author,200,2);
        System.out.println("After deleting a book");
        displayBooksByAuthorName(booksAfterDeletion);
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
}

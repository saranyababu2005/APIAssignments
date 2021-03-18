import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class LibraryApiAssignmentTest {
    ReusableFunctions library;
    BookResponse addNewBook;


    @Test(dataProvider = "addBookData")
    public void verifyAddBook(BookResponse addBook, int status, String message) {

        String id = library.addBook(addBook, status, message);
        //System.out.println(id);
        if (id != "")
            library.verifyGetBook(id, addBook, 200);
    }

    @DataProvider
    public Object[][] addBookData() {
        Object[][] data = new Object[2][3];

        BookResponse addBook = new BookResponse();
        addBook.setName("Rest Assured");
        addBook.setAisle("2");
        addBook.setIsbn(ReusableFunctions.randomIsbn());
        addBook.setAuthor("SaranyaaaaBabu");

        BookResponse addBookOne = new BookResponse();
        addBookOne.setName("Appium");
        addBookOne.setAisle("2");
        addBookOne.setIsbn("3");
        addBookOne.setAuthor("Johfnfoe2");

        data[0][0] = addBook;
        data[0][1] = 200;
        data[0][2] = "successfully added";

        data[1][0] = addBookOne;
        data[1][1] = 404;
        data[1][2] = "Add Book operation failed, looks like the book already exists";

        return data;
    }


    @Test
    public void verifyDelete() {
        int status = 200;
        String message = "successfully added";

        addNewBook =library.newBook();
        String id = library.addBook(addNewBook, status, message);
        library.verifyGetBook(id, addNewBook, 200);
        library.deleteBook(id, 200, "book is successfully deleted");
        library.verifyGetBook(id, addNewBook, 404);
        id = library.addBook(addNewBook, status, message);
    }

    @Test
    public void verifyBookByAuthorName()
    {
        String author=library.newBooks();
      //  System.out.println(author);

        GetBookResponse[] books=library.getBookByAuthorName(author,200,3);
        //System.out.println("After adding books");
        library.displayBooksByAuthorName(books);
        library.deleteOneBookByAuthorName(books);
        GetBookResponse[] booksAfterDeletion=library.getBookByAuthorName(author,200,2);
        //System.out.println("After deleting a book");
        library.displayBooksByAuthorName(booksAfterDeletion);
    }
    @BeforeClass
    public void library()
    {
        library  = new ReusableFunctions();
        addNewBook = new BookResponse();
    }

}

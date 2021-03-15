import com.fasterxml.jackson.annotation.JsonProperty;
public class GetBookResponse {

        @JsonProperty("book_name")
        public String name;
        public String isbn;
        public String aisle;
        public String author;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getIsbn() {
            return isbn;
        }

        public void setIsbn(String isbn) {
            this.isbn = isbn;
        }

        public String getAisle() {
            return aisle;
        }

        public void setAisle(String aisle) {
            this.aisle = aisle;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }
    }



package org.fslabs.Collections;

import com.fslabs.models.collection.Book;
import com.fslabs.models.collection.Library;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Set;

public class Collections {
    private static final Logger logger = LoggerFactory.getLogger(Collections.class);

    public static void main(String[] args) {
        Book book1 = Book.newBuilder()
                .setAuthor("Timothy")
                .setTitle("4 hour work week ")
                .setPublicationYear(2010)
                .build();
        Book book2 = Book.newBuilder()
                .setAuthor("Robin Sharma")
                .setTitle("The 5 am Club")
                .setPublicationYear(2016)
                .build();

        // List
        Library library1 = Library.newBuilder()
                .setName("Anna Library")
                // to add books one by one
//                .addBooks(book1)
//                .addBooks(book2)
                /// to add list of books
                .addAllBooks(List.of(book1, book2, book1))
                .build();
        logger.info("Library : {}", library1);

        // Set
        Library library2 = Library.newBuilder()
                .setName("Anna Library")
                // to add books one by one
//                .addBooks(book1)
//                .addBooks(book2)
                /// to add list of books
                .addAllBooks(Set.of(book1, book2))
                .build();
        logger.info("Library : {}", library2);
    }
}

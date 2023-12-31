package hakanozdmr.library.dto;

import hakanozdmr.library.model.Book;
import hakanozdmr.library.model.BookStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.File;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder

public class BookListItemResponse {
    private Long id;
    private String title;
    private String authorName;
    private BookStatus bookStatus;
    private String publisher;
    private Integer lastPageNumber;
    private File image;
    private String categoryName;
    private Integer totalPage;
    private Long userId;
    private String imageUrl;

    public static BookListItemResponse from(Book book) {
        return  BookListItemResponse.builder()
                .id(book.getId())
                .title(book.getTitle())
                .authorName(book.getAuthorName())
                .bookStatus(book.getBookStatus())
                .publisher(book.getPublisher())
                .lastPageNumber(book.getLastPageNumber())
                .categoryName(book.getCategory().getName())
                .totalPage(book.getTotalPage())
                .userId(book.getUserId())
                .imageUrl(book.getImage() != null ? book.getImage().getImageUrl() : null)
                .build();
    }
}

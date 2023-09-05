package hakanozdmr.library.dto;

import hakanozdmr.library.model.BookStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.File;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class BookResponse {
    private Long id;
    private String title;
    private String authorName;

    private BookStatus bookStatus;
    private String publisher;
    private Integer lastPageNumber;
    private Integer totalPage;
    private File image;

    private Long categoryId;

    private String imageUrl;
}

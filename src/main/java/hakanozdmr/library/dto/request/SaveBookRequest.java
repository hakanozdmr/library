package hakanozdmr.library.dto.request;

import hakanozdmr.library.model.BookStatus;
import hakanozdmr.library.model.Category;
import hakanozdmr.library.model.Image;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.File;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public final class SaveBookRequest {
    @NotBlank
    private String title;
    @NotBlank
    private String authorName;
    @NotNull
    private BookStatus bookStatus;
    @NotBlank
    private String publisher;
    @NotNull
    private Integer lastPageNumber;
    @NotNull
    private Integer totalPage;
    private File image;
    @NotNull
    private Long categoryId;

    private Long userId;
}

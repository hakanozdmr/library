package hakanozdmr.library.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Table(name = "books")
public class Book extends BaseEntity {

    private String title;
    private String authorName;

    @Enumerated(EnumType.STRING)
    private BookStatus bookStatus;
    private String publisher;
    private Integer lastPageNumber;
    private Integer totalPage;
    @OneToOne
    private Image image;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    private Long userId;

    public Image getImage() {
        return image == null ? new Image() : image;
    }
}

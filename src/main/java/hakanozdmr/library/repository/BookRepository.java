package hakanozdmr.library.repository;

import hakanozdmr.library.model.Book;
import hakanozdmr.library.model.BookStatus;
import hakanozdmr.library.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface BookRepository extends JpaRepository<Book,Long>, JpaSpecificationExecutor<Book> {

    List<Book> findByBookStatus(BookStatus bookStatus);
    List<Book> findByTitle(String title);
}

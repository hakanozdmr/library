package hakanozdmr.library.repository;

import hakanozdmr.library.model.Book;
import hakanozdmr.library.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category,Long>, JpaSpecificationExecutor<Book> {
    Optional<Category> findByName(String name);
}

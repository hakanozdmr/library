package hakanozdmr.library.service;

import hakanozdmr.library.model.Category;
import hakanozdmr.library.repository.BookRepository;
import hakanozdmr.library.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public Category loadCategory(Long id){
        return categoryRepository.findById(id).orElseThrow();
    }

    public Category findByName(String value) {
       return categoryRepository.findByName(value).orElseThrow(RuntimeException::new);
    }
}

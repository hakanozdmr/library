package hakanozdmr.library.service;


import hakanozdmr.library.dto.BookResponse;
import hakanozdmr.library.repository.UserRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookRecommendationService {
    private final UserService userService;
    private final BookListService bookListService;
    private final UserRepository userRepository;

    public BookRecommendationService(UserService userService, BookListService bookListService, UserRepository userRepository) {
        this.userService = userService;
        this.bookListService = bookListService;
        this.userRepository = userRepository;
    }

    @Cacheable("recommendation")
    public List<BookResponse> recommendBooks() {
        return bookListService.listBooks(10, 0, getRandomUserId());
    }

    private Long getRandomUserId() {
        return userRepository.findAll().stream().findAny().get().getId();
    }
}

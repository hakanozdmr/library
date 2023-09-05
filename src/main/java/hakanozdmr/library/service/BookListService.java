package hakanozdmr.library.service;

import hakanozdmr.library.dto.BookResponse;
import hakanozdmr.library.dto.BookSeachRequest;
import hakanozdmr.library.dto.CategoryType;
import hakanozdmr.library.dto.ErrorCode;
import hakanozdmr.library.exception.GenericException;
import hakanozdmr.library.model.Book;
import hakanozdmr.library.model.BookStatus;
import hakanozdmr.library.model.Category;
import hakanozdmr.library.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class BookListService {
    private final CategoryService categoryService;
    private final BookRepository bookRepository;

    public List<BookResponse> listBooks(int size, int page, Long userId) {
        return bookRepository.findAll(BookSearchSpecification.searchByUserBooks(userId), PageRequest.of(page,size))
                .getContent()
                .stream()
                .map(BookListService::convertResponse)
                .collect(Collectors.toList());
    }

    @Cacheable(value = "bookList", key = "'saveBook_' + #userId")
    public List<BookResponse> searchByCategory(CategoryType categoryType, Long userId) {
        final Category category = categoryService.findByName(categoryType.getValue());
        return category.getBooks()
                .stream()
                .filter(book -> book.getUserId().equals(userId))
                .map(BookListService::convertResponse)
                .collect(Collectors.toList());
    }

    private static BookResponse convertResponse(Book model) {
        return BookResponse.builder()
                .authorName(model.getAuthorName())
                .title(model.getTitle())
                .imageUrl(model.getImage().getImageUrl())
                .id(model.getId())
                .categoryId(model.getCategory().getId())
                .build();
    }


    @Cacheable(value = "bookList", key = "'status' + #bookStatus + #userId")
    public List<BookResponse> searchBookStatus(BookStatus bookStatus, Long userId) {
        return bookRepository.findAll(BookSearchSpecification.searchByBookStatus(bookStatus, userId ))
                .stream()
                .map(each ->
                        BookResponse.builder()
                                .id(each.getId())
                                .imageUrl(each.getImage().getImageUrl())
                                .build())
                .collect(Collectors.toList());
    }

    public List<BookResponse> searchByTitle(String title) {
        return bookRepository.findAll(BookSearchSpecification.search(title))
                .stream()
                .map(each ->
                        BookResponse.builder()
                                .id(each.getId())
                                .imageUrl(each.getImage().getImageUrl())
                                .build())
                .collect(Collectors.toList());
    }

    public BookResponse findBook(Long bookId) {
        final Book fromDb = bookRepository.findById(bookId).orElseThrow(() -> GenericException.builder().errorCode(ErrorCode.BOOK_NOT_FOUND).build());
        return BookResponse.builder()
                .id(fromDb.getId())
                .bookStatus(fromDb.getBookStatus())
                .publisher(fromDb.getPublisher())
                .authorName(fromDb.getAuthorName())
                .totalPage(fromDb.getTotalPage())
                .lastPageNumber(fromDb.getLastPageNumber())
                .title(fromDb.getTitle())
                .imageUrl(fromDb.getImage().getImageUrl())
                .categoryId(fromDb.getCategory().getId())
                .build();
    }

    private static class BookSearchSpecification {
        public static Specification<Book> search(String value) {
            return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("title"), "%" + value + "%");
        }

        public static Specification<Book> searchByBookStatus(BookStatus bookStatus, Long userId) {
            return (root, query, criteriaBuilder) -> criteriaBuilder.and(
                    criteriaBuilder.equal(root.get("bookStatus"), bookStatus),
                    criteriaBuilder.equal(root.get("userId"), userId)
            );
        }

        public static Specification<Book> searchByUserBooks(Long userId) {
            return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("userId"), userId);
        }
    }
}

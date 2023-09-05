package hakanozdmr.library.api;

import hakanozdmr.library.dto.BookListItemResponse;
import hakanozdmr.library.dto.BookResponse;
import hakanozdmr.library.dto.CategoryType;
import hakanozdmr.library.dto.request.SaveBookRequest;
import hakanozdmr.library.model.BookStatus;
import hakanozdmr.library.service.BookListService;
import hakanozdmr.library.service.BookSaveService;
import hakanozdmr.library.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/book")
@RequiredArgsConstructor
public class BookRestController {

    private final BookListService bookListService;
    private final BookSaveService bookSaveService;
    private final UserService userService;


    @PostMapping("/save")
    public ResponseEntity<BookListItemResponse> saveBook(@Valid @RequestBody SaveBookRequest request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(bookSaveService.saveBook(request));
    }

    @GetMapping("/search")
    public ResponseEntity<List<BookResponse>> listBook(@RequestParam(name = "size") int size, @RequestParam(name = "page") int page) {
        final Long userID = userService.findUserInContext().getId();
        return ResponseEntity.ok(bookListService.listBooks(size, page, userID));
    }


    @GetMapping("/search/{categoryType}")
    public ResponseEntity<List<BookResponse>> listByCategory(@PathVariable CategoryType categoryType) {
        final Long userID = userService.findUserInContext().getId();
        return ResponseEntity.ok(this.bookListService.searchByCategory(categoryType, userID));
    }


    @GetMapping("/{status}")
    public ResponseEntity<List<BookResponse>> listByCategory(@PathVariable BookStatus status) {
        final Long userID = userService.findUserInContext().getId();
        return ResponseEntity.ok(this.bookListService.searchBookStatus(status, userID));
    }

    @GetMapping("/list/{title}")
    public ResponseEntity<List<BookResponse>> listByTitle(@PathVariable String title) {
        return ResponseEntity.ok(this.bookListService.searchByTitle(title));
    }

    @DeleteMapping("/delete/{bookId}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long bookId) {
        bookSaveService.deleteBook(bookId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/get/{bookId}")
    public ResponseEntity<BookResponse> searchBook(@PathVariable Long bookId) {
        return ResponseEntity.ok(bookListService.findBook(bookId));
    }

}


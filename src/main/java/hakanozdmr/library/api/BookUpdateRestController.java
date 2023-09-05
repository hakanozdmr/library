package hakanozdmr.library.api;

import hakanozdmr.library.dto.BookListItemResponse;
import hakanozdmr.library.dto.request.BookUpdateRequest;
import hakanozdmr.library.service.BookUpdateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/bookImage")
@RestController
@RequiredArgsConstructor
public class BookUpdateRestController {
    private final BookUpdateService bookUpdateService;

    @PutMapping("/update")
    public ResponseEntity<BookListItemResponse> updateBook(@RequestBody BookUpdateRequest request) {
        return ResponseEntity.ok(bookUpdateService.updateBook(request));
    }
}


package com.lms.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lms.response.BookResponse;
import com.lms.service.BookService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/books")
public class BookController {

	private BookService bookService;

	public BookController(BookService bookService) {
		super();
		this.bookService = bookService;
	}

	@PostMapping
	public ResponseEntity<BookResponse> addBook(@RequestBody @Valid BookResponse bookResponse) {
		return new ResponseEntity<BookResponse>(bookService.addBook(bookResponse), HttpStatus.CREATED);
	}

	@GetMapping
	public ResponseEntity<List<BookResponse>> getAllBooks() {
		return ResponseEntity.ok(bookService.getAllBooks());
	}

	@GetMapping("/{id}")
	public ResponseEntity<BookResponse> getBookById(@PathVariable Long id) {
		return ResponseEntity.ok(bookService.getBookById(id));
	}

	@PutMapping("/{id}")
	public ResponseEntity<BookResponse> updateBook(@RequestBody @Valid BookResponse bookResponse,
			@PathVariable Long id) {
		return ResponseEntity.ok(bookService.updateBook(bookResponse, id));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteBook(@PathVariable Long id) {
		bookService.deleteBook(id);
		return ResponseEntity.ok(Map.of("deleted", Boolean.TRUE));
	}
	
	@PostMapping("/borrow/book/{bookId}/user/{userId}")
    public ResponseEntity<?> borrowBook(@PathVariable Long bookId,@PathVariable Long userId){
		return ResponseEntity.ok(bookService.borrowBook(bookId, userId));
	}

	@PostMapping("/return/book/{bookId}")
    public ResponseEntity<?> returnBook(@PathVariable Long bookId){
		return ResponseEntity.ok(bookService.returnBook(bookId));
	}
}

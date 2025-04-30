package com.lms.service;

import java.util.List;

import com.lms.response.BookResponse;

public interface BookService {

	BookResponse addBook(BookResponse bookResonse);

	List<BookResponse> getAllBooks();

	BookResponse getBookById(Long id);

	BookResponse updateBook(BookResponse bookResponse, Long id);

	void deleteBook(Long id);
	
	BookResponse borrowBook(Long bookId, Long userId);
	
	BookResponse returnBook(Long bookId);
}

package com.lms.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.lms.entity.Book;
import com.lms.entity.User;
import com.lms.exception.BookAlreadyBorrowedException;
import com.lms.exception.BookNotBorrowedException;
import com.lms.exception.ResourceNotFoundException;
import com.lms.repository.BookRepository;
import com.lms.repository.UserRepository;
import com.lms.response.BookResponse;
import com.lms.service.BookService;

@Service
public class BookServiceImpl implements BookService {

	private BookRepository bookRepository;
	private ModelMapper modelMapper;
	private UserRepository userRepository;

	public BookServiceImpl(BookRepository bookRepository, ModelMapper modelMapper, UserRepository userRepository) {
		super();
		this.bookRepository = bookRepository;
		this.modelMapper = modelMapper;
		this.userRepository = userRepository;
	}

	@Override
	public BookResponse addBook(BookResponse bookResponse) {

		Book book = modelMapper.map(bookResponse, Book.class);
		Book savedBook = bookRepository.save(book);

		return modelMapper.map(savedBook, BookResponse.class);
	}

	@Override
	public List<BookResponse> getAllBooks() {

		List<Book> books = bookRepository.findAll();
		List<BookResponse> bookResponses = books.stream().map(book -> modelMapper.map(book, BookResponse.class))
				.collect(Collectors.toList());
		return bookResponses;
	}

	@Override
	public BookResponse getBookById(Long id) {
		Book book = bookRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Book not found with id " + id));
		return modelMapper.map(book, BookResponse.class);
	}

	@Override
	public BookResponse updateBook(BookResponse bookResponse, Long id) {
		Book book = bookRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Book not found with id " + id));
		book.setTitle(bookResponse.getTitle());
		Book savedBook = bookRepository.save(book);
		return modelMapper.map(savedBook, BookResponse.class);
	}

	@Override
	public void deleteBook(Long id) {
		Book book = bookRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Book not found with id " + id));
		bookRepository.delete(book);

	}

	@Override
	public BookResponse borrowBook(Long bookId, Long userId) {

		Book book = bookRepository.findById(bookId)
				.orElseThrow(() -> new ResourceNotFoundException("Book not found with id " + bookId));
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with id " + userId));
		if (book.getBorrowedBy() != null) {
			throw new BookAlreadyBorrowedException(
					"Book with id " + bookId + " was already borrowed by user with id "+book.getBorrowedBy().getId());
		}
		book.setBorrowedBy(user);

		Book savedBook = bookRepository.save(book);

		return modelMapper.map(savedBook, BookResponse.class);
	}

	@Override
	public BookResponse returnBook(Long bookId) {
		Book book = bookRepository.findById(bookId)
				.orElseThrow(() -> new ResourceNotFoundException("Book not found with id " + bookId));
		if (book.getBorrowedBy() == null) {
			throw new BookNotBorrowedException("Book with id " + bookId + " in not borrowed");
		}
		book.setBorrowedBy(null);
		Book savedBook = bookRepository.save(book);

		return modelMapper.map(savedBook, BookResponse.class);
	}

}

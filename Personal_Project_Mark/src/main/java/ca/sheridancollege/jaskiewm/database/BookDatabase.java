package ca.sheridancollege.jaskiewm.database;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import ca.sheridancollege.jaskiewm.beans.Book;

@Repository
public class BookDatabase {
	@Autowired
	protected NamedParameterJdbcTemplate jdbc;
	
	// Getting books by their ID for adding to personal collection
	public List<Book> getBookListById(long id) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		String query = "SELECT * FROM books WHERE id = :id";
		namedParameters.addValue("id", id);
		return jdbc.query(query, namedParameters, new BeanPropertyRowMapper<Book>(Book.class));
	}
	
	// Methods for searching books
	public List<Book> getBookListByTitle(String bookTitle) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		String query = "SELECT * FROM books WHERE LOWER(bookTitle) LIKE LOWER(:bookTitle)";
		namedParameters.addValue("bookTitle", "%"+bookTitle+"%");
		return jdbc.query(query, namedParameters, new BeanPropertyRowMapper<Book>(Book.class));
	}
	public List<Book> getBookListByAuthor(String bookAuthor) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		String query = "SELECT * FROM books WHERE LOWER(bookAuthor) LIKE LOWER(:bookAuthor)";
		namedParameters.addValue("bookAuthor", "%"+bookAuthor+"%");
		return jdbc.query(query, namedParameters, new BeanPropertyRowMapper<Book>(Book.class));
	}
	public List<Book> getBookListByISBN(Long bookISBN) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		String query = "SELECT * FROM books WHERE LOWER(bookISBN) LIKE LOWER(:bookISBN)";
		namedParameters.addValue("bookISBN", "%"+bookISBN+"%");
		return jdbc.query(query, namedParameters, new BeanPropertyRowMapper<Book>(Book.class));
	}

	// Gets the book lists to output into the table
	public List<Book> getBookList() {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		String query = "SELECT * FROM books";
		return jdbc.query(query, namedParameters, new BeanPropertyRowMapper<Book>(Book.class));
	}
	
	// Method for adding books to the "All book list"
	public void insertBook(Book book) {
		try {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		String query = "INSERT INTO books(bookTitle,bookAuthor,bookISBN,bookRating) VALUES (:bookTitle,:bookAuthor,:bookISBN,:bookRating)";
		namedParameters.addValue("bookTitle", book.getBookTitle());
		namedParameters.addValue("bookAuthor", book.getBookAuthor());
		namedParameters.addValue("bookISBN", book.getBookISBN());
		namedParameters.addValue("bookRating", 0);
		int rowsAffected = jdbc.update(query, namedParameters);
		
		if (rowsAffected > 0)		
			System.out.println("Book added to database");
		}catch(Exception e) {
			System.out.println("Duplicate ISBN");
		}
	}
}

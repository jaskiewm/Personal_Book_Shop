package ca.sheridancollege.jaskiewm.database;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
//import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Repository;

import ca.sheridancollege.jaskiewm.beans.Book;

@Repository
public class UserBookDatabase {
	@Autowired
	protected NamedParameterJdbcTemplate jdbc;
	
	public List<Book> getPersonalBookList() {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		String query = "SELECT * FROM userBooks";
		return jdbc.query(query, namedParameters, new BeanPropertyRowMapper<Book>(Book.class));
	}
	
	public List<Book> getPersonalBookList2(long userID) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		String query = "SELECT * FROM booksPerUser WHERE userID = :userID";
		namedParameters.addValue("userID", userID);
		return jdbc.query(query, namedParameters, new BeanPropertyRowMapper<Book>(Book.class));
	}
	
	// Adding books to the personal bookshelf
	public void addBook(Book book) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		// Try-Catch for if the ISBN is duplicated
		try {
			String query = "INSERT INTO userBooks(bookTitle,bookAuthor,bookISBN, bookRating) VALUES (:bookTitle,:bookAuthor,:bookISBN, :bookRating)";
			namedParameters.addValue("bookTitle", book.getBookTitle());
			namedParameters.addValue("bookAuthor", book.getBookAuthor());
			namedParameters.addValue("bookISBN", book.getBookISBN());
			namedParameters.addValue("bookRating", 0);
			int rowsAffected = jdbc.update(query, namedParameters);
			
			if (rowsAffected > 0)		
				System.out.println("Book added to personal shelf");
		}catch(Exception e) {
			System.out.println("Duplicate ISBN");
		}
	}

	// Deleting books from the personal bookshelf
	public void deleteBookById(Long id) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		String query = "DELETE FROM userBooks WHERE id = :id";
		namedParameters.addValue("id", id);
		if (jdbc.update(query, namedParameters) > 0) {
			System.out.println("Deleted book " + id + " from the book shelf.");
		}
	}
	
	// Updating the rating in the personal bookshelf
	public void updatePersonalBookRating(Long id, int bookRating) {
		if(bookRating <=5 && bookRating >=0) {
			MapSqlParameterSource namedParameters = new MapSqlParameterSource();
			String query = "UPDATE userBooks SET bookRating=:bookRating WHERE id = :id";
			namedParameters.addValue("id", id);
			namedParameters.addValue("bookRating", bookRating);
			if (jdbc.update(query, namedParameters) > 0) {
				System.out.println("Set Book rating for book " + id + " to a " + bookRating);
			}
		}else
			System.out.println("Rating outside the range allowed.");
		}

	
	//-----------------CUSTOM PERSONAL PAGES------------------//
	
	// Adding books to the personal bookshelf
//	public void addPersonalBook(long id, Authentication authentication) {
//		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
//		// Try-Catch for if the ISBN is duplicated
//		try {
//			
//			Object auth = authentication.getPrincipal();
//			long userId = auth.getUserId();
//			
//			String query = "INSERT INTO booksPerUser(id,userId) VALUES (:id,:userId)";
//			namedParameters.addValue("id", id);
//			namedParameters.addValue("userId", book.getBookAuthor());
//			namedParameters.addValue("bookISBN", book.getBookISBN());
//			namedParameters.addValue("bookRating", 0);
//			int rowsAffected = jdbc.update(query, namedParameters);
//			
//			if (rowsAffected > 0)		
//				System.out.println("Book added to personal shelf");
//		}catch(Exception e) {
//			System.out.println("Duplicate ISBN");
//		}
//	}
	
	
}

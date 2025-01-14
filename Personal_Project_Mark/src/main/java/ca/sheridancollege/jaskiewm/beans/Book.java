package ca.sheridancollege.jaskiewm.beans;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Book {
	private long id;
	private String bookTitle;
	private String bookAuthor;
	private long bookISBN;
	private int bookRating;
}
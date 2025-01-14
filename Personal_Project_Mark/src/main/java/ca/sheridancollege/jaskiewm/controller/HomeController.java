package ca.sheridancollege.jaskiewm.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ca.sheridancollege.jaskiewm.beans.Book;
import ca.sheridancollege.jaskiewm.database.BookDatabase;
import ca.sheridancollege.jaskiewm.database.DatabaseAccess;
import ca.sheridancollege.jaskiewm.database.UserBookDatabase;

@Controller
public class HomeController {

	@Autowired
	private BookDatabase database;
	
	@Autowired
	private UserBookDatabase userBookDatabase;
	
	@Autowired
	private DatabaseAccess userDatabase;
	
	Book book = new Book();
	
	// Main HTML Pages
	@GetMapping("/")
	public String index(Model model) {
		model.addAttribute("book", new Book());
		model.addAttribute("bookList", database.getBookList());
		model.addAttribute("searchedBookList", database.getBookList());
		return("index");
	}
	
//	// OLD PERSONAL BOOK PAGE
//	@GetMapping("/personalBookPage")
//	public String personalBookPage(Model model) {
//		model.addAttribute("book", new Book());
//		model.addAttribute("personalBookList", userBookDatabase.getPersonalBookList());
//		return("personalBookPage");
//	}
	
	// NEW PERSONAL BOOK PAGE
	@GetMapping("/userSecured/personalBookPage")
	public String personalBookPage(Model model, Authentication authentication) {
		try{
			String email = authentication.getName();
			System.out.println(email + " is logged in");
			List<String> roleList = new ArrayList<String>();
			for (GrantedAuthority ga: authentication.getAuthorities()) {
				roleList.add(ga.getAuthority());
			}
			model.addAttribute("email", email);
			model.addAttribute("roleList", roleList);
		}catch(Exception e){
			return("login");
		}
		
		model.addAttribute("book", new Book());
		model.addAttribute("personalBookList", userBookDatabase.getPersonalBookList());
		return("/userSecured/personalBookPage");
	}
	
	// ADMIN ONLY PAGE
	@GetMapping("/admin")
	public String adminIndex(Model model, Authentication authentication) {
		List<String> roleList = new ArrayList<String>();
		for (GrantedAuthority ga: authentication.getAuthorities()) {
			roleList.add(ga.getAuthority());
		}
		
		//Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		model.addAttribute("roleList", userDatabase.getUserList());
		return "/admin/admin";
	}
	
	@GetMapping("/logout")
	public String logout() {
		return "redirect:/";
	}
	
	// LOGIN PAGE
	@GetMapping("/login")
	public String login() {
		return "login";
	}

	// PERMISSION DENIED
	@GetMapping("/permission-denied")
	public String permissionDenied() {
		return "/error/permission-denied";
	}
	
	// REGISTERING NEW USERS
	@GetMapping("/register")
	public String getRegister () {
		return ("register");
	}
	@PostMapping("/register")
	public String postRegister(@RequestParam String username, @RequestParam String password) {
		userDatabase.addUser(username, password);
		Long userId = userDatabase.findUserAccount(username).getUserId();
		// this next line is dangerous! For extra credit, try making a DatabaseAccess method to find a roleId
		// associate with a role of “ROLE_USER” and add the “correct” id every time
		userDatabase.addRole(userId, Long.valueOf(2));
		return ("redirect:/");
	}
	
	// Inserting books
	@PostMapping("/insertBook")
	public String insertBook(Model model, @ModelAttribute Book book) {
		//List<Book> existingBook = database.getBookListById(book.getId());
		database.insertBook(book);
		model.addAttribute("book", new Book());
		model.addAttribute("bookList", database.getBookList());
		return ("index");
	}
	
	// Deletes a personal book by their ID
	@GetMapping("/deletePersonalBookById/{id}")
	public String deletePersonalBookById(Model model, @PathVariable Long id) {
		userBookDatabase.deleteBookById(id);
		model.addAttribute("book", new Book());
		model.addAttribute("personalBookList", userBookDatabase.getPersonalBookList());
		return ("/userSecured/personalBookPage");
	}
	// Deletes a personal book by their ID
	@GetMapping("/deleteUser/{userId}")
	public String deleteUserById(Model model, @PathVariable Long userId) {
		userDatabase.deleteUserById(userId);
		model.addAttribute("roleList", userDatabase.getUserList());
		return ("/admin/admin");
	}
	
	
	//Method adds a book into the User's personal page
//	@GetMapping("/addNewPersonalBookById/{id}")
//	public String addNewPersonalBookByID(Model model, @PathVariable Long id, Authentication authentication) {
//		userBookDatabase.addPersonalBook(id,authentication);
//		model.addAttribute("book", new Book());
//		model.addAttribute("personalBookList", userBookDatabase.getPersonalBookList());
//		return ("userSecured/personalBookPage");
//	}
	
	//Method adds a book into the User's personal page
	@GetMapping("/addPersonalBookById/{id}")
	public String addPersonalBookByID(Model model, @PathVariable Long id) {
		Book personalbook = database.getBookListById(id).get(0);
		userBookDatabase.addBook(personalbook);
		model.addAttribute("book", new Book());
		model.addAttribute("personalBookList", userBookDatabase.getPersonalBookList());
		return ("userSecured/personalBookPage");
	}
	
	 // USER CAN REVIEW THE BOOKS ON THEIR SHELF
	@GetMapping("/ratePersonalBook/{rating}/{id}")
	public String ratePersonalBook(Model model, @PathVariable int rating, @PathVariable Long id) {
		userBookDatabase.updatePersonalBookRating(id,rating);
		model.addAttribute("book", new Book());
		model.addAttribute("personalBookList", userBookDatabase.getPersonalBookList());
		return ("userSecured/personalBookPage");
	}

	// SEARCHING FOR BOOKS
	@PostMapping("/searchBookByTitle")
	public String searchBookByTitle(Model model, @RequestParam String bookTitle) {
		System.out.println("Searching for "+bookTitle);
		model.addAttribute("book", new Book());
		model.addAttribute("bookList", database.getBookListByTitle(bookTitle));
		return "index";
	}	
	@PostMapping("/searchBookByAuthor")
	public String searchBookByAuthor(Model model, @RequestParam String bookAuthor) {
		System.out.println("Searching for author "+bookAuthor);
		model.addAttribute("book", new Book());
		model.addAttribute("bookList", database.getBookListByAuthor(bookAuthor));
		return "index";
	}
	@PostMapping("/searchBookByISBN")
	public String searchBookByISBN(Model model, @RequestParam Long bookISBN) {
		System.out.println("Searching for "+bookISBN);
		model.addAttribute("book", new Book());
		model.addAttribute("bookList", database.getBookListByISBN(bookISBN));
		return "index";
	}
}
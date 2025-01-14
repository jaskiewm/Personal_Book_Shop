CREATE TABLE user_security (
  userId            BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  userEmail         VARCHAR(75) NOT NULL UNIQUE,
  userPassword		VARCHAR(128) NOT NULL,
  enabled           BIT NOT NULL 
);

CREATE TABLE role_security(
  roleId   BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  roleName VARCHAR(30) NOT NULL UNIQUE
);

CREATE TABLE user_role(
  id     BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  userId BIGINT NOT NULL,
  roleId BIGINT NOT NULL
);

ALTER TABLE user_role
  ADD CONSTRAINT user_role_uk UNIQUE (userId, roleId);

ALTER TABLE user_role
  ADD CONSTRAINT user_role_fk1 FOREIGN KEY (userId)
  REFERENCES user_security (userId);
 
ALTER TABLE user_role
  ADD CONSTRAINT user_role_fk2 FOREIGN KEY (roleId)
  REFERENCES role_security (roleId);

CREATE TABLE books (
id LONG PRIMARY KEY AUTO_INCREMENT,
bookTitle VARCHAR(255),
bookAuthor VARCHAR(255),
bookISBN LONG NOT NULL UNIQUE,
bookRating INT);

CREATE TABLE userBooks (
id LONG PRIMARY KEY AUTO_INCREMENT,
bookTitle VARCHAR(255),
bookAuthor VARCHAR(255),
bookISBN LONG NOT NULL UNIQUE,
bookRating INT);



CREATE TABLE booksPerUser (
id LONG,
userId BIGINT);

ALTER TABLE booksPerUser
  ADD CONSTRAINT user_role_fk3 FOREIGN KEY (userId)
  REFERENCES user_security (userId);
  
  ALTER TABLE booksPerUser
  ADD CONSTRAINT user_book_fk1 FOREIGN KEY (id)
  REFERENCES books (id);

INSERT INTO books (bookTitle, bookAuthor, bookISBN, bookRating) VALUES
('To Kill a Mockingbird', 'Harper Lee', 9780061120084, 0),
('1984', 'George Orwell', 9780451524935, 0),
('Pride and Prejudice', 'Jane Austen', 9781503290563, 0),
('The Great Gatsby', 'F. Scott Fitzgerald', 9780743273565, 0),
('Moby Dick', 'Herman Melville', 9781503280786, 0),
('War and Peace', 'Leo Tolstoy', 9781400079988, 0),
('The Catcher in the Rye', 'J.D. Salinger', 9780316769488, 0),
('Brave New World', 'Aldous Huxley', 9780060850524, 0),
('The Hobbit', 'J.R.R. Tolkien', 9780547928227, 0),
('Fahrenheit 451', 'Ray Bradbury', 9781451673319, 0),
('The Picture of Dorian Gray', 'Oscar Wilde', 9780141439570, 0),
('The Chronicles of Narnia', 'C.S. Lewis', 9780066238500, 0),
('Crime and Punishment', 'Fyodor Dostoevsky', 9780486454115, 0),
('The Odyssey', 'Homer', 9780140268867, 0),
('Catch-22', 'Joseph Heller', 9781451626650, 0),
('The Lord of the Rings', 'J.R.R. Tolkien', 9780618640157, 0),
('Animal Farm', 'George Orwell', 9780451526342, 0),
('Jane Eyre', 'Charlotte Brontë', 9780142437209, 0),
('Wuthering Heights', 'Emily Brontë', 9781853260012, 0),
('The Grapes of Wrath', 'John Steinbeck', 9780143039433, 0),
('The Kite Runner', 'Khaled Hosseini', 9781594631931, 0),
('The Alchemist', 'Paulo Coelho', 9780062315007, 0),
('The Bell Jar', 'Sylvia Plath', 9780060837020, 0),
('The Road', 'Cormac McCarthy', 9780307387899, 0),
('Slaughterhouse-Five', 'Kurt Vonnegut', 9780385333207, 0),
('The Handmaids Tale', 'Margaret Atwood', 9780385490818, 0),
('The Great Expectations', 'Charles Dickens', 9780486275478, 0),
('A Tale of Two Cities', 'Charles Dickens', 9780486406514, 0),
('Les Misérables', 'Victor Hugo', 9780451525260, 0),
('Little Women', 'Louisa May Alcott', 9780143106655, 0),
('The Count of Monte Cristo', 'Alexandre Dumas', 9780451532114, 0),
('The Sun Also Rises', 'Ernest Hemingway', 9780743297332, 0),
('One Hundred Years of Solitude', 'Gabriel García Márquez', 9780060883287, 0),
('The Outsiders', 'S.E. Hinton', 9780142407332, 0),
('Dune', 'Frank Herbert', 9780441013593, 0),
('The Fault in Our Stars', 'John Green', 9780525478812, 0),
('The Good Earth', 'Pearl S. Buck', 9780743252021, 0),
('The Joy Luck Club', 'Amy Tan', 9780143038092, 0),
('Gone with the Wind', 'Margaret Mitchell', 9781416558999, 0),
('The Godfather', 'Mario Puzo', 9780451201088, 0),
('Life of Pi', 'Yann Martel', 9780156012191, 0),
('The Lovely Bones', 'Alice Sebold', 9780316163509, 0),
('Cloud Atlas', 'David Mitchell', 9780340822786, 0),
('The Poisonwood Bible', 'Barbara Kingsolver', 9780061577075, 0),
('The Girl with the Dragon Tattoo', 'Stieg Larsson', 9780307473476, 0),
('The Night Circus', 'Erin Morgenstern', 9780385534643, 0),
('Station Eleven', 'Emily St. John Mandel', 9780804148532, 0),
('The Seven Husbands of Evelyn Hugo', 'Taylor Jenkins Reid', 9781501188760, 0),
('The Vanishing Half', 'Brit Bennett', 9780525536963, 0),
('Daisy Jones & The Six', 'Taylor Jenkins Reid', 9781524798628, 0),
('Anxious People', 'Fredrik Backman', 9780593105858, 0),
('The Midnight Library', 'Matt Haig', 9780525559474, 0),
('Normal People', 'Sally Rooney', 9781984802719, 0),
('Educated', 'Tara Westover', 9780399590504, 0),
('Becoming', 'Michelle Obama', 9781524763130, 0),
('Sapiens: A Brief History of Humankind', 'Yuval Noah Harari', 9780062316097, 0);
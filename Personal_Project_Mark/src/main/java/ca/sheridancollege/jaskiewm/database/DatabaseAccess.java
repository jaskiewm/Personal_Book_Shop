package ca.sheridancollege.jaskiewm.database;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

//import ca.sheridancollege.jaskiewm.beans.Book;
import ca.sheridancollege.jaskiewm.beans.User;

@Repository
public class DatabaseAccess {

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private NamedParameterJdbcTemplate jdbc;
	
	// Method to add a user to our database
	public void addUser(String userEmail, String password) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		String query = "INSERT INTO user_security "
		+ "(userEmail, userPassword, enabled) "
		+ "VALUES (:userEmail, :userPassword, 1)";
		namedParameters.addValue("userEmail", userEmail);
		namedParameters.addValue("userPassword",
		passwordEncoder.encode(password));
		System.out.println("User "+userEmail+" added.");
		jdbc.update(query, namedParameters);
	}

	
	public void addRole(Long userId, Long roleId) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		String query = "INSERT INTO user_role (userId, roleId) "
		+ "VALUES (:userId, :roleId)";
		namedParameters.addValue("userId", userId);
		namedParameters.addValue("roleId", roleId);
		jdbc.update(query, namedParameters);
	}
	
	// Method to find a user account by email
	public User findUserAccount(String userEmail) {
		MapSqlParameterSource namedParameters = new
		MapSqlParameterSource();
		String query = "SELECT * FROM user_security where userEmail = :userEmail";
		namedParameters.addValue("userEmail", userEmail);
		try {
			return jdbc.queryForObject(query, namedParameters, new
			BeanPropertyRowMapper<>(User.class));
		} catch (EmptyResultDataAccessException erdae) {
			return null;
		}
	}
	
	// Method to get User Roles for a specific User id
	public List<String> getRolesById(Long userId) {
		MapSqlParameterSource namedParameters = new
		MapSqlParameterSource();
		String query = 
			"SELECT role_security.roleName " +
			"FROM user_role, role_security " +
			"WHERE user_role.roleId = role_security.roleId " +
			"AND userId = :userId";
		namedParameters.addValue("userId", userId);
		return jdbc.queryForList(query, namedParameters,
		String.class);
	}
	
	public List<User> getUserList() {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		String query = "SELECT * FROM user_security WHERE userEmail != 'admin@admin.ca'";
		return jdbc.query(query, namedParameters, new BeanPropertyRowMapper<User>(User.class));
	}
	
	// Deleting books from the personal bookshelf
		public void deleteUserById(Long userId) {
			MapSqlParameterSource namedParameters = new MapSqlParameterSource();
			String query1 = "DELETE FROM user_role WHERE userId = :userId";
			String query2 = "DELETE FROM booksPerUser WHERE userId = :userId";
			String query3 = "DELETE FROM user_security WHERE userId = :userId";
			namedParameters.addValue("userId", userId);
			jdbc.update(query1, namedParameters);
			jdbc.update(query2, namedParameters);
			if (jdbc.update(query3, namedParameters) > 0) {
				System.out.println("Deleted user " + userId + " from the user database.");
			}
		}
}

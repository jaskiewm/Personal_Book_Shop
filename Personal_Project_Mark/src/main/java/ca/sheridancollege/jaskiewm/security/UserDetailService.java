package ca.sheridancollege.jaskiewm.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ca.sheridancollege.jaskiewm.beans.User;
import ca.sheridancollege.jaskiewm.database.DatabaseAccess;


@Service
public class UserDetailService implements UserDetailsService{
	@Autowired
	@Lazy
	private DatabaseAccess da;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println(username);
		
		// Find the user based on the username (read email)
		User user = da.findUserAccount(username);
		
		// If the user doesn't exist, throw an exception
		if (user == null) {
			System.out.println("User not found:" + username);
			throw new UsernameNotFoundException("User " + username + "was not found in the database");
		}
		
		// Get a list of roles for that user
		List<String> roleNameList = da.getRolesById(user.getUserId());
		
		// Change the list of the user's roles into a list of GrantedAuthority
		List<GrantedAuthority> grantList = new ArrayList<>();
		if (roleNameList != null) {
			for (String role : roleNameList) {
				grantList.add(new SimpleGrantedAuthority(role));
			}
		}
		// Convert custom User bean into Spring Boot UserDetails
		UserDetails userDetails = new org.springframework.security.core.userdetails.User(user.getUserEmail(),
				user.getUserPassword(), grantList);
		return userDetails;
	}
}
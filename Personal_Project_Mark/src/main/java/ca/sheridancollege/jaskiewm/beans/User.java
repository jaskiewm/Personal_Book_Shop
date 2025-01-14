package ca.sheridancollege.jaskiewm.beans;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@RequiredArgsConstructor

// User will have their email and password to login
public class User {
	
	private long userId;
	
	// Login Credentials
	@NonNull
	private String userEmail;
	@NonNull
	private String userPassword;
	
	// Extra Information (not needed)
//	private String userFirstName;
//	private String userLastName;
//	private String userBio;
	
	private boolean enabled;
}

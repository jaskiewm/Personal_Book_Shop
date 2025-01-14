function verify() {
	var password1 = document.forms['form']['password'].value;
	var password2 = document.forms['form']['verifyPassword'].value;
	
	if (password1 == null || password1 == "" || password1 != password2) {
		document.getElementById("error").innerHTML = "Please check your passwords";
		return false;
	}
	
	if (password1.length < 6) {
		document.getElementById("error").innerHTML = "Ensure your password is 6 characters long";
		return false;
	}
}
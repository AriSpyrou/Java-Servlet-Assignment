package old_classes;

public class Users {

	public Users() {
		usersCounter++;
	}
	
	// Attributes
	private String username, password, name, 
	surname, telephone;
	private static int usersCounter = 0;
	
	// Methods 
	public void login() {
		// login method
		System.out.println("Login");
	}
	public void logout() {
		// logout method
		System.out.println("Logout");
	}
	
	// Getters & Setters
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
}

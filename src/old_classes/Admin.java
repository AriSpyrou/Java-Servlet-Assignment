package old_classes;

public class Admin extends Users {

	public Admin(String argAMKA) {
		AMKA = argAMKA;
		System.out.println("Admin with AMKA "+argAMKA+" was added to the system.");
	}
	
	// Attributes
	private final String AMKA;
	
	// Methods
	
	// Getters & Setters
	public String getAMKA() {
		return AMKA;
	}
}

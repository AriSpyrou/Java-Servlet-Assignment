package old_classes;

public class Patient extends Users {
	
	public Patient(String argAMKA) {
		AMKA = argAMKA;
		System.out.println("User with AMKA "+argAMKA+" was added to the system.");
		// Database update
	}
	
	// Attributes
	private final String AMKA;
	private int age;
	
	// Methods
	protected void register(String username, String password, String name, String surname, String telephone, int age) {
		setUsername(username);
		setPassword(password);
		setName(name);
		setSurname(surname);
		setTelephone(telephone);
		setAge(age);
		System.out.println("Patient registered");
		// Database implementation
	}
	protected void rateDoctor() {
		// Obsolete
	}
	protected void searchAppointment() {
		// Database search
	}
	protected void searchBySpecialty() {
		// Database search
	}
	protected void upcomingAppointment() {
		// Patient's appointments
	}
	// Getters & Setters
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getAMKA() {
		return AMKA;
	}
}

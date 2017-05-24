package old_classes;

public class Doctor extends Users {

	public Doctor(String argAMKA) {
		AMKA = argAMKA;
		System.out.println("Doctor with AMKA "+argAMKA+" was added to the system.");
	}
	
	// Attributes
	private final String AMKA;
	private String specialty;
	
	// Methods
	protected void setAvailability(int day, int month, int hour, int duration) {
		// TODO
		System.out.println("Schedule successfully updated.");
	}
	protected void setProfile() {
		// TODO
		System.out.println("Profile Modified");
	}
	protected void viewSchedule() {
		// TODO
		System.out.println("ViewSchedule");
	}
	
	// Getters & Setters
	public String getSpecialty() {
		return specialty;
	}
	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}
	public String getAMKA() {
		return AMKA;
	}
	
}

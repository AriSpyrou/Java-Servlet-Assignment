package old_classes;

public class Appointment {

	public Appointment(Patient patient, Doctor doctor, int arghour, int argminutes, int argday, int argmonth) {
		// Database update here
		hour = arghour;
		minutes = argminutes;
		day = argday;
		month = argmonth;
		System.out.println("Appointment was successfully registered.");
	}
	// Attributes
	private int hour, minutes, day, month;
	
	// Methods
	protected void cancelAppointment(){
		// Database update here
		// Set object to null in main
		System.out.println("Appointment was successfully canceled");
	}
	
	// Getters & Setters
	public int getHour() {
		return hour;
	}

	public int getMinutes() {
		return minutes;
	}

	public int getDay() {
		return day;
	}

	public int getMonth() {
		return month;
	}
		
}

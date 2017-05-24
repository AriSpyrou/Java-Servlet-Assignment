package old_classes;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class CreateUsers {

	public CreateUsers() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		Patient pat = new Patient("123");
		Doctor doc = new Doctor("231");
		Admin adm = new Admin("321");
		Appointment app = new Appointment(pat, doc, 13, 20, 3, 4);
		pat.login();
		doc.login();
		adm.login();
		pat.logout();
		pat.register("mixPapatouk", "123456vapenation", "Mixalis", "Papatoukakis", "693000000", 10);
		pat.rateDoctor();
		app.cancelAppointment();
		System.out.println(app.getHour()+":"+app.getMinutes());
		Scanner input = new Scanner(System.in);
		try	{
			Patient pat2 = new Patient(input.nextLine());
			pat2.login();
		}
		catch(InputMismatchException e) {
			System.out.println(e);
		}
		catch(Exception e)	{
			System.out.println(e);
		}
		try {
			Doctor doc2 = new Doctor(input.nextLine());
			doc2.login();
		}
		catch(InputMismatchException e) {
			System.out.println(e);
		}
		catch(Exception e)	{
			System.out.println(e);
		}
		input.close();
		try	{
			BufferedReader input2 = new BufferedReader(new FileReader("source.txt"));
			String[] splitString = input2.readLine().split(" ");
			Patient pat3 = new Patient(splitString[0]);
			pat3.register(splitString[1], splitString[2], splitString[3], splitString[4], splitString[5], Integer.parseInt(splitString[6]));
			input2.close();
		}
		catch(FileNotFoundException e) { 
			
		}
		catch(IOException e) {
			
		}
		catch(NumberFormatException e) {
			
		}
	}

}

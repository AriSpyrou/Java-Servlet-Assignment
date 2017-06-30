package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * Servlet implementation class PatientServlet
 */
@WebServlet("/PatientServlet")
public class PatientServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	String username, password, patientAMKA;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PatientServlet() {
        super();
    }
    
    void show_patientDetails(HttpServletRequest request, HttpServletResponse response, PrintWriter out) {
    	try
        {
            Context ctx = new InitialContext();
            DataSource ds = (DataSource)ctx.lookup("java:comp/env/jdbc/postgres");
            if (ds != null) 
            {
                Connection conn = ds.getConnection();
                if(conn != null) 
                {
                    Statement stmt = conn.createStatement();
                    ResultSet rst = stmt.executeQuery("SELECT patientAMKA,name,surname,gender FROM public.patient WHERE userid='"+username+"';");
                    out.println(""
                    		+ "<table BORDER=1 CELLPADDING=0 CELLSPACING=0 WIDTH=100%>"
                            +"<tr><th>Patient AMKA</th><th>Name</th><th>Surname</th><th>Gender</th></tr>"
                            + "");
                    while(rst.next()){
                        out.println("<tr><td><center>"+rst.getString("patientamka")+"</center></td>"
                             + "<td><center>"+rst.getString("name")+"</center></td>"
                             + "<td><center>"+rst.getString("surname")+"</center></td>"
                             + "<td><center>"+rst.getString("gender")+"</center></td></tr>");
                    }
                    out.println("</table>");
                    conn.close();
                }
            }
        }
        catch(Exception e) 
        {
            e.printStackTrace();
        }
    }
    
    void show_appointment_history(HttpServletRequest request, HttpServletResponse response, PrintWriter out) {
    	try
        {
            Context ctx = new InitialContext();
            DataSource ds = (DataSource)ctx.lookup("java:comp/env/jdbc/postgres");
            if (ds != null) 
            {
                Connection conn = ds.getConnection();
                if(conn != null) 
                {
                    Statement stmt = conn.createStatement();
                    ResultSet rst = stmt.executeQuery("SELECT * FROM public.appointments WHERE \"patientAMKA\"='"+patientAMKA+"';");
                    out.println(""
                    		+ "<table BORDER=1 CELLPADDING=0 CELLSPACING=0 WIDTH=100%>"
                            +"<tr><th>Date</th><th>Doctor AMKA</th><th>Diagnosis</th></tr>"
                            + "");
                    while(rst.next()){
                        out.println("<tr><td><center>"+rst.getString("t")+"</center></td>"
                             + "<td><center>"+rst.getString("doctorAMKA")+"</center></td>"
                             + "<td><center>"+rst.getString("diagnosis")+"</center></td></tr>");
                    }
                    out.println("</table>");
                    conn.close();
                }
            }
        }
    	catch(Exception e) 
        {
            e.printStackTrace();
        }
    }
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		username = loginServlet.username;
		patientAMKA = loginServlet.AMKA;
		if (request.getParameter("show_patientDetails") != null) {
			show_patientDetails(request, response , out);
		}
		else if (request.getParameter("show_appointment_history") != null) {
			show_appointment_history(request, response , out);
		}
	}
}

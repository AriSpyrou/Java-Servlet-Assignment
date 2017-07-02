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
                    ResultSet rst = stmt.executeQuery("SELECT * FROM public.patient WHERE userid='"+loginServlet.username+"';");
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
                    ResultSet rst = stmt.executeQuery("SELECT * FROM public.appointments WHERE \"patientAMKA\"='"+loginServlet.AMKA+"';");
                    out.println(""
                    		+ "<table BORDER=1 CELLPADDING=0 CELLSPACING=0 WIDTH=100%>"
                            +"<tr><th>ID</th><th>Date</th><th>Doctor AMKA</th><th>Diagnosis</th></tr>"
                            + "");
                    while(rst.next()){
                        out.println("<tr><td><center>"+rst.getString("id")+"</center></td>"
                           	 +	"<td><center>"+rst.getString("t")+"</center></td>"
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
    
    void cancel_appointment(HttpServletRequest request, HttpServletResponse response, PrintWriter out) {
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
                    if(stmt.executeUpdate("DELETE FROM public.appointments WHERE id="+request.getParameter("id")+" AND t > NOW()+INTERVAL '2 DAY' AND \"patientAMKA\"='"+loginServlet.AMKA+"'")==0)
                    {
                    	out.println("Wrong ID or appointment is closer than 3 days");
                    }
                    conn.close();
                }
            }
        }
    	catch(Exception e) 
        {
            e.printStackTrace();
        }
    }
    
    void book_appointment(HttpServletRequest request, HttpServletResponse response, PrintWriter out) {
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
	                ResultSet rst = stmt.executeQuery("SELECT * FROM public.vacancy WHERE \"doctorAMKA\" ="+request.getParameter("amka")+" AND date ='"+request.getParameter("date")+"' AND \"time\" ='"+request.getParameter("time")+"'");
	                if(rst.next())
	                {
	                	stmt.executeUpdate("DELETE FROM public.vacancy WHERE \"doctorAMKA\" ="+request.getParameter("amka")+" AND date ='"+request.getParameter("date")+"' AND \"time\" ='"+request.getParameter("time")+"'");
	                	rst = stmt.executeQuery("SELECT id FROM public.appointments ORDER BY id DESC LIMIT 1");
	                	rst.next();
	                	int num = rst.getInt("id")+1;
	                	stmt.executeUpdate("INSERT INTO public.appointments VALUES("+num+",'"+request.getParameter("date")+"',"+loginServlet.AMKA+","+request.getParameter("amka")+")");
	                }
	                else
	                {
	                	out.println("Doctor isn't available at that time or AMKA is incorrect");
	                }
                }
            }
        }
    	catch(Exception e) 
        {
            e.printStackTrace();
        }
    }
    
    void show_specialty_availability(HttpServletRequest request, HttpServletResponse response, PrintWriter out) {
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
	                ResultSet rst = stmt.executeQuery("SELECT * FROM public.vacancy v JOIN public.doctor d ON v.\"doctorAMKA\" = d.doctorAMKA WHERE d.specialty ="+request.getParameter("spec"));
	                out.println(""
                    		+ "<table BORDER=1 CELLPADDING=0 CELLSPACING=0 WIDTH=100%>"
                            +"<tr><th>Doctor AMKA</th><th>Date</th><th>Time</th></tr>"
                            + "");
                    while(rst.next()){
                        out.println("<tr><td><center>"+rst.getString("doctorAMKA")+"</center></td>"
                        	 +	"<td><center>"+rst.getString("date")+"</center></td>"
                             + "<td><center>"+rst.getString("time")+"</center></td>");
                    }
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
		if (request.getParameter("show_patientDetails") != null) {
			show_patientDetails(request, response , out);
		}
		else if (request.getParameter("show_appointment_history") != null) {
			show_appointment_history(request, response , out);
		}
		else if(request.getParameter("show_specialty_availability") != null) {
			RequestDispatcher view = request.getRequestDispatcher("html/form_show_specialty_availability.html");
			view.forward(request, response);
		}
		else if(request.getParameter("form_show_specialty_availability") != null) {
			show_specialty_availability(request, response, out);
		}
		else if(request.getParameter("book_appointment") != null) {
			RequestDispatcher view = request.getRequestDispatcher("html/form_book_appointment.html");
			view.forward(request, response);
		}
		else if(request.getParameter("form_book_appointment") != null) {
			book_appointment(request, response, out);
		}
		else if(request.getParameter("cancel_appointment") != null) {
			RequestDispatcher view = request.getRequestDispatcher("html/form_cancel_appointment.html");
			view.forward(request, response);
		}
		else if(request.getParameter("form_cancel_appointment") != null) {
			cancel_appointment(request, response, out);
		}
	}
}

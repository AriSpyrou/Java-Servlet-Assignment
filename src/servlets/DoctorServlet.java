package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Calendar;
import java.util.GregorianCalendar;

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
 * Servlet implementation class DoctorServlet
 */
@WebServlet("/DoctorServlet")
public class DoctorServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static int hour = 6;
	static int min = 30;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DoctorServlet() {
        super();
    }
    String dateFormat(String day)
    {
    	Calendar c = new GregorianCalendar();
    	String year = Integer.toString(c.get(Calendar.YEAR));
    	String month = Integer.toString(c.get(Calendar.MONTH)+1);
    	String date = year+"-"+month+"-"+day;
    	return date;
    }
    
    String timeFormat()
    {
    	if(hour == 12 && min == 30)
    	{
    		hour = 7;
    		min = 0;
    	}
    	else if(min == 30)
    	{
    		hour += 1;
    		min = 0;
    	}
    	else if(min == 0)
    	{
    		min = 30;
    	}
    	String time = Integer.toString(hour)+":"+Integer.toString(min)+":"+"00";
    	return time;
    }
    
    void availability_set(HttpServletRequest request, HttpServletResponse response, PrintWriter out)
    {
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
                    if(request.getParameter("a1")!= null)
                    {
                    	try
                    	{
		                    for(int i=Integer.parseInt(request.getParameter("a1"));i<Integer.parseInt(request.getParameter("a2"))+1;i++)
		                    {
		                    	for(int j=1;j<13;j++)
		                    	{
		                    		stmt.executeUpdate("INSERT INTO public.vacancy VALUES("+loginServlet.AMKA+",'"+dateFormat(Integer.toString(i))+"','"+timeFormat()+"')");
		                    	}
		                    }
                    	}
                    	catch(Exception e){}
                    }
                    if(request.getParameter("b1")!= null)
                    {
                    	try
                    	{
		                    for(int i=Integer.parseInt(request.getParameter("b1"));i<Integer.parseInt(request.getParameter("b2"))+1;i++)
		                    {
		                    	for(int j=1;j<13;j++)
		                    	{
		                    		stmt.executeUpdate("INSERT INTO public.vacancy VALUES("+loginServlet.AMKA+",'"+dateFormat(Integer.toString(i))+"','"+timeFormat()+"')");
		                    	}
		                    }
                    	}
                    	catch(Exception e){}
                    }
                    if(request.getParameter("c1")!= null)
                    {
                    	try
                    	{
		                    for(int i=Integer.parseInt(request.getParameter("c1"));i<Integer.parseInt(request.getParameter("c2"))+1;i++)
		                    {
		                    	for(int j=1;j<13;j++)
		                    	{
		                    		stmt.executeUpdate("INSERT INTO public.vacancy VALUES("+loginServlet.AMKA+",'"+dateFormat(Integer.toString(i))+"','"+timeFormat()+"')");
		                    	}
		                    }
                    	}
                    	catch(Exception e){}
                    }
                    if(request.getParameter("d1")!= null)
                    {
                    	try
                    	{
		                    for(int i=Integer.parseInt(request.getParameter("d1"));i<Integer.parseInt(request.getParameter("d2"))+1;i++)
		                    {
		                    	for(int j=1;j<13;j++)
		                    	{
		                    		stmt.executeUpdate("INSERT INTO public.vacancy VALUES("+loginServlet.AMKA+",'"+dateFormat(Integer.toString(i))+"','"+timeFormat()+"')");
		                    	}
		                    }
                    	}
                    	catch(Exception e){}
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
                    ResultSet rst = stmt.executeQuery("SELECT * FROM public.appointments WHERE \"doctorAMKA\"='"+loginServlet.AMKA+"';");
                    out.println(""
                    		+ "<table BORDER=1 CELLPADDING=0 CELLSPACING=0 WIDTH=100%>"
                            +"<tr><th>ID</th><th>Date</th><th>Patient AMKA</th><th>Diagnosis</th></tr>"
                            + "");
                    while(rst.next()){
                        out.println("<tr><td><center>"+rst.getString("id")+"</center></td>"
                        	 +	"<td><center>"+rst.getString("t")+"</center></td>"
                             + "<td><center>"+rst.getString("patientAMKA")+"</center></td>"
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
                    if(stmt.executeUpdate("DELETE FROM public.appointments WHERE id="+request.getParameter("id")+" AND t > NOW()+INTERVAL '2 DAY' AND \"doctorAMKA\"='"+loginServlet.AMKA+"'")==0)
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

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		if (request.getParameter("availability_statement") != null) {
			loginServlet.validateCookie(request, response);
			RequestDispatcher view = request.getRequestDispatcher("html/form_availability.html");
			view.forward(request, response);
		}
		else if(request.getParameter("availability_set") != null){
			loginServlet.validateCookie(request, response);
			availability_set(request, response, out);
		}
		else if(request.getParameter("show_appointment_history") != null) {
			loginServlet.validateCookie(request, response);
			show_appointment_history(request, response , out);
		}
		else if(request.getParameter("cancel_appointment") != null) {
			loginServlet.validateCookie(request, response);
			RequestDispatcher view = request.getRequestDispatcher("html/form_cancel_appointment.html");
			view.forward(request, response);
		}
		else if(request.getParameter("form_cancel_appointment") != null) {
			loginServlet.validateCookie(request, response);
			cancel_appointment(request, response, out);
		}
	}

}

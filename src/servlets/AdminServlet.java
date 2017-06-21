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

import old_classes.PasswordStoring;

/**
 * Servlet implementation class AdminServlet
 */
@WebServlet("/AdminServlet")
public class AdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminServlet() {
        super();
    }
    
    void add_patient(HttpServletRequest request, HttpServletResponse response, PrintWriter out)
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
    
    void remove_patient(HttpServletRequest request, HttpServletResponse response, PrintWriter out)
    {
    }
    
    void add_doctor(HttpServletRequest request, HttpServletResponse response, PrintWriter out)
    {
    }
    
    void remove_doctor(HttpServletRequest request, HttpServletResponse response, PrintWriter out)
    {
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		if (request.getParameter("add_patient") != null) {
			RequestDispatcher view = request.getRequestDispatcher("html/form_add_user.html");
			view.forward(request, response);
		}
		else if (request.getParameter("remove_patient") != null) {
			RequestDispatcher view = request.getRequestDispatcher("html/form_remove.html");
			view.forward(request, response);
		}
		else if (request.getParameter("add_doctor") != null) {
			RequestDispatcher view = request.getRequestDispatcher("html/form_add_doctor.html");
			view.forward(request, response);
		}
		else if (request.getParameter("remove_doctor") != null) {
			RequestDispatcher view = request.getRequestDispatcher("html/form_remove.html");
			view.forward(request, response);
		}
	}

}

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
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher view = request.getRequestDispatcher("html/login.html");
		view.forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		String username = request.getParameter("uname");
		String password = request.getParameter("pass");
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
                    ResultSet rst = stmt.executeQuery("SELECT * FROM public.patient WHERE userid='"+username+"' AND password='"+password+"';");
                    if(rst.next())
                    {
                    	RequestDispatcher view = request.getRequestDispatcher("html/main.html");
                		view.forward(request, response);
                    }
                    else
                    {
                    	out.append("Wrong username-password combination");
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
}

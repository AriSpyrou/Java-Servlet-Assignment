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
    
    void login(HttpServletRequest request, HttpServletResponse response, PrintWriter out) {
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
                    // cgeorge5x@newyorker.com
                    // TxOu7F
                    if(rst.next())
                    {
                    	RequestDispatcher view = request.getRequestDispatcher("html/main.html");
                		view.forward(request, response);
                		/* 2o Erotima thelei ligo tropopoihsh vasiki leitourgia douleuei
                		Statement stmt_ = conn.createStatement();
                        ResultSet rst_ = stmt_.executeQuery("SELECT patientAMKA,name,surname,gender FROM public.patient WHERE userid='"+username+"';");
                        out.println("<table BORDER=1 CELLPADDING=0 CELLSPACING=0 WIDTH=100%>"
                                +"<tr><th>Patient AMKA</th><th>Name</th></tr><th>Surname</th></tr><th>Gender</th></tr>");
 
                        while(rst_.next()){
                            try { if (rst != null) rst.close(); } catch (Exception e) {};
                            try { if (stmt != null) stmt.close(); } catch (Exception e) {};
                            out.println("<tr><td><center>"+rst_.getString("patientamka")+"</center></td>"
                                 + "<td><center>"+rst_.getString("name")+"</center></td></tr>"
                                 + "<td><center>"+rst_.getString("surname")+"</center></td></tr>"
                                 + "<td><center>"+rst_.getString("gender")+"</center></td></tr>");
                        }
                        out.println("</table>"); 
                		*/
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

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher view = request.getRequestDispatcher("html/login.html");
		view.forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		//login(request, response, out);
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
                    // cgeorge5x@newyorker.com
                    // TxOu7F
                    if(rst.next())
                    {
                    	RequestDispatcher view = request.getRequestDispatcher("html/main.html");
                		view.forward(request, response);
                		/* 2o Erotima thelei ligo tropopoihsh vasiki leitourgia douleuei
                		Statement stmt_ = conn.createStatement();
                        ResultSet rst_ = stmt_.executeQuery("SELECT patientAMKA,name,surname,gender FROM public.patient WHERE userid='"+username+"';");
                        out.println("<table BORDER=1 CELLPADDING=0 CELLSPACING=0 WIDTH=100%>"
                                +"<tr><th>Patient AMKA</th><th>Name</th></tr><th>Surname</th></tr><th>Gender</th></tr>");
 
                        while(rst_.next()){
                            try { if (rst != null) rst.close(); } catch (Exception e) {};
                            try { if (stmt != null) stmt.close(); } catch (Exception e) {};
                            out.println("<tr><td><center>"+rst_.getString("patientamka")+"</center></td>"
                                 + "<td><center>"+rst_.getString("name")+"</center></td></tr>"
                                 + "<td><center>"+rst_.getString("surname")+"</center></td></tr>"
                                 + "<td><center>"+rst_.getString("gender")+"</center></td></tr>");
                        }
                        out.println("</table>"); */
                		
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

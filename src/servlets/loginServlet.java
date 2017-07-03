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
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import old_classes.PasswordStoring;

/**
 * Servlet implementation class loginServlet
 */
@WebServlet("/loginServlet")
public class loginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static String AMKA; 
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public loginServlet() {
        super();
    }
    void login_function(HttpServletRequest request, HttpServletResponse response, PrintWriter out) {
    	String username = request.getParameter("uname");
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
                    ResultSet rst = stmt.executeQuery("SELECT * FROM public.patient WHERE userid='"+username+"';");
                    if(rst.next() && PasswordStoring.verifyPassword(request.getParameter("pass"), rst.getString("password")))
                    {
                    	AMKA = rst.getString("patientAMKA");
                    	Cookie loginCookie = new Cookie("amka", AMKA);
                    	loginCookie.setMaxAge(3600);
                    	response.addCookie(loginCookie);
                    	RequestDispatcher view = request.getRequestDispatcher("html/main_p.html");
                		view.forward(request, response);
                    }
                    stmt = conn.createStatement();
                    rst = stmt.executeQuery("SELECT * FROM public.doctor WHERE username='"+username+"';");
                    if(rst.next() && PasswordStoring.verifyPassword(request.getParameter("pass"), rst.getString("password")))
                    {
                    	AMKA = rst.getString("doctorAMKA");
                    	Cookie loginCookie = new Cookie("amka", AMKA);
                    	loginCookie.setMaxAge(3600);
                    	response.addCookie(loginCookie);
                    	RequestDispatcher view = request.getRequestDispatcher("html/main_d.html");
                		view.forward(request, response);
                    }
                    stmt = conn.createStatement();
                    rst = stmt.executeQuery("SELECT * FROM public.admin WHERE userid='"+username+"';");
                    if(rst.next() && PasswordStoring.verifyPassword(request.getParameter("pass"), rst.getString("password")))
                    {
                    	AMKA = rst.getString("adminAMKA");
                    	Cookie loginCookie = new Cookie("amka", AMKA);
                    	loginCookie.setMaxAge(3600);
                    	response.addCookie(loginCookie);
                    	RequestDispatcher view = request.getRequestDispatcher("html/main_a.html");
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
    
    static void validateCookie(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	loginServlet.AMKA = null;
    	Cookie[] cookies = request.getCookies();
    	if(cookies != null) {
    		for(Cookie cookie : cookies) {
    			if(cookie.getName().equals("amka")) loginServlet.AMKA = cookie.getValue();
    		}
    	}
    	if(loginServlet.AMKA == null) {
    		RequestDispatcher view = request.getRequestDispatcher("html/index.html");
    		view.forward(request, response);
    	}
    }
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher view = request.getRequestDispatcher("html/index.html");
		view.forward(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		if (request.getParameter("login") != null) {
			login_function(request, response, out);
		}	
	}
}

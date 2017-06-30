package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
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
                    if(stmt.executeUpdate("INSERT INTO public.patient VALUES("+request.getParameter("amka")+",'"+request.getParameter("uname")+"','"+PasswordStoring.createHash(request.getParameter("pass"))+"','"+request.getParameter("fname")+"','"+request.getParameter("sname")+"','"+request.getParameter("gender")+"')")==0)
                    {
                    	out.println("Insert failed");
                    }
                    else
                    {
                    	out.println("Successfully inserted");
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
    
    void remove_user(HttpServletRequest request, HttpServletResponse response, PrintWriter out)
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
                    if(stmt.executeUpdate("DELETE FROM public.patient WHERE patientAMKA='"+request.getParameter("amka")+"';")==0)
                    {
                    	if(stmt.executeUpdate("DELETE FROM public.doctor WHERE doctorAMKA='"+request.getParameter("amka")+"';")==0)
                    	{
                    		out.println("Invalid AMKA");
                    	}
                    	else
                    	{
                    		out.println("Successfully removed");
                    	}
                    }
                    else
                    {
                    	out.println("Successfully removed");
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
    
    void add_doctor(HttpServletRequest request, HttpServletResponse response, PrintWriter out)
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
                    if(stmt.executeUpdate("INSERT INTO public.doctor VALUES("+request.getParameter("amka")+",'"+request.getParameter("uname")+"','"+PasswordStoring.createHash(request.getParameter("pass"))+"','"+request.getParameter("fname")+"','"+request.getParameter("sname")+"',"+request.getParameter("specialty")+")")==0)
                    {
                    	out.println("Insert failed");
                    }
                    else
                    {
                    	out.println("Successfully inserted");
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
		else if (request.getParameter("form_remove") != null) {
			remove_user(request, response , out);
		}
		else if (request.getParameter("form_add_user") != null) {
			add_patient(request, response, out);
		}
		else if (request.getParameter("form_add_doctor") != null) {
			add_doctor(request, response, out);
		}
	}

}

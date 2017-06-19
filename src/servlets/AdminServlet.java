package servlets;

import java.io.IOException;
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
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
                    stmt.setMaxRows(2000);
                    ResultSet rst = stmt.executeQuery("SELECT * FROM public.doctor ORDER BY doctorAMKA ASC;");
                    /*
                     	cgeorge5x@newyorker.com
                     	TxOu7F
                    */
                    while(rst.next()) 
                    {
                    	/*String username = rst.getString("doctorAMKA");
                        String password = rst.getString("password");
                        String newHash = PasswordStoring.createHash(password);
                    	DataSource ds2 = (DataSource)ctx.lookup("java:comp/env/jdbc/postgres");
                    	Connection conn2 = ds2.getConnection();
                    	Statement stmt2 = conn2.createStatement();
                        stmt2.executeUpdate("UPDATE public.doctor SET \"password\"='"+newHash+"' WHERE doctorAMKA='"+username+"';");
                        conn2.close();*/
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
		
	}

}

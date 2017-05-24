package servlets;
import javax.naming.*;
import javax.sql.*;
import java.sql.*;
public class DBTest {
	String foo = "Not Connected";
    int bar = -1;

    @SuppressWarnings("unused")
	public void init() 
    {
        try
        {
            Context ctx = new InitialContext();
            if(ctx == null )
                throw new Exception("Boom - No Context");

            // /jdbc/postgres is the name of the resource above 
            DataSource ds = (DataSource)ctx.lookup("java:comp/env/jdbc/postgres");

            if (ds != null) 
            {
                Connection conn = ds.getConnection();

                if(conn != null) 
                {
                    foo = "Got Connection "+conn.toString();
                    Statement stmt = conn.createStatement();
                    ResultSet rst = stmt.executeQuery("select id, name from drugs");

                    if(rst.next())
                    {
                        foo=rst.getString(2);
                        bar=rst.getInt(1);
                    }
                    conn.close();
                }
            }
        }
        catch(Exception e) 
        {
            e.printStackTrace();
            System.out.println("You dun goofed");
        }
    }

    public String getFoo() { return foo; }

    public int getBar() { return bar;}
}

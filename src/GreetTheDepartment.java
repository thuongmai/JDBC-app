import java.sql.*;

//OUT parameters

public class GreetTheDepartment {

	public static void main (String[] args) throws Exception {
		Connection myConn = null;
		CallableStatement myStmt = null;
		
		String url = "jdbc:mysql://localhost:3306/demo?useSSL=false";
		String user = "student";
		String password = "student";
		
		String theDepartment = "Engineering";
		
		try {
			//Get a connection to database
			myConn = DriverManager.getConnection(url, user, password);
			
			//Prepare the stored procedure call
			myStmt = myConn.prepareCall("{call greet_the_department(?)}");
			
			//Set the parameters
			myStmt.registerOutParameter(1, Types.VARCHAR);
			myStmt.setString(1, theDepartment);
			
			//Call stored procedure
			System.out.println("Calling stored procedure");
			myStmt.execute();
			System.out.println("Finished calling stored procedure");
			
			// Get the value of the INOUT parameter
			String theResult = myStmt.getString(1);
			System.out.println("\nThe result = " + theResult);
			
		} catch (Exception exc) {
			exc.printStackTrace();
		} finally {
			if (myStmt != null) myStmt.close();
			if (myConn != null) myConn.close();
		}
	}
	
}

import java.sql.*;

public class GetEmployeesForDepartment {

	public static void main(String[] args) throws Exception {
		Connection myConn = null;
		CallableStatement myStmt = null;
		ResultSet myRs = null;

		String url = "jdbc:mysql://localhost:3306/demo?useSSL=false";
		String user = "student";
		String password = "student";

		String theDepartment = "Engineering";

		try {
			// Get a connection to the database
			myConn = DriverManager.getConnection(url, user, password);

			// Prepare the store procedure call
			myStmt = myConn.prepareCall("{call get_employees_for_department(?)}");

			// Set the parameter
			myStmt.setString(1, theDepartment);

			// Call stored procedure
			myStmt.execute();

			// Get the result set
			myRs = myStmt.getResultSet();

			// Display the result set
			display(myRs);

		} catch (Exception exc) {
			exc.printStackTrace();
		} finally {
			if (myRs != null)
				myRs.close();
			if (myStmt != null)
				myStmt.close();
			if (myConn != null)
				myConn.close();
		}
	}

	private static void display(ResultSet myRs) throws Exception{
		while (myRs.next())
			System.out.println(myRs.getString(1) + "\t" + myRs.getString(2) + "\t" + myRs.getString(3));
	}

}

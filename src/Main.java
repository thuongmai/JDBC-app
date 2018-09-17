import java.sql.*;

public class Main {
	public static void main(String[] args) throws SQLException {
		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;

		// Added ?useSSL=false to get rid of a warning about missing server's identity
		// verification
		String dbUrl = "jdbc:mysql://localhost:3306/demo?useSSL=false";
		String user = "student";
		String pass = "student";

		try {
			// 1. Get a connection to database
			myConn = DriverManager.getConnection(dbUrl, user, pass);
			System.out.println("Database connection successfull!\n");

			// 2. Create a statement
			myStmt = myConn.createStatement();

			// 3. Execute SQL query
			// System.out.println("SELECT * FROM employees\n");
			// myRs = myStmt.executeQuery("select * from employees");
			// System.out.println("Inserting a new employee to database\n");
			myStmt.executeUpdate(
					"insert into employees (last_name, first_name, email, department, salary) values " + 
					"('Wright', 'Eric', 'eric.wright@foo.com', 'HR', 33000.00)"	);
			System.out.println("UPDATE the employee");
			int rowsAffected = myStmt.executeUpdate("update employees set email = 'john.doe@luv2code.com' where "
					+ "last_name='Doe' and first_name='John'");
			displayEmployee(myConn, "John", "Doe");

			// 4. Verify this by getting a list of employees
			myRs = myStmt.executeQuery("SELECT * FROM employees order by last_name");

			// 5. Process the result set
			while (myRs.next()) {
				System.out.println(myRs.getString("last_name") + "," + myRs.getString("first_name"));
			}
		} catch (Exception exc) {
			exc.printStackTrace();
		} finally {
			close(myConn, myStmt, myRs);
		}
	}

	private static void close(Connection myConn, Statement myStmt, ResultSet myRs) {
		try {
			if (myRs != null)
				myRs.close();
			if (myStmt != null)
				myStmt.close();
			if (myConn != null)
				myConn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static void displayEmployee(Connection myConn, String firstName, String lastName) {
		try {
			// Java Statement
//			Statement myStmt = myConn.createStatement();
//			ResultSet myRs = myStmt.executeQuery(
//					"SELECT * FROM employees WHERE last_name= '" + lastName + "' AND first_name= '" + firstName + "'");
			/*Prepared statement
			 * Benefit:
			 * - Help improve performance
			 * - Prevent SQL dependency injection attacks
			 * - Easier to input SQL parameters
			 */
			PreparedStatement myStmt = myConn.prepareStatement("SELECT * FROM employees WHERE last_name = ? AND first_name = ?");
			myStmt.setString(1, lastName);
			myStmt.setString(2, firstName);
			ResultSet myRs = myStmt.executeQuery();
			
			while (myRs.next())
				System.out.println(lastName + " " + firstName + " : " + myRs.getString("email"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}

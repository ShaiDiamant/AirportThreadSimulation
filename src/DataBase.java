import java.sql.*;
import java.util.Arrays;

public class DataBase {

	private Statement stmt;

	public DataBase(){//From the powerpoint presentation for the university computers
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/test";
			Connection conn = DriverManager.getConnection(url, "root", "root");
			stmt = conn.createStatement();
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println(Arrays.toString(e.getStackTrace()));
		}
		createTakeoffTable();
		createLandingsTable();
	}
	private void createLandingsTable() {//creates the according landings table
		try{
			stmt.executeUpdate("DROP TABLE IF EXISTS " + "Landings");
			String createFirstTable = "CREATE TABLE " + "Landings" +"(ID varchar(10), Passengers int, Cargo int, Cost int, isSecurityIssue boolean, timeInAirfield int)";
			stmt.executeUpdate(createFirstTable);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	} //create table
	private void createTakeoffTable() {//Creates the according takeoff table
		try{
			stmt.executeUpdate("DROP TABLE IF EXISTS " + "Takeoffs");
			String createFirstTable = "CREATE TABLE " + "Takeoffs" +"(ID varchar(10), Passengers int, Destination varchar(30), timeInAirfield int)";
			stmt.executeUpdate(createFirstTable);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	} //create table
	public void insertIntoTable(String tableName, FlightDetails f) throws SQLException{//Inserts into table - checks the instance according to tablename
		if(tableName.equals("Landings")){
			ArrivalFlightDetails curr = (ArrivalFlightDetails)f;
			int binarySecurityIssue;
			if(curr.getSecurityIssue()){
				binarySecurityIssue = 1;
			}
			else{
				binarySecurityIssue = 0;
			}
			String insertDetails = "INSERT INTO " + tableName + "(ID, Passengers, Cargo, Cost, isSecurityIssue, timeInAirfield) VALUES"
					+"('"+curr.flightCode+"','"+curr.numOfPassengers+"','"+curr.getCargo()+"','"+curr.getCost()+"','"+binarySecurityIssue+
					"','"+curr.getTimeInAirfield()+"')";
			stmt.executeUpdate(insertDetails);
		}
		else{
			DepartureFlightDetails curr = (DepartureFlightDetails)f;
			String insertDetails = "INSERT INTO " + tableName + "(ID, Passengers, Destination, timeInAirfield) VALUES"
					+"('"+curr.flightCode+"','"+curr.numOfPassengers+"','"+curr.getDestination()+"','"+curr.getTimeInAirfield()+"')";
			stmt.executeUpdate(insertDetails);
		}
	}
}

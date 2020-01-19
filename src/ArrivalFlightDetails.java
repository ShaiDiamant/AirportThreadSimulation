import java.util.Vector;

public class ArrivalFlightDetails extends FlightDetails {
	private int cargo; //number of bags on flight
	private int cost;//total cost of flight to our teams
	private boolean securityIssue;//did this flight have a security issue

	public ArrivalFlightDetails(String flightCode, int numOfPassengers, int timeInAirfield,
								int cargo, int cost, boolean securityIssue) {//Basic builder method
		super(flightCode, numOfPassengers, timeInAirfield);
		this.cargo = cargo;
		this.cost = cost;
		this.securityIssue = securityIssue;
	}

	public int getCost() {//total cost for flight getter
		return this.cost;
	}

	public boolean getSecurityIssue() {//security issue getter
		return this.securityIssue;
	}

	public int getCargo() {//num of bags getter
		return this.cargo;
	}
}

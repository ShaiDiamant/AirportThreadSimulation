
public class ArrivalFlightDetails extends FlightDetails {
	private int cargo;
	private int cost;
	private boolean securityIssue;

	public ArrivalFlightDetails(String flightCode, int numOfPassengers, int timeInAirfield,
			int cargo, int cost, boolean securityIssue) {
		super(flightCode, numOfPassengers, timeInAirfield);
		this.cargo = cargo;
		this.cost = cost;
		this.securityIssue = securityIssue;
	}
	public int getCost() {
		return this.cost; 
	}
	
	public boolean getSecurityIssue() {
		return this.securityIssue;
	}
	
	public int getCargo() {
		return this.cargo;
	}

}

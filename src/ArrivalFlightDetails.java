
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

}

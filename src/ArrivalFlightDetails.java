import java.util.Vector;

public class ArrivalFlightDetails extends FlightDetails {
	private int cargo;
	private int cost;
	private int amountOfFuel;
	private boolean securityIssue;

	public ArrivalFlightDetails(String flightCode, int numOfPassengers, int timeInAirfield,
								int cargo, int cost, boolean securityIssue) {
		super(flightCode, numOfPassengers, timeInAirfield);
		this.cargo = cargo;
		this.cost = cost;
		this.securityIssue = securityIssue;
		this.amountOfFuel = 0;
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

	public int getamountOfFuel() {//TODO: Find solution
		return 0;
	}
}

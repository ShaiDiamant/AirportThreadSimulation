import java.util.*;
public class FuelCrew extends Crew {

	private int maxCapacity;

	public FuelCrew(String name, int maxCapacity, QueueManager qm) {
		super(name, qm);
		this.maxCapacity=maxCapacity;
		Thread t= new Thread(this);
		t.start();
	}

	@Override
	public void doWork() {
		Arrival curr;
		curr = qm.fuelingQ.extract();
		fuelTest(curr);
		forwardPlane(curr);

	}

	public void fuelTest(Arrival curr) {
		if(maxCapacity>=1000) {
			Random rand = new Random();
			int fuelTime = rand.nextInt(2)+3;
			try {
				Thread.sleep(fuelTime*1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			maxCapacity=maxCapacity-1000;
		}
		else {
			qm.fuelingQ.insert(curr);
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	public void forwardPlane(Arrival curr) {
		curr.setLatestTreater(this);
		if(Math.random()<=0.35) {
			qm.technicalQ.insert(curr);
		}
		else {
			FlightDetails f = curr.getFlightDetails();
			qm.managementQ.insert(f);
		}
	}
}

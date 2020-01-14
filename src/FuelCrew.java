import java.util.*;
public class FuelCrew extends Crew {

	private int maxCapacity;
	private int numOfFuels;
	private int currCapacity;

	public FuelCrew(String name, int maxCapacity, QueueManager qm) {
		super(name, qm);
		this.maxCapacity=maxCapacity;
		this.currCapacity = this.maxCapacity;
		this.numOfFuels=0;
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
		if(currCapacity>=1000) {
			Random rand = new Random();
			int fuelTime = rand.nextInt(2)+3;
			try {
				Thread.sleep(fuelTime*1000);
				curr.increaseTime(fuelTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			this.currCapacity=this.currCapacity-1000;
			this.numOfFuels++;
		}
		else {
			qm.fuelingQ.insert(curr);
			try {
				Thread.sleep(5000);
				this.currCapacity = this.maxCapacity;
				
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

	public int getNumOfFuels(){
		return this.numOfFuels;
	}
}

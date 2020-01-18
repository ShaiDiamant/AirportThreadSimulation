
public class LogisticsCrew extends Crew {

	private int cargoCapacity;//Max amount of bags that this crew can carry
	private int numOfCargoTrucks;

	public LogisticsCrew(String name, int cargoCapacity, Airport ap) {//Basic builder method
		super(name,ap);
		this.cargoCapacity=cargoCapacity;
		this.numOfCargoTrucks=0;
		Thread t = new Thread(this);
		t.start();
	}

	@Override
	public void doWork() {//Uncharges plane and forwards to next queue. if receives null from queue, day ended
		Arrival curr;
		curr = qm.logisticsQ.extract();
		if(curr == null) {
			this.dayEnd=true;
			return;
		}
		capacityCheck(curr);
		forwardPlane(curr);
	}

	private void capacityCheck(Arrival curr) {//Checks to see if it has enough capacity, if not, will call for backup
		if(cargoCapacity >= curr.getNumOfBags()){
			int timeWait= curr.getNumOfBags();
			try {
				Thread.sleep(timeWait*100);
				curr.increaseTime(timeWait/10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		else {
			int timeWait= curr.getNumOfBags();
			try {
				Thread.sleep(2000);
				curr.increaseTime(2);
				this.numOfCargoTrucks++;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			try {
				Thread.sleep(timeWait*100);
				curr.increaseTime(timeWait/10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
	}

	private void forwardPlane(Arrival curr) {//10% to have technical issue and forward to tech q, else to security q
		curr.setLatestTreater(this);
		if(Math.random()<=0.1) {
			qm.technicalQ.insert(curr);
		}
		else {
			qm.securityQ.insert(curr);
		}
	}

	public int getNumOfCargoTrucks(){//Num of cargo trucks called getter
		return this.numOfCargoTrucks;
	}
}

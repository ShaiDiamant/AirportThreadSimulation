
public class SecurityMan implements Runnable{

	private boolean dayEnd; //has the day ended
	private String rank; //rank for this security man
	private int checkTime;//security check time from GUI
	private QueueManager qm; //QueueManager instance for access to queues
	private Airport ap;

	public SecurityMan (String rank, int checkTime,	Airport ap) {//Basic builder method
		this.dayEnd = false;
		this.rank=rank;
		this.checkTime=checkTime;
		this.ap = ap;
		this.qm = ap.getQM();
		Thread t = new Thread(this);
		t.start();
	}

	public void run() {//Will keep doing work untill dayEnd has changed to true
		while (!dayEnd){
			doWork();
		}
	}

	public void doWork() {//Will check for suspicious object and forward the plane to the fueling queue
		Arrival curr;
		curr = qm.securityQ.extract();
		if(curr == null){
			this.dayEnd = true;
			return;
		}
		suspiciousObjectCheck(curr);
		forwardPlane(curr);
	}

	public void suspiciousObjectCheck(Arrival curr) {//5% chance to find a security issue, takes a while to make the test
		try {
			Thread.sleep(checkTime*1000);
			curr.increaseTime(checkTime);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if(Math.random()<=0.05) {
			try {
				Thread.sleep(2000);
				curr.increaseTime(2);
				curr.securityIssue();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void forwardPlane(Arrival curr) {//Forwards plane to the fueling queue and updates latest treater of flight
		curr.setLatestTreater(this);
		qm.fuelingQ.insert(curr);
	}

}

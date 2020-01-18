
abstract public class Crew implements Runnable {
	
	protected String nameCrew; //Unique name for the crew
	protected QueueManager qm;//QueueManager instance for access to all queues
	protected Airport ap;
	protected boolean dayEnd;//Has the day ended

	public Crew (String name, Airport ap) {//Basic builder method
		this.nameCrew=name;
		this.dayEnd=false;
		this.ap = ap;
		this.qm = ap.getQM();
	}

	public void run(){//Will run until dayEnd will become true
		while(!dayEnd){
			doWork();
		}
	}

	abstract public void doWork();//Each crew and it's own doWork method
}

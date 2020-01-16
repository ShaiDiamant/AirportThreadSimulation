
abstract public class Crew implements Runnable {
	
	protected String nameCrew;
	protected QueueManager qm;
	protected boolean dayEnd;

	public Crew (String name, QueueManager qm) {
		this.nameCrew=name;
		this.dayEnd=false;
		this.qm = qm;
	}

	public void run(){
		while(!dayEnd){
			doWork();
		}
	}

	abstract public void doWork();
}

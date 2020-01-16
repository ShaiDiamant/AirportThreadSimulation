import java.util.*;

public class Queue<T> {
	protected Vector<T> queue;//Vector for managing Queue
	public Queue() {//Basic builder method
		queue = new Vector<T>();
	}
	public int size() {//Gets queue size
		return queue.size();
	}
	public synchronized boolean insert(T t) {//Inserts T to queue and notifies all waiting threads
		boolean status = this.queue.add(t);
		notifyAll();
		return status;
	}
	public synchronized T extract() {//Extracts T from queue, waits if queue is empty
		while(queue.isEmpty()) {
			try {
				wait();
			}
			catch(InterruptedException e) {
				System.out.println(e.getMessage());
			}
		}
		T t = queue.get(0);
		queue.remove(t);
		return t;
	}
}


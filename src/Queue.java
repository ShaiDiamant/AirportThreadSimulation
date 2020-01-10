import java.util.*;

public class Queue<T> {
	Vector<T> queue;
	public Queue() {
		queue = new Vector<T>();
	}
	public int size() {
		return queue.size();
	}
	public synchronized boolean insert(T t) {
		boolean status = this.queue.add(t);
		notifyAll();
		return status;
	}
	public synchronized T extract() {
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


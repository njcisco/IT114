import java.util.Queue;
import java.util.LinkedList;

public class Q {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Queue<Students> QQ = new LinkedList<Students>();
		for(int i = 0; i < 10; i++) {
			QQ.add(new Students(i, "name"));
		}
		//print out the queue
		System.out.println("Show the queue: " + QQ);
		
		System.out.println(":*************************");

		//remove the head of the queue
		Students first = QQ.remove();

		//print out the head of the queue
		System.out.println("pull head of the queue: " + first);
		
		System.out.println("*************************");

		//print out the queue after removing the head
		System.out.println("Show altered queue after the head removed: " + QQ);
		 //print out the size of the queue
		
		System.out.println("*************************");

		int siz = QQ.size();
	    System.out.println("size of queue : " + siz); 
	    
		System.out.println("*************************");

		//view the head of the queue and show the queue 
		Students peek = QQ.peek();
		System.out.println("view the head of queue: " + peek);
		
		System.out.println("*************************");
		
		System.out.println("Show unaltered queue: " + QQ);

	}

}
class Students {
	public int ucid;
	public String Name;
	public Students(int ucid, String Name) {
		this.ucid= ucid;
		this.Name=Name;
	}
	@Override
	public String toString() {
		return "{'ucid':'" + this.ucid + "', 'Name':'" + this.Name + "'}";
	}
}
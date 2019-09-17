package animals;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class ListAnim {
	public static void main(String[] args) {
		// create arrayList
		List<String> myAnimals = new ArrayList<String>();
		//adding element to the ArrayList
		myAnimals.add("Eel");
		myAnimals.add("Cat");
		myAnimals.add("Dog");
		myAnimals.add("Fish");
		myAnimals.add("Lizard");
		myAnimals.add("Ant");
		//printing the list as it was
		int ize = myAnimals.size();
		for(int i=0; i < ize; i++ ) {
			System.out.println(myAnimals.get(i));
			}
		System.out.println("*************************");
;
		//printing reversed arrayList
		Collections.reverse(myAnimals);
		for(int i=0; i < ize; i++ ) {
			System.out.println(myAnimals.get(i));
			}
		System.out.println("*************************");
		//shuffling ArrayList
		Collections.shuffle(myAnimals);
		for(int i=0; i < ize; i++ ) {
			System.out.println(myAnimals.get(i));
			}
		System.out.println("*************************");

		//create list of numbers
		List<Integer> myNumlist = new ArrayList<Integer>();
		for (int j=0; j < 10; j++) {
			myNumlist.add(j);
		}
		//printing the list
		for(int j=0; j < 10; j++ ) {
			System.out.println(myNumlist.get(j));
			}
		//summing the element of the list
		int total = 0;
		int Size= myNumlist.size();
		for(int i=0; i< Size; i++) {
			total += myNumlist.get(i);
		}
		//printing the total
		System.out.println(total);
		for(int i=0; i<Size; i++) {
			int t= myNumlist.get(i);
			if(i % 2 == 0) {
				System.out.println("index "+ "number");
				System.out.println(+ i +"        "  + t + "    is even");}
			
			else {System.out.println("index "+ "number");
				System.out.println(+ i +"        "  + t + "    is odd");	
			}
			
		}
		//create arrayList myAnimal
		System.out.println("*************************");

		String[]myAnimal = {"Eal", "Cat", "Dog", "Fish", "Lizard", "Ant"}; 
		
		for (int i=0 ; i < myAnimal.length; i++) {
			//generate a random number index and prent the index, value of index and value of i
			int index = (int)(Math.random() * myAnimal.length);
			System.out.println(index + "\t" + myAnimal[index] + "\t"+ myAnimal[i]);
			//create an empty String temp and use it to switch
			String temp = myAnimal[i];
			myAnimal[i]= myAnimal[index];
			myAnimal[index]= temp;
			
		for (String n: myAnimal) {
			System.out.print(n+ " ");
			}
			System.out.println();
		}
		
		}
				
		
		
	}



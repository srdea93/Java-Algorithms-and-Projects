package dea_s_hw3;
import java.util.*;

/*
 * This Test class demonstrates the use of Inheritance/Abstraction through the use
 * of various super-classes and sub-classes. It also demonstrates the use of error handling
 * via input verification as well as the use of collections to contain
 * various user-defined objects.
 */

public class Test {

	public static void main(String[] args) {
		
		// Destroyer ArrayList
		ArrayList<Destroyer> destroyers = new ArrayList<Destroyer>();
		Destroyer d1 = new Destroyer(5, "10", "HMS Steven", "Destroyer", 4);
		Destroyer d2 = new Destroyer();
		
		// Use setters to set member variables of d2
		d2.setLength(8);
		d2.setName("HMS Vinnie");
		d2.setSpeed("50");
		d2.setType("Destroyer");
		
		// Should not parse correctly and set the number to 2
		d2.setNumberMissiles("Foo");
		
		// Add both destroyers to the new ArrayList
		destroyers.add(d1);
		destroyers.add(d2);
		
		// Iterate through ArrayList of Destroyers and print out member variables FOR TESTING
//		for (Destroyer des : destroyers) {
//			System.out.println(des);
//			System.out.println("\n");
//		}
		
		// Submarine LinkedList
		LinkedList<Submarine> submarines = new LinkedList<Submarine>();
		Submarine s1 = new Submarine(1, "7", "Undertow", "Submarine", 25);
		Submarine s2 = new Submarine();
		
		// Set member variables of s2
		s2.setLength(39);
		s2.setName("Sneaky");
		s2.setNumberTorpedoes("Foo");
		s2.setSpeed("37");
		s2.setType("Submarine");
		
		// Add both subs to LinkedList
		submarines.add(s1);
		submarines.add(s2);

		// Print all submarines in the submarines LinkedList FOR TESTING
//		Iterator i = submarines.iterator();
//		while(i.hasNext()) {
//			Submarine sub = (Submarine)i.next();
//			System.out.println(sub);
//			System.out.println("\n");
//		}
		
		// Create two P3s
		P3 p1 = new P3(23, "5000", "Plane1", "P3", 700, 4);
		P3 p2 = new P3(65, "1000", "Plane2", "P3", 15000, 10);
		
		// Ship Stack
		Stack<Ship> ships = new Stack<Ship>();
		
		// Add all ships to Ship Stack
		ships.add(d1);
		ships.add(d2);
		ships.add(s1);
		ships.add(s2);
		
		// Print all ships in Stack FOR TESTING
//		Iterator i = ships.iterator();
//		while(i.hasNext()) {
//			Ship ship = (Ship)i.next();
//			System.out.println(ship);
//			System.out.println("\n");
//		}
		
		// Contact HashSet
		HashSet<Contact> contacts = new HashSet<Contact>();
		
		// Add all contacts to HashSet
		contacts.add(d1);
		contacts.add(d2);
		contacts.add(s1);
		contacts.add(s2);
		contacts.add(p1);
		contacts.add(p2);
		
		// Print all Contacts in contacts HashSet
		Iterator i = contacts.iterator();
		while(i.hasNext()) {
			Contact contact = (Contact)i.next();
			System.out.println(contact);
			System.out.println("\n");
		}
	}
}

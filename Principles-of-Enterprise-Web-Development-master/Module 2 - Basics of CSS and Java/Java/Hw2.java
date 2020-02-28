package com.srdea.hw2;
import java.util.Scanner;

/* 
 * This class is designed to have a method that takes in two
 * integers and returns the product of those two integers.
 * The main function of this class prompts a user to input
 * two integers to be multiplied and returned. If the product
 * is negative, it is returned within (), if it is positive,
 * it is returned normally.
 */
public class Hw2 {
	
	// Method to multiple two integers and return the product
	public static int intProduct(int int1, int int2) {
		int product = (int1 * int2);
		return product;
	}
		
	public static void main(String[] args) {
		// Prompt user to input two integers to be multiplied
		System.out.println("Please enter two integers to multiply: ");
		
		// User input is read using Scanner
		Scanner scan = new Scanner(System.in);
		int int1 = scan.nextInt();
		int int2 = scan.nextInt();
		int product = intProduct(int1, int2);
		
		scan.close();
		
		// If output is negative, take absolute value and place it in parenthesis
		if (product <= 0) {
			System.out.println("Product = (" + Math.abs(product) + ")");
		}
		else {
			System.out.println("Product = " + product);
		}
	}
}

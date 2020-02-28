package com.srdea;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;


/*
 * This program implements both a naive matrix multiplication using 3 for loops with a O(n^3) time complexity
 * as well as a matrix multiplication function using Strassen's Algorithm. The program reads an input file
 * of various matrices and multiplies them together using both implementations of matrix multiplication.
 * The implementation of the naive matrix multiplication is a simple triple nested for loop while the implementation
 * of Strassen's Algorithm is a recursive divide and conquer method. The method partitions the input matrices into sub-matrices
 * that are further partitioned until they reach a 2x2 matrix. Once they reach a 2x2 matrix, Strassen's Algorithm is 
 * used to solve for the resulting matrix multiplication and then further combined with the rest of the resulting matrices.
 * Both implementations have a count feature to demonstrate how Strassen's Algorithm is more efficient than the 
 * naive matrix multiplication method.
 * @author Steven Dea
 */


public class StrassenLab {
	
	// Global counter variable for recursive calls
	public static int counter = 0;
	
	public static int multiplications = 0;
	
	// Method to check the matrices that have been read in and write them to output file and console
	public static void showMatrix(int[][]matrix, String file, boolean io, int nOrS ) {
		
		File outfile = new File(file);
		FileWriter fr = null;
		String matrixOutput = "";
		
		// If matrix is an input matrix, print this out
		if (io == true) {
			matrixOutput = matrixOutput + "Input Matrix: \n";
		}
		else {
			// If normal matrix multiplication
			if (nOrS == 1) {
			matrixOutput = matrixOutput + "Output Matrix (Naive): \n";
			matrixOutput = matrixOutput + "Total Multiplications: " + multiplications + "\n";
			}
			// If Strassen matrix multiplication
			else {
				matrixOutput = matrixOutput + "Output Matrix (Strassen): \n";
				matrixOutput = matrixOutput + "Total Multiplications: " + counter + "\n";
			}
		}
		
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				System.out.print(matrix[i][j] + " ");
				matrixOutput = matrixOutput + matrix[i][j] + " "; 
			}
			matrixOutput = matrixOutput + "\n";
			System.out.println();
		}
		// One last line separator for file input
		matrixOutput = matrixOutput + "\n";
		
		// Use FileWriter to write matrixOutput to file
		try {
			fr = new FileWriter(outfile, true);
			fr.write(matrixOutput);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			try {
				fr.close();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	// Method for adding matrices to be used in the recursive Strassen's Algorithm
	public static int[][] add(int[][]matrix1, int[][]matrix2){
		int size = matrix1.length;
		int[][]newMatrix = new int[size][size];
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				newMatrix[i][j] = matrix1[i][j] + matrix2[i][j];
			}
		}
		
		return newMatrix;
	}
	
	// Method for subtracting matrices to be used in the recursive Strassen's Algorithm
	public static int[][] subtract(int[][]matrix1, int[][]matrix2){
		int size = matrix1.length;
		int[][]newMatrix = new int[size][size];
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				newMatrix[i][j] = matrix1[i][j] - matrix2[i][j];
			}
		}
		
		return newMatrix;
	}
	
	// Method for combining matrices to be used in the recursive Strassen's Algorithm
	public static int[][] combine(int[][] c11, int[][] c12, int[][] c21, int[][] c22, int halfSize){
		int[][] outputMatrix = new int[halfSize*2][halfSize*2];
		for (int i = 0; i < halfSize; i++) {
			for (int j = 0; j < halfSize; j++) {
				outputMatrix[i][j] = c11[i][j];
				outputMatrix[i][j+halfSize] = c12[i][j];
				outputMatrix[i+halfSize][j] = c21[i][j];
				outputMatrix[i+halfSize][j+halfSize] = c22[i][j];
			}
		}
		return outputMatrix;
	}
	
	// Implementation of Strassen's algorithm for matrix multiplication for a 2x2 matrix
	public static int[][] strassen(int[][]matrix1, int[][]matrix2){
		
		int size = matrix1.length;
		
		int p1, p2, p3, p4, p5, p6, p7 = 0;
		p1 = matrix1[0][0]*(matrix2[0][1]-matrix2[1][1]);
		p2 = (matrix1[0][0] + matrix1[0][1])*matrix2[1][1];
		p3 = (matrix1[1][0] + matrix1[1][1])*matrix2[0][0];
		p4 = matrix1[1][1]*(matrix2[1][0] - matrix2[0][0]);
		p5 = (matrix1[0][0] + matrix1[1][1])*(matrix2[0][0] + matrix2[1][1]);
		p6 = (matrix1[0][1] - matrix1[1][1])*(matrix2[1][0]+matrix2[1][1]);
		p7 = (matrix1[0][0] - matrix1[1][0])*(matrix2[0][0] + matrix2[0][1]);
		
		int outputMatrix[][] = new int[size][size];
		outputMatrix[0][0] = p5 + p4 - p2 +p6;
		outputMatrix[0][1] = p1 + p2;
		outputMatrix[1][0] = p3 + p4;
		outputMatrix[1][1] = p1 + p5 - p3 - p7;
		
		// Increment global variable counter by 7 each time this exit case is called in recursive function
		counter += 7;
		
		return outputMatrix;
	}
	
	// Implementation of a recursive function that splits input matrices until they reach 2x2, multiplies, and combines
	public static int[][] strassenAlgorithm(int[][]matrix1, int[][]matrix2){
		int size = matrix1.length;
		int[][] outputMatrix = new int[size][size];
		
		if (size > 2) {
			int halfSize = size/2;
			
			// Initialize 8 new sub-matrices of half dimensions of the original matrices
			int[][]A11 = new int[halfSize][halfSize];
			int[][]A12 = new int[halfSize][halfSize];
			int[][]A21 = new int[halfSize][halfSize];
			int[][]A22 = new int[halfSize][halfSize];
			
			int[][]B11 = new int[halfSize][halfSize];
			int[][]B12 = new int[halfSize][halfSize];
			int[][]B21 = new int[halfSize][halfSize];
			int[][]B22 = new int[halfSize][halfSize];
			
			// Partition matrix1 and matrix2 into sub-matrices using double for loop
			for (int i = 0; i < halfSize; i++) {
				for (int j = 0; j < halfSize; j++) {
					A11[i][j] = matrix1[i][j];
					A12[i][j] = matrix1[i][j + halfSize];
					A21[i][j] = matrix1[i + halfSize][j];
					A22[i][j] = matrix1[i + halfSize][j + halfSize];
					
					B11[i][j] = matrix2[i][j];
					B12[i][j] = matrix2[i][j + halfSize];
					B21[i][j] = matrix2[i + halfSize][j];
					B22[i][j] = matrix2[i + halfSize][j + halfSize];
				}
			}
			// Use these two matrices to compute addition/subtraction of sub-matrices and then recursively call on them
			int a[][] = new int[halfSize][halfSize];
			int b[][] = new int[halfSize][halfSize];
			
			// Calculate 7 new sub-matrices for p1-p7
			// p1 = (a11 + a22) * (b11 + b22)
			a = add(A11, A22);
			b = add(B11, B22);
			int[][] p1 = strassenAlgorithm(a, b);
			
			// p2 = (a21 + a22) * b11
			a = add(A21, A22);
			int[][] p2 = strassenAlgorithm(a, B11);
			// p3 = a11 * (b12 - b22)
			b = subtract(B12, B22);
			int[][] p3 = strassenAlgorithm(A11, b);
			
			// p4 = a22 * (b21 - b11)
			b = subtract(B21, B11);
			int[][] p4 = strassenAlgorithm(A22, b);
			
			// p5 = (a11 + a12) * b22
			a = add(A11, A12);
			int[][] p5 = strassenAlgorithm(a, B22);
			
			// p6 = (a21 - a11) * (b11 + b12)
			a = subtract(A21, A11);
			b = add(B11, B12);
			int[][] p6 = strassenAlgorithm(a, b);
			
			// p7 = (a12 - a22) * (b21 + b22)
			a = subtract(A12, A22);
			b = add(B21, B22);
			int[][] p7 = strassenAlgorithm(a, b);
			
			// Solve for all C values using add and subtract matrices functions
			// c11 = p1 + p4 - p5 + p7
			int[][] c11 = add(subtract(add(p1, p4), p5), p7);
			
			// c12 = p3 + p5
			int[][] c12 = add(p3, p5);
			
			// c21 = p2 + p4
			int[][] c21 = add(p2, p4);
			
			// c22 = p1 - p2 + p3 + p6
			int[][] c22 = add(add(subtract(p1, p2), p3), p6);
			
			// Combine all c-quadrants into a joined matrix
			outputMatrix = combine(c11, c12, c21, c22, halfSize);
					
		}
		// Base case if matrix has reached 2x2
		else
		{
			outputMatrix = strassen(matrix1, matrix2);
		}
		return outputMatrix;
	}
	
	public static int[][] normalMatrixMult(int[][]matrix1, int[][]matrix2, int size) {
		//Implement normal matrix multiplication of O(n^3)
		int outputMatrix[][] = new int[size][size];
		
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				for (int k = 0; k < size; k++) {
					// outputMatrix has a += here because it is the summation of the row of matrix1 + column of matrix 2
					outputMatrix[i][j] += (matrix1[i][k] * matrix2[k][j]);
					multiplications += 1;
				}
			}
		}
		System.out.print("Naive Matrix Multiplication");
		System.out.println();
		System.out.print("Total Multiplications: " + multiplications);
		System.out.println();
		return outputMatrix;
	}
	
	// Main driver function to show implementation of both matrix multiplication methods as well as file parsing
	public static void main(String[] args) throws FileNotFoundException{
		File infile = new File(args[0]);
		
		// Check if output file is already present
		File outfile = new File(args[1]);
		if(outfile.exists()) {
			outfile.delete();
			try {
				outfile.createNewFile();
			}
			catch(IOException e) {
				e.printStackTrace();
			}
		}
		
		String ofile = args[1];
		
		Scanner scanner = new Scanner(infile);
		
		// Continue scanning the input file until bool returns False
		while (scanner.hasNextLine()) {
		
			int dimension = scanner.nextInt();
			
			// allocates a new matrix of size dimension by dimension
			int[][] matrix1 = new int[dimension][dimension];
			for (int i = 0; i < dimension; i++) {
				for (int j = 0; j < dimension; j++) {
					if (scanner.hasNextInt()) {
						matrix1[i][j] = scanner.nextInt();
					}
				}	
			}
			
			// Display matrix1 to verify file has been read correctly
			System.out.println("Matrix 1");
			showMatrix(matrix1, ofile, true, 1);
			System.out.println();
			
			int[][] matrix2 = new int[dimension][dimension];

			if(scanner.hasNextLine()) {
				// Purge the empty buffer
				scanner.nextLine();
				
				// Grab the next line 
				String newLine = scanner.nextLine();
//				System.out.println("NewLine: " + newLine);
				
				String[] tokens = newLine.split(" ");
				
				// If the new line is empty, copy matrix1 into matrix2
				if (tokens.length == 1) {
					matrix2 = matrix1;
				}
				else {
					int tlen = tokens.length;
					// Fill in first row of matrix2
					for (int j = 0; j < tlen; j++) {
						matrix2[0][j] = Integer.parseInt(tokens[j]);
					}
					// Fill in the rest of matrix22 and start i at 1 since we have already filled in the first row
					for (int i = 1; i < dimension; i++) {
						for (int j = 0; j < dimension; j++) {
							// Check if scanner reads an empty line next, if empty, copy matrix 1 into matrix2
							matrix2[i][j] = scanner.nextInt();
							}
					}
				}
			}
			else {
				matrix2 = matrix1;
			}
			
			// Check that matrices are of equal dimensions before proceeding
			if (matrix1.length != matrix2.length) {
				System.out.println("Error in input matrices.");
				break;
			}
			
			// Display matrix2 to verify file has been read correctly	
			System.out.println("Matrix 2");
			showMatrix(matrix2, ofile, true, 1);
			System.out.println();
			
			// Multiply matrices using naive method
			int[][] normMatrix = normalMatrixMult(matrix1, matrix2, matrix1.length);
			showMatrix(normMatrix, ofile, false, 1);
			
			System.out.println("\n");
			
			// Multiply matrices using divide and conquer Strassen Algorithm
			int[][]strassenMatrix = strassenAlgorithm(matrix1, matrix2);
			
			System.out.println("Strassen Matrix Multiplication");
			System.out.println("Total Multiplications: " + counter);
			showMatrix(strassenMatrix, ofile, false, 2);
			
			// Reset counter after termination of multiplications of matrices
			counter = 0;
			
			multiplications = 0;
			
			System.out.println("\n");
			
		}		
		scanner.close();
	}
}
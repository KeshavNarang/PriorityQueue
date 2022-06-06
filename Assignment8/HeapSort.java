/**
	@author: Keshav Narang
	@version: November 1st, 2021
	@purpose: 
	Implement a version of heapsort based on complete heap-ordered 3-ary.
	Test your implementation using 100 randomly ordered distinct keys.
*/
import java.lang.*;
import java.util.*;
public class HeapSort
{
	public static void main (String [] args)
	{
		// Number of Keys
		int numberTerms = 100;
		
		// Has every number from 0 - 99 exactly once and in proper order.
		ArrayList <Integer> generatedOrderedNumbers = new ArrayList <Integer> ();
		for (int i = 1; i < numberTerms; i++)
		{
			generatedOrderedNumbers.add(i+1);
		}
		
		// Has every number from 0 - 99 exactly once and in a random order.
		int [] randomNumbers = new int [numberTerms+1];
		for (int i = 1; i < numberTerms; i++)
		{
			// Randomly take one of the remaining numbers from the ArrayList.
			Random generator = new Random ();
			int index = generator.nextInt(generatedOrderedNumbers.size());
			int number = generatedOrderedNumbers.get(index);
			
			// Remove that number from the ArrayList. 
			generatedOrderedNumbers.remove(index);
			
			// Assign that number to the current index of the array.  Repeat.
			randomNumbers[i] = number;
		}
		
		// Instead of print all ints on one line, method prints neatly on multiple.
		System.out.println("Initial 100 Random Numbers: ");
		printArray(randomNumbers);
		
		// Sort the random numbers into a max-ordered heap array.
		int [] heapOrderedNumbers = constructHeap(randomNumbers, numberTerms);
		
		// Sort the heap ordered numbers into the proper pattern.
		int [] sortedNumbers = sortHeap(heapOrderedNumbers);
		
		// Instead of print all ints on one line, method prints neatly on multiple.
		System.out.println("Final 100 Sorted Numbers: ");
		printArray(sortedNumbers);
	}
	
	public static int [] sink (int [] array, int parent, int finalElement)
	{
		while (parent*3 < finalElement)
		{
			// Find the biggest node between parent and its children
			// If the parent is the biggest node, a max-ordered heap is formed. Return.
			// If a child is bigger than a parent, swap them. Repeat.
			int biggestNode = parent;
			
			if (3*parent-1 < finalElement) // Does the parent have a child at 3k+1?
			{
				// If so, and if it is larger than current biggestNode, set biggestNode to 3k+1.
				if (array[3*parent-1] > array[biggestNode])
				{
					biggestNode = 3*parent-1;
				}
			}
			
			if ((3*parent) < finalElement) // Does the parent have a child at 3k+2?
			{
				// If so, and if it is larger than current biggestNode, set biggestNode to 3k+2.
				if (array[3*parent] > array[biggestNode])
				{
					biggestNode = 3*parent;
				}
			}
			if ((3*parent+1) < finalElement) // Does the parent have a child at 3k+3?
			{
				// If so, and if it is larger than current biggestNode, set biggestNode to 3k+3.
				if (array[3*parent+1] > array[biggestNode])
				{
					biggestNode = 3*parent+1;
				}
			}
			
			// If the parent is the biggest node, we have a max ordered heap.
			if (parent == biggestNode)
			{
				break;
			}
			
			// Otherwise, swap the parent with its biggest child. Repat
			int parentValue = array[parent];
			array[parent] = array[biggestNode];
			array[biggestNode] = parentValue;
			
			parent = biggestNode;
		}
		return array;
	}
	
	public static int [] constructHeap (int [] randomNumbers, int numberTerms)
	{				
		// In the binary heap, we used either:
		// 1) A 0-indexed array with children 2k+1 and 2k+2
		// 2) A 1-indexed array with children 2k and 2k+1 
		
		// In my 3-ary heap, I will be using:
		// 1) A 0-indexed array with children 3k-1, 3k, 3k+1
		
		// Start from the right side and, using sink, keeping creating heaps.
		// All leaves are already heaps. The first non-leaf will be numberTerms/3 - 1.
		for (int parent = (numberTerms+1)/3; parent >= 1; parent--)
		{
			// Keep sinking the parent until it is a leaf
			randomNumbers = sink(randomNumbers, parent, numberTerms);
		}
		return randomNumbers;
	}
	
	public static int [] sortHeap (int [] heapOrderedNumbers)
	{
		int numberTermsNeedingSorting = heapOrderedNumbers.length - 1;
		
		while (numberTermsNeedingSorting > 0)
		{
			// The top element is the greatest number remaining.
			// Swap it with the last element of the heap.			
			int firstTerm = heapOrderedNumbers[0];
			heapOrderedNumbers[0] = heapOrderedNumbers[numberTermsNeedingSorting];
			heapOrderedNumbers[numberTermsNeedingSorting] = firstTerm;
			
			// Now the last element of the heap is improperly on top. 
			// Sink from the 0th index (inclusive) to numberTermsNeedingSorting (exclusive).
			heapOrderedNumbers = sink(heapOrderedNumbers, 0, numberTermsNeedingSorting);
			
			// The size of the heap keeps decreasing.
			// Behind the heap, elements are sorted in reverse (increasing) order.
			numberTermsNeedingSorting--;
		}
		
		return heapOrderedNumbers;
	}
	
	public static void printArray (int [] array)
	{
		int startIndex = 1; // Index of first term to be printed on each line
		int numberTermsLine = 20;  // How many terms will be printed on each line
		
		System.out.println();
		while (startIndex < array.length) // Start from startIndex (initially 0)
		{
			// Print the next numberTermsLine (20) terms
			for (int i = startIndex; i < startIndex + numberTermsLine; i++) 
			{
				System.out.printf("%-2d", array[i]);
				System.out.print(" ");
			}
			startIndex += numberTermsLine; // Increase startIndex appropriately
			System.out.println(); // Repeat on next line
		}
		System.out.println();
	}
}
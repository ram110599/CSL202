//package Sorting;
//It returns the type of object of Algorithm
import java.util.Scanner;

public class AlgoObject {
	public static SortingType ReturnObject() {
		Scanner scan = new Scanner(System.in);
		
		System.out.println("Here are sorting algorithms.");
		System.out.println("Type 1 for insertion sort.");
		System.out.println("Type 2 for merge sort.");
		System.out.println("Type 3 for quick sort.");
		System.out.println("Type 4 for heap sort.");
		System.out.println("Default is bubblesort. Type any integer.");
		//System.out.println("Enter the algorithm you want to implement.");
		//String S = scan.next();
		int type = scan.nextInt();
		//scan.close();
		if(type==1) {
			return (new InsertionSort());
		}
		else if(type==2) {
			return (new MergeSort());
		}
		else if(type==3){
			return (new QuickSort());
		}
		else if(type==4) {
			return (new HeapSort());
		}
		return (new BubbleSort());
	}
}

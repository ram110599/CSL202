//package Sorting;
//QuickSort Here
public class QuickSort implements SortingType {
	/* This function takes last element as pivot,
    places the pivot element at its correct
    position in sorted array, and places all
    smaller (smaller than pivot) to left of
    pivot and all greater elements to right
    of pivot */
 int partition(int arr[], int low, int high)
 {
     int pivot = arr[high]; 
     int i = (low-1); // index of smaller element
     for (int j=low; j<high; j++)
     {
         // If current element is smaller than or
         // equal to pivot
         if (arr[j] <= pivot)
         {
             i++;

             // swap arr[i] and arr[j]
             int temp = arr[i];
             arr[i] = arr[j];
             arr[j] = temp;
         }
     }

     // swap arr[i+1] and arr[high] (or pivot)
     int temp = arr[i+1];
     arr[i+1] = arr[high];
     arr[high] = temp;

     return i+1;
 }


 /* The main function that implements QuickSort()
   arr[] --> Array to be sorted,
   low  --> Starting index,
   high  --> Ending index */
 void sort1(int arr[], int low, int high)
 {
     if (low < high)
     {
         /* pi is partitioning index, arr[pi] is 
           now at right place */
         int pi = partition(arr, low, high);

         // Recursively sort elements before
         // partition and after partition
         sort1(arr, low, pi-1);
         sort1(arr, pi+1, high);
     }
 }
 public void sort(int arr[]) {
	 sort1(arr,0,arr.length-1);
 }

 /* A utility function to print array of size n */
 public void printArray(int arr[])
 {
	 System.out.println("Quick Sort");
     int n = arr.length;
     for (int i=0; i<n; ++i)
         System.out.print(arr[i]+" ");
     System.out.println();
 }

}
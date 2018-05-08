
public class Main {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//Scanner scan = new Scanner(System.in);
		
		//
		int[] intArray = new int[]{ 1,2,3,10,67,23 }; 
		
		SortingType Algo1 = AlgoObject.ReturnObject();
		Algo1.sort(intArray);
		Algo1.printArray(intArray);
		
	}
}

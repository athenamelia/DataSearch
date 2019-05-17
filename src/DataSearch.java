import java.io.*;
import java.util.Scanner;
import java.util.Arrays;
/**
* Database search that uses binary search
*
* @author Amelia Tran
* @version 3/20/2019
*/

public class DataSearch {
	// declare global variables
	private static int intValData, intValSeek;
	private static Integer valData, valSeek;
	private static Scanner parserData, parserSeek;
	private static String tokenData, tokenSeek, exprData, exprSeek;
	// 17, 63, 94, 3, 18
	private static int seek[] = new int[5]; 
	// 24, 8, 19, 50, 73, 64, 18, 32, 91, 44, 3
	private static int data[] = new int[11];
	// 3, 18 yes
	private static int found[] = new int[5];
	private static String result;
	private static PrintStream outs;
	
	/** main method to scan the files 
	* @param args -main method argument
	*/
	public static void main (String[] args) throws FileNotFoundException {		
		outs = new PrintStream(new FileOutputStream( args[2]));	
		File fileData = new File(args[1]);			
		Scanner dataIn = new Scanner(fileData);
		// read the data file 
		while (dataIn.hasNext()) {
			exprData = dataIn.nextLine();
			parserData = new Scanner(exprData);
		}
		
		// read the tokens in the data file
		int indexData=0;
		while (parserData.hasNext()) {
			tokenData = parserData.next();
			valData = Integer.parseInt(tokenData);
			intValData = valData.intValue(); 
			if (indexData<data.length) {
				data[indexData] = intValData;
				indexData++;
			}			
		}	

		File fileSeek = new File(args[0]);			
		Scanner dataSeek = new Scanner(fileSeek);
		// read the seek file 
		while (dataSeek.hasNext()) {
			exprSeek = dataSeek.nextLine();
			parserSeek = new Scanner(exprSeek);
		}
		
		// read the tokens in the seek file
		int index=0; 
		while (parserSeek.hasNext()) {
			tokenSeek = parserSeek.next();
			valSeek = Integer.parseInt(tokenSeek);
			intValSeek = valSeek.intValue(); 
			if (index<seek.length) {
				seek[index] = intValSeek;
				found[index] = intValSeek;
				index++;
			}
		}					

		// sort the data array before searching
		// 3, 8, 18, 19, 24, 32, 44, 50, 64, 73, 91
		sort(data);
	
		// search for the seek data 
		for (int indexSeek=0; indexSeek<seek.length; indexSeek++) {
			int target = seek[indexSeek];
			int test = binarySearch(data, target);
			// if seek[i] is found, then found[i] should be set to 1
			if (test != -1) {
				found[indexSeek] = 1;
			} else { // otherwise, set found[i] to 0
				found[indexSeek] = 0;
			}
		}

		for (int i=0; i<found.length; i++) {
			// if the data is found, print yes
			if (found[i]==1) {
				result = seek[i]+": "+"Yes";
			} else { //otherwise, print no
				result = seek[i]+": "+"No";
			}
			System.out.println(result);
			// writing output			
			outs.println(result);
		}
		outs.close();
	}

	/** sort method using loop (manually)
	* @param items an array
	*/
	public static void sort(int[] items) {
		int temp =0;
		for (int i =0; i<items.length-1;i++){
			for (int j=i+1; j<items.length;j++) {
				if (items[i] >items[j]) {
					temp = items[j];
					items[j] = items[i];
					items[i] = temp;
				}
			}
		}
	}

	/** helper method with recursive
	* @param items an array
	* @param target the integer we want to look for
	* @param first first position in array
	* @param last last position in array
	* @return integer
	*/
	public static int recursiveBinarySearch( int[] items, int target, int first, int last) {
		// base case for unsuccessful search, return -1
		if (first > last)
			return -1;
		else {
			// next probe index
			int middle = (first+last)/2; 
			if (target == items[middle]) 
				//base case for successful search
				return middle; 
			else if (target < items[middle])
				// recursion 
				return recursiveBinarySearch(items, target, first, middle-1);
			else
				// recursion
				return recursiveBinarySearch(items, target, middle+1, last);
		}
	}

	/** wrapper method 
	* @param items an array
	* @param target the integer we want to look for
	* @return integer
	*/
	public static int binarySearch( int[] items, int target) {
		return recursiveBinarySearch(items, target, 0, items.length-1);
	}
}



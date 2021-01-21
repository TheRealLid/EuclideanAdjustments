import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.math.BigInteger;

public class EuclideanAdjustment {
	public static void main(String[] args) throws FileNotFoundException {
		ArrayList<BigInteger> movementValues = readCharacters();
		BigInteger target = getTarget(movementValues);
		BigInteger targetTracking = new BigInteger("0");
		movementValues.sort(null);
		// Size of the list(M values)
		int size = movementValues.size();
		// Counter to keep track of how many times each operation was done
		int[] counter = new int[size];
		for (int i = 0; i < size; i++) {
			counter[i] = 0;
		}

		System.out.println("Target: " + target);
		System.out.println(movementValues);

		// loops through and subtracts the values from the target until the m values are
		// smaller than the target or until we run out of M values

		for (int i = 0; i < size; i++) {
			while (movementValues.get(size - i - 1).compareTo(target.subtract(targetTracking)) == -1) {
				targetTracking = targetTracking.add(movementValues.get(size - i - 1));
				counter[size - i - 1] += 1;
			}
		}
		targetTracking = targetTracking.add(movementValues.get(0));

		System.out.println(targetTracking);
		printArr(counter);
		
	}
	

	/**
	 * gets the Bigints from the file
	 * 
	 * @return an arrayList containing the bigIntegers
	 * @throws FileNotFoundException
	 */
	public static ArrayList<BigInteger> readCharacters() throws FileNotFoundException {
		File fileName = new File("input.txt");
		Scanner in = new Scanner(fileName);
		ArrayList<BigInteger> fileContent = new ArrayList<BigInteger>();
		while (in.hasNextLine()) {
			BigInteger val = new BigInteger(in.nextLine());
			fileContent.add(val);
		}
		in.close();
		return fileContent;
	}

	/**
	 * Extracts the target value from the list and removes it from the list
	 * 
	 * @param list to extract the target from
	 * @return the target value
	 */
	public static BigInteger getTarget(ArrayList<BigInteger> list) {
		BigInteger target = list.get(0);
		list.remove(0);
		return target;
	}

	public static void printArr(int[] arr) {
		for (int i = 0; i < arr.length; i++) {
			System.out.println(arr[i]);
		}
	}
	


	
}

//try to find a combination of M values that produce as small values as possible
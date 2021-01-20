import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.math.BigInteger;

public class EuclideanAdjustment {
	public static void main(String[] args) throws FileNotFoundException {
		ArrayList<BigInteger> movementValues = readCharacters();
		BigInteger target = getTarget(movementValues);
		movementValues.sort(null);
		// Size of the list(M values)
		int size = movementValues.size();
		
		System.out.println("Target: " + target);
		System.out.println(movementValues);
		
		//loops through and subtracts the values from the target until the m values are smaller than the target or until we run out of M values
		for(int i = 0; i < size; i++) {
			while(movementValues.get(size - i - 1).compareTo(target) == -1) {
				target = target.subtract(movementValues.get(size-i - 1));
			}
		}
		
		System.out.println(target);
		
	}
	
	/**
	 * gets the Bigints from the file
	 * @return an arrayList containing the bigIntegers
	 * @throws FileNotFoundException
	 */
	public static ArrayList<BigInteger> readCharacters() throws FileNotFoundException {
		File fileName = new File("inputBig.txt");
		Scanner in = new Scanner(fileName);
		ArrayList<BigInteger> fileContent = new ArrayList<BigInteger>();
		while(in.hasNextLine()) {
			BigInteger val = new BigInteger(in.nextLine());
			fileContent.add(val);
		}
		return fileContent;
	}
	/**
	 * Extracts the target value from the list and removes it from the list
	 * @param list to extract the target from
	 * @return the target value
	 */
	public static BigInteger getTarget(ArrayList<BigInteger> list) {
		BigInteger target = list.get(0);
		list.remove(0);
		return target;
	}
	
}

//try to find a combination of M values that produce as small values as possible
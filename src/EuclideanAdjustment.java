import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.math.BigInteger;
public class EuclideanAdjustment {
	
	public static void main(String[] args) throws FileNotFoundException {
		ArrayList<BigInteger> movementValues = readCharacters();
		BigInteger target = movementValues.get(0);
		movementValues.remove(0);
		movementValues.sort(null);
		int size = movementValues.size();
		System.out.println("Target: " + target);
		System.out.println(movementValues);
		for(int i = 0; i < size; i++) {
			while(movementValues.get(size - i - 1).compareTo(target) == -1) {
				target = target.subtract(movementValues.get(size-i - 1));
			}
		}
		
		System.out.println(target);
		
	}
	
	public static ArrayList<BigInteger> readCharacters() throws FileNotFoundException {
		File fileName = new File("input.txt");
		Scanner in = new Scanner(fileName);
		ArrayList<BigInteger> fileContent = new ArrayList<BigInteger>();
		while(in.hasNextLine()) {
			int temp = Integer.parseInt(in.nextLine());
			fileContent.add(BigInteger.valueOf(temp));
		}
		return fileContent;
	}
	
}

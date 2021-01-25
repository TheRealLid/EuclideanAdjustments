import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.math.BigInteger;

public class EuclideanAdjustment {
    public static void main(String[] args) throws IOException {
        ArrayList<BigInteger> vals = readCharacters();
        BigInteger goal = getGoal(vals);
        BigInteger targetTracking = new BigInteger("0");
        vals.sort(null);
        // Size of the list(M values)
        int size = vals.size();
        // Counter to keep track of how many times each operation was done
        int[] counter = new int[size];
        for (int i = 0; i < size; i++) {
            counter[i] = 0;
        }

        System.out.println("Target: " + goal);
        System.out.println(vals);

        // loops through and subtracts the values from the goal until the m values are
        // smaller than the goal or until we run out of M values



        gcd(vals);
        inputTest();
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
    public static BigInteger getGoal(ArrayList<BigInteger> list) {
        BigInteger target = list.get(0);
        list.remove(0);
        return target;
    }

    public static void printArr(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.println(arr[i]);
        }
    }


    public static void distanceToNeigbor(ArrayList<BigInteger> list) {
        ArrayList<BigInteger> newVals = new ArrayList<BigInteger>();

        for (int i = 0; i < list.size() - 1; i++) {
            newVals.add(list.get(i + 1).subtract(list.get(i)));
            // System.out.println(list.get(i+1).subtract(list.get(i)));
        }
        System.out.println("");
        for (int i = 0; i < list.size() - 1; i++) {
            BigInteger temp = newVals.get(i + 1).subtract(newVals.get(i));
            if (temp.abs().compareTo(newVals.get(i)) == -1) {
                newVals.add(newVals.get(i + 1).subtract(newVals.get(i)));
            }
        }
        combineList(list, newVals);
        newVals.sort(null);
        for (int i = 0; i < newVals.size(); i++) {
            System.out.println(newVals.get(i));
        }
    }

    // combines adds all items in list 1 into list 2
    public static void combineList(ArrayList<BigInteger> list1, ArrayList<BigInteger> list2) {
        for (int i = 0; i < list1.size(); i++) {
            if (!list2.contains(list1.get(i)))
                list2.add(list1.get(i));
        }
    }

    public static void gcd(ArrayList<BigInteger> vals) {
        ArrayList<BigInteger> haveGcdWithOtherVals = new ArrayList<>();
        BigInteger one = new BigInteger("1");
        for (int i = 0; i < vals.size(); i++) {
            for (int j = 0; j < vals.size(); j++) {
                if (!vals.get(i).gcd(vals.get(j)).equals(one) && i != j) {
                    haveGcdWithOtherVals.add(vals.get(i));
                    haveGcdWithOtherVals.add(vals.get(j));
                }
            }
        }
        System.out.println("numbers that have a gcd with atleast one other number in the set: " + haveGcdWithOtherVals);

    }

    public static void inputTest() throws IOException {
        FileWriter outPut = new FileWriter("OutputTest.txt");
        int smallest = Integer.MAX_VALUE;
        int x = 0;
        int a = 5;//-1190
        int b = 27050;//550
        int c = 3787;//-3927
        while (a > 0 || b > 0 || c > 0) {
            if (a > 0) {
                x -= 1190;
                a--;
            }
            if (b > 0) {
                x += 550;
                b--;
            }
            if (c > 0) {
                x -= 3927;
                c--;
            }
            if(x < smallest){
                smallest = x;
            }
            String str = "";
            str += x;
                outPut.write("x = " + x + "\n");

        }
        System.out.println("X = " + x);
        System.out.println("smallest = " + smallest);
        outPut.close();
    }
    public static void gettingMin(ArrayList<BigInteger> vals, BigInteger goal, BigInteger targetTracking, int[] counter){
    	int size = vals.size();
		for (int i = 0; i < size; i++) {
			while (vals.get(size - i -
					1).compareTo(goal.subtract(targetTracking)) == -1) {
				targetTracking =
						targetTracking.add(vals.get(size - i - 1));
				counter[size - i - 1]
						+= 1;
			}
		}
		targetTracking = targetTracking.add(vals.get(0));
	}
}

//try to find a combination of M values that produce as small values as possible
// Find sets of numbers that have a GCD with one another. A and B have a non 1 GCD, A and C have a non 1 GCD, C and B have a non 1 GCD
// Transitivity does not apply to GCD. Must manually compare to find the desired set
// Find set {a, b, c , n} then do computation on those until we get a value T that had a non 1 GCD with values a-n
//(1190 * 92195) + 18439 has a non 1 GCD for both 550 and 3927. Both of these have a GCD of 11
//All x values are a multiple of T
// new formula 1 = [m1 * x1] + [m2 * x2] + [mn * xn]
// once 1 is found multiply the x movements by goal to get the required movements to find goal
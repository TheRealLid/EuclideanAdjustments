import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.math.BigInteger;
//-3927, is divisible by 550? no. -1190, divisible by 550? no. -1190, divisible by 550? no. -1190, divisible by 550? no.
//revert back to -3927, subtract -3927 again. now subtract -1190, check, -1190, check, -1190 check. Repeat until divisible by 550
// this only justifies at most 3 * -1190 and not the 5 we see in the solution
public class EuclideanAdjustment {
    public static void main(String[] args) throws IOException {
        ArrayList<BigInteger> vals = readCharacters();
        BigInteger goal = getGoal(vals);
        BigInteger currentVal = new BigInteger("0");
        vals.sort(null);
        // Size of the list(M values)
        int size = vals.size();
        // Counter to keep track of how many times each operation was done
        BigInteger[] counter = new BigInteger[size];
        for (int i = 0; i < size; i++) {
            counter[i] = new BigInteger("0");
        }
        System.out.println("Target: " + goal);
        System.out.println(vals);

        ArrayList<BigInteger> shortVals = new ArrayList<>();
        boolean flag = false;
        for(int i = 0; i < vals.size(); i++){
            for(int j = 1; j < vals.size()-1; j++){
                for (int k = 0; k < size; k++) {
                    counter[k] = new BigInteger("0");
                }
                shortVals.clear();
                shortVals.add(vals.get(i));
                shortVals.add(vals.get(j));
                shortVals.add(vals.get(j+1));
                if(staticAttemp(shortVals, counter, goal)){
                    flag = true;
                    break;
                }
            }
            if(flag) break;
        }
       /* shortVals.add(vals.get(0));//smallest
        shortVals.add(vals.get(4));//medium
        shortVals.add(vals.get(5));//largest
        System.out.println(staticAttemp(shortVals, counter, goal));*/
        //inputTest();

    }

    public static boolean staticAttemp(ArrayList<BigInteger> vals, BigInteger counter[], BigInteger goal) throws IOException {
        int loops = 0;
        BigInteger current = new BigInteger("0");
        BigInteger one = new BigInteger("1");
        while(current.mod(vals.get(0)).compareTo(one)!=0){
            if(loops > 100) break;
            current = current.subtract(vals.get(2));
            counter[2] = counter[2].subtract(one);
            BigInteger temp = current;
            if(current.mod(vals.get(0)).compareTo(one)==0) {
                System.out.println("Solved");
                break;
            }
            for(int i = 0; i < 7; i++){
                current = current.subtract(vals.get(1));
                counter[1] = counter[1].subtract(one);
                if(current.mod(vals.get(0)).compareTo(one)==0) {
                    break;
                }
            }
            if(current.mod(vals.get(0)).compareTo(one)==0) {
                System.out.println("Solved");
                break;
            }
            counter[1] = new BigInteger("0");
            current = temp;
            loops++;
        }
        if(loops > 100) return false;
        //System.out.println("current " + current);
        //System.out.println("loops: " + loops);
        counter[0] = ((current.subtract(one)).divide(vals.get(0))).abs();

        writeAnswerToFile(vals, counter, goal);
        printArr(counter,goal);
        System.out.println("Added together " + counter[0].multiply(vals.get(0)).add(counter[1].multiply(vals.get(1))).add(counter[2].multiply(vals.get(2))));
        return true;
    }
    public static void writeAnswerToFile(ArrayList<BigInteger> vals, BigInteger counter[], BigInteger goal) throws IOException {
        FileWriter outPut = new FileWriter("outputSelfTest.txt");
        for(int i = 0; i < vals.size(); i++){
            outPut.write(vals.get(i).toString() + " " + counter[i].multiply(goal).toString() + "\n");
        }
        outPut.close();
    }
    public static int[] attempt(ArrayList<BigInteger> vals, BigInteger currentVal, int counter[]) {
    	BigInteger one = new BigInteger("1");
    	for(int i = 0; i < vals.size();i++) {
    		if(vals.get(i).mod(currentVal) == one) {
    			return counter;
    		}

    	}
    	return null;
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

    public static void printArr(BigInteger[] arr, BigInteger goal) {
        for (int i = 0; i < arr.length; i++) {
            System.out.println(arr[i].multiply(goal));
        }
    }

    // combines adds all items in list 1 into list 2
    public static void combineList(ArrayList<BigInteger> list1, ArrayList<BigInteger> list2) {
        for (int i = 0; i < list1.size(); i++) {
            if (!list2.contains(list1.get(i)))
                list2.add(list1.get(i));
        }
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
            if(c == 0){
                String temp = "";
                temp += "value when c ends is = " + x + "\n";
                outPut.write(temp);
                c--;
            }
            if(a == 0){
                String temp = "";
                temp += "value when a ends is = " + x + "\n";
                outPut.write(temp);
                a--;
            }
            int testVal = 550;
            if(Math.abs(gcd(testVal, x)) != 1){
                String temp = "";
                temp += testVal + " and X have a a gcd of " + gcd(testVal, x) + "\n";
                //outPut.write(temp);
            }
            str += x;
            outPut.write("x = " + x + "\n");

        }
        System.out.println("X = " + x);
        System.out.println("smallest = " + smallest);
        outPut.close();
    }

    public static int gcd(int a, int b) {
        if (b==0) return a;
        return gcd(b,a%b);
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
// once we find a way to select the useful numbers from the list of M we use the smallest one for adding and the rest for subtracting

//Current tasks
//how to select which numbers to use from list
//how to know when to stop subtracting. Why is 1190 subtracted EXACTLY 5 times

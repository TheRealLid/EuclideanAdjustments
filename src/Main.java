/**
 * Matthew Quinn
 * ID: 026127164
 * CECS 328 T/TH 11am
 * This program will calculate the adjustments left or right to achieve some number "goal"
 * The adjustments are fixed values provided in the input.txt file
 * This version of the program is extremely limited and is only capable of solving variations of the problem where
 * the solution uses exactly 3 of the provided adjustment values
 * <p>
 * The program simplifies the initial problem and attempts to find a case where m1*val1 + m2*val2 + m3*val3 = 1
 * then multiplies both sides by goal, giving the solution for reaching goal.
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.math.BigInteger;


public class Main {
    public static void main(String[] args) throws IOException {
        // gets the values from input.txt
        ArrayList<BigInteger> vals = readCharacters();
        // saves a copy of the unsorted list
        ArrayList<BigInteger> originalValues = (ArrayList<BigInteger>) vals.clone();
        // the number we want to reach
        BigInteger goal = getGoal(vals);
        // sets up our currentValue used in the solution attempt
        BigInteger currentVal = new BigInteger("0");
        //sorts the array
        vals.sort(null);
        System.out.println(vals);
        // Counter to keep track of how many times each operation was done
        BigInteger[] counter = new BigInteger[4];

        //main loop to try different combinations of numbers
        ArrayList<BigInteger> shortVals = new ArrayList<>();
        //breaks if a solution has already been found
        boolean flag = false;
        for (int i = 0; i < vals.size() - 3; i++) {
            //breaks if a solution has already been found
            if (flag) break;
            for (int j = i + 1; j < vals.size() - 2; j++) {
                //breaks if a solution has already been found
                if (flag) break;
                for (int k = j + 1; k < vals.size()-1; k++) {
                    if (flag) break;
                    for (int l = k + 1; l < vals.size(); l++) {
                        //breaks if a solution has already been found
                        if (flag) break;
                        for (int n = 0; n < counter.length; n++) {
                            counter[n] = new BigInteger("0");
                        }
                        //clears the list
                        shortVals.clear();
                        //smallest of the 3 values
                        shortVals.add(vals.get(i));
                        //middle of the 3 values
                        shortVals.add(vals.get(j));
                        //largest of the 3 values
                        shortVals.add(vals.get(k));
                        shortVals.add(vals.get(l));
                        if (limitedAttempt(shortVals, counter)) {
                            //sets flag to true to allow for break out of loop
                            flag = true;
                        }
                    }
                }

            }
        }
        if (!flag) {
            System.out.println("program failed to find a solution :(");
        } else {

            System.out.println("program found a solution :D");
            //multiplies counters by goal, before this multiplication counters solve for 1. Multiplying by goal
            //gives us the expected values we needed.

            for (int i = 0; i < counter.length; i++) {

                counter[i] = counter[i].multiply(goal);
                System.out.println("Goal: " + goal);
            }
            BigInteger sum = new BigInteger("0");
            for(int i = 0; i < counter.length; i++){
                sum = sum.add(counter[i].multiply(shortVals.get(i)));
                System.out.print("(" + shortVals.get(i) + "*" + counter[i] + ") + ");
            }
            System.out.println(sum);
            //writes the answer to output.txt
            writeAnswerToFile(shortVals, counter, originalValues);
        }

    }

    /**
     * @param vals    the numbers we are attempting to use to find a solution
     * @param counter the number of times each vale was used
     * @return true if it finds a solution, false if it doesnt
     * @throws IOException
     */
    public static boolean limitedAttempt(ArrayList<BigInteger> vals, BigInteger counter[]) {
        int size = vals.size();
        int largest = size-1;
        int upperMid = size-2;
        int lowerMid = size-3;
        int smallest = size-4;
        // loops keeps track of the number of loops the program has gone though, used to break after 5,000 loops
        BigInteger loops = new BigInteger("0");
        // the current value we are working with, starts at 0
        BigInteger current = new BigInteger("0");
        // are target is One as once we solve for one we can multiply all counts by goal to get the actual number of
        //movements. This reduces the total amount of operations we need to do
        BigInteger one = new BigInteger("1");
        BigInteger upperMidLoops = vals.get(largest).divide(vals.get(upperMid));
        BigInteger lowMidLoops = vals.get(upperMid).divide(vals.get(lowerMid));
        int lowMidTermination = lowMidLoops.intValue();
        lowMidTermination += 15;
        int upperMidTermination = upperMidLoops.intValue();
        upperMidTermination += 15;
        while (current.mod(vals.get(0)).compareTo(one) != 0) {
            //if the number of loops is larger than 5000 return false. Meaning the program failed.
            if (loops.compareTo(new BigInteger("5000")) == 1) {
                return false;
            }
            // subtracts the largest of the 3 values we passed in
            current = current.subtract(vals.get(largest));
            //updates the counter
            counter[largest] = counter[largest].subtract(one);
            BigInteger temp = current;
            //checks if this is a valid solution set up
            if (current.mod(vals.get(0)).compareTo(one) == 0) {
                break;
            }
            //adds the medium number we passed in 10 times
            for (int i = 0; i < upperMidTermination; i++) {
                current = current.subtract(vals.get(upperMid));
                //updates the counter
                counter[upperMid] = counter[upperMid].subtract(one);
                BigInteger localTemp = current;
                for(int j = 0; j < lowMidTermination; j++){
                    current = current.subtract(vals.get(lowerMid));
                    counter[lowerMid] = counter[lowerMid].subtract(one);
                    if (current.mod(vals.get(0)).compareTo(one) == 0) {
                        break;
                    }
                }
                //checks if this is a valid solution set up
                if (current.mod(vals.get(0)).compareTo(one) == 0) {
                    break;
                }
                current = localTemp;
                counter[lowerMid] = new BigInteger("0");

            }
            if (current.mod(vals.get(0)).compareTo(one) == 0) {
                break;
            }
            //updates the counter
            counter[upperMid] = new BigInteger("0");
            //if the middle number didnt reach a solution we undo the changes it made.
            current = temp;

            loops = loops.add(one);

        }
        //returns a failure to find a solution if hte loops go over 5000
        if (loops.compareTo(new BigInteger("5000")) == 1) {
            System.out.println("Loops: " + loops.toString());
            return false;
        }
        //calculates the shifts for our smallest value
        counter[0] = ((current.subtract(one)).divide(vals.get(0))).abs();
        BigInteger sum = new BigInteger("0");
        for(int i = 0; i < vals.size(); i++){
            sum = sum.add(counter[i].multiply(vals.get(i)));
        }
        System.out.println("EARLY SUM: " + sum);
        //returns true meaning the program successfully found a solution
        return true;
    }

    /**
     * Writes the solution to output.txt
     *
     * @param vals           the values used in the solution
     * @param counter        the number of times each value was used
     * @param originalValues the unsorted original list, used to set the list back into its original order
     * @throws IOException throws IOExecption if an error occurs on file write
     */
    public static void writeAnswerToFile(ArrayList<BigInteger> vals, BigInteger counter[], ArrayList<BigInteger> originalValues) throws IOException {

        //converts the order of the list back to the original input(before sorting)
        FileWriter outPut = new FileWriter("output.txt");
        for (int i = 0; i < originalValues.size(); i++) {
            for (int j = 0; j < vals.size(); j++) {
                if (vals.get(j).compareTo(originalValues.get(i)) == 0) {
                    outPut.write(vals.get(j).toString() + " " + counter[j].toString() + "\n");
                }
            }
        }


        outPut.close();

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
}


package softwaredesign;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.Arrays;

public class Minigame {

    /**
     * List of blocks that need to be sorted in the minigame
     */
    private ArrayList<Integer> myList;

    public Minigame(){
        this.myList = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));
    }

    private int correctNum(){
        System.out.println("\nList in function: \n" + this.myList);
        int correctNum = 0;
        for (int temp : this.myList) {
            if (temp == this.myList.indexOf(temp) + 1){
                correctNum += 1;
            }
        }
        return correctNum;
    }

    private void displayBlocks() {
        System.out.println("Current block configuration:");
        for (int block : this.myList) {
            System.out.println("|" + block + "|");
        }
    }

    public void playGame() {
        System.out.println("A miniGame session has begun!");
        Collections.shuffle(this.myList);

        // now that the list is shuffled we need to repeat the rest of the code until the correct num == 5
        int correct = 0;
        Scanner sc= new Scanner(System.in);
        while (correct < 5){
            this.displayBlocks();
            System.out.print("\n correct locks: "+ correct + "\n");

            //Swapping Numbers Time
            System.out.print("Enter first position to swap (1-5): ");
            int a= sc.nextInt();
            System.out.print("Enter second position to swap (1-5): ");
            int b= sc.nextInt();
            System.out.print(a+"v"+b);

            int aVal = this.myList.get(a-1);
            int bVal = this.myList.get(b-1);
            System.out.print(aVal+"val"+bVal);
            this.myList.set(a-1, bVal);
            this.myList.set(b-1, aVal);
            System.out.println("\n swapped List : \n" + this.myList);
            correct = this.correctNum();
        }
        this.displayBlocks();
        System.out.println("\n You Won! Your tamagotchi is now more energetic and happier! The game will now close. \n");
    }
}

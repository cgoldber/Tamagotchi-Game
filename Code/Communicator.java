package softwaredesign;

import softwaredesign.Tamagotchi;
import java.util.ArrayList;
import java.util.Scanner;

public class Communicator{
    /**
     * Scanner object for the Communicator object.
     */
    private Scanner scan;

    /**
     * Constructor for Communicator objects.
     */
    public Communicator() {
        this.scan = new Scanner(System.in);
    }

    /**
     * Asks for the user's name to store it in the game.
     */
    public String inputUsername(){
        boolean usernameConfirmed = false;
        String username = "";
        do{
            System.out.println("Please enter your desired username.");
            username = scan.nextLine();
            System.out.println("Confirm username:  " + username);
            System.out.println("Type y to confirm, n to change your username.");
            String confirm = scan.nextLine();
            usernameConfirmed = confirm.toLowerCase().equals("y") ? true : false;
        } while(!usernameConfirmed);
        return username;
    }

    /**
     * Gets the name and the species of the Tamagotchi a user is attempting to create.
     */
    public String[] getNewTamagotchiInfo(){
        String[] tamagotchiInfo = new String[2];
        System.out.println("Enter the name of your Tamagotchi: ");
        String name = scan.nextLine();
        String type = getTamagotchiType();
        tamagotchiInfo[0] = name;
        tamagotchiInfo[1] = type;
        return tamagotchiInfo;
    }

    /**
     * Prints the list of types to the user.
     */
    private void printTypePrompt() {
        System.out.println("What type would you like your Tamagtochi to be?");
        System.out.println("Type 1 for Dog");
        System.out.println("Type 2 for Cat");
        System.out.println("Type 3 for Bunny");
    }

    /**
     * Asks the user for the desired Tamagotchi type.
     * @return type is the inputted type of the Tamagotchi.
     */
    private String getTamagotchiType() {
        boolean validType = false;
        String type = "";
        do {
            printTypePrompt();
            while(!scan.hasNextInt()) {
                System.out.println("Invalid input. Try again.");
                scan.next();
            }
            int typeIdentifier = scan.nextInt();
            switch(typeIdentifier) {
                case 1:
                    type = "Dog";
                    validType = true;
                    break;
                case 2:
                    type = "Cat";
                    validType = true;
                    break;
                case 3:
                    type = "Bunny";
                    validType = true;
                    break;
                default:
                    System.out.println("Invalid input. Try again.");
            }
        } while(!validType);
        return type;
    }
    /**
     * Allows the user to select a new Tamagotchi.
     */
    public Tamagotchi selectNewTamagotchi(ArrayList<Tamagotchi> activePets){
        int tamagotchiIndex = getTamagotchiSelection(activePets);
        return activePets.get(tamagotchiIndex);
    }

    /**
     * Lists the selectable Tamagotchi's and retrieves the users input.
     */
    private int getTamagotchiSelection(ArrayList<Tamagotchi> activePets) {
        boolean validSelection = false;
        int tamagotchiIndex;
        do{
            int count = 1;
            for (Tamagotchi t: activePets) {
                System.out.println("Type " + count + " to select: " + t.getName());
                count++;
            }
            while(!scan.hasNextInt()) {
                System.out.println("Invalid input. Try again.");
                scan.next();
            }
            tamagotchiIndex = scan.nextInt() - 1;
            if(tamagotchiIndex < activePets.size() && tamagotchiIndex >= 0) {
                validSelection = true;
            }
            else {
                System.out.println("Invalid input. Try again.");
            }
        } while(!validSelection);
        return tamagotchiIndex;
    }


    /**
     * Asks the user which action they are looking to perform on their Tamagotchi.
     */
    public int promptAction() {
        printActions();
        int action;
        do{
            while(!scan.hasNextInt()) {
                System.out.println("Invalid input. Try again.");
                scan.next();
            }
            action = scan.nextInt();
        } while(!(action >= 0) && !(action < 8));
        return action;
    }

    /**
     * Helper method for printing out the list of actions the user can commit.
     */
    private void printActions() {
        System.out.println("What would you like to do?");
        System.out.println("Type 0 to quit");
        System.out.println("Type 1 to select a different Tamagotchi");
        System.out.println("Type 2 to create a new Tamagotchi");
        System.out.println("Type 3 to clean your Tamagotchi");
        System.out.println("Type 4 to discipline your Tamagotchi");
        System.out.println("Type 5 to make your Tamagotchi sleep");
        System.out.println("Type 6 to feed your Tamagotchi");
        System.out.println("Type 7 to play with your Tamagotchi");
    }

    /**
     * Responsible for prompting the user for the method of discipline.
     * @return String representing method of discipline.
     */
    public String disciplineSelection() {
        System.out.println("How would you like to discipline your Tamagtochi?");
        System.out.println("Type 1 to yell at it (no swearing).");
        System.out.println("Type 2 to yell at it (with swearing).");
        System.out.println("Type 3 to throw a rock at it.");
        while(true) {
            while(!scan.hasNextInt()) {
                System.out.println("Invalid input. Try again.");
                scan.next();
            }
            int input = scan.nextInt();
            switch (input) {
                case 1:
                    return "yell at it (no swearing)";
                case 2:
                    return "yell at it (with swearing)";
                case 3:
                    return "throw a rock at it";
                default:
                    System.out.println("Invalid input.");
            }
        }
    }

    /**
     * Responsible for prompting the user for food selection.
     * @return String representing desired food selection.
     */
    public String foodSelection() {
        System.out.println("What would you like to feed your Tamagotchi?");
        System.out.println("Type 1 for Chicken");
        System.out.println("Type 2 for Fish");
        System.out.println("Type 3 for Carrot");
        while(true) {
            while(!scan.hasNextInt()) {
                System.out.println("Invalid input. Try again.");
                scan.next();
            }
            int input = scan.nextInt();
            switch (input) {
                case 1:
                    return "Chicken";
                case 2:
                    return "Fish";
                case 3:
                    return "Carrot";
                default:
                    System.out.println("Invalid input.");
            }
        }
    }
}

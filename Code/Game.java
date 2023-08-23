package softwaredesign;

import softwaredesign.Communicator;
import softwaredesign.Minigame;
import softwaredesign.Tamagotchi;

import java.util.ArrayList;
import java.lang.*;
import java.util.concurrent.TimeUnit;

public class Game{
    /**
     * Singleton implementation, as we only ever want one instance of Game.
     */
    private static final Game INSTANCE = new Game();

    /**
     * List of a user's pets in a game.
     */
    private ArrayList<Tamagotchi> pets;

    /**
     * The current Tamagotchi that a user has selected.
     */
    private Tamagotchi currPet;

    /**
     * The user's name.
     */
    private String userName;

    /**
     * The communicator for the game, used to communicate with the user.
     */
    private Communicator myCom;

    /**
     * The minigame for a game.
     */
    private Minigame myMinigame;

    /**
     * The status of the game session.
     */
    private boolean gameStatus; //Add to diagrams!!

    /**
     * Constructor for Game objects.
     */
    private Game(){
        System.out.println("A game session has been created");
        this.pets = new ArrayList<Tamagotchi>();
        this.currPet = null;
        this.userName = "";
        this.gameStatus = true;
        this.myCom = new Communicator();
        this.myMinigame = new Minigame();
    }

    /**
     * Singleton implementation
     * @return the instance of Game
     */
    public static Game getInstance() {
        return INSTANCE;
    }
    /**
     * Responsible for running the game.
     */
    public void runGame(){

        this.initConfig();

        //start the clock
        long prevTime = System.currentTimeMillis();

        //run the main loop
        while(this.gameStatus){

            //get action from user and do action
            int actNum = this.myCom.promptAction();
            this.doAction(actNum);

            //decrement meters based on time (maybe make this a helper function)
            long currTime = System.currentTimeMillis();
            if(this.gameStatus && currTime - prevTime > 60000){ //hardcoded value: make global var and also check to make sure it's reasonable
                this.decrementAllPetMeters();
                prevTime = currTime;
            }
        }
        System.out.println("The game is now over. Thanks for playing! :)");
    }

    /**
     * Responsible for configuration of the user's first Tamagotchi.
     */
    private void initConfig(){
        this.userName = this.myCom.inputUsername();
        this.createTamagotchi();
        this.currPet = this.myCom.selectNewTamagotchi(this.pets); //change this name or change diagram
    }

    /**
     * Responsible for creating Tamagotchis.
     */
    private void createTamagotchi(){
        //get info about new pet
        String[] info = this.myCom.getNewTamagotchiInfo();
        String name = info[0]; //make sure this order is right
        String type = info[1].toLowerCase();

        //make new pet
        Tamagotchi newPet = null;
        if(type.equals("cat")){ //maybe we should just leave it all as numbers
            newPet = new Cat(name);
        }else if(type.equals("dog")){
            newPet = new Dog(name);
        }else if(type.equals("bunny")){
            newPet = new Bunny(name);
        }

        //add new pet to pet list
        this.pets.add(newPet);

        //attach this game instance as an observer to the new pet
        newPet.attachGame(this);
    }

    /**
     * Selects which already created pet will be played with.
     */
    private void selectCurrPet(){
        Tamagotchi selectedPet = this.myCom.selectNewTamagotchi(this.pets);
        for (Tamagotchi pet : this.pets){
            if (pet.getName().equals(selectedPet.getName())){
                this.currPet = pet;
            }
        }
    }

    /**
     * Does an action based on user input (i.e sleep, clean, feed).
     */
    private void doAction(int actNum){
        //pause to make game more realistic
        try {
            if(actNum!= 0 && actNum != 1 && actNum != 2) {
                System.out.println("Attempting to do action...");
                TimeUnit.SECONDS.sleep(3); //value hardcoded in: make global var
            }
        } catch (InterruptedException e) {
            System.out.println("Interrupted " + "while Sleeping");
        }
        //do action
        switch(actNum){
            case 0:
                //exit game
                this.gameStatus = false;
                break;
            case 1:
                //select new tamagotchi
                this.selectCurrPet();
                break;
            case 2:
                createTamagotchi();
                break;
            case 3:
                //clean
                this.currPet.clean();
                break;
            case 4:
                //discipline
                String discMethod = this.myCom.disciplineSelection();
                this.currPet.discipline(discMethod);
                break;
            case 5:
                //sleep
                this.currPet.rest(); //change this because we need sleep name (fix on diagram)
                break;
            case 6:
                //feed
                int result = 0;
                while (result == 0){
                    String foodSelection = this.myCom.foodSelection();
                    result = this.currPet.feed(foodSelection);
                }
                break;
            case 7:
                //play minigame
                this.runMinigame();
                break;
        }
    }

    /**
     * Runs the minigame.
     */
    private void runMinigame(){
        this.myMinigame.playGame();
        this.currPet.miniGameEffects();
    }

    /**
     * Decrements the meters of the current pet and checks to see what the meter levels are afterwards.
     */
    private void decrementAllPetMeters(){
        this.currPet.decrementAllMeters();
    }

    /**
     * Displays the meters of the current pet.
     */
    private void displayCurrMeters(){
        System.out.println("Here are " + this.currPet.getName() + "'s current meter levels:");
        this.currPet.displayAllMeters();
    }

    /**
     * Kills a pet.
     */
    protected void die(String meter){
        this.pets.remove(currPet);
        System.out.println("Shame on you! " + this.currPet.getName() + "'s " + meter + " meter went to 0 and it died :(");
        if(this.pets.isEmpty()){
            System.out.println("You have no pets left, you must create a new one.");
            this.createTamagotchi();
        }
        this.currPet = this.myCom.selectNewTamagotchi(this.pets);
    }

}

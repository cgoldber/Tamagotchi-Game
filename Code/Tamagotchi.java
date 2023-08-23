package softwaredesign;

import softwaredesign.Meter;
import java.util.concurrent.TimeUnit;
import softwaredesign.Meter.meterLevel;

public abstract class Tamagotchi{
    /**
     * Used to label a Tamagotchi's favorite food.
     */
    enum Food {
        Carrot, Fish, Chicken;
    }

    /**
     * The name of the Tamagotchi.
     */
    private String name;

    /**
     * A Tamagotchi's favorite food, used to determine how to feed a Tamagotchi.
     */
    protected Food favFood;

    /**
     * The Tamagotchi's energy meter.
     */
    private Meter energy;

    /**
     * The Tamagotchi's happiness meter.
     */
    private Meter happiness;

    /**
     * The Tamagotchi's foodLevel meter.
     */
    private Meter foodLevel;

    /**
     * The Tamagotchi's discipline meter.
     */
    private Meter discipline;

    /**
     * The Tamagotchi's cleanliness meter.
     */
    private Meter cleanliness;

    /**
     * A List for all of the Meters. //GOTTa ADD TO DIAGRAM
     */
    private Meter[] meters;

    /**
     * The game session that is observing this pet
     */
    private Game petObserver;

    /**
     * Constructor for Tamagotchi objects
     * @param name is the name of the Tamagotchi
     */
    public Tamagotchi(String name){
        this.name = name;
        this.initializeMeters();
        this.makeObserver();
    }

    /**
        Initialize the Tamagotchi's meters.
     */
    private void initializeMeters(){
        this.energy = new Meter("Energy");
        this.happiness = new Meter("Happiness");
        this.foodLevel = new Meter("Food Level");
        this.discipline = new Meter("Discipline");
        this.cleanliness = new Meter("Cleanliness");
        this.meters = new Meter[]{this.energy, this.happiness, this.foodLevel, this.discipline, this.cleanliness};
    }

    /**
     * Make this pet an observer to all of its meters.
     */
    private void makeObserver(){
        for (Meter meter: this.meters){
            meter.attach(this);
        }
    }

    /**
     * Getter method for name.
     * @return name of the Tamagtochi
     */
    public String getName() {
        return name;
    }

    /**
     * Tamagotchi's clean method.
     */
    public void clean(){
        this.cleanliness.increment();
    }

    /**
     * Tamagotchi's discipline method.
     */
    public void discipline(String scoldMethod){ //gonna need to ask for method before
        this.discipline.increment(); //need a check for increment somewhere
        if (this.discipline.getCurrStatus().equals(meterLevel.HIGH)){
            System.out.println("You scolded " + this.name + " with method: " + scoldMethod + " but they were //" +
                    "already perfectly behaved. Therefore, + " + this.name + " is more sad");
            this.happiness.decrement();
        } else{
            System.out.println("You scolded " + this.name + " with method: " + scoldMethod + " so now they are //" +
                    "better behaved. Good tough parenting!");
        }
    }

    /**
     * Tamagotchi's rest method.
     */
    public void rest(){
        if(this.energy.getCurrStatus().equals(meterLevel.HIGH)){ //prob gonna wanna make this an enumeration
            System.out.println(this.name + " does not need sleep right now. Try again later");
        }else{
            //pause game while it sleeps
            try {
                System.out.println("SHHHHH, " + this.name + " is sleeping for 20 seconds");
                TimeUnit.SECONDS.sleep(20);; //value hardcoded in: make global var
            } catch (InterruptedException e) {
                System.out.println("Interrupted " + "while Sleeping");
            }

            this.energy.increment();
        }
    }

    /**
     * Tamagotchi's feed method.
     *
     * @return 1 if feed is completed, 0 if feed isn't completed (wrong food)
     */
    public int feed(String curr_food){ //A LOT OF CHANGES TO HOW THIS WORKS THAT WE'LL HAVE TO EXPLAIN IN DIAGRAM
        Food food = this.strToFood(curr_food);
        if(this.isFavoriteFood(food)){
            this.foodLevel.increment(); //incrementing no matter what?? we need a check somewhere
            if(this.foodLevel.getCurrStatus().equals(meterLevel.HIGH)){
                this.energy.decrement();
                System.out.println("You just fed " + this.name + " a" + food + " even though they were full. They barfed it up and their energy level went down :(");
            } else {
                System.out.println("You just fed " + this.name + " a " + food + " and their energy went up!");
            }
            return 1;
        }
        System.out.println("You tried to feed " + this.name + " a " + food + " even though it only likes " + this.favFood + ".");
        return 0;
    }

    /**
     * Communicates with the pet's meters that increase after completing a minigame session.
     */
    public void miniGameEffects(){
        this.energy.increment();
        this.happiness.increment();
    }

    /**
     * Decrements all of a Tamagotchi's meters.
     */
    public void decrementAllMeters(){
        for (Meter meter : this.meters){
            meter.decrement();
        }
    }

    /**
     * Displays all of a Tamagotchi's meters.
     */
    public void displayAllMeters(){
        for (Meter meter : this.meters){
            meter.displayMeter();
        }
    }

    /**
     * Receives status changes of meters if meter level is low or dead.
     * @return The String of the Meter
     */
    protected void update(){
       for (Meter meter: this.meters){
           meterLevel currState = meter.getCurrStatus();
           if (currState.equals(meterLevel.DEAD)){
               this.notifyDeath(meter.getMeterName());
           }else if(currState.equals(meterLevel.LOW)){
               System.out.println(this.name + "'s " + meter + " meter is low.");
           }
       }
    }

    /**
     * Checks if a given Food is the Tamagotchi's favorite.
     */
    private boolean isFavoriteFood(Food food) {
        if (food == this.favFood) {
            return true;
        }
        return false;
    }

    /**
     * Returns a String representation of a food //this might be unecessary
     */
    private String foodToStr(Food food){
        switch(food){
            case Carrot: return "Carrot";
            case Chicken: return "Chicken";
            case Fish: return "Fish";
        }
        return null;
    }

    /**
     * Returns a food representation of a String
     */
    private Food strToFood(String food){
        switch(food.toLowerCase()){
            case "carrot": return Food.Carrot;
            case "chicken": return Food.Chicken;
            case "fish": return Food.Fish;
        }
        return null;
    }

    /**
     * Attaches the game session as an observer for the pet
     */
    public void attachGame(Game petObserver){
        this.petObserver = petObserver;
    }

    /**
     * Notifies game of its death
     */
    private void notifyDeath(String meter){
        this.petObserver.die(meter);
    }
}


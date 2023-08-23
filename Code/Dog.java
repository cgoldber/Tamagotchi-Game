package softwaredesign;

public class Dog extends Tamagotchi {
    /**
     * Constructor for Dog objects.
     * favFood set to Chicken.
     * @param name is the name of the Tamagotchi
     */
    public Dog(String name) {
        super(name);
        this.favFood = Food.Chicken;
    }
}

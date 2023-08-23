package softwaredesign;

public class Cat extends Tamagotchi {
    /**
     * Constructor for Cat object.
     * favFood is set to Fish
     * @param name is the name of the Tamagotchi
     */
    public Cat(String name) {
        super(name);
        this.favFood = Food.Fish;
    }
}

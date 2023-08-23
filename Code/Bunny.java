package softwaredesign;

public class Bunny extends Tamagotchi {
    /**
     * Constructor for Bunny objects.
     * favFood set to Carrot
     * @param name is the name of the Tamagotchi
     */
    public Bunny(String name) {
        super(name);
        this.favFood = Food.Carrot;
    }
}

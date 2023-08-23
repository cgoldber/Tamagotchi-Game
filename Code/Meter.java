package softwaredesign;

public class Meter{
    //enum to store the different meter statuses a meter could have. (low = 1-2, normal= 3-9, high=10)
    enum meterLevel {
        DEAD, LOW, NORMAL, HIGH
    }
    private int currVal; //private attributes that meter keeps track of
    private meterLevel currStatus;
    private String meterName;


    private Tamagotchi observer; // putting this in so we can implement tamagotchi design pattern (only allows for one observer instead of a list)
        
    public Meter(String meterName){ //should we call in the meter name by string ? or by enumeration number?
        this.currVal = 5; //defaulted meter value of 5
        this.meterName= meterName; //attribute which stores the specific meter name we're working with (for printing purposes)
        this.currStatus = meterLevel.NORMAL;
    }

    public void decrement(){
        if (this.currVal>0){  //meter cannot have a level below 0.
            this.currVal -=1;}
        this.displayMeter(); //display the new value of the meter after changing
        this.setState(); //check the state after each time the currVal is changed
    }

    public void increment(){
        if (this.currVal < 10){ //meter level cannot surpass a 10.
            this.currVal+=1;}
        this.displayMeter();
        this.setState();
    }

    private void setState(){
        if(this.currVal <= 2) {
            if (this.currVal == 1 || this.currVal == 2) {
                this.currStatus = meterLevel.LOW;
            } //if currVal is 1 or 2, the meter is low.
            else {
                this.currStatus = meterLevel.DEAD;
            }
            this.notifyObserver(); //notify pet if meter level is low or dead
        } else if (this.currVal == 10){ //if currVal 10, currState is High.
            this.currStatus = meterLevel.HIGH;
        } else {
            this.currStatus = meterLevel.NORMAL;} //any other value, the meter is at a normal level.
    }

    /**
     * Attaches an observer to this meter object
     * @param observer
     */
    public void attach(Tamagotchi observer){
        this.observer = observer;
    }

    /**
     * Notifies the pet in the change of meter status
     */
    private void notifyObserver(){
        this.observer.update();
    }

    public void displayMeter(){
        System.out.println(this.meterName + ": " + this.currVal);} //displays the updated meter level of the current meter affected.
        
    public int getCurrVal(){ //this getter method can be used by other classes to check the current val of a meter.
        return this.currVal;} 
    
    public meterLevel getCurrStatus(){ //getter method for outside classes to receive information on the current status of a meter
        return this.currStatus;}
    
    public String getMeterName(){ //getter method for outside classes to receive information on the name of the current meter (mostly for printing purposes)
        return this.meterName;}
     
}

/**
 * Created by Fredrik on 2015-08-06.
 */
public class SpeedAndFuelInfo {

    private long lastFuelMessageTimestamp;
    private long lastSpeedMessageTimestamp;
    private float lastSpeed;
    private float lastFuel;
    private boolean currentlyError = true;


    public void setFuel(float lastFuel,long lastFuelMessageTimestamp){
        this.lastFuel=lastFuel;
        this.lastFuelMessageTimestamp=lastFuelMessageTimestamp;
    }
    public void setSpeed(float lastSpeed,long lastSpeedMessageTimestamp){
        this.lastSpeed=lastSpeed;
        this.lastSpeedMessageTimestamp=lastSpeedMessageTimestamp;
    }
    public boolean isThereNewError(){
        if ((lastFuel<20 && lastSpeed>40  )  &&  Math.abs(lastFuelMessageTimestamp-lastSpeedMessageTimestamp)<1000){
            System.out.println("isThereNewError " );
            if(!currentlyError){
                return false;
            }
            else {
                currentlyError = false;
                return true;
            }
//            currentlyError = true;
//            return currentlyError;

        }
        else{
            currentlyError = true;
            return false;
        }
    }
}

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.HashMap;

/**
 * Created by Fredrik on 2015-07-06.
 */
public class ErrorCallBack implements MqttCallback{
    private MqttClient client;
    private HashMap<String,SpeedAndFuelInfo> clientToInfo;


    public ErrorCallBack(MqttClient client){
        this.client = client;
        clientToInfo = new HashMap<>();
    }

    @Override
    public void connectionLost(Throwable throwable) {
        System.out.println("ErrorPredictonService lost connection!");
    }


    //nu timestampar vi här, vi borde skicka med timestampet, men då kanske vi måste ändra i databasen, vi kan göra det imorgon
    @Override
    public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {

//        System.out.println(mqttMessage.toString());
        String message = mqttMessage.toString();
        String[] messageParts =message.split(";");
        String carID = messageParts[0];
//        Long currentTime = System.currentTimeMillis();
        Long currentTime = Long.parseLong(messageParts[1]);
        if (topic.equals("telemetry/fuel")||topic.equals("telemetry/speed")) {
            if (topic.equals("telemetry/fuel")) {
                float fuelLevel = Float.parseFloat(messageParts[2]);
                if (!clientToInfo.containsKey(carID)) {
                    clientToInfo.put(carID, new SpeedAndFuelInfo());
                }
                clientToInfo.get(carID).setFuel(fuelLevel, currentTime);

            } else if (topic.equals("telemetry/speed")) {
                float speed = Float.parseFloat(messageParts[2]);
                if (!clientToInfo.containsKey(carID)) {
                    clientToInfo.put(carID, new SpeedAndFuelInfo());
                }
                clientToInfo.get(carID).setSpeed(speed, currentTime);
                //boolean error = clientToInfo.get(carID).isThereNewError();
            }
            boolean error = clientToInfo.get(carID).isThereNewError();
            System.out.println("error: "+error);
            if (error){
                new PublishThread(client,carID+"/message").start();
            }
        }

        else {
            System.out.println("unkown topic \""+topic+"\"");
        }


//        float fuelLevel = Float.parseFloat(messageParts[1]);
//        if(fuelLevel>10){
//            fuelLevel=fuelLevel-10.0f;
//        }
//        else{
//            fuelLevel=0.0f;
//        }
//        String fuelLevelAsString = ""+fuelLevel;
//        System.out.println("'"+carID+"/distancePrediction"+"'");
//        System.out.println(fuelLevelAsString);
//        String topicToPublish = carID + "/distancePrediction";

        //client.publish(carID + "/distancePrediction", fuelLevelAsString.getBytes(),0, false);




      //  "d8:50:e6:71:c6:db/distancePrediction"

    }

    public void checkForError(String carID){

    }


    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

    }
}

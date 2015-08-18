import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;

/**
 * Created by Fredrik on 2015-07-06.
 */
public class PublishThread extends Thread {
    private MqttClient client;
    private String topic;

    public PublishThread(MqttClient client, String topic){
        this.client=client;
        this.topic=topic;

    }

    @Override
    public void run(){
        try {
            client.publish(topic,"message;messageText:You run at high speed and havbe a low battery level, perhaps you should slow down.".getBytes(),0,false);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
}

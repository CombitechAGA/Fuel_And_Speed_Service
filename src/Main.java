import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

/**
 * Created by Fredrik on 2015-07-06.
 */

//läs config
public class Main {
    public static void main(String[] args) {
        MqttClient client = null;
        MemoryPersistence persistence = new MemoryPersistence();
        try {
            client = new MqttClient("tcp://mqtt.phelicks.net:1883", "errorService", persistence);
            MqttConnectOptions options = new MqttConnectOptions();
            options.setUserName("cab");
            options.setPassword("sjuttongubbar".toCharArray());
            client.connect(options);
           // client.connect();
        } catch (MqttException e) {
            e.printStackTrace();
        }

        ErrorCallBack distanceCallBack = new ErrorCallBack(client);
        client.setCallback(distanceCallBack);


        try {
            client.subscribe("telemetry/fuel");
            client.subscribe("telemetry/speed");
        } catch (MqttException e) {
            e.printStackTrace();
        }

    }
}

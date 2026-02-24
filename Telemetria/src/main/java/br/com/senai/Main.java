package br.com.senai;

import org.eclipse.paho.client.mqttv3.*;

public class Main {
    public static void main(String[] args) {
        // C7: Identificador exclusivo para o cliente Java
        String broker = "tcp://broker.hivemq.com:1883";
        String clientId = "JavaClient_Senai_Data";
        String topic = "senai/telemetria/dados";

        try {
            MqttClient client = new MqttClient(broker, clientId);
            MqttConnectOptions options = new MqttConnectOptions();
            options.setCleanSession(true);

            client.setCallback(new MqttCallback() {
                public void connectionLost(Throwable cause) {
                    System.out.println("Conexão perdida!");
                }

                public void messageArrived(String topic, MqttMessage message) {
                    // C11: Validação da recepção dos dados conforme requisitos de sucesso
                    String dados = new String(message.getPayload());
                    System.out.println("Dados de Telemetria Coletados com Sucesso: [" + dados + "]");
                }

                public void deliveryComplete(IMqttDeliveryToken token) {}
            });

            System.out.println("Conectando ao broker...");
            client.connect(options);
            client.subscribe(topic);
            System.out.println("Aguardando dados da telemetria...");

        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
}
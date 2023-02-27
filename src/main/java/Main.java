import com.google.common.collect.ImmutableList;
import org.eclipse.milo.opcua.sdk.client.OpcUaClient;
import org.eclipse.milo.opcua.sdk.client.api.subscriptions.UaSubscription;
import org.eclipse.milo.opcua.sdk.client.nodes.UaNode;
import org.eclipse.milo.opcua.sdk.client.nodes.UaVariableNode;
import org.eclipse.milo.opcua.sdk.client.subscriptions.OpcUaSubscriptionManager;
import org.eclipse.milo.opcua.sdk.core.nodes.Node;
import org.eclipse.milo.opcua.stack.core.Identifiers;
import org.eclipse.milo.opcua.stack.core.types.builtin.DataValue;
import org.eclipse.milo.opcua.stack.core.types.builtin.NodeId;
import org.eclipse.milo.opcua.stack.core.types.enumerated.TimestampsToReturn;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception{

        //Здравствуйте. Работал с технологией OPC UA. Использовал Eclipse Milo для реализации клиента.
        // OPC UA Server Simulator от https://integrationobjects.com/ для сервера.

        //Получаем экземпляр клиента
        OpcUaClient opcUaClient = OpcUaClient.create("opc.tcp://desktop-qul7l7j:62640/IntegrationObjects/ServerSimulator");

        //Подключаемся к серверу
        opcUaClient.connect().get();

        //Останавливаю поток чтобы клиент успел подключиться к серверу
        Thread.sleep(3000);

        //Ищем все NodeId начиная с корневой папки
        List<? extends UaNode> listNodeId = opcUaClient.getAddressSpace().browseNodes(Identifiers.RootFolder);

        //Пытаюсь прочитать значения тэга(я так понял, что NodeId как раз содержит название нашего тэга и NameSpaceIndex чтобы мы точно обратились к нужному узлу),
        // но выпадает ошибка Bad_AttributeIdInvalid.
        // Прочитал в интернете, что ошибка со стороны сервера.
        for (UaNode nodeId : listNodeId) {
            nodeId.getNodeId();
            DataValue dataValue = opcUaClient.readValue(0, TimestampsToReturn.Both, nodeId.getNodeId()).get();
            System.out.println(dataValue);
        }

        //Подписку на событие не реализовал, так как так и несмог получит значение тэга из за ошибки Bad_AttributeIdInvalid.

    }
}

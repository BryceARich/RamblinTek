package actors;

import akka.actor.UntypedActor;
import utils.XbeeStream;

import java.util.Scanner;

/**
 * The broker between the WebSocket and the StockActor(s).  The UserActor holds the connection and sends serialized
 * JSON data to the client.
 */

public class XbeeActor extends UntypedActor {

    public XbeeActor() {
        onReceive("");
    }

    public void onReceive(Object message) {
    }

}

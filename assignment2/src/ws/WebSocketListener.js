import {EventEmitter} from "events"
import {Client} from "@stomp/stompjs"

class WebSocketListener extends EventEmitter{
    constructor(username, password) {
        super();
        this.client = new Client({
            brokerURL: "ws://localhost:8081/api/websocket",
            connectHeaders: {
                login: username,
                passcode: password
            },
            onConnect: () => {
                this.client.subscribe("/topic/events", message => {
                    const receivedMessage = JSON.parse(message.body);
                    this.emit(receivedMessage.eventType, receivedMessage)
                })
            }
        });
        this.client.activate();
    }
}

const WebSocketListenerInstance = new WebSocketListener("andreea", "aki");

export default WebSocketListenerInstance;
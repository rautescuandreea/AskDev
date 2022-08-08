import {EventEmitter} from "events";
import WebSocketListenerInstance from "../ws/WebSocketListener";

class UserModel extends EventEmitter{
    constructor() {
        super();
        this.state = {
            users: []
        };
        WebSocketListenerInstance.on("CLIENT_CREATED", event => {
            UserModelInstance.addUser(event.client);
            this.emit("event", this.state.users);
        })
    }

    addUsers(newUsers) {
        this.state.users = [
            ...newUsers
        ];
    }

    addUser(newUser) {
        this.state.users = [
            ...this.state.users,
            {
                ...newUser,
                questions: [],
                answers: []
            }
        ];
    }

    getUsers() {
        return this.state.users;
    }

    getUserByData(username, password) {
        for (let index = 0; index < this.getUsers().length; index++) {
            if (this.getUsers()[index].username === username && this.getUsers()[index].password === password) {
                return this.getUsers()[index].userId;
            }
        }
        return 0;
    }
}

// folosim Singleton Design Pattern
const UserModelInstance = new UserModel();

export default UserModelInstance;
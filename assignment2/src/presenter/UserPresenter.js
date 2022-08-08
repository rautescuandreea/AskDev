import UserModelInstance from "../model/UserModel";
import {EventEmitter} from "events";
import InvokerInstance from "../command/Invoker";
import AddUserCommand from "../command/AddUserCommand";
import restClientInstance from "../rest/RestClient";
import ModifyUserCommand from "../command/ModifyUserCommand";
import DeleteUserCommand from "../command/DeleteUserCommand";

class UserPresenter extends EventEmitter {
    constructor() {
        super();
        UserModelInstance.on("event", event => {
            this.emit("event", event)
        })
    }

    async addUser(newUser) {
        const user = await restClientInstance.postUser(newUser.username, newUser.password)
        InvokerInstance.invoke(new AddUserCommand(newUser))
        return user;
    }

    async modifyUser(newUser) {
        InvokerInstance.invoke(new ModifyUserCommand(newUser))
    }

    async deleteUser(newUser) {
        InvokerInstance.invoke(new DeleteUserCommand(newUser))
    }

    async getUserByIndex(index) {
        return restClientInstance.getUserById(index);
    }

   async getLoggedInUser() {
        return restClientInstance.getLoggedInUser();
   }

    async getUsers() {
        const users = await restClientInstance.getUsers();
        UserModelInstance.addUsers(users)
        return UserModelInstance.getUsers();
    }

    verifyUser(username, password) {
        restClientInstance.setRestClient(username, password)
    }
}

const UserPresenterInstance = new UserPresenter();

export default UserPresenterInstance;
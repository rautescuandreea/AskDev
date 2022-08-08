import UserModelInstance from "../model/UserModel";

class DeleteUserCommand {
    constructor(newUser) {
        this.newUser = newUser;
    }

    execute() {
        UserModelInstance.addUser(this.newUser);
    }
}

export default DeleteUserCommand;
import UserModelInstance from "../model/UserModel";

class AddUserCommand {
    constructor(newUser) {
        this.newUser = newUser;
    }

    execute() {
        UserModelInstance.addUser(this.newUser);
    }
}

export default AddUserCommand;
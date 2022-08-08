import UserModelInstance from "../model/UserModel";

class ModifyUserCommand {
    constructor(newUser) {
        this.newUser = newUser;
    }

    execute() {
        UserModelInstance.addUser(this.newUser);
    }
}

export default ModifyUserCommand;
import React, {useState} from "react";
import UserSignIn from "../presentational/UserSignIn";
import UserPresenterInstance from "../presenter/UserPresenter";

function SmartUserSignIn() {
    const [newUser, setNewUser] = useState({
        username: "",
        password: ""
    });

    const onChange = (property, newValue) => {
        setNewUser({
            ...newUser,
            [property]: newValue
        });
    };

    const onSignUpUser = () => {
        window.location.assign("/#/sign-up")
    }

    const onSignInUser = () => {
        UserPresenterInstance.verifyUser(newUser.username, newUser.password);
        window.location.assign("/#/user-profile");
    }

    return (
        <UserSignIn
            username={newUser.username}
            password={newUser.password}
            onChange={onChange}
            onSignInUser={onSignInUser}
            onSignUpUser={onSignUpUser}/>
    );
}

export default SmartUserSignIn;
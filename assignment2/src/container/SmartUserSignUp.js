import React, {useState} from "react";
import UserSignUp from "../presentational/UserSignUp";
import UserPresenterInstance from "../presenter/UserPresenter";

function SmartUserSignUp() {
    const [newUser, setNewUser] = useState({
        username: "",
        password: "",
        userId: 0,
        questions: [],
        answers: []
    });

    const onChange = (property, newValue) => {
        setNewUser({
            ...newUser,
            [property]: newValue
        });
    };

    const onCreate = async () => {
        await UserPresenterInstance.addUser(newUser);
        window.location.assign("/#")
    }

    return (
        <UserSignUp
            username={newUser.username}
            password={newUser.password}
            onCreate={onCreate}
            onChange={onChange}/>
    );
}

export default SmartUserSignUp;
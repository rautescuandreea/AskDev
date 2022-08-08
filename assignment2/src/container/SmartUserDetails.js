import React, {useEffect, useState} from "react";
import UserDetails from "../presentational/UserDetails";
import {useParams} from "react-router-dom";
import UserPresenterInstance from "../presenter/UserPresenter";

const SmartUserDetails = () => {
    const [user, setUser] = useState({
        username: "",
        password: "",
        questions: [],
        answers: []
    });
    const {index} = useParams();

    useEffect(() => {
        setUser(UserPresenterInstance.getUserByIndex(index))
    }, []);

    return (
        <UserDetails username={user.username}
                        password={user.password}
                        questions={user.questions}
                        answers={user.answers}/>
    );
}

export default SmartUserDetails;
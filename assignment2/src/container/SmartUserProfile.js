import React, {useEffect, useState} from "react";
import UserPresenterInstance from "../presenter/UserPresenter";
import AnswerPresenterInstance from "../presenter/AnswerPresenter";
import UserProfile from "../presentational/UserProfile";

const SmartUserProfile = () => {
    const [user, setUser] = useState({
        userId: 0,
        username: "",
        password: "",
        answers: [],
        questions: []
    });

    useEffect(() => {
        UserPresenterInstance.getLoggedInUser().then(foundUser => {
           setUser(foundUser)
        });
    }, []);

    const onEdit = (id) => {
        window.location.assign("/#/answer-edit/" + id)
    };

    const onDelete = async (id) => {
        console.log(user.answers[id].answerId + " ")
        await AnswerPresenterInstance.deleteAnswer(user.answers[id].answerId)
    };

    const onShowQuestionList = () => {
        window.location.assign("/#/question-list")
    }

    const onSignOut = () => {
        window.location.assign("/#")
    }

    return (
        <UserProfile username={user.username}
                     answers={user.answers}
                     onEdit={onEdit}
                     onDelete={onDelete}
                     onShowQuestionList={onShowQuestionList}
                     onSignOut={onSignOut}/>
    );
}

export default SmartUserProfile;
import React, {useEffect, useState} from "react";
import AnswerCreate from "../presentational/AnswerCreate";
import AnswerPresenterInstance from "../presenter/AnswerPresenter";
import UserPresenterInstance from "../presenter/UserPresenter";
import {useParams} from "react-router-dom";

function SmartAnswerCreate() {
    let [user, setUser] = useState({
        userId: 0,
        username: "",
        password: "",
        answers: [],
        questions: []
    });

    const [newAnswer, setNewAnswer] = useState({
        answerId: 0,
        body: "",
        author: "",
        upVote: 0,
        downVote: 0,
        userId: 0,
        questionId: 0
    });

    const {id} = useParams();

    useEffect(() => {
        UserPresenterInstance.getLoggedInUser().then(foundUser => {
            setUser(foundUser)
        });
    }, []);

    const onChange = (property, newValue) => {
        setNewAnswer({
            ...newAnswer,
            [property]: newValue,
            author: user.username,
            userId: user.userId,
            questionId: id
        });
    };

    const onCreate = async () => {
        console.log("trimit pe " + id)
        await AnswerPresenterInstance.postAnswer(newAnswer);
        window.location.assign("/#/question-details/" + id)
    };

    return (
        <AnswerCreate
            body={newAnswer.body}
            onCreate={onCreate}
            onChange={onChange}/>
    );
}

export default SmartAnswerCreate;
import React, {useEffect, useState} from "react";
import AnswerEdit from "../presentational/AnswerEdit";
import AnswerPresenterInstance from "../presenter/AnswerPresenter";
import UserPresenterInstance from "../presenter/UserPresenter";
import {useParams} from "react-router-dom";
import QuestionPresenterInstance from "../presenter/QuestionPresenter";

function SmartAnswerEdit() {
    const [newAnswer, setNewAnswer] = useState({
        answerId: 0,
        body: "",
        author: "",
        creationDate: (new Date()).toLocaleString(),
        upVote: 0,
        downVote: 0,
        userId: 0,
        questionId: 0
    });

    const {answerId} = useParams();

    const onChange = (property, newValue) => {
        setNewAnswer({
            ...AnswerPresenterInstance.getAnswerByIndex(answerId),
            [property]: newValue
        });
    };

    const onEdit = async () => {
        await AnswerPresenterInstance.putAnswer(newAnswer)
        window.location.assign("/#/user-profile")
    };

    return (
        <AnswerEdit
            body={newAnswer.body}
            onEdit={onEdit}
            onChange={onChange}/>
    );
}

export default SmartAnswerEdit;
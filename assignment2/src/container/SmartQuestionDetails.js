import React, {useEffect, useState} from "react";
import QuestionDetails from "../presentational/QuestionDetails";
import {useParams} from "react-router-dom";
import QuestionPresenterInstance from "../presenter/QuestionPresenter";
import AnswerPresenterInstance from "../presenter/AnswerPresenter";
import UserPresenterInstance from "../presenter/UserPresenter";
import VoteAnswerPresenterInstance from "../presenter/VoteAnswerPresenter";
import VoteQuestionPresenterInstance from "../presenter/VoteQuestionPresenter";

const SmartQuestionDetails = () => {
    const [question, setQuestion] = useState({
        questionId: 0,
        title: "",
        body: "",
        author: "",
        creationDate: "",
        vote: -1,
        tags: [],
        userId: 0,
        answers: []
    });

    let voteAnswer = {
        userId: -1,
        answerId: -1
    };

    let voteQuestion = {
        userId: -1,
        questionId: -1
    };

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

    const {index} = useParams();

    useEffect(() => {
        console.log("Cer " + index)
        UserPresenterInstance.getLoggedInUser().then(foundUser => {
            setUser(foundUser)
        });
        QuestionPresenterInstance.getQuestionByIndex(index).then(foundQuestion => {
           setQuestion(foundQuestion);
        });
    }, []);

    const onChange = (body, answerBody) => {
        setNewAnswer({
            ...newAnswer,
            body: answerBody,
            author: user.username,
            userId: user.userId,
            questionId: index
        });
    };

    const onCreate = async () => {
        console.log("trimit " + newAnswer.body + " " + newAnswer.author + " " + newAnswer.questionId + " " + newAnswer.userId)
        await AnswerPresenterInstance.postAnswer(newAnswer);
    };

    const onBack = () => {
        window.location.assign("/#/question-list")
    }

    const onUpVoteQuestion = () => {
        // voteQuestion.userId = UserPresenterInstance.getLoggedInUser().userId
        // voteQuestion.questionId = index
        // if (QuestionPresenterInstance.getQuestionByIndex(index).userId === UserPresenterInstance.getLoggedInUser().userId) {
        //     alert('You cannot vote your own question!')
        // } else if (VoteQuestionPresenterInstance.voteQuestionExists(voteQuestion) === true) {
        //     alert('You cannot vote this question twice!')
        // } else {
        //     VoteQuestionPresenterInstance.addVoteQuestion(voteQuestion)
        //     QuestionPresenterInstance.setUpVote(index)
        //     console.log(QuestionPresenterInstance.getVote(index) + " upvote")
        // }
    }

    const onDownVoteQuestion = () => {
        // voteQuestion.userId = UserPresenterInstance.getLoggedInUser().userId
        // voteQuestion.questionId = index
        // if (QuestionPresenterInstance.getQuestionByIndex(index).userId === UserPresenterInstance.getLoggedInUser().userId) {
        //     alert('You cannot vote your own question!')
        // } else if (VoteQuestionPresenterInstance.voteQuestionExists(voteQuestion) === true) {
        //     alert('You cannot vote this question twice!')
        // } else {
        //     VoteQuestionPresenterInstance.addVoteQuestion(voteQuestion)
        //     QuestionPresenterInstance.setDownVote(index)
        //     console.log(QuestionPresenterInstance.getVote(index) + " downvoted")
        // }
    }

    const onUpVoteAnswer = (i) => {

        // voteAnswer.userId = UserPresenterInstance.getLoggedInUser().userId
        // voteAnswer.answerId = i
        // console.log("ANSWEEEEEEEEEEER " + i + " " + AnswerPresenterInstance.getAnswerByIndex(i).userId + " this is vote answer")
        // console.log(AnswerPresenterInstance.getAnswerByIndex(i).body + " " + UserPresenterInstance.getLoggedInUser().userId)
        // if (AnswerPresenterInstance.getAnswerByIndex(i).userId === UserPresenterInstance.getLoggedInUser().userId) {
        //     alert('You cannot vote your own answer!')
        // } else if (VoteAnswerPresenterInstance.voteAnswerExists(voteAnswer) === true) {
        //     alert('You cannot vote this answer twice!')
        // } else {
        //     VoteAnswerPresenterInstance.addVoteAnswer(voteAnswer)
        //     console.log('add vote answer: ' + voteAnswer.answerId + " " + voteAnswer.userId)
        //     AnswerPresenterInstance.setUpVote(i)
        // }
    }

    const onDownVoteAnswer = (i) => {
        // voteAnswer.userId = UserPresenterInstance.getLoggedInUser().userId
        // voteAnswer.answerId = i
        // if (AnswerPresenterInstance.getAnswerByIndex(i).userId === UserPresenterInstance.getLoggedInUser().userId) {
        //     alert('You cannot vote your own answer!')
        // } else if (VoteAnswerPresenterInstance.voteAnswerExists(voteAnswer) === true) {
        //     alert('You cannot vote this answer twice!')
        // } else {
        //     VoteAnswerPresenterInstance.addVoteAnswer(voteAnswer)
        //     AnswerPresenterInstance.setDownVote(i)
        // }
    }

    return (
        <QuestionDetails title={question.title}
                     body={question.body}
                     author={question.author}
                     creationDate={question.creationDate}
                     vote={question.upVote - question.downVote}
                     tags={question.tags}
                     answers={question.answers}
                     answerBody={newAnswer.body}
                     onCreate={onCreate}
                     onChange={onChange}
                     onBack={onBack}
                     onUpVoteQuestion={onUpVoteQuestion}
                     onDownVoteQuestion={onDownVoteQuestion}
                     onUpVoteAnswer={onUpVoteAnswer}
                     onDownVoteAnswer={onDownVoteAnswer}/>
    );
}

export default SmartQuestionDetails;
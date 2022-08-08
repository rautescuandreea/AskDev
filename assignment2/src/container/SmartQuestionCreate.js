import React, {useEffect, useState} from "react";
import QuestionCreate from "../presentational/QuestionCreate";
import QuestionPresenterInstance from "../presenter/QuestionPresenter";
import UserPresenterInstance from "../presenter/UserPresenter";
import TagPresenterInstance from "../presenter/TagPresenter";

function SmartQuestionCreate() {
    let created = false;
    let [user, setUser] = useState({
        userId: 0,
        username: "",
        password: "",
        answers: [],
        questions: []
    });
    const [newQuestion, setNewQuestion] = useState({
        questionId: 0,
        title: "",
        body: "",
        author: "",
        creationDate: (new Date()).toLocaleString(),
        upVote: 0,
        downVote: 0,
        userId: 0,
        tags: []
    });

    const [newTag, setNewTag] = useState({
        tagId: 0,
        tagName: ""
    });

    const [tags, setTags] = useState([]);

    const [questions, setQuestions] = useState([]);

    const modifyQuestions = (newQuestions) => {
        setQuestions([
            ...newQuestions
        ])
    };

    const modifyTags = (newTags) => {
        setTags([
            ...newTags
        ])
    };

    useEffect(() => {
        UserPresenterInstance.getLoggedInUser().then(foundUser => {
            setUser(foundUser)
        });
        TagPresenterInstance.getTags().then(modifyTags);
        TagPresenterInstance.on("event", modifyTags);
        QuestionPresenterInstance.getQuestions().then(modifyQuestions);
        return () => {
            TagPresenterInstance.removeListener("event", modifyTags);
        }
    }, []);

    const onChange = (property, newValue) => {
        setNewQuestion({
            ...newQuestion,
            [property]: newValue,
            userId: user.userId,
            author: user.username
        });
        setNewTag({
            ...newTag,
            [property]: newValue
        });
    };

    const onCreate = async () => {
        if(created === false) {
            console.log("Primesc " + user.userId + " " + user.username + " si dau " + newQuestion.userId + " " + newQuestion.author)
            await QuestionPresenterInstance.addQuestion(newQuestion)
            created = true
        }

        window.location.assign("/#/question-list")
    };

    const onAddTag = async (index) => {
        if(created === false) {
            console.log("Primesc " + user.userId + " " + user.username + " si dau " + newQuestion.userId + " " + newQuestion.author)
            await QuestionPresenterInstance.addQuestion(newQuestion)
            created = true
        }
        await TagPresenterInstance.setTagQuestion(questions[0].questionId + 1, tags[index].tagId)
    };

    const onCreateTag = async () => {
        await TagPresenterInstance.addTag(newTag);
    };

    return (
        <QuestionCreate
            title={newQuestion.title}
            body={newQuestion.body}
            tags={tags}
            tagName={newTag.tagName}
            onCreate={onCreate}
            onCreateTag={onCreateTag}
            onChange={onChange}
            onAddTag={onAddTag}/>
    );
}

export default SmartQuestionCreate;
import React, {useEffect, useState} from "react";
import QuestionsList from "../presentational/QuestionList";
import QuestionPresenterInstance from "../presenter/QuestionPresenter";

const SmartQuestionsList = () => {
    const [questions, setQuestions] = useState([]);

    const [newSearch, setNewSearch] = useState({
        toSearch: "",
    });

    let [newQuestions, setNewQuestions] = useState([]);

    const modifyQuestions = (newQuestions) => {
        setQuestions([
                ...newQuestions
        ])
        setNewQuestions([
            ...newQuestions
        ])
    };

    useEffect(() => {
        QuestionPresenterInstance.getQuestions().then(modifyQuestions);
        QuestionPresenterInstance.on("event", modifyQuestions)

        return () => {
            QuestionPresenterInstance.removeListener("event", modifyQuestions)
        }
    }, []);

    const onViewDetails = (index) => {
        window.location.assign("/#/question-details/" + newQuestions[index].questionId)
    };

    const onQuestionCreate = () => {
        window.location.assign("/#/question-create")
    };

    const onChange = (property, newValue) => {
        setNewSearch({
            ...newSearch,
            [property]: newValue
        });
    }

    const onSearchTitle = () => {
         setNewQuestions([
            ...questions.filter(question => question.title.toLowerCase().includes(newSearch.toSearch.toLowerCase()))
         ])
    }

    const onSearchTag = () => {
        // newQuestions = questions.filter(question => question.title.toLowerCase().includes(newSearch.toSearch.toLowerCase()))
        let questionsAux = []
        for(let i = 0; i < questions.length; i++) {
            for(let j = 0; j < questions[i].tags.length; j++) {
                if(questions[i].tags[j].tag.toLowerCase().includes(newSearch.toSearch.toLowerCase())) {
                    questionsAux.push(questions[i]);
                    break;
                }
            }
        }
        setNewQuestions([
            ...questionsAux
        ])
    }

    const onGetProfile = () => {
        window.location.assign("/#/user-profile")
    }

    return (
        <QuestionsList
            questions={[].concat(newQuestions)}
            title={"Questions"}
            toSearch={newSearch.toSearch}
            onChange={onChange}
            onViewDetails={onViewDetails}
            onSearchTitle={onSearchTitle}
            onSearchTag={onSearchTag}
            onQuestionCreate={onQuestionCreate}
            onGetProfile={onGetProfile}/>
    );
};

export default SmartQuestionsList;
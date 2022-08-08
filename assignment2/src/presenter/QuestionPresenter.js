import QuestionModelInstance from "../model/QuestionModel";
import {EventEmitter} from "events";
import restClientInstance from "../rest/RestClient";

class QuestionPresenter extends EventEmitter {
    constructor() {
        super();
        QuestionModelInstance.on("event", event => {
            this.emit("event", event)
        })
    }

    async addQuestion(newQuestion) {
        //in assignment3 aici o sa adaugam call-urile catre backend
        const question = await restClientInstance.postQuestion(newQuestion.author, newQuestion.title, newQuestion.body, newQuestion.userId)
        QuestionModelInstance.addQuestion(newQuestion)
        return question;
    }

    setUpVote(index) {
        return QuestionModelInstance.setUpVote(index);
    }

    setDownVote(index) {
        return QuestionModelInstance.setDownVote(index);
    }

    async getQuestionByIndex(index) {
        return restClientInstance.getQuestionById(index);
    }

    async getTagByQuestionId(questionId) {
        return restClientInstance.getTagByQuestionId(questionId);
    }

    async getQuestions() {
        const questions = await restClientInstance.getQuestions();
        QuestionModelInstance.addQuestions(questions)
        return QuestionModelInstance.getQuestions();
    }

    getVote(index) {
        return QuestionModelInstance.getVote(index);
    }
}

const QuestionPresenterInstance = new QuestionPresenter();

export default QuestionPresenterInstance;
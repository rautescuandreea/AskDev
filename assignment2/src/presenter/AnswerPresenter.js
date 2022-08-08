import AnswerModelInstance from "../model/AnswerModel";
import restClientInstance from "../rest/RestClient";
import {EventEmitter} from "events";
import QuestionModelInstance from "../model/QuestionModel";

class AnswerPresenter extends EventEmitter {
    constructor() {
        super();
        AnswerModelInstance.on("event", event => {
            this.emit("event", event)
        })
    }

    async postAnswer(newAnswer) {
        //in assignment3 aici o sa adaugam call-urile catre backend
        const answer = await restClientInstance.putAnswer(newAnswer.author, newAnswer.body, newAnswer.questionId, newAnswer.userId);
        AnswerModelInstance.addAnswer(newAnswer)
        return answer;
    }

    async putAnswer(newAnswer) {
        const answer = await restClientInstance.putAnswer(newAnswer.answerId, newAnswer.author, newAnswer.body, newAnswer.questionId, newAnswer.userId, newAnswer.upVote, newAnswer.downVote);
        AnswerModelInstance.addAnswer(newAnswer)
        return answer;
    }

    async deleteAnswer(index) {
        await restClientInstance.deleteAnswerById(index);
    }

    setUpVote(index) {
        return AnswerModelInstance.setUpVote(index);
    }

    setDownVote(index) {
        return AnswerModelInstance.setDownVote(index)
    }

    async getAnswerByIndex(index) {
        return AnswerModelInstance.getAnswerByIndex(index);
    }

    async getAnswers() {
        const questions = await restClientInstance.getQuestions();
        QuestionModelInstance.addQuestions(questions)
        return QuestionModelInstance.getQuestions();
    }

    getAnswerByQuestionId(questionId) {
        return AnswerModelInstance.getAnswerByQuestionId(questionId)
    }

    getAnswerByUserId(userId) {
        return AnswerModelInstance.getAnswerByUserId(userId)
    }
}

const AnswerPresenterInstance = new AnswerPresenter();

export default AnswerPresenterInstance;
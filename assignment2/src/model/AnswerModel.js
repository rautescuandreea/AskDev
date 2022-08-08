import {EventEmitter} from "events";
import WebSocketListenerInstance from "../ws/WebSocketListener";
import QuestionModelInstance from "./QuestionModel";

class AnswerModel extends EventEmitter{
    constructor() {
        super();
        this.state = {
            answers: []
        };
        WebSocketListenerInstance.on("ANSWER_CREATED", event => {
            AnswerModelInstance.addAnswer(event.answer)
            this.emit("event", this.state.answers);
        })
    }

    addAnswers(newAnswers) {
        this.state.answers = [
            ...newAnswers
        ];
    }

    addAnswer(newAnswer) {
        this.state.answers = [
            ...this.state.answers,
            {
                ...newAnswer
            }
        ];
    }

    editAnswer(newAnswer, index) {
        this.state.answers[index] = newAnswer
    }

    deleteAnswer(index) {
        this.state.answers[index].splice(index, 1)
    }

    setUpVote(index) {
        this.state.answers[index].upVote += 1
    }

    setDownVote(index) {
        this.state.answers[index].downVote += 1
    }

    getAnswerByIndex(index) {
        return this.state.answers[index];
    }

    getAnswers() {
        return this.state.answers;
    }

    getAnswerByQuestionId(questionId) {
        return this.state.answers.filter(answer => answer.questionId === questionId);
    }

    getAnswerByUserId(userId) {
        if (this.state.answers.filter(answer => answer.userId === userId) === undefined) {
            return []
        }
        return this.state.answers.filter(answer => answer.userId === userId);
    }

    getVote(index) {
        return this.getAnswerByIndex(index).upVote - this.getAnswerByIndex(index).downVote;
    }
}

// folosim Singleton Design Pattern
const AnswerModelInstance = new AnswerModel();

export default AnswerModelInstance;
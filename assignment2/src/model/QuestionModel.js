import {EventEmitter} from "events";
import WebSocketListenerInstance from "../ws/WebSocketListener";

class QuestionModel extends EventEmitter {
    constructor() {
        super();
        this.state = {
            questions: []
        };
        WebSocketListenerInstance.on("QUESTION_CREATED", event => {
            QuestionModelInstance.addQuestion(event.question)
            this.emit("event", this.state.questions);
        })
    }

    addQuestions(newQuestions) {
        this.state.questions = [
            ...newQuestions
        ];
    }

    addQuestion(newQuestion) {
        this.state.questions = [
            ...this.state.questions,
            {
                ...newQuestion
            }
        ];
    }

    addAnswer(newAnswer) {
        this.getQuestionByIndex(newAnswer.questionId).answers.push(newAnswer)
    }

    setUpVote(index) {
        this.state.questions[index].upVote += 1;
    }

    setDownVote(index) {
        this.state.questions[index].downVote += 1;
    }

    getQuestionByIndex(index) {
        return this.state.questions[index];
    }

    getQuestions() {
        return this.state.questions;
    }

    getVote(index) {
        return this.getQuestionByIndex(index).upVote - this.getQuestionByIndex(index).downVote;
    }
}

// folosim Singleton Design Pattern
const QuestionModelInstance = new QuestionModel();

export default QuestionModelInstance;
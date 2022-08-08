class VoteQuestionModel {
    constructor() {
        this.state = {
            voteQuestions: []
        };
    }

    addVoteQuestions(newVoteQuestions) {
        this.state.voteQuestions = [
            ...newVoteQuestions
        ];
    }

    addVoteQuestion(newVoteQuestion) {
        this.state.voteQuestions = [
            ...this.state.voteQuestions,
            {
                ...newVoteQuestion,
            }
        ];
    }

    voteQuestionExists(voteQuestion) {
        for (let i = 0; i < this.state.voteQuestions.length; i++) {
            if(this.state.voteQuestions[i].userId === voteQuestion.userId && this.state.voteQuestions[i].questionId === voteQuestion.questionId) {
                return true;
            }
        }
        return false;
        //return this.state.voteQuestions.includes(voteQuestion)
    }

    getVoteQuestionByIndex(index) {
        return this.state.voteQuestions[index];
    }

    getVoteQuestions() {
        return this.state.voteQuestions;
    }
}

// folosim Singleton Design Pattern
const VoteQuestionModelInstance = new VoteQuestionModel();

export default VoteQuestionModelInstance;
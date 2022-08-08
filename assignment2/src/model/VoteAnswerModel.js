class VoteAnswerModel {
    constructor() {
        this.state = {
            voteAnswers: []
        };
    }

    addVoteAnswers(newVoteAnswers) {
        this.state.voteAnswers = [
            ...newVoteAnswers
        ];
    }

    addVoteAnswer(newVoteAnswer) {
        console.log("Adaug " + newVoteAnswer.userId + " " + newVoteAnswer.answerId)
        this.state.voteAnswers = [
            ...this.state.voteAnswers,
            {
                ...newVoteAnswer,
            }
        ];
    }

    voteAnswerExists(voteAnswer) {
        console.log("Caut " + voteAnswer.userId + " " + voteAnswer.answerId)
        console.log("Zic " + this.state.voteAnswers.includes(voteAnswer) + " ")
        for (let i = 0; i < this.state.voteAnswers.length; i++) {
            if(this.state.voteAnswers[i].userId === voteAnswer.userId && this.state.voteAnswers[i].answerId === voteAnswer.answerId) {
                return true;
            }
        }
        return false;
        // return this.state.voteAnswers.includes(voteAnswer)
    }

    getVoteAnswerByIndex(index) {
        return this.state.voteAnswers[index];
    }

    getVoteAnswers() {
        return this.state.voteAnswers;
    }
}

// folosim Singleton Design Pattern
const VoteAnswerModelInstance = new VoteAnswerModel();

export default VoteAnswerModelInstance;
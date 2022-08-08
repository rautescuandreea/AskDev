import VoteQuestionModelInstance from "../model/VoteQuestionModel";

class VoteQuestionPresenter {
    addVoteQuestion(newVoteQuestion) {
        //in assignment3 aici o sa adaugam call-urile catre backend
        return VoteQuestionModelInstance.addVoteQuestion(newVoteQuestion);
    }

    voteQuestionExists(voteQuestion) {
        return VoteQuestionModelInstance.voteQuestionExists(voteQuestion)
    }

    getVoteQuestionByIndex(index) {
        return VoteQuestionModelInstance.getVoteQuestionByIndex(index);
    }

    getVoteQuestions() {
        return VoteQuestionModelInstance.getVoteQuestions();
    }

    // in caz ca apucam va arat si un sneak peek din ce vom face la assignment 3
    // async getVoteQuestions() {
    //     return new Promise((resolve, reject) => {
    //       VoteQuestionModelInstance.addVoteQuestions([{
    //               firstName: "Ioana",
    //               lastName: "Onofrei",
    //               grades: [10, 10, 9]
    //           }, {
    //               firstName: "Liana",
    //               lastName: "Timar",
    //               grades: [10, 9, 9]
    //           }]);
    //       resolve(VoteQuestionModelInstance.getVoteQuestions())
    //     })
    // }
}

const VoteQuestionPresenterInstance = new VoteQuestionPresenter();

export default VoteQuestionPresenterInstance;
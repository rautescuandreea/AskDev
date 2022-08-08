import VoteAnswerModelInstance from "../model/VoteAnswerModel";

class VoteAnswerPresenter {
    addVoteAnswer(newVoteAnswer) {
        //in assignment3 aici o sa adaugam call-urile catre backend
        return VoteAnswerModelInstance.addVoteAnswer(newVoteAnswer);
    }

    voteAnswerExists(voteAnswer) {
        return VoteAnswerModelInstance.voteAnswerExists(voteAnswer)
    }

    getVoteAnswerByIndex(index) {
        return VoteAnswerModelInstance.getVoteAnswerByIndex(index);
    }

    getVoteAnswers() {
        return VoteAnswerModelInstance.getVoteAnswers();
    }

    // in caz ca apucam va arat si un sneak peek din ce vom face la assignment 3
    // async getVoteAnswers() {
    //     return new Promise((resolve, reject) => {
    //       VoteAnswerModelInstance.addVoteAnswers([{
    //               firstName: "Ioana",
    //               lastName: "Onofrei",
    //               grades: [10, 10, 9]
    //           }, {
    //               firstName: "Liana",
    //               lastName: "Timar",
    //               grades: [10, 9, 9]
    //           }]);
    //       resolve(VoteAnswerModelInstance.getVoteAnswers())
    //     })
    // }
}

const VoteAnswerPresenterInstance = new VoteAnswerPresenter();

export default VoteAnswerPresenterInstance;
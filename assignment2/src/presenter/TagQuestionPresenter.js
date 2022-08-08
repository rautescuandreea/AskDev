import TagQuestionModelInstance from "../model/TagQuestionModel";

class TagQuestionPresenter {
    addTagQuestion(newTagQuestion) {
        //in assignment3 aici o sa adaugam call-urile catre backend
        return TagQuestionModelInstance.addTagQuestion(newTagQuestion);
    }

    getTagQuestionByIndex(index) {
        return TagQuestionModelInstance.getTagQuestionByIndex(index);
    }

    getTagQuestions() {
        return TagQuestionModelInstance.getTagQuestions();
    }

    getTagsQuestionByQuestion(questionId) {
        return TagQuestionModelInstance.getTagsQuestionByQuestion(questionId);
    }

    getTagsQuestionByTags(tagId) {
        return TagQuestionModelInstance.getTagsQuestionByTags(tagId);
    }

    // in caz ca apucam va arat si un sneak peek din ce vom face la assignment 3
    // async getTagQuestions() {
    //     return new Promise((resolve, reject) => {
    //       TagQuestionModelInstance.addTagQuestions([{
    //               firstName: "Ioana",
    //               lastName: "Onofrei",
    //               grades: [10, 10, 9]
    //           }, {
    //               firstName: "Liana",
    //               lastName: "Timar",
    //               grades: [10, 9, 9]
    //           }]);
    //       resolve(TagQuestionModelInstance.getTagQuestions())
    //     })
    // }
}

const TagQuestionPresenterInstance = new TagQuestionPresenter();

export default TagQuestionPresenterInstance;
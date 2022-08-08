class TagQuestionModel {
    constructor() {
        this.state = {
            tagQuestions: []
        };
    }

    addTagQuestions(newTagQuestions) {
        this.state.tagQuestions = [
            ...newTagQuestions
        ];
    }

    addTagQuestion(newTagQuestion) {
        this.state.tagQuestions = [
            ...this.state.tagQuestions,
            {
                ...newTagQuestion,
            }
        ];
    }

    getTagQuestionByIndex(index) {
        return this.state.tagQuestions[index];
    }

    getTagQuestions() {
        return this.state.tagQuestions;
    }

    getTagsQuestionByQuestion(questionId) {
        let tagIds = []
        console.log("Sunt in TagQuestion, size-ul e ", this.getTagQuestions().length)
        console.log("Caut questionID = " + questionId)
        for (let index = 0; index < this.getTagQuestions().length; index++) {
            console.log("Parcurg questionID = " + this.getTagQuestions()[index].questionId + " si index = " + index)
            if (this.getTagQuestions()[index].questionId === questionId) {
                console.log("Adaug tagId = " + this.getTagQuestions()[index].tagId)
                tagIds.push(this.getTagQuestions()[index].tagId)
            }
            else {
                console.log("Cica questionId = " + questionId + " si tag.questionId = " + this.getTagQuestions()[index].questionId + " nu is egale")
                console.log(typeof questionId)
                console.log(typeof this.getTagQuestions()[index].questionId)
            }
        }
        return tagIds;
    }

    getTagsQuestionByTags(tagId) {
        let questionIds = []
        for (let index = 0; index < this.getTagQuestions().length; index++) {
            if (this.getTagQuestions()[index].tagId === tagId) {
                questionIds.push(index)
            }
        }
        return questionIds;
    }
}

// folosim Singleton Design Pattern
const TagQuestionModelInstance = new TagQuestionModel();

export default TagQuestionModelInstance;
import TagModelInstance from "../model/TagModel";
import restClientInstance from "../rest/RestClient";
import {EventEmitter} from "events";

class TagPresenter extends EventEmitter{
    constructor() {
        super();
        TagModelInstance.on("event", event => {
            this.emit("event", event)
        })
    }

    async addTag(newTag) {
        const tag = await restClientInstance.postTag(newTag.tagName)
        //in assignment3 aici o sa adaugam call-urile catre backend
        return TagModelInstance.addTag(tag);
    }

    async getTagByIndex(index) {
        return restClientInstance.getTagById(index);
    }

    async getQuestionByTagId(tagId) {
        return restClientInstance.getQuestionByTagId(tagId)
    }

    async getTags() {
        return restClientInstance.getTags();
    }

    async setTagQuestion(questionId, tagId) {
        await restClientInstance.setTagQuestion(questionId, tagId)
    }
}

const TagPresenterInstance = new TagPresenter();

export default TagPresenterInstance;
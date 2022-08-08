import WebSocketListenerInstance from "../ws/WebSocketListener";
import {EventEmitter} from "events";

class TagModel extends EventEmitter{
    constructor() {
        super();
        this.state = {
            tags: []
        };
        WebSocketListenerInstance.on("TAG_CREATED", event => {
            TagModelInstance.addTag(event.tag);
            this.emit("event", this.state.users);
        })
    }

    addTags(newTags) {
        this.state.tags = [
            ...newTags
        ];
    }

    addTag(newTag) {
        this.state.tags = [
            ...this.state.tags,
            {
                ...newTag,
            }
        ];
    }

    getTagByIndex(index) {
        return this.state.tags[index];
    }

    getTags() {
        return this.state.tags;
    }
}

// folosim Singleton Design Pattern
const TagModelInstance = new TagModel();

export default TagModelInstance;
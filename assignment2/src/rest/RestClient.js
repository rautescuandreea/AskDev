const BASE_URL = "http://localhost:8081";

class RestClient {
    constructor(username, password) {
        this.authorization = "Basic " + btoa(username + ":" + password);
    }

    setRestClient(username, password) {
        this.authorization = "Basic " + btoa(username + ":" + password);
    }

    //user
    getUsers = () => {
        return fetch(`${BASE_URL}/users`, {
            method: "GET",
            headers: {
                "Authorization": this.authorization,
                "Content-Type": "application/json"
            }
        }).then(response => response.json());
    }

    getUserById = (id) => {
        return fetch(`${BASE_URL}/users/${id}`, {
            method: "GET",
            headers: {
                "Authorization": this.authorization,
                "Content-Type": "application/json"
            }
        }).then(response => response.json());
    }

    getLoggedInUser = () => {
        return fetch(`${BASE_URL}/users/me`, {
            method: "GET",
            headers: {
                "Authorization": this.authorization,
                "Content-Type": "application/json"
            }
        }).then(response => response.json());
    }

    postUser = (username, password) => {
        console.log("primesc " + username + " " + password)
        return fetch(`${BASE_URL}/users`, {
            method: "POST",
            body: JSON.stringify({
                username: username,
                password: password
            }),
            headers: {
                "Authorization": this.authorization,
                "Content-Type": "application/json"
            }
        }).then(response => response.json());
    }

    //question
    getQuestions = () => {
        return fetch(`${BASE_URL}/questions`, {
            method: "GET",
            headers: {
                "Authorization": this.authorization,
                "Content-Type": "application/json"
            }
        }).then(response => response.json());
    }

    getQuestionById = (id) => {
        return fetch(`${BASE_URL}/questions/${id}`, {
            method: "GET",
            headers: {
                "Authorization": this.authorization,
                "Content-Type": "application/json"
            }
        }).then(response => response.json());
    }

    getQuestionByTagId = (id) => {
        return fetch(`${BASE_URL}/questions/byTagId/${id}`, {
            method: "GET",
            headers: {
                "Authorization": this.authorization,
                "Content-Type": "application/json"
            }
        }).then(response => response.json());
    }

    postQuestion = (author, title, body, userId) => {
        return fetch(`${BASE_URL}/questions`, {
            method: "POST",
            body: JSON.stringify({
                author: author,
                title: title,
                body: body,
                userId: userId
            }),
            headers: {
                "Authorization": this.authorization,
                "Content-Type": "application/json"
            }
        }).then(response => response.json());
    }

    putQuestion = (questionId, author, title, body, creationDate, upVote, downVote, userId) => {
        console.log(body + "")
        return fetch(`${BASE_URL}/questions`, {
            method: "PUT",
            body: JSON.stringify({
                questionId: questionId,
                author: author,
                title: title,
                body: body,
                creationDate: creationDate,
                upVote: upVote,
                downVote: downVote,
                userId: userId
            }),
            headers: {
                "Authorization": this.authorization,
                "Content-Type": "application/json"
            }
        }).then(response => response.json());
    }

    //tags
    getTags = () => {
        return fetch(`${BASE_URL}/tags`, {
            method: "GET",
            headers: {
                "Authorization": this.authorization,
                "Content-Type": "application/json"
            }
        }).then(response => response.json());
    }

    getTagById = (id) => {
        return fetch(`${BASE_URL}/tags/${id}`, {
            method: "GET",
            headers: {
                "Authorization": this.authorization,
                "Content-Type": "application/json"
            }
        }).then(response => response.json());
    }

    getTagByQuestionId = (questionId) => {
        return fetch(`${BASE_URL}/tags/byQuestionId/${questionId}`, {
            method: "GET",
            headers: {
                "Authorization": this.authorization,
                "Content-Type": "application/json"
            }
        }).then(response => response.json());
    }

    postTag = (tag) => {
        return fetch(`${BASE_URL}/tags`, {
            method: "POST",
            body: JSON.stringify({
                tag: tag
            }),
            headers: {
                "Authorization": this.authorization,
                "Content-Type": "application/json"
            }
        }).then(response => response.json());
    }

    setTagQuestion = (questionId, tagId) => {
        return fetch(`${BASE_URL}/tags/${questionId}/${tagId}`, {
            method: "POST",
            headers: {
                "Authorization": this.authorization,
                "Content-Type": "application/json"
            }
        }).then(response => response.json());
    }

    //answer
    getAnswers = () => {
        return fetch(`${BASE_URL}/answers`, {
            method: "GET",
            headers: {
                "Authorization": this.authorization,
                "Content-Type": "application/json"
            }
        }).then(response => response.json());
    }

    getAnswerById = (id) => {
        return fetch(`${BASE_URL}/answers/${id}`, {
            method: "GET",
            headers: {
                "Authorization": this.authorization,
                "Content-Type": "application/json"
            }
        }).then(response => response.json());
    }

    getAnswerByQuestionId = (questionId) => {
        return fetch(`${BASE_URL}/answers/byQuestionId/${questionId}`, {
            method: "GET",
            headers: {
                "Authorization": this.authorization,
                "Content-Type": "application/json"
            }
        }).then(response => response.json());
    }

    postAnswer = (author, body, questionId, userId) => {
        return fetch(`${BASE_URL}/answers`, {
            method: "POST",
            body: JSON.stringify({
                author: author,
                body: body,
                questionId: questionId,
                userId: userId
            }),
            headers: {
                "Authorization": this.authorization,
                "Content-Type": "application/json"
            }
        }).then(response => response.json());
    }

    putAnswer = (answerId, author, body, questionId, userId, upVote, downVote) => {
        return fetch(`${BASE_URL}/answers`, {
            method: "PUT",
            body: JSON.stringify({
                answerId: answerId,
                author: author,
                body: body,
                questionId: questionId,
                userId: userId,
                upVote: upVote,
                downVote: downVote
            }),
            headers: {
                "Authorization": this.authorization,
                "Content-Type": "application/json"
            }
        }).then(response => response.json());
    }

    deleteAnswerById = (id) => {
        return fetch(`${BASE_URL}/answers/${id}`, {
            method: "DELETE",
            headers: {
                "Authorization": this.authorization,
                "Content-Type": "application/json"
            }
        }).then(response => response.json());
    }

}
let restClientInstance = new RestClient("andreea", "aki");

export default restClientInstance;


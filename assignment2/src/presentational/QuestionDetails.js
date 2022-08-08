import React from "react";

const QuestionDetails = ({title, body, author, creationDate, vote, answers, tags, onUpVoteQuestion, onDownVoteQuestion, onUpVoteAnswer, onDownVoteAnswer, onBack, answerBody, onCreate, onChange}) => (
    <div>
        <h2>Question</h2>
        <label>Title: </label>
        <span>{title}</span>
        <br/>
        <label>Body: </label>
        <span>{body}</span>
        <br/>
        <label>Author: </label>
        <span>{author}</span>
        <br/>
        <label>Creation Date: </label>
        <span>{creationDate}</span>
        <br/>
        <label>Vote: </label>
        <span>{vote}</span>
        <br/>
        <button onClick={onUpVoteQuestion}>Up Vote</button>
        <button onClick={onDownVoteQuestion}>Down Vote</button>
        <br/>
        <br/>
        {<ul>
            {
                tags.map(tag => (
                    <li>
                        {tag.tag}
                    </li>
                ))
            }
        </ul>}
        <p> </p>
            <div>
                    <h2>Create Answer</h2>
                    <div>
                            <label>Body: </label>
                            <input value={answerBody}
                                   onChange={ e => onChange("answerBody", e.target.value)}/>
                            <br/>
                            <button onClick={onCreate}>Add Answer!</button>
                    </div>
            </div>
        <p> </p>
        <table border="1">
            <thead>
            <tr>
                    <th>Body</th>
                    <th>Author</th>
                    <th>Creation Date</th>
                    <th>Vote</th>
                    <th></th>
                    <th></th>
            </tr>
            </thead>
            <tbody>
            {
                    answers.map((answer, i) => (
                        <tr key={i}>
                                <td>{answer.body}</td>
                                <td>{answer.author}</td>
                                <td>{answer.creationDate}</td>
                                <td>{answer.upVote - answer.downVote}</td>
                                <td><button onClick={() => onUpVoteAnswer(i)}>Up Vote</button></td>
                                <td><button onClick={() => onDownVoteAnswer(i)}>Down Vote</button></td>
                        </tr>
                    ))
            }
            </tbody>
        </table>
        <p> </p>
        <button onClick={onBack}>Back to the question list</button>
    </div>
);

export default QuestionDetails;
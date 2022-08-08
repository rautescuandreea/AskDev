import React from "react"

const QuestionsList = ({questions, toSearch, title = "Questions", onViewDetails, onQuestionCreate, onChange, onSearchTitle, onSearchTag, onGetProfile}) => (
    <div>
        <br/>
        <label>Search question: </label>
        <input value={toSearch} onChange={ e => onChange("toSearch", e.target.value)}/>
        <p>{"   "}</p>
        <button onClick={onSearchTitle}>Search by title</button>
        <button onClick={onSearchTag}>Search by tag</button>
        <br/>
        <h2>{title || "Questions"}</h2>
        <table border="1">
            <thead>
            <tr>
                <th>Title</th>
                <th>Author</th>
                <th>Creation Date</th>
                <th>Tags</th>
            </tr>
            </thead>
            <tbody>
            {
                questions.map((question, index) => (
                    <tr key={index}>
                        <td>{question.title}</td>
                        <td>{question.author}</td>
                        <td>{question.creationDate}</td>
                        <td>
                            <ul>
                                {
                                    question.tags.map((tag) => (
                                        <li>
                                            {tag.tag}
                                        </li>
                                    ))
                                }
                            </ul>
                        </td>
                        <td><button onClick={() => onViewDetails(index)}>View Details</button></td>
                    </tr>
                ))
            }
            </tbody>
        </table>
        <button onClick={onQuestionCreate}>Add question</button>
        <button onClick={onGetProfile}>View your profile</button>
    </div>
);

export default QuestionsList;
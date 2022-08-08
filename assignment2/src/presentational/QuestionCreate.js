import React from "react"

const QuestionCreate = ({ title, body, tags, tagName, onCreate, onChange, onAddTag, onCreateTag}) => (
    <div>
        <h2>Create Question</h2>
        <div>
            <label>Title: </label>
            <input value={title}
                   onChange={ e => onChange("title", e.target.value)}/>
            <br/>
            <label>Body: </label>
            <input value={body}
                   onChange={ e => onChange("body", e.target.value)}/>
            <br/>
            <h4>{"Select Tags"}</h4>
            <table border="1">
                <thead>
                <tr>
                    <th>Tag</th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                {
                    tags.map((tag, index) => (
                        <tr key={index}>
                            <td>{tag.tag}</td>
                            <td><button onClick={() => onAddTag(index)}>Add</button></td>
                        </tr>
                    ))
                }
                </tbody>
            </table>

            <p>{"Or Add Tag"}</p>
            <label>Tag Name: </label>
            <input value={tagName} onChange={ e => onChange("tagName", e.target.value)}/>
            <p>{"   "}</p>
            <button onClick={onCreateTag}>Add Tag To Our List</button>
            <br/>
            <br/>
            <button onClick={onCreate}>Create question!</button>
            {/*<button className={"btn btn-primary"} onClick={onCreate}>Create!</button>*/}
        </div>
    </div>
);

export default QuestionCreate;
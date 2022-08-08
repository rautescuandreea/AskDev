import React from "react"

const AnswerCreate = ({ body, onCreate, onChange}) => (
    <div>
        <h2>Create Answer</h2>
        <div>
            <label>Body: </label>
            <input value={body}
                   onChange={ e => onChange("body", e.target.value)}/>
            <br/>
            <button onClick={onCreate}>Create!</button>
        </div>
    </div>
);

export default AnswerCreate;
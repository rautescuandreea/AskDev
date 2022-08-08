import React from "react"

const AnswerEdit = ({ body, onEdit, onChange}) => (
    <div>
        <h2>Edit Answer</h2>
        <div>
            <label>Body: </label>
            <input value={body}
                   onChange={ e => onChange("body", e.target.value)}/>
            <br/>
            <button onClick={onEdit}>Edit!</button>
        </div>
    </div>
);

export default AnswerEdit;
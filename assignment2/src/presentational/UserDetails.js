import React from "react";

const UserDetails = ({username, password, questions, answers}) => (
    <div>
        <h2>User</h2>
        <label>Username: </label>
        <span>{username}</span>
        <br/>
        <label>Password: </label>
        <span>{password}</span>
        <br/>
        {<ul>
            {
                questions.map((question, index) => (
                    <li key={index}>
                        {question}
                    </li>
                ))
            }
        </ul>}
        <br/>
        {<ul>
            {
                answers.map((answer, index) => (
                 <li key={index}>
                     {answer}
                 </li>
                 ))
            }
        </ul>}
        </div>
);

export default UserDetails;
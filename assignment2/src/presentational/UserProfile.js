import React from "react";

const UserProfile = ({username, answers, onEdit, onDelete, onShowQuestionList, onSignOut}) => (
    <div>
        <h2>Profile</h2>
        <label>Username: </label>
        <span>{username}</span>
        <br/>
            <table border="1">
                    <thead>
                    <tr>
                            <th>Body</th>
                            <th></th>
                            <th></th>
                    </tr>
                    </thead>
                    <tbody>
                    {
                            answers.map((answer, index) => (
                                <tr key={index}>
                                        <td>{answer.body}</td>
                                        <td><button onClick={() => onEdit(index)}>Edit</button></td>
                                        <td><button onClick={() => onDelete(index)}>Delete</button></td>
                                </tr>
                            ))
                    }
                    </tbody>
            </table>
            <button onClick={onShowQuestionList}>Show Question List</button>
        <button onClick={onSignOut}>Sign out</button>
    </div>
);

export default UserProfile;
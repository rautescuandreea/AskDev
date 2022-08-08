import React from "react"

const UsersList = ({users, title = "Users", onViewDetails}) => (
    <div>
        <h2>{title || "Users"}</h2>
        <table border="1">
            <thead>
            <tr>
                <th>Username</th>
                <th>Password</th>
                <th></th>
            </tr>
            </thead>
            <tbody>
            {
                users.map((user, index) => (
                    <tr key={index}>
                        <td>{user.username}</td>
                        <td>{user.password}</td>
                        <td><button onClick={() => onViewDetails(index)}>View Details</button></td>
                    </tr>
                ))
            }
            </tbody>
        </table>
    </div>
);

export default UsersList;
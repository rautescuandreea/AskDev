import React from "react"
import {Button} from "bootstrap";

const UserSignUp = ({ username, password, onCreate, onChange}) => (
    <div>
        <h2>Sign Up</h2>
        <div>
            <label>Username: </label>
            <input value={username}
                   onChange={ e => onChange("username", e.target.value)}/>
            <br/>
            <label>Password: </label>
            <input value={password}
                   onChange={ e => onChange("password", e.target.value)}/>
            <br/>
            <button onClick={onCreate}>Sign up!</button>
            {/*<button className={"btn btn-primary"} onClick={onCreate}>Create!</button>*/}
        </div>
    </div>
);

export default UserSignUp;
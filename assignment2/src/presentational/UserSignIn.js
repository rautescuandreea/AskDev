import React from "react"
import {Button} from "bootstrap";

const UserSignIn = ({ username, password, onChange, onSignInUser, onSignUpUser}) => (
    <div>
        <h2>Sign In</h2>
        <div>
            <label>Username: </label>
            <input value={username}
                   onChange={ e => onChange("username", e.target.value)}/>
            <br/>
            <label>Password: </label>
            <input value={password}
                   onChange={ e => onChange("password", e.target.value)}/>
            <br/>
            <button onClick={onSignInUser}>Sign In!</button>
            <p>Or</p>
            <button onClick={onSignUpUser}>Sign Up!</button>
            {/*<button className={"btn btn-primary"} onClick={onCreate}>Create!</button>*/}
        </div>
    </div>
);

export default UserSignIn;
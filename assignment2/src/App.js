import React from "react";
import './App.css';
import {HashRouter, Routes, Route} from "react-router-dom";
import SmartUserSignUp from "./container/SmartUserSignUp";
import SmartUserSignIn from "./container/SmartUserSignIn";
import SmartUserList from "./container/SmartUserList";
import SmartUserDetails from "./container/SmartUserDetails";
import SmartQuestionCreate from "./container/SmartQuestionCreate";
import SmartQuestionList from "./container/SmartQuestionList";
import SmartQuestionDetails from "./container/SmartQuestionDetails";
import SmartAnswerCreate from "./container/SmartAnswerCreate";
import SmartUserProfile from "./container/SmartUserProfile";
import SmartAnswerEdit from "./container/SmartAnswerEdit";

function App() {
    return <div className="App">
        <HashRouter>
            <Routes>
                <Route exact={true} element={<SmartUserSignIn/>} path="/" />
                <Route exact={true} element={<SmartUserSignUp/>} path="/sign-up" />
                <Route exact={true} element={<SmartUserList/>} path="/user-list" />
                <Route exact={true} element={<SmartUserDetails/>} path="/user-details/:index" />
                <Route exact={true} element={<SmartQuestionCreate/>} path="/question-create" />
                <Route exact={true} element={<SmartQuestionList/>} path="/question-list" />
                <Route exact={true} element={<SmartQuestionDetails/>} path="/question-details/:index" />
                <Route exact={true} element={<SmartAnswerCreate/>} path="/answer-create/:questionId" />
                <Route exact={true} element={<SmartAnswerEdit/>} path="/answer-edit/:answerId" />
                <Route exact={true} element={<SmartUserProfile/>} path="/user-profile" />
            </Routes>
        </HashRouter>
    </div>
}

export default App;

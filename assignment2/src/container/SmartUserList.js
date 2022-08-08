import React, {useEffect, useState} from "react";
import UsersList from "../presentational/UserList";
import UserPresenterInstance from "../presenter/UserPresenter";

const SmartUsersList = () => {
    const [users, setUsers] = useState([]);

    const modifyUsers = (newUsers) => {
        setUsers([
            ...newUsers
        ])
    };
    // detecteaza schimbari
    // similar cu componentDidMount/componentDidUpdate din React
    // sau din Angular cu ngOnInit + ngOnChanges
    useEffect(() => {
        UserPresenterInstance.getUsers().then(modifyUsers);
        UserPresenterInstance.on("event", modifyUsers);

        return () => {
            UserPresenterInstance.removeListener("event", modifyUsers);
        }
    }, []);

    const onViewDetails = (index) => {
        window.location.assign("/#/user-details/" + index)
    };

    return (
        <UsersList
            users={users}
            title={"Users"}
            onViewDetails={onViewDetails}/>
    );
};

export default SmartUsersList;
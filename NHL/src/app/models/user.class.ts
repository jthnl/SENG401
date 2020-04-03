export class User {
    constructor(
        public id?: string,
        public username?: string,
        public token?: string,
        public password?: string,
        public firstName?: string,
        public lastName?: string,
        public email?: string,
        public joined?: string,

    ) {
    }



    createUser(username, password, firstName, lastName, email, joined?) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.joined = joined;
    }

    getUser(userId, password, fname, lname, email, joined) {
        this.id = userId;
        this.password = password;
        this.firstName = fname;
        this.lastName = lname;
        this.email = email;
        this.joined = joined;
    }
}

// export class User {
//     id: number;
//     username: string;
//     password: string;
//     firstName: string;
//     lastName: string;
//     email: string;
// }
// }

export class User {
    constructor(
        public id?: number,
        public username?: string,
        public token?: string,
        public password?: string,
        public firstName?: string,
        public lastName?: string,
        public email?: string,
    ) {
    }

    createUser(username, password, firstName, lastName, email) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
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

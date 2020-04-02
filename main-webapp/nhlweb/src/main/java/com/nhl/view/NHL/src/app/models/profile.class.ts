export class Profile {
    constructor(
        private userId: string,
        private username: string,
        private password: string,
        private fname: string,
        private lname: string,
        private email: string,
        private joined: string) {}

    getUserID()   { return this.userId;   }
    getUsername() { return this.username; }
    getPassword() { return this.password; }
    getFname()    { return this.fname;    }
    getLname()    { return this.lname;    }
    getEmail()    { return this.email;    }
    getJoined()   { return this.joined;   }
}

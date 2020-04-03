// import { Teams } from '../models/teams';

export class Notification {
    constructor(
        public time: string,
        public user_id: string,
        public forum_id: string,
        public post_id: string,
        public seenFlag: string,
        public message: string) {
            this.message = message.substring(0, 22);
            // this.message += Teams.
        }
}


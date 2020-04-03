export class Notification {
    constructor(
        private time: string,
        private user_id: string,
        private forum_id: string,
        private post_id: string,
        private seenFlag: string,
        private message: string) { }
}


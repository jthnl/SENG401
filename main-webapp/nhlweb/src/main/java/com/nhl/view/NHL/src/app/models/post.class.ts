export class Post {
    constructor(
        private forum_id: string,
        private author_id: string,
        private title: string,
        private content: string) {}

    getForumId() {
        return this.forum_id;
    }
}

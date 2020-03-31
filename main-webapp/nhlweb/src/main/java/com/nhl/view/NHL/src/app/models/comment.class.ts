export class Comment {
    constructor(
        private postId: string,
        private content: string,
        private id?: string,
        private upvotes?: number,
        private downvotes?: number) {}

        getPostId() {
            return this.postId;
        }

        getContent() {
            return this.content;
        }

}


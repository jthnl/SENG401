export class Comment {
    constructor(
        private id?: string,
        private postId?: string,
        private content?: string,
        private upvotes?: number,
        private downvotes?: number) {}

        setPostId(postId: string) {
            this.postId = postId;
        }

        setContent(content: string) {
            this.content = content;
        }

        upvote() {
            this.upvotes++;
        }

        downvote() {
            this.downvotes++;
        }

        getId() {
            return this.id;
        }
        getPostId() {
            return this.postId;
        }

        getContent() {
            return this.content;
        }
}


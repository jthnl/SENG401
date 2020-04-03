export class Comment {
    constructor(
        public id?: string,
        public parentId?: string,
        public authorId?: string,
        public content?: string,
        public upvotes?: number,
        public downvotes?: number) {}

        // setPostId(authorId: string) {
        //     this.postId = postId;
        // }

        // setContent(content: string) {
        //     this.content = content;
        // }

        upvote() {
            this.upvotes++;
        }

        downvote() {
            this.downvotes++;
        }

        // getId() {
        //     return this.id;
        // }
        // getPostId() {
        //     return this.postId;
        // }

        getContent() {
            return this.content;
        }
}


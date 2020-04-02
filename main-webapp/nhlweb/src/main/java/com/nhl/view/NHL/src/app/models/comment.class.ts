export class Comment {
    constructor(
        private parentId?: string,
        private authorId?: string,
        private content?: string,
        private upvotes?: number,
        private downvotes?: number) {}

        setParentId(parentId: string) {
            this.parentId = parentId;
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
            return this.parentId;
        }
        getPostId() {
            return this.parentId;
        }

        getContent() {
            return this.content;
        }
}


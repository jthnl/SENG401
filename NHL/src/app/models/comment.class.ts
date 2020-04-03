export class Comment {
    constructor(
        public id?: string,
        public parentId?: string,
        public authorId?: string,
        public content?: string,
        public upvotes?: number,
        public downvotes?: number,
        public indentation?: number) {}


        upvote() {
            this.upvotes++;
        }

        downvote() {
            this.downvotes++;
        }


        getContent() {
            return this.content;
        }
}


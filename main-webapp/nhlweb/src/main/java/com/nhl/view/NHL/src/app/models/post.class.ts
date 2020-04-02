export class Post {
    constructor(
        public id?: string,
        public forum_id?: string,
        public author_id?: string,
        public title?: string,
        public content?: string,
        public timestamp?: string,
        public upvote?: string,
        public downvote?: string,
        public imgURL?: string) {}


    creatPost(forum_id, author_id, title, content) {
        this.forum_id = forum_id;
        this.author_id = author_id;
        this.title = title;
        this.content = content;
    }
}



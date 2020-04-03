import { teams } from '../models/teams';



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
        public imgURL?: string) {
            if (timestamp != null) {
                this.timestamp = timestamp.substring(11, 16);
                if (+timestamp.substring(11, 13) > 12) {
                    this.timestamp += " pm";
                } else {
                    this.timestamp += " am";
                }
            }


            if (forum_id != null) {
                this.imgURL = this.getImgUrl(forum_id);
            }
        }

    getImgUrl(forum_id) { 
        if (teams.filter(x => x.forumId === forum_id)[0] != null) {
            return teams.filter(x => x.forumId === forum_id)[0].imgURL;
        } else {
            return null;
        }
    }




    createPost(forum_id, author_id, title, content) {
        this.forum_id = forum_id;
        this.author_id = author_id;
        this.title = title;
        this.content = content;
    }
}



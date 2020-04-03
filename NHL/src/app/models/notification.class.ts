import { teams } from '../models/teams';

export class Notification {
    constructor(
        public time: string,
        public user_id: string,
        public forum_id: string,
        public post_id: string,
        public seenFlag: string,
        public message: string,
        public forumName?: string) {
            console.log(this);
        }
}


        //     if (forum_id != null) {
        //         this.forumName = this.getForumName(forum_id);
        //         this.message = 'New post in' + forumName;
        //     }
        // }

    // getForumName(forum_id) {
    //     console.log(forum_id);
    //     if (teams.filter(x => x.forumId === forum_id)[0] != null) {
    //         return teams.filter(x => x.forumId === forum_id)[0].name;
    //     } else {
    //         return null;
    //     }
    // }



    // getForumName(forum_id) {
    //     // return teams.filter(x => x.forumId === forum_id)[0].name;

    //     if (teams.filter(x => x.forumId === forum_id)[0] !== null ) {
    //         return teams.filter(x => x.forumId === forum_id)[0].imgURL;
    //     } else {
    //         return null;
    //     }
    // }



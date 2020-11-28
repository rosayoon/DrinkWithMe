package Data;

import java.util.ArrayList;

public class Post {
    String title;
    User writer;
    String date;
    String time;
    String content;
    int thumbs_up;
    int reply_num;
    ArrayList<Reply> replyList;

    public Post(String title, User writer, String date, String time, String content, int thumbs_up, int reply_num, ArrayList<Reply>replyList){
        this.title =title;
        this.writer = writer;
        this.date =date;
        this.content = content;
        this.thumbs_up =thumbs_up;
        this.reply_num = reply_num;
        this.replyList =replyList;
    }

}

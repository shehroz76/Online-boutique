package com.example.msk.onlinebotique.Pojo;



/**
 * Created by DELL on 5/20/2017.
 */

public class Comments {

    private String Profilepic;
    private String Name;
    private String Comments;
    private String timeStamp;

    public Comments() {
    }

    public Comments(String profilepic, String name, String comments, String timeStamp) {
        Profilepic = profilepic;
        Name = name;
        Comments = comments;
        this.timeStamp = timeStamp;
    }

    public String getProfilepic() {
        return Profilepic;
    }

    public void setProfilepic(String profilepic) {
        Profilepic = profilepic;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getComments() {
        return Comments;
    }

    public void setComments(String comments) {
        Comments = comments;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }
}

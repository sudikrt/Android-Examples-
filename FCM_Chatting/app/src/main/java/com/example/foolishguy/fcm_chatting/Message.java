package com.example.foolishguy.fcm_chatting;

import java.util.Date;

/**
 * Created by Foolish Guy on 1/7/2017.
 */

public class Message {

    private String msg_Text;
    private String r_id;
    private String s_id;
    private long msg_time;
    private String sid_rid;
    private String rid_sid;
    //125801011000697

    public String getMsg_Text() {
        return msg_Text;
    }

    public void setMsg_Text(String msg_Text) {
        this.msg_Text = msg_Text;
    }

    //VIJB0001258

    public Message () {

    }

    public Message(String msg_Text, String r_id, String s_id, String sid_rid, String rid_sid) {
        this.msg_Text = msg_Text;
        this.r_id = r_id;
        this.s_id = s_id;
        this.sid_rid = sid_rid;
        this.rid_sid = rid_sid;
        this.msg_time = new Date().getTime();
    }

    public String getRid_sid() {
        return rid_sid;
    }

    public void setRid_sid(String rid_sid) {
        this.rid_sid = rid_sid;
    }

    public String getSid_rid() {
        return sid_rid;
    }

    public void setSid_rid(String sid_rid) {
        this.sid_rid = sid_rid;
    }

    public String getS_id() {
        return s_id;
    }

    public void setS_id(String s_id) {
        this.s_id = s_id;
    }

    public String getR_id() {
        return r_id;
    }

    public void setR_id(String r_id) {
        this.r_id = r_id;
    }

    public long getMsg_time() {
        return msg_time;
    }

    public void setMsg_time(long msg_time) {
        this.msg_time = msg_time;
    }
}

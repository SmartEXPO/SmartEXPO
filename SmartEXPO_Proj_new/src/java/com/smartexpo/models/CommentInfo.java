/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartexpo.models;

import java.util.Date;

/**
 *
 * @author ben
 */
public class CommentInfo {

    private int id;
    private String username;
    private Date time;
    private String content;
    private long num;

    public CommentInfo() {
    }

    public CommentInfo(int id, String username, Date time, String content) {
        this.username = username;
        this.time = time;
        this.content = content;
        this.id = id;
    }

    public CommentInfo(int id, String username, Date time, String content, long num) {
        this.username = username;
        this.time = time;
        this.content = content;
        this.id = id;
        this.num = num;
    }

    public long getNum() {
        return num;
    }

    public void setNum(long num) {
        this.num = num;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

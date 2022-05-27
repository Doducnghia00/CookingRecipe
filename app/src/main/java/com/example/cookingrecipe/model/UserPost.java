package com.example.cookingrecipe.model;

import java.io.Serializable;

public class UserPost implements Serializable {
    private int id;
    private String idUser, date, postName,status, linkImage;

    public UserPost() {
    }

    public UserPost(int id, String idUser, String date, String postName, String status) {
        this.id = id;
        this.idUser = idUser;
        this.date = date;
        this.postName = postName;
        this.status = status;
    }

    public String getLinkImage() {
        return linkImage;
    }

    public void setLinkImage(String linkImage) {
        this.linkImage = linkImage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPostName() {
        return postName;
    }

    public void setPostName(String postName) {
        this.postName = postName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "UserPost{" +
                "id=" + id +
                ", idUser='" + idUser + '\'' +
                ", date='" + date + '\'' +
                ", postName='" + postName + '\'' +
                ", status='" + status + '\'' +
                ", linkImage='" + linkImage + '\'' +
                '}';
    }
}

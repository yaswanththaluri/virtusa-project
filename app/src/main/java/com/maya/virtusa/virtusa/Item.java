package com.maya.virtusa.virtusa;

public class Item {

    private String contentName;
    private String author;
    private String preview;
    private String isFree;

    public Item()
    {

    }

    public Item(String contentName, String author, String preview, String isFree) {
        this.contentName = contentName;
        this.author = author;
        this.preview = preview;
        this.isFree = isFree;
    }

    public String getContentName() {
        return contentName;
    }

    public void setContentName(String contentName) {
        this.contentName = contentName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPreview() {
        return preview;
    }

    public void setPreview(String preview) {
        this.preview = preview;
    }

    public String getIsFree() {
        return isFree;
    }

    public void setIsFree(String isFree) {
        this.isFree = isFree;
    }
}

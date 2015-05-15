package com.thinkful.notes;

import java.io.Serializable;
import java.util.Calendar;

public class NoteListItem implements Serializable {
    private long id;
    private String text;
    private String status;
    private Calendar date;

    public NoteListItem(String text) {


    }
    public String getText() {
        return this.text;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }
}
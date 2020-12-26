package com.BookKeeping.note;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

import androidx.annotation.NonNull;

@Entity
public class NoteBean {
    @Id(autoincrement = true)
    private Long id;

    @NonNull//text非空
    public String date;
    public String note;
    public String photo;
    @Generated(hash = 710844915)
    public NoteBean(Long id, @NonNull String date, String note, String photo) {
        this.id = id;
        this.date = date;
        this.note = note;
        this.photo = photo;
    }
    @Generated(hash = 451626881)
    public NoteBean() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getDate() {
        return this.date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public String getNote() {
        return this.note;
    }
    public void setNote(String note) {
        this.note = note;
    }
    public String getPhoto() {
        return this.photo;
    }
    public void setPhoto(String photo) {
        this.photo = photo;
    }
}

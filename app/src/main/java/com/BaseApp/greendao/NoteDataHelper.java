package com.BaseApp.greendao;

import android.util.Log;

import com.BookKeeping.note.NoteBean;
import com.google.gson.Gson;

import java.util.List;

public class NoteDataHelper {

    public static void save(DBService dbService, String date, String note, String photo){
        NoteBean noteBean = new NoteBean();
        noteBean.setDate(date);
        noteBean.setNote(note);
        noteBean.setPhoto(photo);
        dbService.getDaoSession().getNoteBeanDao().insert(noteBean);
    }

    public static void alter(DBService dbService, long id, String date, String note, String photo){
        NoteBean noteBean = new NoteBean();
        noteBean.setId(id);
        noteBean.setDate(date);
        noteBean.setNote(note);
        noteBean.setPhoto(photo);
        dbService.getDaoSession().getNoteBeanDao().insertOrReplace(noteBean);
    }

    public static void delete(DBService dbService, String date) {
        NoteBean noteBean = dbService.getDaoSession().getNoteBeanDao().queryBuilder()
                .where(NoteBeanDao.Properties.Date.eq(date)).build().unique();
        if (noteBean != null){
            dbService.getDaoSession().getNoteBeanDao().deleteByKey(noteBean.getId());
        }
    }

    public static List<NoteBean> getNoteData(DBService dbService){
        List<NoteBean> noteBeanList = dbService.getDaoSession().getNoteBeanDao().queryBuilder().build().list();
        Log.e("getNoteData","NoteData："+new Gson().toJson(noteBeanList));
        return noteBeanList;
    }

    public static boolean exsit(DBService dbService, String s) {
        return dbService.getDaoSession().getNoteBeanDao().queryBuilder().where(NoteBeanDao.Properties.Date.eq(s)).count() > 0;
    }
}

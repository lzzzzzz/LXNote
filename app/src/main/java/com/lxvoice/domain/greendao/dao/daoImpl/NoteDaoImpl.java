package com.lxvoice.domain.greendao.dao.daoImpl;


import com.lxvoice.domain.greendao.dao.NoteDao;
import com.lxvoice.domain.greendao.entity.Note;
import com.lxvoice.domain.greendao.main.GreenDaoManager;
import com.lxvoice.domain.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lz on 2018/3/13.
 * 文章数据库操作
 */

public class NoteDaoImpl {
    /**分页数据每页条数*/
    private static final int PER_PAGE_QUERY_NUM= 9;
    /**插入数据*/
    public void insert(Note note){
        NoteDao noteDao = GreenDaoManager.getSingleTon().getmDaoSession().getNoteDao();
        noteDao.insert(note);
    }
    /**删除数据*/
    public void delete(Note note){
        NoteDao noteDao = GreenDaoManager.getSingleTon().getmDaoSession().getNoteDao();
        noteDao.delete(note);
    }
    /**更新数据*/
    public void update(Note note){
        NoteDao noteDao = GreenDaoManager.getSingleTon().getmDaoSession().getNoteDao();
        noteDao.update(note);
    }
    /**根据主键查询笔记*/
    public Note queryByPrimary(long id){
        NoteDao noteDao = GreenDaoManager.getSingleTon().getmDaoSession().getNoteDao();
        return noteDao.load(id);
    }

    /**查询全部*/
    public List<Note> queryAll(int current_page){
        current_page = current_page<1?1:current_page;
        NoteDao noteDao = GreenDaoManager.getSingleTon().getmDaoSession().getNoteDao();
        List<Note> notes = new ArrayList<Note>();
        notes = noteDao.queryBuilder().offset((current_page-1)*PER_PAGE_QUERY_NUM).limit(PER_PAGE_QUERY_NUM)
                .orderDesc(NoteDao.Properties.Utime).list();
        return notes;
    }

    /**按标题查询*/
    public List<Note> queryByTitle(String title, int current_page){
        current_page = current_page<1?1:current_page;
        if(StringUtils.isEmpty(title)){
            queryAll(current_page);
        }
        NoteDao noteDao = GreenDaoManager.getSingleTon().getmDaoSession().getNoteDao();
        List<Note> notes = new ArrayList<Note>();
        notes = noteDao.queryBuilder().where(NoteDao.Properties.Title.eq(title))
                .offset((current_page-1)*PER_PAGE_QUERY_NUM).limit(PER_PAGE_QUERY_NUM)
                .orderDesc(NoteDao.Properties.Ctime).list();
        return notes;
    }

    /**按标题查询*/
    public List<Note> queryByBookId(Long book_id, int current_page){
        current_page = current_page<1?1:current_page;
        if(book_id==null||book_id<=0){
            queryAll(current_page);
        }
        NoteDao noteDao = GreenDaoManager.getSingleTon().getmDaoSession().getNoteDao();
        List<Note> notes = new ArrayList<Note>();
        notes = noteDao.queryBuilder().where(NoteDao.Properties.Book_id.eq(book_id))
                .offset((current_page-1)*PER_PAGE_QUERY_NUM).limit(PER_PAGE_QUERY_NUM)
                .orderDesc(NoteDao.Properties.Ctime).list();
        return notes;
    }
}

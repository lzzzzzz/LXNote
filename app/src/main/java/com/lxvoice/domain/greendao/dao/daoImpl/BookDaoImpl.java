package com.lxvoice.domain.greendao.dao.daoImpl;

import com.lxvoice.domain.greendao.dao.BookDao;
import com.lxvoice.domain.greendao.entity.Book;
import com.lxvoice.domain.greendao.main.GreenDaoManager;
import com.lxvoice.domain.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lz on 2018/3/13.
 * 书目数据库操作
 */

public class BookDaoImpl {
    /**分页数据每页条数*/
    private static final int PER_PAGE_QUERY_NUM= 9;
    /**插入数据*/
    public void insert(Book book){
        BookDao bookDao = GreenDaoManager.getSingleTon().getmDaoSession().getBookDao();
        bookDao.insert(book);
    }
    /**删除数据*/
    public void delete(Book book){
        BookDao bookDao = GreenDaoManager.getSingleTon().getmDaoSession().getBookDao();
        bookDao.delete(book);
    }
    /**更新数据*/
    public void update(Book book){
        BookDao bookDao = GreenDaoManager.getSingleTon().getmDaoSession().getBookDao();
        bookDao.update(book);
    }
    /**根据主键查询书目*/
    public Book queryByPrimary(long id){
        BookDao bookDao = GreenDaoManager.getSingleTon().getmDaoSession().getBookDao();
        return bookDao.load(id);
    }

    /**查询全部*/
    public List<Book> queryAll(int current_page){
        current_page = current_page<1?1:current_page;
        BookDao bookDao = GreenDaoManager.getSingleTon().getmDaoSession().getBookDao();
        List<Book> books = new ArrayList<Book>();
        books = bookDao.queryBuilder().offset((current_page-1)*PER_PAGE_QUERY_NUM).limit(PER_PAGE_QUERY_NUM)
                .orderDesc(BookDao.Properties.Ctime).list();
        return books;
    }

    /**按数目名称查询,并倒序排列*/
    public List<Book> queryByName(String name, int current_page){
        current_page = current_page<1?1:current_page;
        if(StringUtils.isEmpty(name)){
            queryAll(current_page);
        }
        BookDao bookDao = GreenDaoManager.getSingleTon().getmDaoSession().getBookDao();
        List<Book> books = new ArrayList<Book>();
        books = bookDao.queryBuilder().where(BookDao.Properties.Name.eq(name))
                .offset((current_page-1)*PER_PAGE_QUERY_NUM).limit(PER_PAGE_QUERY_NUM)
                .orderDesc(BookDao.Properties.Utime).list();
        return books;
    }

}

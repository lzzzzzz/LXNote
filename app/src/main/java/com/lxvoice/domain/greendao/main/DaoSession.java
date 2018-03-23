package com.lxvoice.domain.greendao.main;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.lxvoice.domain.greendao.dao.BookDao;
import com.lxvoice.domain.greendao.dao.NoteDao;
import com.lxvoice.domain.greendao.entity.Book;
import com.lxvoice.domain.greendao.entity.Note;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig bookDaoConfig;
    private final DaoConfig noteDaoConfig;

    private final BookDao bookDao;
    private final NoteDao noteDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        bookDaoConfig = daoConfigMap.get(BookDao.class).clone();
        bookDaoConfig.initIdentityScope(type);

        noteDaoConfig = daoConfigMap.get(NoteDao.class).clone();
        noteDaoConfig.initIdentityScope(type);

        bookDao = new BookDao(bookDaoConfig, this);
        noteDao = new NoteDao(noteDaoConfig, this);

        registerDao(Book.class, bookDao);
        registerDao(Note.class, noteDao);
    }
    
    public void clear() {
        bookDaoConfig.clearIdentityScope();
        noteDaoConfig.clearIdentityScope();
    }

    public BookDao getBookDao() {
        return bookDao;
    }

    public NoteDao getNoteDao() {
        return noteDao;
    }

}

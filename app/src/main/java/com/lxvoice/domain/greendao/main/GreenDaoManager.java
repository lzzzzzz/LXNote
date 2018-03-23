package com.lxvoice.domain.greendao.main;

import android.database.sqlite.SQLiteDatabase;

import com.lxvoice.domain.base.BaseApplication;
import com.lxvoice.domain.base.Keys;

public class GreenDaoManager {
  
    private MySQLiteOpenHelper mHelper;
    private SQLiteDatabase db;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;
  
    private static GreenDaoManager greenDaoManager;
  
    private GreenDaoManager(){
        initGreenDao();
    }
  
    public static GreenDaoManager getSingleTon(){
        if (greenDaoManager ==null){
            greenDaoManager =new GreenDaoManager();
        }  
        return greenDaoManager;
    }  
  
    private void initGreenDao(){
        mHelper=new MySQLiteOpenHelper(BaseApplication.getContext(), Keys.DB_NAME,null);
        db=mHelper.getWritableDatabase();  
        mDaoMaster=new DaoMaster(db);  
        mDaoSession=mDaoMaster.newSession();  
    }  
  
    public DaoSession getmDaoSession() {  
        if (mDaoMaster==null){  
            initGreenDao();  
        }  
        return mDaoSession;  
    }  
  
   public SQLiteDatabase getDb() {  
        if (db==null){  
            initGreenDao();  
        }  
        return db;  
    }  
  
}

package com.lxvoice.domain;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.lxvoice.domain.activity.BooksActivity;
import com.lxvoice.domain.greendao.dao.daoImpl.BookDaoImpl;

import java.util.Date;

/**
 * Created by lz on 2018/3/13.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        //saveTestData();
        //startActivity(new Intent(this,EditBookActivity.class));
        startActivity(new Intent(this,BooksActivity.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**保存测试数据*/
    private void saveTestData(){
        BookDaoImpl bookDao = new BookDaoImpl();
       /* for(int i=0;i<54;i++){
            Book book = new Book();
            book.setAuthor("lz");
            book.setCtime(new Date());
            book.setDepict("测试书籍");
            book.setName("the book  name is number："+i);
            Log.d(MainActivity.this.getLocalClassName(),"save book==>"+book.getName());
            bookDao.insert(book);
        }*/
    }
}

package com.lxvoice.domain.activity;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.lxvoice.domain.R;
import com.lxvoice.domain.greendao.dao.daoImpl.BookDaoImpl;
import com.lxvoice.domain.greendao.entity.Book;
import com.lxvoice.domain.greendao.entity.BookDao;
import com.lxvoice.domain.utils.StringUtils;

import java.util.Date;

public class EditBookActivity extends  AppCompatActivity {

    private Book book;

    private EditText et_input_book_title;

    private EditText et_input_book_depict;

    private ImageView iv_book_background;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_book);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.str_title);
        toolbar.setNavigationIcon(R.drawable.ic_action_back_arrow);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        eventBundle(getIntent().getExtras());
        initView();
        initData();
    }


    private void eventBundle(Bundle extras) {
        if(null!=extras){
            Long book_id = extras.getLong("book_id",0);
            if(book_id>0){
                BookDaoImpl bookDao = new BookDaoImpl();
                book = bookDao.queryByPrimary(book_id);
            }
        }
    }

    private void initView() {
        et_input_book_title = (EditText) this.findViewById(R.id.et_input_book_title);
        et_input_book_depict = (EditText) this.findViewById(R.id.et_input_book_depict);
        iv_book_background = (ImageView) this.findViewById(R.id.iv_book_background);
    }
    private void initData() {
        if(null!=book){
            String book_title = book.getName();
            String book_depict = book.getDepict();
            if(!StringUtils.isEmpty(book_title)){
                et_input_book_title.setText(book_title);
            }
            if(!StringUtils.isEmpty(book_depict)){
                et_input_book_depict.setText(book_depict);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit_book,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()==R.id.action_menu_save){
            String title = et_input_book_title.getText().toString();
            String depict = et_input_book_depict.getText().toString();
            if(book==null){//新建书目
                if(StringUtils.isEmpty(title)){
                    Toast.makeText(this,"书名不能为空",Toast.LENGTH_SHORT).show();
                    return true;
                }
                if(StringUtils.isEmpty(depict)){
                    Toast.makeText(this,"为书写点介绍吧",Toast.LENGTH_SHORT).show();
                    return true;
                }
                book = new Book();
                book.setName(title);
                book.setDepict(depict);
                book.setCtime(new Date());
                book.setUtime(new Date());
                BookDaoImpl bookDao = new BookDaoImpl();
                bookDao.insert(book);
                Toast.makeText(this,"保存成功^_^",Toast.LENGTH_SHORT).show();
                onBackPressed();
            }else{//更新书目
                if(StringUtils.isEmpty(title)){
                    Toast.makeText(this,"书名不能为空",Toast.LENGTH_SHORT).show();
                    return true;
                }
                if(StringUtils.isEmpty(depict)){
                    Toast.makeText(this,"写点介绍吧",Toast.LENGTH_SHORT).show();
                    return true;
                }
                book.setName(title);
                book.setDepict(depict);
                book.setCtime(new Date());
                book.setUtime(new Date());
                BookDaoImpl bookDao = new BookDaoImpl();
                bookDao.update(book);
                Toast.makeText(this,"保存成功^_^",Toast.LENGTH_SHORT).show();
                onBackPressed();
            }

        }
        return true;
    }
}

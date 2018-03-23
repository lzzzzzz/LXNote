package com.lxvoice.domain.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.lxvoice.domain.R;
import com.lxvoice.domain.adapter.NotesListAdapter;
import com.lxvoice.domain.greendao.dao.daoImpl.BookDaoImpl;
import com.lxvoice.domain.greendao.dao.daoImpl.NoteDaoImpl;
import com.lxvoice.domain.greendao.entity.Book;
import com.lxvoice.domain.greendao.entity.Note;
import com.lxvoice.domain.view.RecyclerViewHelper;
import com.lxvoice.domain.view.librecyle.adapter.SpacesItemDecoration;
import com.lxvoice.domain.view.librecyle.adapter.ViewType;
import com.lxvoice.domain.view.librecyle.footer.FooterState;
import com.lxvoice.domain.view.librecyle.listener.LoadMoreListener;
import com.lxvoice.domain.view.librecyle.listener.OnFooterChangeListener;
import com.lxvoice.domain.view.librecyle.listener.OnViewBindListener;
import com.lxvoice.domain.view.librecyle.listener.TipsListener;

import java.util.ArrayList;
import java.util.List;

public class NotesActivity extends AppCompatActivity {

    private RecyclerView rl_notes_list;

    private NotesListAdapter notesListAdapter;
    private RecyclerViewHelper recyclerViewHelper;
    private NoteDaoImpl noteDao;

    private List<Note> notes;

    private Book book;

    private int loadCount = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        Toolbar toolbar = (Toolbar) this.findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.title_activity_notes);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_action_back_arrow);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        rl_notes_list = (RecyclerView) this.findViewById(R.id.rl_notes_list);

        //设置item间距，30dp
        rl_notes_list.addItemDecoration(new SpacesItemDecoration(0));
        noteDao=new NoteDaoImpl();
        initView();
        eventBundle(getIntent().getExtras());
        initAdapter();
        //initData();
    }
    @Override
    protected void onResume() {
        loadCount = 1;
        notes.clear();
        initData();
        super.onResume();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_note,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()==R.id.action_create){
            Intent intent = new Intent(NotesActivity.this,EditNoteActivity.class);
            if(book!=null){
                Bundle bundle = new Bundle();
                bundle.putLong("book_id", book.getId());
                intent.putExtras(bundle);
            }
            startActivity(intent);
        }
        return true;
    }

    /**获取书籍数据*/
    private void eventBundle(Bundle bundle) {
        if(null!=bundle){
            long book_id = bundle.getLong("book_id",0);
            if(book_id>0){
                BookDaoImpl bookDao = new BookDaoImpl();
                book = bookDao.queryByPrimary(book_id);
                Log.d(NotesActivity.this.getLocalClassName(),"==>load the note by id："+book_id);
            }else{
                Log.d(NotesActivity.this.getLocalClassName(),"==>no book id");
                finish();
            }
        }else{
            Log.d(NotesActivity.this.getLocalClassName(),"==>no bundle (no book id)");
            finish();
        }
    }

    private void initView() {

    }

    private void initAdapter() {
        notes = new ArrayList<Note>();
        notesListAdapter = new NotesListAdapter(notes);
        notesListAdapter.setContext(this);
        recyclerViewHelper = new RecyclerViewHelper(rl_notes_list,notesListAdapter);
        //设置没有数据的Tips
        recyclerViewHelper.setTipsEmptyView(R.layout.view_data_empty);
        //设置加载中的Tips
        recyclerViewHelper.setTipsLoadingView(R.layout.view_data_loading);
        //设置加载失败的Tips
        recyclerViewHelper.setTipsErrorView(R.layout.view_data_error);
        //设置header
        recyclerViewHelper.setHeaderView(R.layout.view_header);

        //默认加载更多 footer 也可自定义
        recyclerViewHelper.useDefaultFooter();

        //加载失败，没有数据时Tips的接口
        recyclerViewHelper.setTipsListener(new TipsListener() {
            @Override
            public void retry() {
                initData();
            }
        });


        //加载更多的接口
        recyclerViewHelper.setLoadMoreListener(new LoadMoreListener() {
            @Override
            public void loadMore() {
                loadNext();
            }
        });


        recyclerViewHelper.setOnViewBindListener(new OnViewBindListener() {
            @Override
            public void onBind(RecyclerView.ViewHolder holder, int viewType) {
                Log.d(NotesActivity.class.getName(), "==============onBind============");
                if (ViewType.TYPE_HEADER == viewType) {
                    // TODO: 2017/7/13 header view bind
                } else if (ViewType.TYPE_FOOTER == viewType) {
                    // TODO: 2017/7/13 footer view bind
                }
            }
        });

        recyclerViewHelper.setFooterChangeListener(new OnFooterChangeListener() {
            @Override
            public void onChange(RecyclerView.ViewHolder holder, int state) {
                Log.d(NotesActivity.class.getName(), "==============onChange============");
                if (FooterState.LOADING == state) {
                    // TODO: 2017/7/13 加载中
                } else if (FooterState.ERROR == state) {
                    // TODO: 2017/7/13 加载失败
                } else if (FooterState.NO_MORE == state) {
                    // TODO: 2017/7/13 加载完成
                }
            }
        });


        initData();

    }

    private void loadNext() {
        loadBookFromDb();
    }
    private void initData() {
        loadBookFromDb();
    }

private void loadBookFromDb(){
        Log.d("BooksActivity","==>load book the page："+loadCount);
    recyclerViewHelper.loadStart();
    List<Note> new_books = noteDao.queryByBookId(book.getId(),loadCount);
    if(null!=new_books&&new_books.size()>0){
        loadCount++;
        getBooksData(new_books);
        recyclerViewHelper.loadComplete(true);
    }else{
        recyclerViewHelper.loadComplete(false);
    }
}

   private List<Note> getBooksData(List<Note> newNotes){
       notes.addAll(newNotes);
        return newNotes;
    }
}

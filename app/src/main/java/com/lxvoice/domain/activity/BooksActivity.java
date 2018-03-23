package com.lxvoice.domain.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.lxvoice.domain.R;
import com.lxvoice.domain.adapter.BooksListAdapter;
import com.lxvoice.domain.greendao.dao.daoImpl.BookDaoImpl;
import com.lxvoice.domain.greendao.entity.Book;
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


public class BooksActivity extends AppCompatActivity implements BooksListAdapter.OnClickViewListener {

    private RecyclerView rl_books_list;

    private BooksListAdapter booksListAdapter;
    private RecyclerViewHelper recyclerViewHelper;
    private BookDaoImpl bookDao;

    private List<Book> books;

    private int loadCount = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books);
        Toolbar toolbar = (Toolbar) this.findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.title_activity_bookss);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_action_back_arrow);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        rl_books_list = (RecyclerView) this.findViewById(R.id.rl_books_list);

        //设置item间距，30dp
        rl_books_list.addItemDecoration(new SpacesItemDecoration(0));
        bookDao=new BookDaoImpl();
        initView();
        initAdapter();
        //initData();
    }

    @Override
    protected void onResume() {
        loadCount = 1;
        books.clear();
        initData();
        super.onResume();
    }

    private void initView() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(BooksActivity.this,EditBookActivity.class));
            }
        });
    }

    private void initAdapter() {
        books = new ArrayList<Book>();
        booksListAdapter = new BooksListAdapter(books);
        booksListAdapter.setOnClickViewListener(this);
        booksListAdapter.setContext(this);
        recyclerViewHelper = new RecyclerViewHelper(rl_books_list,booksListAdapter);
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
                Log.d(BooksActivity.class.getName(), "==============onBind============");
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
                Log.d(BooksActivity.class.getName(), "==============onChange============");
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
    List<Book> new_books = bookDao.queryAll(loadCount);
    if(null!=new_books&&new_books.size()>0){
        loadCount++;
        getBooksData(new_books);
        recyclerViewHelper.loadComplete(true);
    }else{
        recyclerViewHelper.loadComplete(false);
    }
}

   private List<Book> getBooksData(List<Book> newBooks){
        books.addAll(newBooks);
        return books;
    }

    @Override
    public void onDeleteItem(Book book, int position) {
        BookDaoImpl bookDao = new BookDaoImpl();
        bookDao.delete(book);
        booksListAdapter.getData().remove(book);
        recyclerViewHelper.loadComplete(true);
        /*booksListAdapter.notifyItemRemoved(position);
        Log.d(this.getClass().getName(),"==>notifyItemRangeChanged:"+(booksListAdapter.getData().size()+2-position));
        booksListAdapter.notifyItemRangeChanged(position,booksListAdapter.getData().size()+2-position);
        Log.d(this.getClass().getName(),"==>delete book:"+book.getName());*/
    }
}

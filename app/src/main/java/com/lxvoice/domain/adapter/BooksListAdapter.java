package com.lxvoice.domain.adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;

import com.lxvoice.domain.R;
import com.lxvoice.domain.activity.EditBookActivity;
import com.lxvoice.domain.activity.NotesActivity;
import com.lxvoice.domain.greendao.dao.daoImpl.BookDaoImpl;
import com.lxvoice.domain.greendao.entity.Book;
import com.lxvoice.domain.view.librecyle.adapter.BaseViewHolder;
import com.lxvoice.domain.view.librecyle.adapter.CommonAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Created by psp on 2018/3/13.
 */

public class BooksListAdapter extends CommonAdapter<Book> {

    private OnClickViewListener onClickViewListener;

    public BooksListAdapter (@NotNull List<Book> books){
        super(books, R.layout.item_books_view);
    }

    public void setOnClickViewListener(OnClickViewListener onClickViewListener){
        this.onClickViewListener = onClickViewListener;
    }
    @Override
    public void convert(final BaseViewHolder holder, final Book book, final int position) {
        holder.setText(R.id.tv_book_name,book.getName());
        holder.setOnClickListener(R.id.tr_book_details, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(getContext().getPackageName(),"==>click book the id："+book.getId());
                Bundle bundle = new Bundle();
                bundle.putLong("book_id",book.getId());
                Intent intent =new Intent(getContext(), NotesActivity.class);
                intent.putExtras(bundle);
                getContext().startActivity(intent);
            }
        });
        holder.setOnClickListener(R.id.ll_book_edit, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.getView(R.id.ll_book_edit).setVisibility(View.GONE);
            }
        });
        holder.setOnClickListener(R.id.bt_delete_book, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(getContext().getClass().getName(),"==>delete position:"+position);
                if(null!=onClickViewListener){
                    onClickViewListener.onDeleteItem(book,position);
                    holder.getView(R.id.ll_book_edit).setVisibility(View.GONE);
                }
            }
        });
        holder.setOnClickListener(R.id.bt_edit_book, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(getContext().getPackageName(),"==>click book the id："+book.getId());
                Bundle bundle = new Bundle();
                bundle.putLong("book_id",book.getId());
                Intent intent =new Intent(getContext(), EditBookActivity.class);
                intent.putExtras(bundle);
                getContext().startActivity(intent);
                holder.getView(R.id.ll_book_edit).setVisibility(View.GONE);
            }
        });

        holder.setOnLongClickListener(R.id.tr_book_details, new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                holder.getView(R.id.ll_book_edit).setVisibility(View.VISIBLE);
                return true;
            }
        });
    }

    public interface OnClickViewListener{
        void onDeleteItem(Book book,int position);
    }
}

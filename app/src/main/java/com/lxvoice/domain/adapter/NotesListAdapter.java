package com.lxvoice.domain.adapter;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lxvoice.domain.R;
import com.lxvoice.domain.activity.EditNoteActivity;
import com.lxvoice.domain.config.AppConfig;
import com.lxvoice.domain.entity.TaskSpan;
import com.lxvoice.domain.greendao.entity.Note;
import com.lxvoice.domain.utils.StringUtils;
import com.lxvoice.domain.view.librecyle.adapter.BaseViewHolder;
import com.lxvoice.domain.view.librecyle.adapter.CommonAdapter;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by psp on 2018/3/13.
 */

public class NotesListAdapter extends CommonAdapter<Note> {

    public NotesListAdapter(@NotNull List<Note> notes){
        super(notes, R.layout.item_notes_view);
    }
    @Override
    public void convert(BaseViewHolder holder, final Note note, int position) {
        String title = note.getTitle();
        String content = getTextFromContent(note);
        if(!StringUtils.isEmpty(title)){
            holder.setText(R.id.tv_note_name,note.getTitle());
        }else{
            holder.setText(R.id.tv_note_name,content==null?"":content);
        }
        holder.setText(R.id.tv_note_utime,note.getUtime().getYear()+"/"+(note.getUtime().getMonth()+1)+"/"+note.getUtime().getDate());
        holder.setText(R.id.tv_note_content,content==null?"":content);
        holder.setOnClickListener(R.id.note_content, new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putLong("note_id",note.getId());
                Intent intent = new Intent(getContext(), EditNoteActivity.class);
                intent.putExtras(bundle);
                getContext().startActivity(intent);
            }
        });
    }

    /**获取文字缩略内容*/
    private String getTextFromContent(Note note){
        if(note==null||StringUtils.isEmpty(note.getContext())){
            return null;
        }else{
            Type type = new TypeToken<List<com.lxvoice.domain.entity.TaskSpan>>(){}.getType();
            List<TaskSpan> spans =new Gson().fromJson(note.getContext(),type);
            if(null!=spans&&spans.size()>0){
                for (TaskSpan span: spans){
                    if(span.getType() == AppConfig.TYPE_SPAN_TEXT){
                        return span.getThunm();
                    }
                }
            }else{
                return null;
            }
            return null;
        }
    }
}

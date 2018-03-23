package com.lxvoice.domain.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lxvoice.domain.R;
import com.lxvoice.domain.config.AppConfig;
import com.lxvoice.domain.entity.TaskSpan;
import com.lxvoice.domain.greendao.dao.daoImpl.BookDaoImpl;
import com.lxvoice.domain.greendao.dao.daoImpl.NoteDaoImpl;
import com.lxvoice.domain.greendao.entity.Book;
import com.lxvoice.domain.greendao.entity.Note;
import com.lxvoice.domain.utils.RicEditUtils;
import com.lxvoice.domain.utils.StringUtils;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by lz on 2018/3/13.
 */
public class EditNoteActivity extends AppCompatActivity {

    private EditText et_input;

    private Note note;

    private Book book;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.str_title);
        toolbar.setNavigationIcon(R.drawable.ic_action_back_arrow);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        initView();
        eventBundle(getIntent().getExtras());
        initData();
    }

    /**取数据*/
    private void eventBundle(Bundle bundle) {
        if(null!=bundle){
            long book_id = bundle.getLong("book_id",0);
            if(book_id>0){
                BookDaoImpl bookDao = new BookDaoImpl();
                book = bookDao.queryByPrimary(book_id);
                Log.d(EditNoteActivity.this.getLocalClassName(),"load the note by id："+book_id);
            }else{
                Log.d(EditNoteActivity.this.getLocalClassName(),"create new note");
            }
            long note_id = bundle.getLong("note_id",0);
            if(note_id>0){
                NoteDaoImpl noteDao = new NoteDaoImpl();
                note = noteDao.queryByPrimary(note_id);
                Log.d(EditNoteActivity.this.getLocalClassName(),"load the note by id："+note_id);
            }else{
                Log.d(EditNoteActivity.this.getLocalClassName(),"create new note");
            }
        }else{
            Log.d(EditNoteActivity.this.getLocalClassName(),"create new note");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit_note,menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()==R.id.action_menu_save){
           /* String content = et_input.getText().toString();
            Log.d(EditNoteActivity.this.getLocalClassName(),"==>save note content："+content);*/
            //showSaveData();
            List<TaskSpan> spans = getNoteData();
            if(null!=spans&&spans.size()>0){
                NoteDaoImpl noteDao = new NoteDaoImpl();
                String content = new Gson().toJson(spans);
                if(null==note){
                    note = new Note();
                    note.setBook_id(book.getId());
                    note.setCtime(new Date());
                    note.setUtime(new Date());
                    note.setContext(content);
                    noteDao.insert(note);
                }else{
                    note.setUtime(new Date());
                    note.setContext(content);
                    noteDao.update(note);
                }
            }
            finish();
        }
        return true;
    }

    private void initView() {
        et_input= (EditText) this.findViewById(R.id.et_input);
    }

    private void initData() {

        RicEditUtils.setEditContent3(this,et_input,testShowText2());

        /*SpannableString spanString = new SpannableString(" ");
        Drawable d = getResources().getDrawable(R.mipmap.ic_launcher);
        d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
        ImageSpan span = new ImageSpan(d, ImageSpan.ALIGN_BASELINE);
        spanString.setSpan(span, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        et_input.append("\n");
        et_input.append(spanString);
        et_input.append("\n");*/
    }

 /*   public Note testShowText(){
        Note note = new Note();
        note.setText("0123456789");

            Note span = new Note();
            span.setStart(2);
            span.setEnd(3);

        Note span2 = new Note();
        span2.setStart(5);
        span2.setEnd(6);

        List<Note> spans = new ArrayList<Note>();
        spans.add(span);
        spans.add(span2);
        note.setSpans(spans);
        return note;

    }*/

    public List<TaskSpan> testShowText2(){

        if(note!=null){
           Type type = new TypeToken<List<TaskSpan>>(){}.getType();
            List<TaskSpan> spsns = new Gson().fromJson(note.getContext(), type);
            if(null!=spsns&&spsns.size()>0){
                return spsns;
            }
        }
        /*List<TaskSpan> nses = new ArrayList<TaskSpan>();

        TaskSpan span = new TaskSpan();
        span.setType(AppConfig.TYPE_SPAN_IMAGE);
        span.setStart(2);
        span.setEnd(3);

        nses.add(span);

        TaskSpan span1 = new TaskSpan();
        span1.setType(AppConfig.TYPE_SPAN_TEXT);
        span1.setThunm("123");
        nses.add(span1);

        TaskSpan span2 = new TaskSpan();
        span2.setType(AppConfig.TYPE_SPAN_IMAGE);
        span2.setStart(5);
        span2.setEnd(6);
        nses.add(span2);

        TaskSpan span3 = new TaskSpan();
        span3.setType(AppConfig.TYPE_SPAN_TEXT);
        span3.setThunm("456");
        nses.add(span3);*/
        return null;

    }

    public void showSaveData(){
        ImageSpan[] spans = et_input.getText().getSpans(0,et_input.length(),ImageSpan.class);
        for(ImageSpan span: spans){
            int start =et_input.getText().getSpanStart(span);
            Log.d(EditNoteActivity.this.getLocalClassName(),"==>ns1 start:"+ start);

           // char[] x = new char[et_input.getText().subSequence(0,9).toString()];
            String x_str = et_input.getText().subSequence(0,9).toString();
            /*et_input.getText().getChars(0,start,x,0);
            String str =x.toString();*/
            Log.d(EditNoteActivity.this.getLocalClassName(),"==>ns1 char:"+ x_str.trim());
            Log.d(EditNoteActivity.this.getLocalClassName(),"==>ns1 char start:"+ x_str.length());
        }
        Log.d(EditNoteActivity.this.getLocalClassName(),"==>"+spans.length);
    }

    public List<TaskSpan> getNoteData(){
        ImageSpan[] spans = et_input.getText().getSpans(0,et_input.length(),ImageSpan.class);
        List<TaskSpan> nses = new ArrayList<TaskSpan>();

        if(spans==null||spans.length==0){
            if(StringUtils.isEmpty(et_input.getText().toString())){
                return null;
            }
            TaskSpan tx_ns =new TaskSpan();
            tx_ns.setType(AppConfig.TYPE_SPAN_TEXT);
            tx_ns.setThunm(et_input.getText().toString());
            nses.add(tx_ns);
            return nses;
        }

        for(int i=0;i<spans.length;i++){//循环添加span
            int sp_start = et_input.getText().getSpanStart(spans[i]);
            if(i==0){//添加第一个span前的文字
                if(sp_start != 0){
                    TaskSpan tx_ns =new TaskSpan();
                    tx_ns.setType(AppConfig.TYPE_SPAN_TEXT);
                    tx_ns.setThunm(et_input.getText().subSequence(0,sp_start).toString());
                    nses.add(tx_ns);
                    continue;
                }
            }
            int old_endt = et_input.getText().getSpanStart(spans[i-1]);
            if(sp_start>old_endt){//添加当前的span
                TaskSpan tx_ns =new TaskSpan();
                tx_ns.setType(AppConfig.TYPE_SPAN_TEXT);
                tx_ns.setThunm(et_input.getText().subSequence(old_endt,sp_start).toString());
                nses.add(tx_ns);
            }
            TaskSpan ns_img = new TaskSpan();
            ns_img.setType(AppConfig.TYPE_SPAN_IMAGE);
            nses.add(ns_img);
        }
        int final_end = et_input.getText().getSpanEnd(spans[spans.length-1]);
        if(final_end<et_input.getText().length()){//添加最后的文字
            TaskSpan final_ns =new TaskSpan();
            final_ns.setType(AppConfig.TYPE_SPAN_TEXT);
            final_ns.setThunm(et_input.getText().subSequence(final_end,et_input.getText().length()).toString());
            nses.add(final_ns);
        }
        return nses;
    }
}

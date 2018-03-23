package com.lxvoice.domain.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lxvoice.domain.R;
import com.lxvoice.domain.config.AppConfig;
import com.lxvoice.domain.entity.NoteContent;
import com.lxvoice.domain.entity.NoteSpan;
import com.lxvoice.domain.entity.TaskSpan;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by psp on 2018/3/15.
 */

public class RicEditUtils {

    public static void setEditContent(Context context, EditText editText,NoteContent note){
        String str = note.getText();

        List<TaskSpan> spans = note.getSpans();

        for (TaskSpan taskSpan: spans){
            SpannableString spanString = new SpannableString(" ");
            Drawable d = context.getResources().getDrawable(R.mipmap.ic_launcher);
            d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
            ImageSpan span = new ImageSpan(d, ImageSpan.ALIGN_BASELINE);
            spanString.setSpan(span, 0,1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            editText.append("\n");
            editText.append(spanString);
            editText.append("\n");
        }

    }

    public static void setEditContent2(Context context, EditText editText,NoteContent note){
        editText.setText("");
        String str = note.getText();

        List<TaskSpan> spans = note.getSpans();

        //editText.setText(str==null?"":str);
        int spans_length = spans==null?0:spans.size();
        for (int i = 0;i<spans_length;i++){
            if(i==0){
                editText.append(getText(str,null, spans.get(i)));
            }else{
                editText.append(getText(str, spans.get(i-1), spans.get(i)));
            }
            SpannableString spanString = new SpannableString(" ");
            Drawable d = context.getResources().getDrawable(R.mipmap.ic_launcher);
            d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
            ImageSpan span = new ImageSpan(d, ImageSpan.ALIGN_BASELINE);
            spanString.setSpan(span, 0,1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            editText.append("\n");
            editText.append(spanString);
            editText.append("\n");
        }
        editText.append(str.substring(spans.get(spans.size()-1).getStart(),str.length()));

    }
    public static void setEditContent3(Context context, EditText editText,List<TaskSpan> spans){
        editText.setText("");
        //editText.setText(str==null?"":str);
        int spans_length = spans==null?0:spans.size();
        for (int i = 0;i<spans_length;i++){
            if(spans.get(i).getType()== AppConfig.TYPE_SPAN_TEXT){
                editText.append(spans.get(i).getThunm());
            }else{
                SpannableString spanString = new SpannableString(" ");
                Drawable d = context.getResources().getDrawable(R.mipmap.ic_launcher);
                d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
                ImageSpan span = new ImageSpan(d, ImageSpan.ALIGN_BASELINE);
                spanString.setSpan(span, 0,1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                editText.append(spanString);
            }

        }

    }

    private static String getText(String str, TaskSpan oldSpan, TaskSpan newSpan){
        if(oldSpan==null){
            return str.substring(0,newSpan.getStart());
        }
        if(newSpan.getStart()<=oldSpan.getEnd()){
            return "";
        }else{
            String str_f = str.substring(oldSpan.getStart(),newSpan.getStart());
            return str_f;
        }
    }

   /* public static NoteSpan getAudioSpan(Context context, int type, String json, String time, int progress) {
        View spanView = View.inflate(context, R.layout.bbs_audio_bar_tag, null);
        LinearLayout llBg = (LinearLayout) spanView.findViewById(R.id.ll_bg);
        ImageView icPlay = (ImageView) spanView.findViewById(R.id.iv_play);
        ImageView icStop = (ImageView) spanView.findViewById(R.id.iv_stop);
        TextView tvTime = (TextView) spanView.findViewById(R.id.tv_time);
        ProgressBar proBar = (ProgressBar) spanView.findViewById(R.id.progress_bar);

        switch (type) {
            case AUDIO_PLAY_NONE:
                try {
                    final String[] split = json.split(BBSConstants.SPLIT_TAG);
                    JSONObject obj = new JSONObject(split[1]);
                    final JSONObject data = obj.optJSONObject(Constants.RETURN_DATA);
                    int duration = data.optInt(BBSConstants.LONG_DATA_DURATION);

                    tvTime.setText(DateUtil.getDurationTime(duration / 1000, false));
                    proBar.setProgress(0);
                    icPlay.setVisibility(View.VISIBLE);
                    icStop.setVisibility(View.GONE);
                    llBg.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.grey_bg_50dp_corner_no_border));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;

            case AUDIO_PLAY_ING:
                proBar.setProgress(progress);
                icPlay.setVisibility(View.GONE);
                icStop.setVisibility(View.VISIBLE);
                llBg.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.blue_bg_50dp_corner_no_border));
                tvTime.setText(time);
                break;
        }

        BitmapDrawable drawable = (BitmapDrawable) ViewUtil.convertViewToDrawable(spanView);
        drawable.setTargetDensity(MyApplication.getInstance().getResources().getDisplayMetrics());
        final float scale = 1.0f / 6.0f;
        final int width = DeviceUtil.getScreenWidth((Activity) context) - DeviceUtil.dip2px(context, LENGTH);
        float height = (float) width * scale;
        drawable.setBounds(0, 0, width, (int) height);
        return new NoteSpan(drawable, type, json);

    }*/
}

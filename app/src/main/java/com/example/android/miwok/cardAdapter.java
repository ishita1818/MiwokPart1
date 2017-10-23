package com.example.android.miwok;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ishita sharma on 10/22/2017.
 */

class cardAdapter extends RecyclerView.Adapter<cardAdapter.ViewHolder> {
    private ArrayList<Word> wordArrayList;
    private int colorid;
    private Context context;
    private AudioManager audioManager;
    public static MediaPlayer mMediaplayer;
    private int audio;
    public static AudioManager mAudioManager;
    private RecyclerView recyclerView;

    private static AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener(){
        @Override
        public void onAudioFocusChange(int FocusChange) {

            if(FocusChange==AudioManager.AUDIOFOCUS_LOSS_TRANSIENT||FocusChange==AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK){
                mMediaplayer.pause();
                mMediaplayer.seekTo(0);

            }
            else if(FocusChange==AudioManager.AUDIOFOCUS_GAIN){
                mMediaplayer.start();
            }
            else if(FocusChange==AudioManager.AUDIOFOCUS_LOSS){
                releseMediaPlayer();
            }
        }
    };

    public cardAdapter(Context context, ArrayList<Word> words, int colorid,RecyclerView r){
    this.context=context;
        this.colorid=colorid;
        wordArrayList=words;
        recyclerView=r;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView cardView = (CardView) LayoutInflater.from(context).inflate(R.layout.new_item_layout,parent,false);
        ViewHolder viewHolder= new ViewHolder(cardView);
        return viewHolder;
    }

    public  static void releseMediaPlayer(){
        if(mMediaplayer!=null) {
            mMediaplayer.release();

            mMediaplayer = null;
            mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
        }
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
              Word word= wordArrayList.get(position);
        holder.imageView.setImageResource(word.getImageResourceid());
        holder.textView.setText(word.getMiwok());
        holder.englishTextView.setText(word.getDefaultWord());
        //holder.englishTextView.setTextColor(ContextCompat.getColor(context,colorid));
        holder.cardView.setBackgroundColor(ContextCompat.getColor(context,colorid));
        //recyclerView.setBackgroundColor(ContextCompat.getColor(context,colorid));
        holder.linearlayoutInCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                releseMediaPlayer();
                audio= wordArrayList.get(position).getAudioResourceId();

                int result= mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener,
                        AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if(result== AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {

                    mMediaplayer = MediaPlayer.create(context, wordArrayList.get(position).getAudioResourceId());
                    mMediaplayer.start();
                    mMediaplayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mediaplayer) {
                            releseMediaPlayer();
                        }
                    });
            }
        }
        });
        holder.linearlayoutInCardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                animate(view,holder);
                return true;
            }
        });
    }
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void animate(final View view, final ViewHolder holder) {
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP) {
        int cx= holder.englishTextView.getWidth()/2;
        int cy= holder.englishTextView.getHeight()/2;
        float finalRadius = (float)Math.hypot(cx,cy);

            Animator animator = ViewAnimationUtils.createCircularReveal(view, cx, cy, 0, finalRadius);

            animator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {
                    //view.setVisibility(View.GONE);
                    //
                    //holder.englishTextView.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    holder.englishTextView.setVisibility(View.VISIBLE);
                    view.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationRepeat(Animator animator) {
                    view.setVisibility(View.VISIBLE);
                    holder.englishTextView.setVisibility(View.GONE);
                }
            });
            animator.setDuration(3000);
            animator.start();
        }
    }

    @Override
    public int getItemCount() {
        return wordArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView textView,englishTextView;
        public ImageView imageView;
        public CardView cardView;
        public LinearLayout linearlayoutInCardView;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.text_1_view);
            imageView=(ImageView) itemView.findViewById(R.id.image_view);
            cardView=(CardView) itemView.findViewById(R.id.card_view);
            englishTextView=(TextView)itemView.findViewById(R.id.english_textView);
            linearlayoutInCardView=(LinearLayout)itemView.findViewById(R.id.linear_in_card_layout);
        }
    }
}
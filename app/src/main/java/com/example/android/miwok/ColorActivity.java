package com.example.android.miwok;

import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import static com.example.android.miwok.cardAdapter.releseMediaPlayer;

public class ColorActivity extends AppCompatActivity {


    private RecyclerView recycler_view;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    protected  ArrayList<Word> words;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_layout);

        words = new ArrayList<Word>();

        words.add(new Word("red", "weṭeṭṭi", R.drawable.color_red, R.raw.color_red));
        words.add(new Word("green", "chokokki", R.drawable.color_green, R.raw.color_green));
        words.add(new Word("brown", "ṭakaakki", R.drawable.color_brown, R.raw.color_brown));
        words.add(new Word("gray", "ṭopoppi", R.drawable.color_gray, R.raw.color_gray));
        words.add(new Word("black", "kululli", R.drawable.color_black, R.raw.color_black));
        words.add(new Word("white", "kelelli", R.drawable.color_white, R.raw.color_white));
        words.add(new Word("dusty yellow", "ṭopiisә", R.drawable.color_dusty_yellow, R.raw.color_dusty_yellow));
        words.add(new Word("mustard yellow", "chiwiiṭә", R.drawable.color_mustard_yellow, R.raw.color_mustard_yellow));


        //WordAdapter adapter = new WordAdapter(this,words,R.color.category_colors);

        //ListView listView = (ListView)findViewById(R.id.list);
        recycler_view = (RecyclerView) findViewById(R.id.recycler_layout);

        layoutManager = new LinearLayoutManager(this);
        recycler_view.setLayoutManager(layoutManager);
        mAdapter = new cardAdapter(this, words, R.color.category_colors,recycler_view);
        recycler_view.setAdapter(mAdapter);
        //listView.setAdapter(adapter);
        cardAdapter.mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

    }

    @Override
    protected void onStop() {
        super.onStop();
        releseMediaPlayer();
    }
}

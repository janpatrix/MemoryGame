package com.example.patrickgross.memorygame;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class GameActivity extends Activity implements View.OnClickListener{

    //Sound Samples
    private SoundPool soundPool;
    int sample1 = -1;
    int sample2 = -1;
    int sample3 = -1;
    int sample4 = -1;

    //GUI Elements
    Button button1;
    Button button2;
    Button button3;
    Button button4;
    Button buttonReplay;

    TextView userScore;
    TextView difficultyText;
    TextView watchText;

    //Other variables
    int difficultyLevel = 3;
    int[] sequenceToCopy = new int[100];
    private Handler myHandler;
    boolean playSequence = false;
    int elementToPlay = 0;
    int playerResponses;
    int playerScore;
    boolean isResponding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        //Set Sound
        soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
        try {

            AssetManager assetManager = getAssets();
            AssetFileDescriptor descriptor;
            descriptor = assetManager.openFd("Sample1.ogg");
            sample1 = soundPool.load(descriptor, 0);
            descriptor = assetManager.openFd("Sample2.ogg");
            sample2 = soundPool.load(descriptor, 0);
            descriptor = assetManager.openFd("Sample3.ogg");
            sample3 = soundPool.load(descriptor, 0);
            descriptor = assetManager.openFd("Sample4.ogg");
            sample4 = soundPool.load(descriptor, 0);
        } catch (Exception e) {}

        //Reference UI Elements
        userScore = (TextView)findViewById(R.id.textScore);
        userScore.setText("Score: " + playerScore);
        difficultyText = (TextView)findViewById(R.id.textDifficulty);
        difficultyText.setText("Level: " + difficultyLevel);
        watchText = (TextView)findViewById(R.id.textWatch);

        button1 = (Button)findViewById(R.id.button1);
        button2 = (Button)findViewById(R.id.button2);
        button3 = (Button)findViewById(R.id.button3);
        button4 = (Button)findViewById(R.id.button4);
        buttonReplay = (Button)findViewById(R.id.buttonReplay);

        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        buttonReplay.setOnClickListener(this);

        myHandler = new Handler(){
          public void handleMessage(Message msg){
              super.handleMessage(msg);
              if (playSequence){
                  button1.setVisibility(View.VISIBLE);
                  button2.setVisibility(View.VISIBLE);
                  button3.setVisibility(View.VISIBLE);
                  button4.setVisibility(View.VISIBLE);

                  switch (sequenceToCopy[elementToPlay]){
                      case 1:
                          button1.setVisibility(View.INVISIBLE);
                          soundPool.play(sample1, 1, 1, 0, 0, 1);
                          break;
                      case 2:
                          button2.setVisibility(View.INVISIBLE);
                          soundPool.play(sample2, 1, 1, 0, 0, 1);
                          break;
                      case 3:
                          button3.setVisibility(View.INVISIBLE);
                          soundPool.play(sample3, 1, 1, 0, 0, 1);
                          break;
                      case 4:
                          button4.setVisibility(View.INVISIBLE);
                          soundPool.play(sample4, 1, 1, 0, 0, 1);
                          break;
                  }

                  elementToPlay++;
                  if(elementToPlay == difficultyLevel)
                  {
                      sequenceFinished();
                  }
              }
              myHandler.sendEmptyMessageDelayed(0, 900);
          }
        };

        myHandler.sendEmptyMessage(0);
        playASequence();
    }

    public void createSequence(){
        Random randInt = new Random();
        int ourRandom;
        for(int i = 0; i < difficultyLevel; i++)
        {
            ourRandom = randInt.nextInt(4);
            ourRandom++;
            sequenceToCopy[i] = ourRandom;
        }
    }

    public void playASequence(){
        createSequence();
        isResponding = false;
        elementToPlay = 0;
        playerResponses = 0;
        watchText.setText("Watch");
        playSequence = true;
    }

    public void sequenceFinished(){
        playSequence = false;
        button1.setVisibility(View.VISIBLE);
        button2.setVisibility(View.VISIBLE);
        button3.setVisibility(View.VISIBLE);
        button4.setVisibility(View.VISIBLE);
        watchText.setText("GO!");
        isResponding = true;
    }

    @Override
    public void onClick(View v) {

    }
}


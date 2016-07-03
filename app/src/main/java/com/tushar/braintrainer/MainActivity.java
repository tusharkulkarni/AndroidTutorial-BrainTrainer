package com.tushar.braintrainer;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Button  startButton;
    TextView timerText;
    TextView questionText;
    Random rand = new Random();
    int correctAnswer;
    int totalCount ;
    int correctCount ;
    CountDownTimer countDownTimer;
    Button  button1;
    Button  button2;
    Button  button3;
    Button  button4;
    Button  restartButton;
    Button playAgainButton;
    TextView resultText;
    TextView scoreText;

    public void start(View view){
        startButton.setVisibility(View.INVISIBLE);
        scoreText.setVisibility(View.VISIBLE);
        timerText.setVisibility(View.VISIBLE);
        restartButton.setVisibility(View.VISIBLE);
        questionText.setVisibility(View.VISIBLE);
        scoreText.setVisibility(View.VISIBLE);
        resultText.setVisibility(View.VISIBLE);
        GridLayout answerGrid = (GridLayout)findViewById(R.id.answersGrid);
        answerGrid.setVisibility(View.VISIBLE);
        reset();
        newQuestion();

    }
    public void init(){
        button1 = (Button)findViewById(R.id.button1);
        button2 = (Button)findViewById(R.id.button2);
        button3 = (Button)findViewById(R.id.button3);
        button4 = (Button)findViewById(R.id.button4);
        restartButton = (Button)findViewById(R.id.restartButton);
        scoreText = (TextView) findViewById(R.id.scoreText);
        timerText = (TextView) findViewById(R.id.timerText);
        startButton = (Button)findViewById(R.id.startButton);
        questionText = (TextView)findViewById(R.id.questionText);
        playAgainButton = (Button) findViewById(R.id.playAgainButton);
        resultText = (TextView)findViewById(R.id.resultText);
    }

    public void reset(){

        totalCount = 0;
        correctCount = 0;
        correctAnswer = -1;
        startButton.setVisibility(View.INVISIBLE);
        restartButton.setVisibility(View.VISIBLE);
        resultText.setText("");
        playAgainButton.setVisibility(View.INVISIBLE);
        button1.setEnabled(true);
        button2.setEnabled(true);
        button3.setEnabled(true);
        button4.setEnabled(true);
        scoreText.setText(correctCount + "/" + totalCount);
        if(null != countDownTimer) {
            countDownTimer.cancel();
        }
        countDownTimer = new CountDownTimer(30100, 990) {

            public void onTick(long millisUntilFinished) {
                long remTime = millisUntilFinished / 1000;
                String remTimeString = "";
                if(remTime>9){
                    remTimeString = remTime + "s";
                }else{
                    remTimeString = "0" + remTime + "s";
                }
                timerText.setText(remTimeString);
            }

            public void onFinish() {
                timerText.setText("00s");
                countDownTimer.cancel();
                resultText.setText("Your Score : " + correctCount + "/" + totalCount);
                button1.setEnabled(false);
                button2.setEnabled(false);
                button3.setEnabled(false);
                button4.setEnabled(false);
                playAgainButton.setVisibility(View.VISIBLE);
                restartButton.setVisibility(View.INVISIBLE);

            }
        }.start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

       FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        init();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void newQuestion(){
        int val1 = rand.nextInt(21);
        int val2 = rand.nextInt(21);
        questionText.setText(val1 + " + " + val2);

        ArrayList<Integer> answerList = new ArrayList<Integer>();
        correctAnswer = val1 + val2;

        answerList.add(Integer.valueOf(correctAnswer));
        answerList.add(getRandomAnswer(answerList));
        answerList.add(getRandomAnswer(answerList));
        answerList.add(getRandomAnswer(answerList));
        Collections.shuffle(answerList);

        button1.setText(answerList.get(0).toString());
        button2.setText(answerList.get(1).toString());
        button3.setText(answerList.get(2).toString());
        button4.setText(answerList.get(3).toString());
    }

    public Integer getRandomAnswer(List<Integer> answerList){
        Integer newAnswer = -1;
        while(true){
            newAnswer = Integer.valueOf(rand.nextInt(21) + rand.nextInt(21));
            if(!answerList.contains(newAnswer)){
                break;
            }
        }
        return newAnswer;
    }

    public void submitAnswer(View view){
        Button selectedButtonr = (Button)view;
        int selectedAnswer = Integer.parseInt(selectedButtonr.getText().toString());
        if(selectedAnswer == correctAnswer){
            resultText.setText("Correct :)");
            correctCount++;
        }else{
            resultText.setText("Incorrect :(");
        }
        totalCount++;

        scoreText.setText(correctCount + "/" + totalCount);
        newQuestion();
    }

    public void playAgain(View view){
        playAgainButton.setVisibility(View.INVISIBLE);
        reset();
        newQuestion();

    }

    public void restartGame(View view){
        reset();
        newQuestion();
    }
}

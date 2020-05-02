package cn.edu.nju.cyh.voices;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView text_show;
    private Button mSinWaveButton, mChirpButton;
    private Button mplayButton, mPauseButton;
    private EditText mSinWave, mChirp1, mChirp2;
    private thread mPlayThread = null;

    private enum pattern {sinwave , chirp};
    private pattern p;

    private float mSineVal = 0.0f;
    private float mChirpVal1 = 0.0f;
    private float mChirpVal2 = 0.0f;

    public boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //捕获控件
        text_show = (TextView)findViewById(R.id.label);
        mSinWaveButton = (Button)findViewById(R.id.sinwave_button);
        mChirpButton = (Button)findViewById(R.id.chirp_button);
        mplayButton = (Button)findViewById(R.id.play_button);
        mPauseButton = (Button)findViewById(R.id.pause_button);
        mSinWave = (EditText)findViewById(R.id.freq_input);
        mChirp1 = (EditText)findViewById(R.id.freq_input21);
        mChirp2 = (EditText)findViewById(R.id.freq_input22);
        // 注册监听
        mSinWaveButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                p = pattern.sinwave;
                text_show.setText("Pattern Wave");
                if(flag) mPlayThread.stopPlay();
            }
        });

        mChirpButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                p = pattern.chirp;
                text_show.setText("Pattern Chirp");
                if(flag) mPlayThread.stopPlay();
            }
        });

        mplayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag) mPlayThread.stopPlay();
                switch (p){
                    case sinwave:{
                        String value = mSinWave.getText().toString();
                        if(!value.equals("")){
                            text_show.setText("get the sinWave freq! " + value);
                            mSineVal = Float.parseFloat(value);
                            mSinWave.setText("");
                            flag = true;
                            mPlayThread = new thread(mSineVal);
                            mPlayThread.start();
                        }else {
                            text_show.setText("Please input the Wave freq");
                        }
                        break;
                    }
                    case chirp:{
                        String value1 = mChirp1.getText().toString();
                        if(!value1.equals("")){
                            mChirpVal1 = Float.parseFloat(value1);
                            mChirp1.setText("");
                        }else{
                            text_show.setText("Please input the Chirp1 freq");
                            return;
                        }
                        String value2 = mChirp2.getText().toString();
                        if(!value2.equals("")){
                            mChirpVal2 = Float.parseFloat(value2);
                            mChirp2.setText("");
                        }else{
                            mChirpVal2 = mChirpVal1 + 500;
                            text_show.setText("Chrip1 :" + value1 + "; default Chirp2 value :" + mChirpVal2);
                        }
                        if(mChirpVal1 > mChirpVal2)return;
                        flag = true;
                        mPlayThread = new thread(mChirpVal1, mChirpVal2);
                        mPlayThread.start();
                        break;
                    }
                }
            }
        });
        mPauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag){
                    text_show.setText("Pause");
                    mPlayThread.stopPlay();
                    flag = false;
                }
            }
        });
    }

}

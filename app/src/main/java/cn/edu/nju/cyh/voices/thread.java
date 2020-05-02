package cn.edu.nju.cyh.voices;

/**
 * This file was used for signal generation
 * Output: SinWave or Chirp
 * @auther cyh
 */

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.util.Log;

import static android.content.ContentValues.TAG;

public class thread extends Thread{
    public static final int RATE = 44100;
    public static boolean isPlaying = false;
    private AudioTrack mAudioTrack;

    private int length;
    private float waveLen;
    private float Hz;
    public byte[] wave;

    public thread(float rate) {
        if (rate > 0) {
            Hz = rate;
            waveLen = RATE / Hz;
            length =(int)(waveLen * Hz);
            wave = new byte[RATE];
            mAudioTrack = new AudioTrack(AudioManager.STREAM_MUSIC, RATE,
                    AudioFormat.CHANNEL_CONFIGURATION_MONO,//CHANNEL_CONFIGURATION_STEREO
                    AudioFormat.ENCODING_PCM_16BIT, length, AudioTrack.MODE_STREAM);
            isPlaying = true;
            wave = Wave.sin(wave, waveLen, length);
        } else {
            Log.d("rate","negative");
        }
    }

    public thread(float rate1, float rate2) {
        if (rate1 > 0) {
            wave = new byte[2206];
            length = 2206;
            mAudioTrack = new AudioTrack(AudioManager.STREAM_MUSIC, RATE,
                    AudioFormat.CHANNEL_CONFIGURATION_MONO,//CHANNEL_CONFIGURATION_STEREO
                    AudioFormat.ENCODING_PCM_16BIT, length, AudioTrack.MODE_STREAM);
            isPlaying = true;
            wave = Wave.chirp(wave, rate1, rate2);
        } else {
            Log.d("rate","negative");
        }
    }

    @Override
    public void run() {
        super.run();
        if (null != mAudioTrack)
            mAudioTrack.play();
        while (isPlaying) {
            try {
                mAudioTrack.write(wave, 0, length);
            }catch (Exception e){
                Log.d(TAG, "run: narrow pass");
            }
        }
    }

    public void stopPlay() {
        if(isPlaying){
            isPlaying = false;
            releaseAudioTrack();
        }
    }

    private void releaseAudioTrack() {
        if (null != mAudioTrack) {
            mAudioTrack.stop();
            mAudioTrack.release();
            mAudioTrack = null;
        }
    }

    public static short byteToShort(byte[] b) {
        short s = 0;
        short s0 = (short) (b[0] & 0xff);
        short s1 = (short) (b[1] & 0xff);
        s1 <<= 8;
        s = (short) (s0 | s1);
        return s;
    }

    public static short[] byte2short(byte[] wave) {
        int len = wave.length / 2;
        int index = 0;
        short[] trans = new short[len];
        byte[] buf = new byte[2];
        for (int i = 0; i < wave.length;) {
            buf[0] = wave[i];
            buf[1] = wave[i + 1];
            short media = byteToShort(buf);
            trans[index] = media;
            index++;
            i += 2;
        }
        return trans;
    }
}

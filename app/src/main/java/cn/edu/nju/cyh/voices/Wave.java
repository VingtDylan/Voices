package cn.edu.nju.cyh.voices;

public class Wave {
    private static final int HEIGHT = 255;
    private static final double TwoPi = 2 * Math.PI;


    public static byte[] sin(byte[] wave, float waveLen, int length) {
        for (int i = 0; i < length; i++) {
            wave[i] = (byte) (HEIGHT * (Math.sin(TwoPi * ((i % waveLen) * 1.00 / waveLen))));
        }
        return wave;
    }

    public static byte[] chirp(byte[] wave, float Hz1, float Hz2) {
        int length = 5 * 441 + 1;
        double K = (Hz2 - Hz1)/ 0.05;
        for (int i = 0; i < length; i++) {
            double t = i / (length * 1.0) * 0.05;
            wave[i] = (byte) (HEIGHT  * (Math.cos(TwoPi * Hz1 * t + Math.PI * K * t * t)));
        }
        return wave;
    }
}

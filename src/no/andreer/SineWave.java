package no.andreer;

import javax.sound.sampled.AudioFormat;

public class SineWave implements Source {
    AudioFormat af;
    double angle = 0d;

    public SineWave(AudioFormat audioFormat, Source frequencySource) {
        this.af = audioFormat;
    }

    public byte[] next(int size) {
        byte[] sample = new byte[size];
        for (int i = 0; i < size; i++) {
            sample[ i ] = 0;
        }
        return sample;
    }

    public double foo() {
        double sin = Math.sin(angle);

        double frequency = 440;

        double v = (frequency * 2.0 * Math.PI) / af.getSampleRate();
        angle += v;

        return sin;
    }
}
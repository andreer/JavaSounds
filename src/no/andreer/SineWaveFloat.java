package no.andreer;

import javax.sound.sampled.AudioFormat;

public class SineWaveFloat implements Source {
    AudioFormat af;
    double angle = 0d;

    public SineWaveFloat(AudioFormat audioFormat, Source frequencySource) {
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

        double frequency = 441.3;

        double v = (frequency * 2.0 * Math.PI) / af.getSampleRate();
        angle += v;

        return sin;
    }
}
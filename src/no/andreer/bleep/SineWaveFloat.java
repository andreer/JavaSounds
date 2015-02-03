package no.andreer.bleep;

import no.andreer.Source;

import javax.sound.sampled.AudioFormat;

public class SineWaveFloat implements Source, Input {
    AudioFormat af;
    double angle = 0d;
    private double frequency;
    private double v;

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

    @Override
    public double foo() {
        double sin = Math.sin(angle);

        angle += v;

        return sin;
    }

    public void setFrequency(double frequency) {
        v = (frequency * 2.0 * Math.PI) / af.getSampleRate();
    }
}
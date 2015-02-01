package no.andreer;

import javax.sound.sampled.AudioFormat;

public class SquareWave implements Source {
    int frequency;
    int period;
    int n;
    int amplitude;
    AudioFormat af;

    public SquareWave(AudioFormat audioFormat, int frequency, int amplitude) {
        this.frequency = frequency;
        this.af = audioFormat;
        setFrequency(frequency);
        this.amplitude = amplitude;
    }

    public void setFrequency(int frequency) {
        this.period = (int) af.getSampleRate() / frequency;
    }

    public byte[] next(int size) {
        byte[] sample = new byte[size];
        for (int i = 0; i < size; i++) {
            sample[i] = (byte) (n < period ? amplitude : -amplitude);
            n++;
            if(n >= 2 * period) n = 0;
        }
        return sample;
    }
}
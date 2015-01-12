package no.andreer;

import javax.sound.sampled.AudioFormat;

public class SquareWave implements Source {
    int frequency;
    int period;
    int n;
    int amplitude;

    public SquareWave(AudioFormat audioFormat, int frequency, int amplitude) {
        this.frequency = frequency;
        this.period = (int) audioFormat.getSampleRate() / frequency;
        this.amplitude = amplitude;
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
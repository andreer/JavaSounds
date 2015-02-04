package no.andreer.bleep;

import javax.sound.sampled.AudioFormat;

public class SquareWave implements Input {
    double frequency;
    double period;
    double n;
    AudioFormat af;

    public SquareWave(AudioFormat audioFormat, double frequency) {
        this.frequency = frequency;
        this.af = audioFormat;
        setFrequency(frequency);
    }

    public void setFrequency(double frequency) {
        this.period = af.getSampleRate() / frequency;
    }

    @Override
    public double sample() {
        double sample = (n < period) ? 1.0d : -1.0d;
        n++;
        if (n >= 2 * period)
            n -= 2 * period;

        return sample;
    }
}
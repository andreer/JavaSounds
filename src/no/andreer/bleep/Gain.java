package no.andreer.bleep;

public class Gain implements Input {

    final Input input;
    final Input gain;

    public Gain(Input input, Input gain) {
        this.input = input;
        this.gain = gain;
    }

    @Override
    public double sample() {
        return input.sample() * gain.sample();
    }
}

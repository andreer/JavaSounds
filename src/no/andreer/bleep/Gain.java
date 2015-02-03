package no.andreer.bleep;

public class Gain implements Input {

    final Noise input;
    double gain;

    public Gain(Noise input, double gain) {
        this.input = input;
        this.gain = gain;
    }

    @Override
    public double foo() {
        return input.foo() * gain;
    }
}

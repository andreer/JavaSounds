package no.andreer.bleep;

public class DoubleGain {

    final DoubleNoise input;
    double gain;

    public DoubleGain(DoubleNoise input, double gain) {
        this.input = input;
        this.gain = gain;
    }

    public double foo() {
        return input.foo() * gain;
    }
}

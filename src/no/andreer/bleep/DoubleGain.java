package no.andreer.bleep;

public class DoubleGain implements Input {

    final DoubleNoise input;
    double gain;

    public DoubleGain(DoubleNoise input, double gain) {
        this.input = input;
        this.gain = gain;
    }

    @Override
    public double foo() {
        return input.foo() * gain;
    }
}

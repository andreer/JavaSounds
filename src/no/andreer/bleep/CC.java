package no.andreer.bleep;

public class CC implements Input {

    double value = 0;

    @Override
    public double sample() {
        return value;
    }
}

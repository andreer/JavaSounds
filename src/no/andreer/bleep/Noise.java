package no.andreer.bleep;

import java.util.Random;

public class Noise implements Input {

    Random random = new Random();

    @Override
    public double foo() {
        return (random.nextDouble() * 2)-1;
    }
}

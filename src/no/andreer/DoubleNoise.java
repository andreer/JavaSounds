package no.andreer;

import java.util.Random;

public class DoubleNoise {

    Random random = new Random();

    public double foo() {
        return (random.nextDouble() * 2)-1;
    }
}

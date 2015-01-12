package no.andreer;

import java.util.Random;

public class Noise implements Source {

    Random random = new Random();

    @Override
    public byte[] next(int size) {
        byte[] sample = new byte[size];
        random.nextBytes(sample);
        return sample;
    }
}

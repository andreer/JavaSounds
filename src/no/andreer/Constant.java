package no.andreer;

import java.util.Arrays;

public class Constant implements Source {
    private byte constant;

    public Constant(byte constant) {
        this.constant = constant;
    }

    @Override
    public byte[] next(int size) {
        byte[] next = new byte[size];
        Arrays.fill(next, constant);
        return next;
    }
}

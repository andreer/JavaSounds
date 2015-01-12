package no.andreer;

public class Gain implements Source {

    final Source input;
    double gain;

    public Gain(Source input, double gain) {
        this.input = input;
        this.gain = gain;
    }

    @Override
    public byte[] next(int size) {
        byte[] bytes = input.next(size);
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] *= gain;
        }
        return bytes;
    }
}

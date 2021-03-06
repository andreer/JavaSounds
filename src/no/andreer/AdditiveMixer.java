package no.andreer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AdditiveMixer implements Source {
    List<Source> inputs = new ArrayList<>();

    AdditiveMixer(Source... sources) {
        inputs.addAll(Arrays.asList(sources));
    }

    public byte[] next(int size) {
        byte[] sample = new byte[size];

        for (Source input : inputs) {
            byte[] bytes = input.next(size);
            for (int i = 0; i < bytes.length; i++) {
                sample[i] += bytes[i];
            }
        }
        return sample;
    }
}

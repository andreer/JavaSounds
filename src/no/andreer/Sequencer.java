package no.andreer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Sequencer implements Source {
    List<Source> inputs = new ArrayList<>();

    public int period; // .25s
    int n;
    int inputIndex = 0;

    Sequencer(int period, Source... sources) {
        inputs.addAll(Arrays.asList(sources));
        this.period = period;
    }

    public byte[] next(int size) {
        if (n >= period) {
            inputIndex = (inputIndex + 1) % inputs.size();
            n = 0;
        }
        n += size;
        return inputs.get(inputIndex).next(size);
    }
}

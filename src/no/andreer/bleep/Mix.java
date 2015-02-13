package no.andreer.bleep;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Mix implements Input {
    List<Input> inputList = new ArrayList<>();

    Mix(Input... inputs) {
        inputList.addAll(Arrays.asList(inputs));
    }

    @Override
    public double sample() {
        return inputList.stream().mapToDouble(Input::sample).average().orElse(0);
    }
}

package no.andreer.bleep;

import java.util.LinkedList;

public class MovingAverageFilter implements Input {

    private final Input input;
    private final Input winSize;

    LinkedList<Double> samples = new LinkedList<>();

    public MovingAverageFilter(Input input, Input winSize) {
        this.input = input;
        this.winSize = winSize;
    }

    @Override
    public double sample() {
        samples.addLast(input.sample());
        double winSize = this.winSize.sample();
        while(samples.size() > winSize) {
            samples.removeFirst();
        }

        return samples.stream().mapToDouble(i -> i).average().orElse(0);
    }
}

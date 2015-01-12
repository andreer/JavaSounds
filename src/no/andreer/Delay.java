package no.andreer;

import java.util.LinkedList;

public class Delay implements Source {

    Source input;
    int behind;
    LinkedList<byte[]> loop = new LinkedList<>();

    public Delay(Source input, int behind) {
        this.input = input;
        this.behind = behind;
    }

    @Override
    public byte[] next(int size) {
        if(loop.isEmpty()) {
            for (int i = 0; i < behind; i++) {
                loop.addLast(new byte[size]);
            }
        }

        loop.addLast(input.next(size));
        return loop.pop();
    }
}

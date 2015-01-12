package no.andreer;

public class Split implements Source {

    int n = 1;
    int p = 0;
    byte[] last;
    final Source input;

    public Split(Source input) {
        this.input = input;
    }

    @Override
    public byte[] next(int size) {
        if(p == 0) {
            last = input.next(size);
            p = n;
            return last;
        } else {
            p--;
            return last;
        }
    }
}

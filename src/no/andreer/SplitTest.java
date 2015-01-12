package no.andreer;

import org.junit.Test;

import java.util.Arrays;

public class SplitTest {

    @Test
    public void testNext() throws Exception {
        Split split = new Split(new Sequencer(1, new Constant((byte) 1), new Constant((byte) 2)));

        System.out.println(Arrays.toString(split.next(1)));
        System.out.println(Arrays.toString(split.next(1)));
        System.out.println(Arrays.toString(split.next(1)));
        System.out.println(Arrays.toString(split.next(1)));
        System.out.println(Arrays.toString(split.next(1)));
        System.out.println(Arrays.toString(split.next(1)));
        System.out.println(Arrays.toString(split.next(1)));
        System.out.println(Arrays.toString(split.next(1)));
    }
}
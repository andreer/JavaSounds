package no.andreer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class PowerMate implements Runnable, Source {
    static int min = 1;
    static int max = 880*2;
    static int now = 220;

    final SquareWave s;

    public PowerMate(SquareWave s) {
        this.s = s;
    }

    public void run() {
        Process process = null;
        try {
            process = Runtime.getRuntime().exec("/home/andreas/Software/powermate-1.1-llk/rotomatic /dev/input/by-id/usb-Griffin_Technology__Inc._Griffin_PowerMate-event-if00");
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert process != null;
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        try {
            while((line = bufferedReader.readLine()) != null) {
                int i = Integer.parseInt(line.split(" ")[3]);

                now = now + i;

                if(now >= max)
                    now = max;
                if(now <= min)
                    now = min;

                s.setFrequency(now);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public byte[] next(int size) {
        byte[] next = new byte[size];
        Arrays.fill(next, (byte) now);
        return next;    }
}

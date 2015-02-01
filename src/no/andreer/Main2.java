package no.andreer;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

public class Main2 {

    static double twlft_2 = 1.059463;

    public static int tone(int n) {
        return (int) (440.0 * Math.pow(twlft_2, n));
    }

    public static void main(String[] args) throws LineUnavailableException {
        int sampleRate = 44100;
        AudioFormat af = new AudioFormat(sampleRate, 8, 1, true, false);

        SourceDataLine sdl = AudioSystem.getSourceDataLine(af);

        sdl.open(af);
        sdl.start();

        Scope scope = new Scope();

        SquareWave sw = new SquareWave(af, 440, 100);
        PowerMate mix = new PowerMate(sw);
        Thread t = new Thread(mix);
        t.start();

        BetterMixer mix2 = new BetterMixer(sw, new Gain(new Noise(), .1));


        int lengthInMs = 12000;

        int sampleSize = 400; // ~1ms

        int numSamples = 999999999; //lengthInMs * sampleRate / 1000 / sampleSize;

        for (int i = 0; i < numSamples; i++) {
            byte[] d = mix2.next(sampleSize);
            int offset = 0;
            sdl.write(d, offset, d.length);
            if(i%10==0)
            scope.update(d);
        }

        sdl.drain();
        sdl.stop();
        sdl.close();

//        scope.close();
    }
}

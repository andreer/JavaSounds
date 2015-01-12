package no.andreer;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

public class Main {

    private static double E = 1.189207;
    private static Constant
            Silence = new Constant((byte) 0);

    public static void main(String[] args) throws LineUnavailableException {
        int sampleRate = 44100;
        AudioFormat af = new AudioFormat(sampleRate, 8, 1, true, false);

        System.out.println(af.getFrameSize());

        SourceDataLine sdl = AudioSystem.getSourceDataLine(af);

        sdl.open(af);
        sdl.start();

        Scope scope = new Scope();

        // "drums"

        Sequencer drumBeat = new Sequencer(sampleRate / 8,
                new Noise(),
                Silence,
                Silence,
                Silence
        );

        Sequencer drumMachine = new Sequencer(sampleRate / 4,
                drumBeat,
                Silence,
                Silence,
                Silence,
                drumBeat,
                Silence,
                drumBeat,
                Silence
        );

        Gain drums = new Gain(drumMachine, 0.3);

        // "music"

        SquareWave squareWave = new SquareWave(af, 110, 100);
        SquareWave squareWave2 = new SquareWave(af, 220, 100);

        Sequencer sequencer = new Sequencer(sampleRate / 4, squareWave, squareWave2);

        SquareWave squareWave3 = new SquareWave(af, (int) (110 * E * E), 100);
        SquareWave squareWave4 = new SquareWave(af, (int) (220 * E * E), 100);

        Source sequencer2 = new Sequencer(sampleRate / 8, squareWave3, squareWave4);

        Sequencer superSequencer = new Sequencer(sampleRate,
                sequencer,
                sequencer2,
                sequencer,
                sequencer2,
                sequencer,
                new Sequencer(sampleRate / 8, squareWave, squareWave2));

        Split superSeqSplit = new Split(superSequencer);
        Delay superSeqDelay = new Delay(superSeqSplit, 10);

        Source additiveMixer = new AdditiveMixer(superSeqSplit, new Gain(superSeqDelay, 0.4), drums);

        Gain gain = new Gain(additiveMixer, 0.7);

        int lengthInMs = 12000;

        int sampleSize = 400; // ~1ms

        int numSamples = lengthInMs * sampleRate / 1000 / sampleSize;

        for (int i = 0; i < numSamples; i++) {
            byte[] b = gain.next(sampleSize);
            int offset = 0;
            sdl.write(b, offset, b.length);
            if(i%10==0)
            scope.update(b);
        }

        sdl.drain();
        sdl.stop();
        sdl.close();
    }
}

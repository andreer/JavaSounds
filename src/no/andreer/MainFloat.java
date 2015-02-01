package no.andreer;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import java.nio.ByteBuffer;

public class MainFloat {

    static double twlft_2 = 1.059463;

    public static double tone(double n) {
        return 440.0 * Math.pow(twlft_2, n);
    }

    public static void main(String[] args) throws LineUnavailableException {
        int sampleRate = 48000;
        AudioFormat af = new AudioFormat(sampleRate, 16, 1, true, true);

        SourceDataLine sdl = AudioSystem.getSourceDataLine(af);

        sdl.open(af);
        sdl.start();

        int sampleSize = 400; // ~1ms

        int numSamples = 200; //lengthInMs * sampleRate / 1000 / sampleSize;

        SineWaveFloat sin = new SineWaveFloat(af, new Constant((byte) 127));

        DoubleGain ditheringNoise = new DoubleGain(new DoubleNoise(), 0.001);

        ByteBuffer b = ByteBuffer.allocate(Short.BYTES * sampleSize);
        for (int i = 0; i < numSamples; i++) {
            for (int j = 0; j < sampleSize; j++) {
                double foo = sin.foo();// + ditheringNoise.foo();
                int value = (int) (foo*Short.MAX_VALUE);
                if (value > Short.MAX_VALUE) {
                    value = Short.MAX_VALUE;
                    System.err.println("CLIP");
                }
                if (value < Short.MIN_VALUE) {
                    value = Short.MIN_VALUE;
                    System.err.println("CLIP");
                }
                b.putShort((short) value);
            }
            int offset = 0;
            sdl.write(b.array(), offset, b.limit());
            b.clear();
        }

//        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(b.array());
//        AudioInputStream audioInputStream = new AudioInputStream(byteArrayInputStream, af, 400 * 200);
//        try {
//            AudioSystem.write(audioInputStream, AudioFileFormat.Type.WAVE, new File("outMax2.wav"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        sdl.drain();
        sdl.stop();
        sdl.close();

//        scope.close();
    }
}

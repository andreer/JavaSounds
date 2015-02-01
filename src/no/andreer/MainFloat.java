package no.andreer;

import javax.sound.midi.*;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.xml.bind.DatatypeConverter;
import java.nio.ByteBuffer;

public class MainFloat {

    static double twlft_2 = 1.059463;
    private static int NOTE_ON_CHANNEL_0;

    public static double tone(double n) {
        return (440.0 / 64) * Math.pow(twlft_2, n);
    }

    public static void main(String[] args) throws LineUnavailableException, MidiUnavailableException, InterruptedException {
        int sampleRate = 48000;
        AudioFormat af = new AudioFormat(sampleRate, 16, 1, true, true);

        SourceDataLine sdl = AudioSystem.getSourceDataLine(af);

        sdl.open(af);
        sdl.start();

        int sampleSize = 400; // ~1ms

        SineWaveFloat sin = new SineWaveFloat(af, new Constant((byte) 127));

        DoubleGain ditheringNoise = new DoubleGain(new DoubleNoise(), 0.001);

        MyMidiDeviceReceiver receiver = new MyMidiDeviceReceiver();
        MidiSystem.getTransmitter().setReceiver(receiver);

        ByteBuffer b = ByteBuffer.allocate(Short.BYTES * sampleSize);
        while (true) {
            for (int j = 0; j < sampleSize; j++) {

                if(receiver.currentNote == -1) {
                    sin.setFrequency(0.001); //silence..?
                } else {
                    sin.setFrequency(tone(receiver.currentNote));
                }
                double foo = sin.foo();// + ditheringNoise.foo();

                int value = (int) (foo * Short.MAX_VALUE);
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
            if(1 > 2) {
                break;
            }
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

    private static class MyMidiDeviceReceiver implements MidiDeviceReceiver {

        byte currentNote = -1;

        @Override
        public MidiDevice getMidiDevice() {
            return null;
        }

        @Override
        public void send(MidiMessage message, long timeStamp) {
            NOTE_ON_CHANNEL_0 = 0x90;
            if (message.getStatus() == NOTE_ON_CHANNEL_0) {
                if (message.getLength() < 3) {
                    System.err.println("unexpected size message: " + DatatypeConverter.printHexBinary(message.getMessage()));
                } else {
                    byte[] bytes = message.getMessage();
                    byte note = bytes[1];
                    byte velocity = bytes[2];

                    switch (velocity) {
                        case 0:
                            if (note == currentNote)
                                currentNote = -1;
                            break;
                        default:
                            currentNote = note;
                    }

                    System.err.println("note = " + note + ", velocity = " + velocity);
                }
            } else {
                System.err.println("Unknown midi message: " + DatatypeConverter.printHexBinary(message.getMessage()));
            }
        }

        @Override
        public void close() {
        }
    }
}

package chatgpt;

import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.transform.DftNormalization;
import org.apache.commons.math3.transform.FastFourierTransformer;

import javax.sound.sampled.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import static org.apache.commons.math3.transform.TransformType.FORWARD;

public class Sounder {

    public static void main(String... args) throws LineUnavailableException {
        AudioFormat audioFormat = new AudioFormat(
                AudioFormat.Encoding.PCM_SIGNED, // Encoding technique
                44100, // Sample rate
                16, // Sample size in bits
                2, // Number of channels (1 for mono, 2 for stereo)
                4, // Frame size
                44100, // Frame rate
                false // Big-endian or little-endian
        );
        TargetDataLine line = (TargetDataLine) AudioSystem.getLine(new DataLine.Info(TargetDataLine.class, audioFormat));
//        line.getControl(Control.Type)
        line.open(audioFormat);

        line.start(); // Start capturing
        System.out.println("Recording started.");

// Define the buffer size for reading audio data
        int bufferSize = 4096;
        byte[] buffer = new byte[bufferSize];

        // Define the range of frequencies you want to monitor
        double minFrequency = 1000; // Minimum frequency (in Hz)
        double maxFrequency = 20000; // Maximum frequency (in Hz)

// Set up the Fast Fourier Transform (FFT)
        int fftSize = 8192; // FFT size
        double sampleRate = audioFormat.getSampleRate();
        FastFourierTransformer fft = new FastFourierTransformer(DftNormalization.STANDARD);
        double[] audioData = new double[fftSize];

// Calculate the frequency bin range to monitor
        int minBin = (int) Math.round(minFrequency * fftSize / sampleRate);
        int maxBin = (int) Math.round(maxFrequency * fftSize / sampleRate);


// Create a FileOutputStream to write the captured audio to a file
        File audioFile = new File("output.wav");
        try (OutputStream outputStream = new FileOutputStream(audioFile)) {
            // Continuously read data from the TargetDataLine and write to the file
            while (true) {

                // Read audio data from the TargetDataLine
                int bytesRead = line.read(buffer, 0, buffer.length);

                // Convert the byte array to an array of doubles
                for (int i = 0; i < bytesRead / 2; i++) {
                    audioData[i] = (double) ((buffer[2 * i] & 0xFF) | (buffer[2 * i + 1] << 8)) / 32767.0;
                }

                // Perform the FFT
                Complex[] fftResult = fft.transform(audioData, FORWARD);

                // Calculate the magnitude spectrum
                double[] magnitude = new double[fftSize / 2];
                for (int i = 0; i < fftSize / 2; i++) {
                    magnitude[i] = Math.sqrt(fftResult[i].getReal() * fftResult[i].getReal() +
                            fftResult[i].getImaginary() * fftResult[i].getImaginary());
                }

                // Check if the magnitude exceeds the threshold in the specified frequency range
                boolean thresholdExceeded = false;
                for (int i = minBin; i <= maxBin; i++) {
//                    if (magnitude[i] > threshold) {
//                        thresholdExceeded = true;
//                        break;
//                    }
                    System.out.println(magnitude[i]);
                }

//
//                // Print a message if the threshold is exceeded
//                if (thresholdExceeded) {
//                    System.out.println("Threshold exceeded in the specified frequency range!");
//                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

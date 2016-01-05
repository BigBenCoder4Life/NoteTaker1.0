import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;

/**
 * A sample program is to demonstrate how to record sound in Java author:
 * www.codejava.net
 */
public class JavaSoundRecorder
{
    static File wavFile;

    /**
     * A utility class provides general functions for recording sound.
     * 
     * @author www.codejava.net
     * 
     */
        private static final int BUFFER_SIZE = 4096;
        private ByteArrayOutputStream recordBytes;
        private TargetDataLine audioLine = null;
        private AudioFormat format;
        private boolean isRunning;

        /**
         * Defines a default audio format used to record
         */
        AudioFormat getAudioFormat()
        {
            float sampleRate = 16000;
            int sampleSizeInBits = 16;
            int channels = 2;
            boolean signed = true;
            boolean bigEndian = true;
            return new AudioFormat(sampleRate, sampleSizeInBits, channels, signed, bigEndian);
        }

        /**
         * Start recording sound.
         * 
         * @throws LineUnavailableException
         *             if the system does not support the specified audio format
         *             nor open the audio data line.
         */
        public void start() throws LineUnavailableException
        {
            format = getAudioFormat();
                    
            DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);

            // checks if system supports the data line       
            if (!AudioSystem.isLineSupported(info)) throw new LineUnavailableException("The system does not support the specified format.");
            
            audioLine = AudioSystem.getTargetDataLine(format);
                      
            audioLine.open(format);
              
            audioLine.start();
            
            byte[] buffer = new byte[BUFFER_SIZE];
            int bytesRead = 0;
            
            recordBytes = new ByteArrayOutputStream();
            
            isRunning = true;
           
            while (isRunning)
            {
                bytesRead = audioLine.read(buffer, 0, buffer.length);
                recordBytes.write(buffer, 0, bytesRead);
                System.out.println(recordBytes.size()); 
            }    
        }

        /**
         * Stop recording sound.
         * 
         * @throws IOException
         *             if any I/O error occurs.
         */
        public void stop() throws IOException
        {
            isRunning = false;
            
            if (lineOpen())
            {
                System.out.println("Closing line");
                audioLine.drain();
                audioLine.close();
            }
        }

        /**
         * Save recorded sound data into a .wav file format.
         * 
         * @param wavFile
         *            The file to be saved.
         * @throws IOException
         *             if any I/O error occurs.
         */
        public void save(File wavFile) throws IOException
        {          
            byte[] audioData = recordBytes.toByteArray();
            
            ByteArrayInputStream byteInput = new ByteArrayInputStream(audioData);
            
            AudioInputStream audioInputStream = new AudioInputStream(byteInput, format, audioData.length / format.getFrameSize());
            
            AudioSystem.write(audioInputStream, AudioFileFormat.Type.WAVE, wavFile);
            
            audioInputStream.close();
            
            recordBytes.close();
        }
        
        /*
         * To see if audio line is open.
         */
        boolean lineOpen()
        {
            return audioLine != null;
            
        }
        
        /*
         * Sets the file save path.
         */
        static void setFilePath(String file)
        {
           wavFile = new File(file);
        }
        
        String getBytes()
        {
            return " " + recordBytes.size();
            
        }
    }

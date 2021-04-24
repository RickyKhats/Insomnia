package uweellibs;

import com.uweeldteam.ExceptionOccurred;

import javax.sound.sampled.*;
import java.io.IOException;

public class AudioPlayer {
    Clip clip;
    boolean loop = false;

    public AudioPlayer(String path) {
        AudioFile(new File(path));
    }
    public AudioPlayer(File audioFile) {
        AudioFile(audioFile);
    }
    void AudioFile(File audioFile){
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(audioFile.get());
            clip = AudioSystem.getClip();
            clip.open(ais);
            clip.setFramePosition(0);
        } catch (LineUnavailableException e) {
            new ExceptionOccurred(e);
        } catch (UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        }
    }

    public void Looping(boolean value) {
        loop = value;
    }

    public void Play() {
        new Thread(() -> {
            clip.start();
            new WaitForMills(clip.getMicrosecondLength() / 1000);
            if (loop) {
                clip.stop();
                clip.close();
            } else
                clip.setFramePosition(0);
        }).start();
    }
}

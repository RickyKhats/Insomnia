package uweellibs;

import com.uweeldteam.ExceptionOccurred;

import javax.sound.sampled.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class AudioPlayer {
    Clip clip;
    boolean loop = false;
    ArrayList<File> audioFiles = new ArrayList<>();
    public AudioPlayer(File... audioFile) {
        AudioFiles(audioFile);
        Debug.Log("Audio Player created");
    }

    void AudioFiles(File... audioFiles) {
        this.audioFiles = new ArrayList<>(Arrays.asList(audioFiles));
        Debug.Log("Audio added");
    }

    public void AddAudio(File audioFile){
        audioFiles.add(audioFile);
        Debug.Log("Audio added");
    }
    public void RemoveAudio(int id){
        audioFiles.remove(id);
        Debug.Log("Audio removed");
    }

    public void RemoveAudio(File file){
        RemoveAudio(audioFiles.indexOf(file));
    }

    public AudioPlayer Looping(boolean value) {
        loop = value;
        return this;
    }

    public void Play() {
        new Thread(() -> {
            AudioInputStream ais;
            for (File audioFile : audioFiles) {
                try {
                    ais = AudioSystem.getAudioInputStream(audioFile.get());
                    clip = AudioSystem.getClip();
                    clip.open(ais);
                } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                    e.printStackTrace();
                }
                clip.setFramePosition(0);
                clip.start();
                Debug.Log("audio started");
                new WaitForMills(clip.getMicrosecondLength() / 1000);
                Debug.Log("audio ended");
                clip.stop();
                clip.close();
                Debug.Log("audio stopped");
            }
            if (loop) {
                Debug.Log("audio replay");
                Play();
            }
        }).start();
    }
}

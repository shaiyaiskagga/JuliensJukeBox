package juliensjukebox;

import java.io.IOException;
import java.net.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.*;
import java.io.File;

public class Playlist{
    
    // Path
    String path = "C:\\Dokumente und Einstellungen\\Administrator\\Desktop\\sfx\\";
    
    // Set Objects
    final Random r = new Random();
    final File[] files = new File(path).listFiles();
    final Clip[] clips = new Clip[files.length];
    LineListener listener;
    
    
    public Playlist() {
            
        // Line Listener
        listener = new LineListener() {

            int lastSong = r.nextInt(files.length);
            int currentSong;

            @Override
            public void update(LineEvent event) {
                if (event.getType() == LineEvent.Type.STOP) {

                    // Don't repeat a Song
                    do
                        currentSong = r.nextInt(files.length);
                    while(lastSong == currentSong);
                    lastSong = currentSong;

                    // Play clip
                    Clip currentClip = clips[currentSong];
                    currentClip.setFramePosition(0);
                    currentClip.start();
                }
            }
        };   
    }
    
    public void openClips() {
        for(int i = 0; i < clips.length; i++) {
            try {
                clips[i] = AudioSystem.getClip();
                clips[i].open(AudioSystem.getAudioInputStream(new URL("file:\\" + path + files[i].getName())));
            } catch (    LineUnavailableException | IOException | UnsupportedAudioFileException ex) {
                Logger.getLogger(Playlist.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        // Add LineListeners
        for(int i = 0; i < clips.length; i++) {
            clips[i].addLineListener(listener);
        }
    }
    
    public void startPlaying() {
        clips[r.nextInt(files.length)].start();
    }
    
    public void stopPlaying() {
        for(int i = 0; i < clips.length; i++) {
            clips[i].stop();
            clips[i].close();
        }
    }
}

package com.pokewords.framework.views.sound;

import com.pokewords.framework.commons.utils.Resources;
import com.pokewords.framework.views.SoundPlayer;

import javax.sound.sampled.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

/**
 * A sound player that is tightly coupled to the Swing GUI framework.
 * (Only when the GUI has been init can the player work.)
 * @author johnny850807 (waterball)
 */
public class SwingSoundPlayer implements SoundPlayer {
    private Map<Object, byte[]> soundsStreamMap = new HashMap<>();  // <sound's type, sound bytes>
    private Map<Object, Clip> activeSoundsMap = Collections.synchronizedMap(new HashMap<>());
    private Set<Object> playingSounds = Collections.synchronizedSet(new HashSet<>()); // <sound's type>

    @Override
    public void addSound(Object name, String soundPath) {
        try {
            byte[] soundBytes = Files.readAllBytes(Resources.get(soundPath).toPath());
            soundsStreamMap.put(name, soundBytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeSound(Object name) {
        doIfSoundExistsOtherwiseThrowException(name, ()-> {
            if (activeSoundsMap.containsKey(name))
                stopSound(name);
            soundsStreamMap.remove(name);
        });
    }

    @Override
    public void playSound(Object name) {
        doIfSoundExistsOtherwiseThrowException(name, ()-> {
            Clip clip = readSoundClip(name);
            clip.start();
        });
    }

    @Override
    public boolean isPlayingSound(Object name) {
        return playingSounds.contains(name);
    }


    @Override
    public void playSoundLoopingForever(Object name) {
        playSoundLooping(name, -1);
    }

    @Override
    public void playSoundLooping(Object name, int loop) {
        doIfSoundExistsOtherwiseThrowException(name, ()-> {
            Clip clip = readSoundClip(name);
            clip.loop(loop);
        });
    }

    private Clip readSoundClip(Object soundType) {
        try {
            byte[] soundBytes = soundsStreamMap.get(soundType);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new ByteArrayInputStream(soundBytes));
            Clip clip = AudioSystem.getClip();
            clip.addLineListener(new MyLineListener(soundType, clip));
            clip.open(audioInputStream);
            return clip;
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void pauseSound(Object name) {
        doIfSoundExistsOtherwiseThrowException(name, ()-> {
            Clip sound = activeSoundsMap.get(name);
            sound.stop();
        });
    }

    @Override
    public void stopSound(Object name) {
        doIfSoundExistsOtherwiseThrowException(name, ()-> {
            Clip soundClip = activeSoundsMap.get(name);
            soundClip.close();
        });
    }

    public void doIfSoundExistsOtherwiseThrowException(Object name, Runnable runnable) {
        if (soundsStreamMap.containsKey(name))
            runnable.run();
        else
            throw new IllegalArgumentException("Sound " + name + " not found.");
    }


    private class MyLineListener implements LineListener {
        private Object soundType;
        private Clip clip;

        public MyLineListener(Object soundType, Clip clip) {
            this.soundType = soundType;
            this.clip = clip;
        }

        @Override
        public void update(LineEvent event) {
            if (event.getType().equals(LineEvent.Type.OPEN))
                activeSoundsMap.put(soundType, clip);
            else if (event.getType().equals(LineEvent.Type.START))
                playingSounds.add(soundType);
            else if (event.getType().equals(LineEvent.Type.STOP))
                playingSounds.remove(soundType);
            else if (event.getType().equals(LineEvent.Type.CLOSE))
                activeSoundsMap.remove(soundType);
        }
    }
}

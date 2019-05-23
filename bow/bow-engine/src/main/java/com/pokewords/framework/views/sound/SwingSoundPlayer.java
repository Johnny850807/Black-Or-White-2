package com.pokewords.framework.views.sound;

import com.pokewords.framework.commons.utils.Resources;
import com.pokewords.framework.views.SoundPlayer;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.util.HashMap;
import java.util.Map;

/**
 * A sound player that is tightly coupled to the Swing GUI framework.
 * (Only when the GUI has been init can the player work.)
 * @author johnny850807 (waterball)
 */
public class SwingSoundPlayer implements SoundPlayer {
    private Map<Object, Clip> soundsMap = new HashMap<>();

    @Override
    public void addSound(Object name, String soundPath) {
        try {
            Clip soundClip = AudioSystem.getClip();
            soundClip.open(AudioSystem.getAudioInputStream(Resources.get(soundPath)));
            soundsMap.put(name, soundClip);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeSound(Object name) {
        doIfSoundExistsOtherwiseThrowException(name, ()-> {
            soundsMap.get(name).close();
            soundsMap.remove(name);
        });
    }

    @Override
    public void playSound(Object name) {
        doIfSoundExistsOtherwiseThrowException(name, ()-> {
            soundsMap.get(name).start();
        });
    }


    @Override
    public void playSoundLoopingForever(Object name) {
        playSoundLooping(name, -1);
    }

    @Override
    public void playSoundLooping(Object name, int loop) {
        doIfSoundExistsOtherwiseThrowException(name, ()-> {
            Clip sound = soundsMap.get(name);
            sound.loop(loop);
        });
    }

    @Override
    public void pause(Object name) {
        doIfSoundExistsOtherwiseThrowException(name, ()-> {
            Clip sound = soundsMap.get(name);
            sound.stop();
        });
    }

    @Override
    public void stop(Object name) {
        doIfSoundExistsOtherwiseThrowException(name, ()-> {
            Clip sound = soundsMap.get(name);
            sound.close();
        });
    }

    public void doIfSoundExistsOtherwiseThrowException(Object name, Runnable runnable) {
        if (soundsMap.containsKey(name))
            runnable.run();
        else
            throw new IllegalArgumentException("Sound " + name + " not found.");
    }

}

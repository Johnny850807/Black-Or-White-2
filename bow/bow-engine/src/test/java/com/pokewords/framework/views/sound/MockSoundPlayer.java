package com.pokewords.framework.views.sound;

import com.pokewords.framework.views.SoundPlayer;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;


//TODO
public class MockSoundPlayer implements SoundPlayer {
    private Map<Object, String> soundsMap = new HashMap<>();
    private HashSet<Object> loopingSounds = new HashSet<>();
    private LinkedList<String> playedSoundHistoryList = new LinkedList<>();

    @Override
    public void addSound(Object name, String soundPath) {
        soundsMap.put(name, soundPath);
    }

    @Override
    public void removeSound(Object name) {
        soundsMap.remove(name);
        loopingSounds.remove(name);
    }

    @Override
    public void playSound(Object name) {
        playedSoundHistoryList.add(soundsMap.get(name));
    }

    @Override
    public void playSoundLooping(Object name, int loop) {

    }

    @Override
    public void playSoundLoopingForever(Object name) {

    }

    @Override
    public void pause(Object name) {

    }

    @Override
    public void stop(Object name) {

    }

}

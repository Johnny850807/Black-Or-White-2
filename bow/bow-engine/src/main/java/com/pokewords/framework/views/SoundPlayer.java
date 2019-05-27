package com.pokewords.framework.views;

/**
 * @author johnny850807 (waterball)
 */
public interface SoundPlayer {
    /**
     * add a sound by its name into the player.
     * @param name the sound's name
     * @param soundPath the sound's path
     */
    void addSound(Object name, String soundPath);

    /**
     * remove the sound by its name.
     * @param name the sound's name
     */
    void removeSound(Object name);

    /**
     * play a sound once.
     * @param name the sound's name
     */
    void playSound(Object name);

    /**
     * @return if the sound of the given name is playing
     */
    boolean isPlayingSound(Object name);


    default void playSoundIfNotPlaying(Object name) {
        if (!isPlayingSound(name))
            playSound(name);
    }

    /**
     * play a sound with looping given time
     * @param name the sound's name
     */
    void playSoundLooping(Object name, int loop);


    /**
     * play a sound with looping forever.
     * @param name the sound's name
     */
    void playSoundLoopingForever(Object name);

    /**
     * pauseSound a sound.
     * @param name the sound's name
     */
    void pauseSound(Object name);

    /**
     * stopSound a sound.
     * @param name the sound's name
     */
    void stopSound(Object name);

}

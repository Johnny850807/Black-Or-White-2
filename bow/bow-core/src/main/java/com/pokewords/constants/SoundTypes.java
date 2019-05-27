package com.pokewords.constants;

public enum SoundTypes {
    OPENING("assets/musics/opening.wav");

    private String soundPath;

    SoundTypes(String soundPath) {
        this.soundPath = soundPath;
    }

    public String getSoundPath() {
        return soundPath;
    }
}

package com.github.pepek42.rain.resource;

public enum Resource {
    BUCKET_TEXTURE("bucket.png"),
    DROP_TEXTURE("droplet.png"),
    DROP_SOUND("drop.wav"),
    RAIN_MUSIC("rain.mp3"),
    ;

    private final String path;
    Resource(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

}

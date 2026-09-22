package inheritance_polymorphism.class_problems;

interface Playable {
    String play();
    String play(int fromSecond);
    String pause();
}

abstract class MediaFile {
    private static int nextId = 1000;
    private final String fileId = "MF-" + (++nextId);
    public abstract String getFormatInfo();
    public String getFileId() { return fileId; }
}

class AudioFile extends MediaFile implements Playable {
    private final String title;
    public AudioFile(String title) { this.title = title; }
    public String play() { return "Playing audio: " + title; }
    public String play(int fromSecond) {
        return "Playing audio: " + title + " from " + (fromSecond / 60) + ":" + String.format("%02d", fromSecond % 60);
    }
    public String pause() { return "Paused audio: " + title; }
    public String getFormatInfo() { return "Audio file, ID: " + getFileId(); }
}

class Podcast implements Playable {
    private final String showName;
    private final int episodeNumber;
    public Podcast(String showName, int episodeNumber) {
        this.showName = showName;
        this.episodeNumber = episodeNumber;
    }
    public String play() { return "Streaming episode " + episodeNumber + " of " + showName; }
    public String play(int fromSecond) {
        return play() + " from " + fromSecond + " seconds";
    }
    public String pause() { return "Paused episode " + episodeNumber + " of " + showName; }
}

public class Problem4UniversalMediaLauncher {
    static void launchAll(Playable[] items) {
        for (Playable x : items) System.out.println(x.play());
    }

    public static void main(String[] args) {
        AudioFile a = new AudioFile("Morning Jazz");
        Podcast p = new Podcast("Tech Talk", 12);
        System.out.println(a.play());
        System.out.println(a.play(30));
        System.out.println(a.getFormatInfo());
        System.out.println(p.play());
        Playable ref = a; // upcasting
        launchAll(new Playable[]{ref, p});
    }
}
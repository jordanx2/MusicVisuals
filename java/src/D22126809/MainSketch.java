package D22126809;

import java.util.ArrayList;

import ddf.minim.AudioBuffer;
import ddf.minim.AudioPlayer;
import ddf.minim.Minim;
import ddf.minim.analysis.FFT;
import processing.core.PApplet;
import processing.core.PVector;

public class MainSketch extends PApplet
{
    Minim minim;
    AudioPlayer ap;
    AudioBuffer ab;
    FFT fft;
    CenterElement element;
    WaveForm wave;
    ArrayList<Star> entities = new ArrayList<>();

    public void settings() {
        size(displayWidth, displayHeight);

    }

    public void setup() {
        background(0);
        noFill();
        smooth();
        noStroke();
        colorMode(RGB);

        minim = new Minim(this);
        ap = minim.loadFile("fadeaway.mp3", 1024);
        ab = ap.mix;
        ap.play();

        // CURRENTLY MUTED
        // ap.mute();

        fft = new FFT(1024, 44100);
        int size = 5;
        entities.add(new Celestial(size, new PVector((width / 2) - 300, height / 2), color(random(255), 255, 255), (int)random(3, 10000), this, fft));
        entities.add(new Celestial(size, new PVector(width / 2, (height / 2) + 300), color(random(255), 255, 255), (int)random(3, 10000), this, fft));
        entities.add(new Celestial(size, new PVector((width / 2) + 300, height / 2), color(random(255), 255, 255), (int)random(3, 10000), this, fft));
        entities.add(new Celestial(size, new PVector(width / 2, (height / 2) - 300), color(random(255), 255, 255), (int)random(3, 10000), this, fft));

        element = new CenterElement(this, fft);
        wave = new WaveForm(this, ab);

    }

    public void keyPressed() {
        if (ap.isPlaying()) {
            ap.pause();
        }

        else if (ap.position() == ap.length()) {
            ap.rewind();
            ap.play();
        } else {
            ap.play();
        }
    }

    public void draw() {
        background(0);
        fft.forward(ab);

        for (Star s : entities) { s.render(); }
        element.render();
        wave.render();

    }
}
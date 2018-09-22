package com.company;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.concurrent.TimeUnit;

public class Sub {

    static String[] english = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z",
            "1", "2", "3", "4", "5", "6", "7", "8", "9", "0",
            ",", ".", "?", " "};
    static String[] morse = {".-", "-...", "-.-.", "-..", ".", "..-.", "--.", "....", "..", ".---", "-.-", ".-..", "--", "-.", "---", ".--.", "--.-", ".-.", "...", "-", "..-", "...-", ".--", "-..-", "-.--", "--..",
            ".----", "..---", "...--", "....-", ".....", "-....", "--...", "---..", "----.", "-----",
            "--..--", ".-.-.-", "..--..", "/"};

    public static String[] translateTM(String input) {

        String[] translation = new String[2];
        translation[0] = "";
        translation[1] = "";

        if (input.matches("[A-Za-z0-9,.?\r\n ]+")) {
            String[] chars = input.toLowerCase().split("");

            for (int i = 0; i < chars.length; i++) {
                for (int j = 0; j < english.length; j++) {
                    if (english[j].matches(chars[i])) {
                        translation[0] = translation[0] + morse[j] + " ";
                    }
                }
            }
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            translation[1] = "Translation successful @ " + timestamp;
        } else {
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            translation[1] = "Translation Failed @ " + timestamp;
        }
        return translation;
    }

    public static String[] translateMT(String input) {
        String[] translation = new String[2];
        translation[0] = "";
        translation[1] = "";

        if (input.matches("[./\\-\r\n ]+")) {
            String[] chars = input.split(" ");

            for (int i = 0; i < chars.length; i++) {
                for (int j = 0; j < morse.length; j++) {
                    if (morse[j].equals(chars[i])) {
                        translation[0] = translation[0] + english[j];
                    }
                }
            }
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            translation[1] = "Translation successful @ " + timestamp;
        } else {
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            translation[1] = "Translation Failed @ " + timestamp;
        }
        return translation;
    }

    public static void playMorse(String tts) throws Exception {

        String pos;
        for (int i = 0; i < tts.length(); i++) {
            pos = String.valueOf(tts.charAt(i));
            if (pos.matches("\\.")) {
                Clip clip = AudioSystem.getClip();
                AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File("Dot.wav"));
                clip.open(inputStream);
                clip.start();
                TimeUnit.MICROSECONDS.sleep(616780);
            } else if (pos.matches("\\-")) {
                Clip clip = AudioSystem.getClip();
                AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File("Dash.wav"));
                clip.open(inputStream);
                clip.start();
                TimeUnit.MICROSECONDS.sleep(419025);
            } else {
                TimeUnit.MICROSECONDS.sleep(500000);
            }

        }
    }

    public static void playText(String ttsIn) {

        TextToSpeech tts = new TextToSpeech();
        tts.setVoice("cmu-slt-hsmm");
        tts.speak(ttsIn, 1.0f, false, false);
    }

    public static String getText(File fileDir) {

        StringBuilder contentBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(fileDir.toString()))) {
            String sCurrentLine;
            while ((sCurrentLine = br.readLine()) != null) {
                contentBuilder.append(sCurrentLine).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return contentBuilder.toString();
    }

}

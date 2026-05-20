package main;

import java.io.Serializable;

/**
 * Ez a class a szerializálálst létrehozó adattípus, ami a ranglistához kell
 */
public class Score implements Serializable {
    String name;
    int score;
    public Score(String str, int n){
        name = str;
        score = n;
    }
}

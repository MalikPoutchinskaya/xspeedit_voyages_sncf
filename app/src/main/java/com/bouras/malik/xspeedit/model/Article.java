package com.bouras.malik.xspeedit.model;

/**
 * Un article
 */

public class Article {
    /**
     * le poids de l'article
     */
    private int weight;


    //Constructor
    public Article(int weight) {
        this.weight = weight;
    }

    //Getter & Setter
    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}

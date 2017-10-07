package com.bouras.malik.xspeedit.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Boite en carton
 */

public class Box {
    /**
     * valeur si il n'y a plus d'espace dans la boite
     */
    private static int NO_SPACE = 0;
    /**
     * List d'article
     */
    private List<Article> articles;
    /**
     * Nombre d'article maximum compris dans la boite
     */
    private int maxSize;


    //Constructor
    public Box(int maxSize) {
        this.articles = new ArrayList<>();
        this.maxSize = maxSize;
    }

    //Getter & Setter

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    public int getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }

    /**
     * Remplie si le poids de tous ces articles est supérieur a son max
     *
     * @return true si rempli
     */
    public boolean isFull() {
        return getSpace() <= NO_SPACE;
    }

    /**
     * retourne l'espace disponible dans la boite
     *
     * @return l'espace disponible dans la boite
     */
    public int getSpace() {
        int weightSum = 0;
        int spaceDiff;
        for (Article article : articles) {
            weightSum += article.getWeight();
        }
        spaceDiff = this.getMaxSize() - weightSum;
        //si l'espace est négatif on retourne zero
        return spaceDiff > NO_SPACE ? spaceDiff : NO_SPACE;
    }


    @Override
    public String toString() {
        String contain = "";
        for (Article article: this.getArticles()){
            contain += Integer.toString(article.getWeight());
        }
        return contain;
    }
}

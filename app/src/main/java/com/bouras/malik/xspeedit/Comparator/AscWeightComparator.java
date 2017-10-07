package com.bouras.malik.xspeedit.Comparator;

import com.bouras.malik.xspeedit.model.Article;

import java.util.Comparator;

/**
 * trie en asc les articles par poids
 */

public class AscWeightComparator implements Comparator<Article> {
    @Override
    public int compare(Article a1, Article a2) {
        return a1.getWeight() - a2.getWeight();
    }
}
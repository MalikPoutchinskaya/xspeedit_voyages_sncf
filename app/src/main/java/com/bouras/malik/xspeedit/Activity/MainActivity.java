package com.bouras.malik.xspeedit.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bouras.malik.xspeedit.Comparator.AscWeightComparator;
import com.bouras.malik.xspeedit.R;
import com.bouras.malik.xspeedit.model.Article;
import com.bouras.malik.xspeedit.model.Box;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    /**
     * par defaut la taille de la boite est de 10
     */
    private static int DEFAULT_SEEKBAR_PROGRESS = 10;
    /**
     * espace dans la boite
     */
    private int boxSpace;
    /**
     * une liste d'article
     */
    private List<Article> articlesToPackage;
    /**
     * une liste de boite
     */
    private List<Box> boxes;

    //UI component
    private EditText editText;
    private TextView packagedCardView;
    private TextView seekBarValueView;
    private SeekBar seekBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //recupere les vues ui
        seekBarValueView= (TextView) findViewById(R.id.activity_main_seekBar_value);
        seekBar= (SeekBar)findViewById(R.id.activity_main_seekBar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                boxSpace = progress;
                seekBarValueView.setText(String.valueOf(boxSpace));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        seekBar.setProgress(DEFAULT_SEEKBAR_PROGRESS);
        editText= (EditText)findViewById(R.id.activity_main_articles);
        packagedCardView = (TextView)findViewById(R.id.activity_main_boxes);
        findViewById(R.id.button_compute_boxes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getArticleFromNumber();
                computeBoxes();
                displayResult();
                //champs de saisie devient vierge
                editText.setText("");
            }
        });
    }


    /**
     * Cree une liste d'articles à partir d'un nombre
     */
    private void getArticleFromNumber() {
        //nouveaux articles
        articlesToPackage = new ArrayList<>();
        //recupère les articles de l'ui
        String request = String.valueOf(editText.getText());
        char[] articlesWeight= request.toCharArray();
        for (int i = 0;i < editText.length(); i++){
            Article article = new Article(Character.getNumericValue(articlesWeight[i]));
            articlesToPackage.add(article);
        }
    }

    /**
     * Remplie les boites avec des articles jusqu'a ce qu'il n'y ai plus de place dans la boite
     */
    public void computeBoxes() {
        //nouvelle liste de boite
        boxes = new ArrayList<>();
        //tri des articles par ordre asc si la liste est sup à 2
        if(articlesToPackage.size() > 2){
            Collections.sort(articlesToPackage, new AscWeightComparator());
        }
        //Tant qu'il reste des articles à stocker
        while (!articlesToPackage.isEmpty()) {
            //Creation d'une boite
            Box box = new Box(boxSpace);
            //prend l'article avec le poids le plus fort
            Article articleHeavy = articlesToPackage.get(articlesToPackage.size() - 1);
            //le place dans la boite
            box.getArticles().add(articleHeavy);
            //l'article est retiré de la liste d'attente
            articlesToPackage.remove(articleHeavy);
            //rempli la boite si possible
            fillBox(box);
            //On ajoute la boite à la liste
            boxes.add(box);
        }
    }

    /**
     * Rempli une boite
     *
     * @param box la boite à remplir
     */
    private void fillBox(Box box){
        while (!box.isFull() && !articlesToPackage.isEmpty()){
            //prend l'article avec le poids le plus faible
            Article articleLight = articlesToPackage.get(0);
            if(isBoxable(box, articleLight)){
                //ajoute l'article leger dans la boite
                box.getArticles().add(articleLight);
                //on retire l'article de la liste d'attente
                articlesToPackage.remove(articleLight);
            }else{
                //sinon on arrete de remplir la boite
                break;
            }
        }
    }

    /**
     * Permet de savoir si un article peut rentrer dans la boite
     *
     * @param boxToFill la boite à remplir
     * @param articleToBox l'article pretendant rentrer dans la boite
     * @return true si la boite peut être replie
     */
    public boolean isBoxable(Box boxToFill, Article articleToBox){
        return articleToBox.getWeight() <= boxToFill.getSpace();
    }

    private void displayResult(){
        String result="";
        for (Box box: boxes) {
            result += box.toString() + "/";
        }
        packagedCardView.setText(result);
    }
}

package ch.amanet.amanet;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;

/**
 * Classe de l'application qui va s'occuper de tout l'affichage
 *
 * Application dévellopée par Loïc Iglesias
 * ========================================
 * Application officielle du site http://amanet.ch
 * Demandeur: Valon Sheqiri
 */
public class MainActivity extends AppCompatActivity {

    // Variable de l'application
    ProgressBar mProgressBar;   // Va tourner tant que la webview n'a pas chargée
    WebView webView;            // Display simple du site
    ImageView imgAmanet;        // Banner du site qui va être affiché lors du "splashscreen"
    boolean first = false;      // Défini si le premier passage a eu lieu

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Récupérer la valeur si rotation de l'écran
        if (savedInstanceState != null) {
            first = savedInstanceState.getBoolean("first");
        }

        // Récupérer les éléments graphique
        mProgressBar = (ProgressBar) findViewById(R.id.mProgress);
        webView = (WebView) findViewById(R.id.webView);
        imgAmanet = (ImageView) findViewById(R.id.txtAmanet);

        // Si le premier passage a eu lieu ne pas rafficher le pseudo splashscreen
        if (first) {
            mProgressBar.setVisibility(View.INVISIBLE);
            imgAmanet.setVisibility(View.INVISIBLE);
            webView.setVisibility(View.VISIBLE);
            first = true;
        }

        // Définition de l'URL et accepter le javaScript
        webView.loadUrl("http://amanet.ch");
        webView.getSettings().setJavaScriptEnabled(true);

        // Gestion du client webView
        // Dans le cas de la webView le plus simple que j'ai trouvé c'est de simuler
        // un splashscreen en superposant les éléments et en jouant avec la visibilité
        // le client webView s'occupe de charger la page (le problème quand je le faisais
        // passer dans un asyncTask c'est que la tâche se base sur le Thread UI, le thread
        // principal). Pour les autres tâche comme les gros calcul c'est mieux de passer
        // par une classe interne qui étends sur asyncTask
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                // Cette méthode est effectué lors du chargement complet de la page
                // ce qui permet de voir le pseudo splashscreen et de charger la page
                // en arrière plan et ensuite afficher le webview et masquer le reste
                super.onPageFinished(view, url);
                webView.setVisibility(View.VISIBLE);
                mProgressBar.setVisibility(View.INVISIBLE);
                imgAmanet.setVisibility(View.INVISIBLE);
                first = true;
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Récupérer le premier passage lors de la rotation de l'écran
        outState.putBoolean("first",first);
    }

}

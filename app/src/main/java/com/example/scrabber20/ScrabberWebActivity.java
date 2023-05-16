package com.example.scrabber20;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.WindowCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class ScrabberWebActivity extends AppCompatActivity {

    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrabber_web);
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);

        setSupportActionBar(findViewById(R.id.toolbar));

        WebView webView = findViewById(R.id.webView1);
        // Set up WebView with desired settings and load content

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSnackbar();
            }
        });
    }

    private void showSnackbar() {
        Snackbar snackbar = Snackbar.make(fab, "SEO Optimizer Tool", Snackbar.LENGTH_LONG);

        snackbar.setAction("Open", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSEOTool();
            }
        });

        snackbar.setActionTextColor(ContextCompat.getColor(ScrabberWebActivity.this, R.color.colorAccent));

        snackbar.show();
    }

    private void openSEOTool() {
        WebView webView = findViewById(R.id.webView1);
        webView.loadUrl("https://dashingknights.com/seo-analyser/");

        Toast.makeText(this, "Opening SEO Optimizer Tool", Toast.LENGTH_SHORT).show();
    }
}

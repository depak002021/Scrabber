package com.example.scrabber20;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
//import android.view.ViewGroup;
import android.view.ViewGroup;
import android.webkit.WebView;
//import android.webkit.WebViewClient;
import android.webkit.WebViewClient;
import android.widget.ImageView;
//import android.widget.LinearLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

public class ScrapedContentActivity extends AppCompatActivity {
    private WebView webView;
    private TextView titleTextView;
    private TextView descriptionTextView;
    private TextView linksTextView;
    private ImageView screenshotImageView;
    private TextView seoScoreTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scraped_content);

        webView = findViewById(R.id.webView);
        titleTextView = findViewById(R.id.titleTextView);
        descriptionTextView = findViewById(R.id.descriptionTextView);
        linksTextView = findViewById(R.id.linksTextView);
        screenshotImageView = findViewById(R.id.screenshotImageView);
        seoScoreTextView = findViewById(R.id.seoScoreTextView);

        // Retrieve the scraped data and URL from the intent extras
        String htmlContent = getIntent().getStringExtra("htmlContent");
        String scrapedTitle = getIntent().getStringExtra("scrapedTitle");
        String scrapedDescription = getIntent().getStringExtra("scrapedDescription");
        String scrapedLinks = getIntent().getStringExtra("scrapedLinks");
        String screenshotImageUrl = getIntent().getStringExtra("screenshotImageUrl");
        String url = getIntent().getStringExtra("url");

        // Load the URL into the WebView
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                // Calculate the content height and set it as the WebView's height
                int contentHeight = webView.getContentHeight();
                webView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, contentHeight));
            }
        });
        webView.loadUrl(url);

        if (htmlContent != null && scrapedTitle != null && scrapedDescription != null && scrapedLinks != null && screenshotImageUrl != null) {
            // Load the scraped data into the respective views
            webView.loadData(htmlContent, "text/html", null);
            titleTextView.setText(scrapedTitle);
            descriptionTextView.setText(scrapedDescription);
            linksTextView.setText(scrapedLinks);

            // Load the screenshot image using Picasso library
            Picasso.get()
                    .load(screenshotImageUrl)
                    .placeholder(R.drawable.ic_placeholder) // Placeholder image while loading
                    .into(new Target() {
                        @Override
                        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                            screenshotImageView.setImageBitmap(bitmap);
                        }

                        @Override
                        public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                            Toast.makeText(ScrapedContentActivity.this, "Failed to load image", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onPrepareLoad(Drawable placeHolderDrawable) {
                            // Handle the image loading preparation if needed
                        }
                    });
        } else {
            Toast.makeText(this, "No data available", Toast.LENGTH_SHORT).show();
        }
    }
}

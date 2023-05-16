package com.example.scrabber20;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
//import android.view.ViewGroup;
//import android.webkit.WebView;
//import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
//import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class HomeActivity extends AppCompatActivity {

    private static final String TAG = "HomeActivity";

    private EditText urlEditText;
    private Button fetchButton;
    private boolean doubleBackPressed = false;
    private ProgressBar progressBar;


    @Override
    public void onBackPressed() {
        if (doubleBackPressed) {
            super.onBackPressed();
        } else {
            doubleBackPressed = true;
            Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    doubleBackPressed = false;
                }
            }, 2000); // Reset the flag after 2 seconds
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        urlEditText = findViewById(R.id.urlEditText);
        fetchButton = findViewById(R.id.fetchButton);
        progressBar = findViewById(R.id.progressBar);
        FloatingActionButton fab1 = findViewById(R.id.fab1);
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSnackbar();
            }
        });


        fetchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = urlEditText.getText().toString().trim();


                if (!url.isEmpty() && isUrlValid(url)) {
                    // Show the progress bar
                    progressBar.setVisibility(View.VISIBLE);

                    // Fetching and scraping logic
                    new FetchDataTask().execute(url);

                } else {
                    Toast.makeText(HomeActivity.this, "Please enter a valid URL", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    private void showSnackbar() {
        FloatingActionButton fab1 = findViewById(R.id.fab1);
        Snackbar snackbar = Snackbar.make(fab1, "SEO Optimizer Tool", Snackbar.LENGTH_LONG);

        snackbar.setAction("Open", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Open ScrabberWebActivity
                Intent intent = new Intent(HomeActivity.this, ScrabberWebActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
            }
        });

        snackbar.setActionTextColor(getResources().getColor(R.color.colorAccent));
        snackbar.show();
    }


    private boolean isUrlValid(String url) {
        try {
            // Use java.net.URL class to validate the URL
            new URL(url);
            return true;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private class FetchDataTask extends AsyncTask<String, Void, ScrapedData> {

        @Override
        protected ScrapedData doInBackground(String... params) {
            String url = params[0];
            try {
                Log.d(TAG, "Fetching data from URL: " + url);

                // Fetch the HTML document from the URL using Jsoup with a user agent
                Document document = Jsoup.connect(url).userAgent("Mozilla").get();

                // Extract the desired data from the document
                String htmlContent = document.html();
                String scrapedTitle = document.title();
                String scrapedDescription = document.select("meta[name=description]").attr("content");
                String scrapedLinks = document.select("a[href]").text();
                String screenshotImageUrl = extractScreenshotImageUrl(document);
                // String seoScore = calculateSeoScore(url);

                // Create a ScrapedData object to hold the extracted data
                ScrapedData scrapedData = new ScrapedData();
                scrapedData.setHtmlContent(htmlContent);
                scrapedData.setScrapedTitle(scrapedTitle);
                scrapedData.setScrapedDescription(scrapedDescription);
                scrapedData.setScrapedLinks(scrapedLinks);
                scrapedData.setScreenshotImageUrl(screenshotImageUrl);
                // scrapedData.setSeoScore(seoScore);

                return scrapedData;

            } catch (IOException e) {
                e.printStackTrace();
                Log.e(TAG, "Failed to fetch data from URL: " + url);
                return null;
            } catch (Exception e) {
                e.printStackTrace();
                Log.e(TAG, "Error occurred while fetching data from URL: " + url);
                return null;
            }
        }

        @Override
        protected void onPostExecute(ScrapedData scrapedData) {
            // Hide the progress bar
            progressBar.setVisibility(View.GONE);

            if (scrapedData != null) {
                // Start ScrapedContentActivity
                Intent intent = new Intent(HomeActivity.this, ScrapedContentActivity.class);
                intent.putExtra("htmlContent", scrapedData.getHtmlContent());
                intent.putExtra("scrapedTitle", scrapedData.getScrapedTitle());
                intent.putExtra("scrapedDescription", scrapedData.getScrapedDescription());
                intent.putExtra("scrapedLinks", scrapedData.getScrapedLinks());
                intent.putExtra("screenshotImageUrl", scrapedData.getScreenshotImageUrl());
                // intent.putExtra("seoScore", scrapedData.getSeoScore());
                intent.putExtra("url", urlEditText.getText().toString().trim()); // Pass the URL
                startActivity(intent);
            } else {
                Toast.makeText(HomeActivity.this, "Failed to fetch data or the URL does not exist", Toast.LENGTH_SHORT).show();
            }
        }

        private String extractScreenshotImageUrl(Document document) {
            // Extract the screenshot image URL from the document
            // Replace this with your actual logic to extract the URL based on your HTML structure
            // For example, you can find an image tag and get the source attribute

            // Check if the document contains any image elements
            Elements imageElements = document.select("img");
            if (!imageElements.isEmpty()) {
                // Iterate through the image elements and retrieve the URL from the first valid image
                for (Element imageElement : imageElements) {
                    String imageUrl = imageElement.absUrl("src");
                    if (isValidImageUrl(imageUrl)) {
                        return imageUrl;
                    }
                }
            }

            // If no valid image URL is found, you can return a default image URL or an empty string
            return "";
        }

        private boolean isValidImageUrl(String imageUrl) {
            // Add your validation logic here based on the requirements of the websites you're scraping
            // For example, you can check if the image URL starts with "http://" or "https://"
            // and if it meets any specific criteria defined by the websites you're targeting

            // Simple example: Check if the image URL starts with "http://" or "https://"
            return imageUrl.startsWith("http://") || imageUrl.startsWith("https://");
        }

    }



}


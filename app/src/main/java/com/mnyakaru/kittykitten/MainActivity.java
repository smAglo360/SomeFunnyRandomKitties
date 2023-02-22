package com.mnyakaru.kittykitten;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity {

    private MainActivityViewModel mainActivityViewModel;
    private static final String TAG = "MainActivity";

    private ProgressBar progressBarCatImage;
    private ProgressBar progressBarCatFact;
    private ImageView imageViewCatPicture;
    private TextView textViewQuote;
    private MaterialButton buttonNextKitten;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        mainActivityViewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
        loadImage();
        loadFact();
        mainActivityViewModel.getIsError().observe(this, error -> {
            if(error){
                Toast.makeText(
                        MainActivity.this,
                        R.string.please_get_back_your_internet_connection,
                        Toast.LENGTH_SHORT).show();
            }
        });
        mainActivityViewModel.getIsLoadingCatImage().observe(this, loading -> {
            if(loading){
                imageViewCatPicture.setVisibility(View.GONE);
                progressBarCatImage.setVisibility(View.VISIBLE);
            }else {
                imageViewCatPicture.setVisibility(View.VISIBLE);
                progressBarCatImage.setVisibility(View.GONE);
            }
        });
        mainActivityViewModel.getIsLoadingCatFact().observe(this, loading -> {
            textViewQuote.setText(" ");
            if(loading){
                textViewQuote.setVisibility(View.GONE);
                progressBarCatFact.setVisibility(View.VISIBLE);
            }else {
                textViewQuote.setVisibility(View.VISIBLE);
                progressBarCatFact.setVisibility(View.GONE);
            }
        });
        buttonNextKitten.setOnClickListener(v -> {
            textViewQuote.setText(" ");
            loadImage();
            loadFact();
        });
    }
    private void loadImage(){
        mainActivityViewModel.loadCatImage();
        mainActivityViewModel.getCatImage().observe(this, image -> Glide.with(MainActivity.this)
                .load(image.getUrl())
                .into(imageViewCatPicture));
    }
    private void loadFact(){
        mainActivityViewModel.loadCatFact();
        mainActivityViewModel.getCatFact().observe(this, fact -> textViewQuote.setText(fact.getFact()));
    }

    private void initViews(){
        progressBarCatFact = findViewById(R.id.progressBarCatFact);
        progressBarCatImage = findViewById(R.id.progressBarCatImage);
        imageViewCatPicture = findViewById(R.id.imageViewCatPicture);
        textViewQuote = findViewById(R.id.textViewQuote);
        buttonNextKitten = findViewById(R.id.buttonNextKitten);
    }
}
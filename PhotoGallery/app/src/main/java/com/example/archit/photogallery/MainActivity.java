package com.example.archit.photogallery;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements GetImage.IImageHandler, GetData.IDataHandler{

    private ImageView nextButton, prevButton;
    private Button goButton;
    private Spinner spinner;
    private ImageView imageView;
    private String[] keywords = {"UNCC", "Android", "Winter", "Aurora", "Wonders"};
    private ArrayList<String> currentURLs;
    private int currentImagePosition = -1;
    private LinearLayout buttonLayout;
    private ProgressDialog pd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nextButton = (ImageView) findViewById(R.id.nextButton);
        prevButton = (ImageView)findViewById(R.id.prevButton);
        goButton = (Button)findViewById(R.id.goButton);
        spinner = (Spinner)findViewById(R.id.spinner);
        imageView = (ImageView)findViewById(R.id.imageView);
        buttonLayout = (LinearLayout) findViewById(R.id.buttonLayout);

        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, keywords);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

    }


    @Override
    public void preprocess() {
        pd = new ProgressDialog(this, ProgressDialog.STYLE_SPINNER);
        pd.setTitle("Loading Photo..");
        pd.show();
    }

    @Override
    public void setImage(Bitmap image) {
        pd.dismiss();
        imageView.setImageBitmap(image);
    }

    @Override
    public void setData(ArrayList<String> data) {
        currentURLs = data;
        if(data == null || data.size() == 0) {
            buttonLayout.setVisibility(View.INVISIBLE);
            imageView.setImageBitmap(null);
            Toast.makeText(this, "No Images found", Toast.LENGTH_LONG).show();
        }
        else {

            new GetImage(this).execute(currentURLs.get(currentImagePosition));

            if(currentURLs.size() == 1) {
                buttonLayout.setVisibility(View.INVISIBLE);
            }

            else if(currentURLs.size() > 1) {
                buttonLayout.setVisibility(View.VISIBLE);
            }

        }

    }

    public void onGoButtonClicked(View v) {

        String keyword = keywords[spinner.getSelectedItemPosition()];
        currentImagePosition = 0;
        new GetData(this).execute(keyword);

    }

    public void onNextButtonClicked(View v) {
        int n = currentURLs.size();
        if(currentImagePosition < n - 1){
            currentImagePosition++;
        }

        else {
            currentImagePosition = 0;
        }

        new GetImage(this).execute(currentURLs.get(currentImagePosition));

    }

    public void onPreviousButtonClicked(View v) {
        int n = currentURLs.size();
        if(currentImagePosition > 0) {
            currentImagePosition--;
        }
        else {
            currentImagePosition = n-1;
        }

        new GetImage(this).execute(currentURLs.get(currentImagePosition));
    }

}

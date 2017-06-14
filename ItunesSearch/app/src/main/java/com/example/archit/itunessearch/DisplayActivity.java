package com.example.archit.itunessearch;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class DisplayActivity extends AppCompatActivity implements GetImageAsyncTask.IImageHandler{


    ImageView iv;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        ResultItem item = (ResultItem) getIntent().getExtras().getSerializable(MainActivity.RESULT_KEY);

        TextView trackView = (TextView)findViewById(R.id.d_trackView);
        trackView.setText(item.getTrackName());

        TextView genreView = (TextView) findViewById(R.id.d_genreView);
        genreView.setText(item.getGenre());

        TextView artistView = (TextView) findViewById(R.id.d_artistView);
        artistView.setText(item.getArtist());

        TextView albumView = (TextView) findViewById(R.id.d_albumView);
        albumView.setText(item.getAlbum());

        TextView trackPriceView = (TextView) findViewById(R.id.d_trackpriceView);
        trackPriceView.setText(item.getTrackprice() + "$");

        TextView albumPriceView = (TextView) findViewById(R.id.d_albumpriceView);
        albumPriceView.setText(item.getAlbumprice()+"$");

        Button finishButton = (Button) findViewById(R.id.finishButton);
        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DisplayActivity.this.finish();
            }
        });

        iv = (ImageView) findViewById(R.id.imageView);
        pd = new ProgressDialog(this, ProgressDialog.STYLE_SPINNER);
        pd.setMessage("Loading Image");

        new GetImageAsyncTask(this).execute(item.getImageURL());
        pd.show();

    }

    @Override
    public void setImage(Bitmap image) {
        iv.setImageBitmap(image);
        pd.dismiss();
    }
}

package com.batchmates.android.bbvabacksearcher.view.thirdactivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.batchmates.android.bbvabacksearcher.R;
import com.bumptech.glide.Glide;
import com.google.android.gms.vision.text.Text;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        ButterKnife.bind(this);

        Glide.with(this).load(getIntent().getStringExtra("PICTURE")).into(picture);
        name.setText(getIntent().getStringExtra("BANKNAME"));
        address.setText(getIntent().getStringExtra("BANKADDRESS"));
        double theRating=getIntent().getDoubleExtra("RATING",0);
        rating.setText(String.valueOf(theRating)+"/5.0");
    }

    @BindView(R.id.ivPicture)
    ImageView picture;

    @BindView(R.id.tvBankName)
    TextView name;

    @BindView(R.id.tvBankAddress)
    TextView address;

    @BindView(R.id.tvBankRating)
    TextView  rating;
}

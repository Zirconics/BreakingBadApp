package com.example.breakingbadapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    private TextView mDisplayName;
    private TextView mDisplayNickname;
    private TextView mDisplayStatus;
    private ImageView mDisplayImage;
    private TextView mDisplayBirthday;
    private TextView mDisplayOccupation;
    private TextView mDisplaySeasonAppearance;
    private CardView mDisplayCardView;
    private final String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //Shows back button on detailActivity.
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Connects the xml elements with the display elements
        mDisplayName = (TextView) findViewById(R.id.character_detail_name);
        mDisplayNickname = (TextView) findViewById(R.id.character_detail_nickname);
        mDisplayImage = (ImageView) findViewById(R.id.character_detail_image);
        mDisplayStatus = (TextView) findViewById(R.id.character_detail_status);
        mDisplayBirthday = (TextView) findViewById(R.id.character_detail_birthday);
        mDisplayOccupation = (TextView) findViewById(R.id.character_detail_occupation);
        mDisplaySeasonAppearance = (TextView) findViewById(R.id.character_detail_appearance);
        mDisplayCardView = (CardView) findViewById(R.id.character_detail_cardview);


        //Gets all character data and puts it in a string
        Intent intent = getIntent();
        Character character = (Character) intent.getSerializableExtra("character");
        String name = character.getName();
        String nickname = character.getNickName();
        String image = character.getImgUrl();
        String status = character.getStatus();
        String birthday = character.getBirthday();
        String occupation = character.getOccupation();
        String appearance = character.getAppearance();

        //Strings with character info and corresponding stings.xml string.
        final String NICKNAME = getString(R.string.character_nickname) + nickname;
        final String STATUS = getString(R.string.character_status) + status;
        final String BIRTHDAY = getString(R.string.character_birthday) + birthday;
        final String OCCUPATION = getString(R.string.character_occupation) + occupation.replaceAll("[\\[\\]\"]","");
        final String APPEARANCE = getString(R.string.character_appearance) + appearance.replaceAll("[\\[\\]\"]","");;

        //Gives information to elements that are shown on screen.
        mDisplayName.setText(name);
        mDisplayNickname.setText(NICKNAME);
        Picasso.get().load(image).resize(600, 600).into(mDisplayImage);
        mDisplayStatus.setText(STATUS);
        mDisplayBirthday.setText(BIRTHDAY);
        mDisplayOccupation.setText(OCCUPATION);
        mDisplaySeasonAppearance.setText(APPEARANCE);

        //Sends user to their browser to view the character image fullscreen.
        mDisplayCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse(character.getImgUrl()));
                mDisplayCardView.getContext().startActivity(browserIntent);
                Log.d(TAG, "onClick was called by clicking on the character image.");
            }
        });

        Log.d(TAG, "onCreate was called");
    }

}
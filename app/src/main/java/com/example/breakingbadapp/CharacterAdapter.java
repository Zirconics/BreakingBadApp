package com.example.breakingbadapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CharacterAdapter extends RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder> implements Parcelable {

    private final String TAG = getClass().getSimpleName();
    private ArrayList<Character> characterList;
    private Context context;

    public CharacterAdapter(ArrayList<Character> characterList, Context context) {
        Log.d(TAG, "Constructor was called");
        this.characterList = characterList;
        this.context = context;

        final String showing = context.getResources().getString(R.string.showing);
        final String characters = context.getResources().getString(R.string.toast_characters);
        final String toast_message = showing + String.valueOf(this.characterList.size()) + characters;
        Toast.makeText(context, toast_message, Toast.LENGTH_SHORT).show();
    }

    @NonNull
    @Override
    public CharacterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.character_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        Log.d(TAG, "onCreateViewHolder was called");
        return new CharacterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CharacterViewHolder holder, int position) {
        Character character = characterList.get(position);
        final String NICKNAME = context.getString(R.string.character_nickname) + character.getNickName();
        final String STATUS = context.getString(R.string.character_status) + character.getStatus();

        holder.mCharacterName.setText(character.getName());
        holder.mCharacterNickname.setText(NICKNAME);
        holder.mCharacterStatus.setText(STATUS);

        // Sets onClickListener on list item.
        //Sends the user to the characters detail page.
        holder.mCharacterLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.mCharacterLayout.getContext(), DetailActivity.class);
                intent.putExtra("character", character);

                holder.mCharacterLayout.getContext().startActivity(intent);
                Log.d(TAG, "onClick was called by clicking on the character information.");
            }
        });

        Picasso.get().load(character.getImgUrl()).resize(350, 350).into(holder.mCharacterImage);

        //Sets onClickListener on image.
        //Sends user to their browser with the character image link.
        holder.mCharacterCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse(character.getImgUrl()));
                holder.mCharacterCardView.getContext().startActivity(browserIntent);
                Log.d(TAG, "onClick was called by clicking on the character image.");
            }
        });

        Log.d(TAG, "Sending data to detail activity.");
    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount was called.");
        return characterList.size();
    }

    @Override
    public int describeContents() {
        Log.d(TAG, "describeContents was called.");
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        Log.d(TAG, "writeToParcel was called.");
    }

    //Creates all display items.
    public class CharacterViewHolder extends RecyclerView.ViewHolder {
        private final TextView mCharacterName;
        private final TextView mCharacterNickname;
        private final TextView mCharacterStatus;
        private final ImageView mCharacterImage;
        private final LinearLayout mCharacterLayout;
        private final CardView mCharacterCardView;

        //Connects all display items with their corresponding views (xml files).
        public CharacterViewHolder(@NonNull View view) {
            super(view);
            mCharacterName = (TextView) view.findViewById(R.id.character_list_item_name);
            mCharacterNickname = (TextView) view.findViewById(R.id.character_list_item_nickname);
            mCharacterStatus = (TextView) view.findViewById(R.id.character_list_item_status);
            mCharacterImage = (ImageView) view.findViewById(R.id.character_list_item_image);
            mCharacterLayout = (LinearLayout) view.findViewById(R.id.character_list_layout);
            mCharacterCardView = (CardView) view.findViewById(R.id.character_list_item_cardview);
            Log.d(TAG, "CharacterViewHolder was called.");
        }
    }

}

package com.example.breakingbadapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.Configuration;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements CharacterAPI.CharacterListener {

    private final String TAG = getClass().getSimpleName();
    private final static String SAVED_ADAPTER = "Adapter";
    private ArrayList<Character> characters = new ArrayList<>();
    private RecyclerView characterRecyclerView;
    private CharacterAdapter characterAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreated was called");

        //Creates SearchBar with functionality so that it is able to search on parts of a characters name.
        EditText filterText = findViewById(R.id.search_bar);
        filterText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.d(TAG, "beforeTextChanged was called");
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d(TAG, "onTextChanged was called");
            }

            // resets the list and passes the written text in different CharacterAdapter constructor
            @Override
            public void afterTextChanged(Editable s) {

                String result = s.toString().toLowerCase();
                ArrayList<Character> filterList = new ArrayList<Character>();
                for (Character c : characters) {
                    String name = c.getName().toLowerCase();
                    String[] nameParts = name.split(" ");
                    for (int i = 0; i < nameParts.length; i++) {
                        if (nameParts[i].startsWith(result) && !filterList.contains(c)) {
                            filterList.add(c);
                        }
                    }
                }
                characterAdapter = new CharacterAdapter(filterList, MainActivity.this);
                characterRecyclerView.setAdapter(characterAdapter);
                Log.d(TAG, "afterTextChanged was called");
            }
        });

        characterRecyclerView = (RecyclerView) findViewById(R.id.characters_recycler_view);

        //Creates 2 layoutManagers.
        // 1 for portrait mode and 1 for landscape mode.
        LinearLayoutManager linearlayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        GridLayoutManager gridlayoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);

        //Checks the orientation of the device
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            characterRecyclerView.setLayoutManager(linearlayoutManager);
        } else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            characterRecyclerView.setLayoutManager(gridlayoutManager);
        }

        if (characterAdapter == null) {
            characterAdapter = new CharacterAdapter(characters, this);
            characterRecyclerView.setAdapter(characterAdapter);
        } else {
            characterRecyclerView.setAdapter(characterAdapter);
        }

        // API links
        String[] params = {
//                Original API link
                "https://breakingbadapi.com/api/characters"

                // API link with ArrayName 'hits'
//                "https://api.npoint.io/95fe588bf94b2e9dc563"
        };

        new CharacterAPI(this).execute(params);
    }

    //Puts character data from API on screen.
    @Override
    public void setCharacterList(List<Character> characters) {
        Log.d(TAG, "We have a total of " + characters.size() + " items.");

        this.characters.clear();
        this.characters.addAll(characters);
        this.characterAdapter.notifyDataSetChanged();
    }

    //Connects the filter menu with the corresponding xml.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.filter, menu);
        Log.d(TAG, "onCreateOptionsMenu was called");
        return true;
    }

    //Adds functionality to the filter button.
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.filter_alive:
                Toast.makeText(this, getResources().getString(R.string.toast_alive), Toast.LENGTH_SHORT).show();
                filterItems("alive");
                Log.d(TAG, "status alive was selected");
                return true;
            case R.id.filter_deceased:
                Toast.makeText(this, getResources().getString(R.string.toast_deceased), Toast.LENGTH_SHORT).show();
                filterItems("deceased");
                Log.d(TAG, "status deceased was selected");
                return true;
            case R.id.filter_presumed_dead:
                Toast.makeText(this, getResources().getString(R.string.toast_presumed_dead), Toast.LENGTH_SHORT).show();
                filterItems("presumed dead");
                Log.d(TAG, "status presumed dead was selected");
                return true;
            case R.id.filter_reset:
                Toast.makeText(this, getResources().getString(R.string.toast_reset), Toast.LENGTH_SHORT).show();
                Log.d(TAG, "status has been reset");
                characterAdapter = new CharacterAdapter(characters, this);
                characterRecyclerView.setAdapter(characterAdapter);
            default:
        }
        return super.onOptionsItemSelected(item);
    }

    //Checks if characters status matches the filter status.
    public void filterItems(String filter) {
        ArrayList<Character> filterList = new ArrayList<>();
        for (Character c : characters) {
            String status = c.getStatus().toLowerCase();
            if (status.equals(filter)) {
                filterList.add(c);
            }
        }
        characterAdapter = new CharacterAdapter(filterList, MainActivity.this);
        characterRecyclerView.setAdapter(characterAdapter);
        Log.d(TAG, "filterItems on name");
    }

    //LifeCycle
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState");
        outState.putParcelable(SAVED_ADAPTER, characterAdapter);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d(TAG, "onRestoreInstanceState");
        characterAdapter = savedInstanceState.getParcelable(SAVED_ADAPTER);
        characterRecyclerView.setAdapter(characterAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        Log.d(TAG, "onPostResume");
    }
}
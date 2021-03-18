package com.example.breakingbadapp;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CharacterAPI extends AsyncTask<String, Void, List<Character>> {

    private final String TAG = getClass().getSimpleName();
    private final String JSON_CHARACTER_NAME = "name";
    private final String JSON_CHARACTER_NICKNAME = "nickname";
    private final String JSON_CHARACTER_STATUS = "status";
    private final String JSON_CHARACTER_IMAGE = "img";
    private final String JSON_CHARACTER_BIRTHDAY = "birthday";
    private final String JSON_CHARACTER_OCCUPATION = "occupation";
    private final String JSON_CHARACTER_APPEARANCES = "appearance";

    private CharacterListener listener = null;

    public CharacterAPI(CharacterListener listener) {
        this.listener = listener;
        Log.d(TAG, "CharacterAPI was called");
    }

    //Gets data from API.
    @Override
    protected List<Character> doInBackground(String... params) {

        String characterUrl = params[0];

        URL url = null;
        HttpURLConnection urlConnection = null;
        try {
            url = new URL(characterUrl);

            //Makes a connection with the API.
            urlConnection = (HttpURLConnection) url.openConnection();

            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            //Checks if the API has more data and puts it into an ArrayList.
            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                String response = scanner.next();
                Log.d(TAG, "response: " + response);

                ArrayList<Character> resultList = convertJsonToArrayList(response);
                return resultList;
            } else {
                return null;
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            //Severs the connection
            if (null != urlConnection) {
                urlConnection.disconnect();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(List<Character> characters) {
        super.onPostExecute(characters);

        Log.d(TAG, "in onPostExecute: " + characters.size() + " items.");

        // Send Character list to adapter.
        listener.setCharacterList(characters);
    }

    // Parses raw JSON data to an ArrayList
    private ArrayList<Character> convertJsonToArrayList(String response) {
        ArrayList<Character> resultList = new ArrayList<>();
          try {
              JSONArray characters = new JSONArray(response);
              for (int i = 0; i < characters.length(); i++) {
                  JSONObject character = characters.getJSONObject(i);

                String name = character.getString(JSON_CHARACTER_NAME);
                String nickname = character.getString(JSON_CHARACTER_NICKNAME);
                String status = character.getString(JSON_CHARACTER_STATUS);
                String image = character.getString(JSON_CHARACTER_IMAGE);
                String birthday = character.getString(JSON_CHARACTER_BIRTHDAY);
                String occupation = character.getString(JSON_CHARACTER_OCCUPATION);
                String appearance = character.getString(JSON_CHARACTER_APPEARANCES);
                resultList.add(new Character(name, nickname, status, image, birthday, occupation, appearance));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d(TAG, "Returning " + resultList.size() + " items.");
        return resultList;
    }

    public interface CharacterListener {
        public void setCharacterList(List<Character> characters);
    }
}

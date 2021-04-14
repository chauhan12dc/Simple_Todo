package com.example.dherya_final_assignment;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;

public class fetchJsonData extends AsyncTask<Void, Void, Void> {

    String data = "";
    String dataparsed = "";
    String singleparsed = "";
    String keys = "";

    @Override
    protected Void doInBackground(Void... voids) {
        try {

//            https://jsonplaceholder.typicode.com/todos/1
            URL url = new URL("https://jsonkeeper.com/b/308S");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            String line = "";
            while (line != null) {
                line = bufferedReader.readLine();
                data += line;
            }
            //remove null value to print
            JSONArray jsonArray = new JSONArray(data);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                singleparsed = "Name:" + jsonObject.get("Name") + "\nAge:" + jsonObject.get("Age") + "\n\n\n";
                dataparsed = dataparsed + singleparsed;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        Frag3.frag3_value.setText(dataparsed);
    }
}
package com.helm108.swimcounterandroid.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;
import com.getpebble.android.kit.PebbleKit;
import com.google.gson.Gson;
import com.helm108.swimcounterandroid.R;
import com.helm108.swimcounterandroid.adapters.SwimListHashmapAdapter;
import com.helm108.swimcounterandroid.controllers.SwimSessions;
import com.helm108.swimcounterandroid.models.SwimSession;

import java.io.*;
import java.util.HashMap;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

  private static final UUID WATCH_APP_UUID = UUID.fromString("a70808b5-d8d3-4086-a8aa-b668d6fdc3e8");
  private static final String FILENAME = "swimsessions.json";
  private SwimSessions swimSessions = new SwimSessions();

  private ListView listView;

  private SwimListHashmapAdapter adapter;


  private void getStoredSessions() {
    System.out.println("Get Stored Sessions");
    try {
      FileInputStream fis = openFileInput(FILENAME);
      int c;
      String json = "";
      while ((c = fis.read()) != -1) {
        json = json + Character.toString((char) c);
      }

      fis.close();


      Gson gson = new Gson();

      swimSessions = gson.fromJson(json, SwimSessions.class);
      System.out.println("Loading");
    }
    catch (IOException e) {
      System.out.println(e);
    }
  }

  private void setStoredSessions() {
    Gson gson = new Gson();
    String json = gson.toJson(swimSessions);

    try {
      FileOutputStream fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
      fos.write(json.getBytes());
      fos.close();
    }
    catch (IOException e) {
      System.out.println(e);
    }
  }

  // Replaces entire thing. Very lazy.
  private void updateAdapter() {
    adapter = new SwimListHashmapAdapter(swimSessions.getSessions());

    // Bind list view to content.
    listView.setAdapter(adapter);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    getStoredSessions();

    setContentView(R.layout.activity_main);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    // Set up list view.
    listView = (ListView) findViewById(R.id.swimListView);

    updateAdapter();

    // Create a receiver to collect logged data
    PebbleKit.PebbleDataLogReceiver dataLogReceiver = new PebbleKit.PebbleDataLogReceiver(WATCH_APP_UUID) {
      @Override
      public void receiveData(Context context, UUID logUuid, Long timestamp, Long tag, byte[] data) {

        System.out.println("Receive Data - UUID: " + logUuid);
        System.out.println("Receive Data - Time: " + timestamp);
        System.out.println("Receive Data - Tag : " + tag);

        Snackbar.make(findViewById(R.id.toolbar), "Pebble Data Received", Snackbar.LENGTH_SHORT)
            .setAction("Action", null).show();

        // TODO Timestamp is time of transmission, not time of swim.
        swimSessions.logSwimSession(logUuid, timestamp * 1000, data);
      }

      @Override
      public void onFinishSession(Context context, UUID logUuid, Long timestamp, Long tag) {
        System.out.println("Session Finished");

        // Update list.
        updateAdapter();
        setStoredSessions();
      }
    };

    // Register the receiver
    PebbleKit.registerDataLogReceiver(getApplicationContext(), dataLogReceiver);
  }
}

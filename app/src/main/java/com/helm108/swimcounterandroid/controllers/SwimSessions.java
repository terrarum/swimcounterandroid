package com.helm108.swimcounterandroid.controllers;

import java.util.HashMap;
import java.util.UUID;

import com.google.gson.Gson;
import com.helm108.swimcounterandroid.activities.MainActivity;
import com.helm108.swimcounterandroid.models.AccelData;
import com.helm108.swimcounterandroid.models.SwimSession;

public class SwimSessions {

  private HashMap<UUID, SwimSession> sessions = new HashMap<>();
  private MainActivity mainActivity;

  public void setMainActivity(MainActivity mainActivity) {
    this.mainActivity = mainActivity;
  }

  public HashMap<UUID, SwimSession> getSessions() {
    return sessions;
  }

  /**
   * Get or create session from sessions hashmap.
   *
   * @param uuid
   * @return
   */
  public SwimSession getSession(UUID uuid, long timestamp) {
    // If session does not exist, create a new one.
    if (!sessions.containsKey(uuid)) {
      // Create new session.
      SwimSession session = new SwimSession();
      // Set UUID and first timestamp.
      session.setID(uuid);
      session.setTimestamp(timestamp);

      // Store the session.
      sessions.put(uuid, session);
    }

    return sessions.get(uuid);
  }

  /**
   * Logs the swim session. Creates a new one if one does not exist.
   *
   * @param uuid
   * @param timestamp
   * @param data
   */
  public void logSwimSession(UUID uuid, long timestamp, byte[] data) {

    // Get session.
    SwimSession session = getSession(uuid, timestamp);

    // Add data to session.
    session.addSwimData(new AccelData(data));

  }

  public String toJSON() {
    Gson gson = new Gson();

    String json = gson.toJson(sessions);
    System.out.println("TO JSON: " + json);
    return json;
  }

  public void fromJSON(String json) {
    System.out.println("FROM JSON: " + json);
  }
}

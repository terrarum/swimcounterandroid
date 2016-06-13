package com.helm108.swimcounterandroid.models;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.UUID;

public class SwimSession {
  private UUID ID;
  private long timestamp;
  private ArrayList<AccelData> swimData = new ArrayList<>();

  public UUID getID() {
    return ID;
  }

  public void setID(UUID ID) {
    this.ID = ID;
  }

  public long getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(long timestamp) {
    this.timestamp = timestamp;
  }

  public boolean addSwimData(AccelData accelData) {
    return swimData.add(accelData);
  }

  public String toString() {
    return new Timestamp(timestamp).toString();
  }
}

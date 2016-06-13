package com.helm108.swimcounterandroid.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.helm108.swimcounterandroid.R;
import com.helm108.swimcounterandroid.models.SwimSession;

import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

public class SwimListHashmapAdapter extends BaseAdapter {
  private final ArrayList mData;

  public SwimListHashmapAdapter(Map<UUID, SwimSession> map) {
    mData = new ArrayList();
    mData.addAll(map.entrySet());
  }

  @Override
  public int getCount() {
    return mData.size();
  }

  @Override
  public Map.Entry<UUID, SwimSession> getItem(int position) {
    return (Map.Entry) mData.get(position);
  }

  @Override
  public long getItemId(int position) {
    // TODO implement you own logic with ID
    return position;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    final View result;

    if (convertView == null) {
      result = LayoutInflater.from(parent.getContext()).inflate(R.layout.swimsession_item, parent, false);
    } else {
      result = convertView;
    }

    Map.Entry<UUID, SwimSession> item = getItem(position);

    // TODO replace findViewById by ViewHolder
//    ((TextView) result.findViewById(android.R.id.text1)).setText(item.getKey());
//    ((TextView) result.findViewById(android.R.id.text2)).setText(item.getValue());
    ((TextView) result.findViewById(android.R.id.text1)).setText(item.getKey().toString());
    ((TextView) result.findViewById(android.R.id.text2)).setText(item.getValue().toString());

    return result;
  }
}

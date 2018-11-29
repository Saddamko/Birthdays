package ua.biz.myshop.birthdays;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class StarsCustomListAdapter extends BaseAdapter {
    private ArrayList<StarsListItem> listData;
    private LayoutInflater layoutInflater;

    public StarsCustomListAdapter(Context context, ArrayList<StarsListItem> listData) {
        this.listData = listData;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.stars_list_item, null);
            holder = new ViewHolder();
            holder.canonical = (TextView) convertView.findViewById(R.id.canonical);
            holder.band = (TextView) convertView.findViewById(R.id.band);
            holder.birthday = (TextView) convertView.findViewById(R.id.birthday);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        StarsListItem newsItem = listData.get(position);
        holder.canonical.setText(newsItem.getCanonical());
        holder.band.setText(newsItem.getBand());
        holder.birthday.setText(newsItem.getBirthday());

        return convertView;
    }

    static class ViewHolder {
        TextView canonical;
        TextView birthday;
        TextView band;
    }
}
package ua.biz.myshop.birthdays;

import java.util.ArrayList;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomListAdapter extends BaseAdapter {
    private ArrayList<ListItem> listData;
    private LayoutInflater layoutInflater;

    public CustomListAdapter(Context context, ArrayList<ListItem> listData) {
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
            convertView = layoutInflater.inflate(R.layout.list_item, null);
            holder = new ViewHolder();
            holder.email = (TextView) convertView.findViewById(R.id.email);
            holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.mobile = (TextView) convertView.findViewById(R.id.mobile);
            holder.birthday = (TextView) convertView.findViewById(R.id.birthday);
            holder.photo = (ImageView) convertView.findViewById(R.id.photo);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ListItem newsItem = listData.get(position);
        holder.name.setText(newsItem.getName());
        holder.mobile.setText(newsItem.getMobile());
        holder.email.setText(newsItem.getEmail());
        holder.birthday.setText(newsItem.getBirthday());

        if (holder.photo != null) {
            new ImageDownloaderTask(holder.photo).execute(newsItem.getUrl());
        }

        return convertView;
    }

    static class ViewHolder {
        TextView name;
        TextView email;
        TextView mobile;
        TextView birthday;
        ImageView photo;
    }
}
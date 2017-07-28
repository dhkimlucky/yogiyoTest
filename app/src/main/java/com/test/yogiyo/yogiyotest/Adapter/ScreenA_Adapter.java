package com.test.yogiyo.yogiyotest.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.test.yogiyo.yogiyotest.App;
import com.test.yogiyo.yogiyotest.Model.UserData;
import com.test.yogiyo.yogiyotest.R;

import java.util.List;

public class ScreenA_Adapter extends BaseAdapter{
    private Activity activity;
    private List<UserData> itemList;
    private ImageLoader imageLoader = App.getInstance().getImageLoader();
    private LayoutInflater inflater;

    public ScreenA_Adapter(Activity activity, List<UserData> itemList) {
        this.activity = activity;
        this.itemList = itemList;
    }

    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public Object getItem(int position) {
        return itemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;

        if(inflater == null){
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        if(convertView == null){
            convertView = inflater.inflate(R.layout.adapter_screen_a, null);

            viewHolder = new ViewHolder();
            viewHolder.login = (TextView) convertView.findViewById(R.id.login);
            viewHolder.ava_image = (ImageView) convertView.findViewById(R.id.ava_image);
            viewHolder.like_image = (ImageView) convertView.findViewById(R.id.like_image);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.like_image.setTag(position);

        final UserData item = itemList.get(position);

        viewHolder.login.setText(item.getLogin());

        imageLoader.get(item.getAva_url(), ImageLoader.getImageListener(viewHolder.ava_image,
                R.drawable.black_user_symbol, R.drawable.question_sign));

        return convertView;
    }

    private class ViewHolder{
        private TextView login;
        private ImageView ava_image;
        private ImageView like_image;
    }
}

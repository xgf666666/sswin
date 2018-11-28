package com.darsh.multipleimageselect.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.darsh.multipleimageselect.R;
import com.darsh.multipleimageselect.models.Album;

import java.util.ArrayList;

/**
 * Created by Darshan on 4/14/2015.
 */
public class CustomAlbumSelectAdapter extends CustomGenericAdapter<Album> {

    private final com.darsh.multipleimageselect.helpers.ImageLoader mLoader;

    public CustomAlbumSelectAdapter(Context context, ArrayList<Album> albums) {
        super(context, albums);
        mLoader = com.darsh.multipleimageselect.helpers.ImageLoader.getInstance();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.grid_view_item_album_select, null);

            viewHolder = new ViewHolder();
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.image_view_album_image);
            viewHolder.textView = (TextView) convertView.findViewById(R.id.text_view_album_name);

            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.imageView.getLayoutParams().width = size;
        viewHolder.imageView.getLayoutParams().height = size;

        viewHolder.textView.setText(arrayList.get(position).name);
        viewHolder.imageView.setImageResource(R.mipmap.ic_avatar_default);
//        Glide.with(context)
//                .load(arrayList.get(position).cover)
//                .placeholder(R.drawable.image_placeholder).centerCrop().into(viewHolder.imageView);
        if (position == 0) {
//            viewHolder.imageView.setScaleType(ImageView.ScaleType.CENTER);
            viewHolder.imageView.setImageResource(R.mipmap.ic_camera_);
        } else {
//                    Glide.with(context)
//                .load(arrayList.get(position).cover)
//                .placeholder(R.drawable.image_placeholder).centerCrop().into(viewHolder.imageView);

            mLoader.loadImage(arrayList.get(position).cover,viewHolder.imageView);
//            ImageLoader.loadAvatarIcon(context, viewHolder.imageView, arrayList.get(position).cover);
        }

        return convertView;
    }

    private static class ViewHolder {
        public ImageView imageView;
        public TextView textView;
    }
}

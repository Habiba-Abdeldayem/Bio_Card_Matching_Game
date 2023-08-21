package com.example.biomatchinggame;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CardAdapter extends BaseAdapter {

    private Context context;
    private List<Card> cards;
    private Map<Integer, ImageView> imageViewMap = new HashMap<>(); // Keep track of ImageView instances


    public CardAdapter(Context context, List<Card> cards) {
        this.context = context;
        this.cards = cards;
    }

    @Override
    public int getCount() {
        return cards.size();
    }

    @Override
    public Object getItem(int position) {
        return cards.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            imageView = new ImageView(context);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(150, 150));
            imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        } else {
            imageView = (ImageView) convertView;
        }

        Card card = cards.get(position);
        if (card.isFlipped() || card.isMatched()) {
            imageView.setImageResource(card.getImageResourceId());
        } else {
            imageView.setImageResource(R.drawable.qmark);
        }
        // Store the ImageView instance in the map
        imageViewMap.put(position, imageView);
        return imageView;
    }

    // Get the ImageView associated with a specific card position
    public ImageView getImageViewForCard(int position) {
        return imageViewMap.get(position);
    }
}

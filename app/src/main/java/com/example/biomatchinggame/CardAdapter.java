package com.example.biomatchinggame;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardViewHolder> {

    private Context context;
    private List<Card> cards;
    private static final int LAYOUT_TERM = R.layout.item_card;
    private static final int LAYOUT_EXPLANATION = R.layout.explain_card;

    public CardAdapter(Context context, List<Card> cards) {
        this.context = context;
        this.cards = cards;
    }

    public int getItemViewType(int position) {
        Card card = cards.get(position);
        return card.isExplanation() ? LAYOUT_EXPLANATION : LAYOUT_TERM;
    }
    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView;

        if (viewType == LAYOUT_TERM) {
            itemView = inflater.inflate(LAYOUT_TERM, parent, false);
        } else {
            itemView = inflater.inflate(LAYOUT_EXPLANATION, parent, false);
        }

        return new CardViewHolder(itemView, viewType); // Pass the viewType here
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        Card card = cards.get(position);
        ImageView qTermImgView = holder.questionTermImageView;
        ImageView qExplainImgView = holder.questionExplainImageView;
//
//        ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
//
//        // Adjust card width and height here
//        int cardWidth = 250; // Set the desired width in pixels
//        int cardHeight = 250; // Set the desired height in pixels
//        layoutParams.width = cardWidth;
//        layoutParams.height = cardHeight;
//        holder.itemView.setLayoutParams(layoutParams);

        if (card.isExplanation()) {
            if (card.isFlipped() || card.isMatched()) {
                qExplainImgView.setVisibility(View.GONE);
                holder.explainImageView.setVisibility(View.VISIBLE);
                holder.explainImageView.setImageResource(card.getImageResourceId());

            } else{
                qExplainImgView.setVisibility(View.VISIBLE);
                holder.explainImageView.setVisibility(View.GONE);
                qExplainImgView.setImageResource(R.drawable.qmark);
            }




        }
        else {
            if (!(card.isExplanation())) {
                if (card.isFlipped() || card.isMatched()) {
//                holder.termTextView.setText(card.getText());
                    holder.termTextView.setVisibility(View.VISIBLE);

                    holder.termTextView.setText("Term");
                    qTermImgView.setVisibility(View.GONE);


                } else { // not flipped
                    holder.termTextView.setVisibility(View.GONE);
                    qTermImgView.setVisibility(View.VISIBLE);
                    qTermImgView.setImageResource(R.drawable.qmark);

                }


            }
        }

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent ii = new Intent(context,PopupCard.class);
                v.getContext().startActivity(ii);
                return false;
            }
        });
    }


    @Override
    public int getItemCount() {
        return cards.size();
    }

    public class CardViewHolder extends RecyclerView.ViewHolder {
        public TextView termTextView;
        public ImageView explainImageView;
        public ImageView questionTermImageView;
        public ImageView questionExplainImageView;



        public CardViewHolder(View itemView,int viewType) {
            super(itemView);

            if (viewType == LAYOUT_TERM) {
                termTextView = itemView.findViewById(R.id.termTextView);
                questionTermImageView = itemView.findViewById(R.id.questionTermImageView);

            } else {
                explainImageView = itemView.findViewById(R.id.explainImgView);
                questionExplainImageView = itemView.findViewById(R.id.questionExplainImageView);
            }


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Get the adapter position of the clicked item
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        Card clickedCard = cards.get(position);
                        // Call the onCardClick method from your MainActivity
                        ((MainActivity) v.getContext()).onCardClick(position);
                    }
                }
            });



        }


    }

}

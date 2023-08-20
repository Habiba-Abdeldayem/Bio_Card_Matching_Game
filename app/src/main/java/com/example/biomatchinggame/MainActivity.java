package com.example.biomatchinggame;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private GridView gridView;
    private CardAdapter adapter;
    private int score = 0;

    private int gridSize = 3;
    private List<Card> cards = generateCards(gridSize);

    private Card firstFlippedCard = null;
    private boolean isClickable = true;
    Card clickedCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridView = findViewById(R.id.gridView);
        adapter = new CardAdapter(this, cards);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                clickedCard = cards.get(position);
                onCardClick(position);
            }
        });
    }

    private List<Card> generateCards(int size) {
        List<Card> cardList = new ArrayList<>();
        int[] imageResourceIds = {R.drawable.brain, R.drawable.brain_txt,
                R.drawable.cell,R.drawable.cell_txt,
                R.drawable.heart,R.drawable.heart_txt,
                R.drawable.lungs,R.drawable.lungs_txt,
                R.drawable.photosy,R.drawable.plant,
                R.drawable.heart,R.drawable.heart_txt}; // Add more IDs
        int imageIndex = 0;
        for (int i = 1; i <= size * 2; i++) {
            cardList.add(new Card(i,imageResourceIds[imageIndex++]));
            cardList.add(new Card(i,imageResourceIds[imageIndex]));
            imageIndex = (imageIndex + 1) % imageResourceIds.length;
        }
//        Collections.shuffle(cardList);
        return cardList;
    }

    private void onCardClick(int position) {
        if (!isClickable) {
            return;
        }

        Card clickedCard = cards.get(position);

        if (!clickedCard.isFlipped()) {
            clickedCard.flip();
            adapter.notifyDataSetChanged();

            final ImageView imageView = adapter.getImageResourceId(position);

            imageView.animate().scaleX(2f).scaleY(2f).alpha(0f).setDuration(1000).withEndAction(new Runnable() {
                @Override
                public void run() {
                    imageView.setScaleX(1f);
                    imageView.setScaleY(1f);
                    imageView.setAlpha(1f);
                    // Continue with your existing logic here
                    // ...
                }
            }).start();

            if (firstFlippedCard == null) {
                firstFlippedCard = clickedCard;
            } else {
                isClickable = false;
                if (firstFlippedCard.getId() == clickedCard.getId()) {
                    firstFlippedCard.setMatched(true);
                    clickedCard.setMatched(true);
                    isClickable = true;
                    adapter.notifyDataSetChanged();
                    firstFlippedCard = null;

                    score += 10;
                    updateScoreTextView();
                } else {
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            firstFlippedCard.flip();
                            clickedCard.flip();
                            adapter.notifyDataSetChanged();
                            firstFlippedCard = null;
                            isClickable = true;
                        }
                    }, 500);
                }
            }

            if (allCardsMatched()) {
                Toast.makeText(this, "Congratulations! You've won!", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void updateScoreTextView() {
        TextView scoreTextView = findViewById(R.id.scoreTextView);
        scoreTextView.setText("Score: " + score);
        scoreTextView.setTextColor(getResources().getColor(R.color.green));

    }

    private boolean allCardsMatched() {
        for (Card card : cards) {
            if (!card.isMatched()) {
                return false;
            }
        }
        return true;
    }
}

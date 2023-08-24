package com.example.biomatchinggame;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    private CardAdapter cardAdapter;
    private int score = 0;
    Button pop;

    private int gridSize = 3;
    private List<Card> cards = generateCards(gridSize);

    private Card firstFlippedCard = null;
    private boolean isClickable = true;
    Card clickedCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pop = (Button) findViewById(R.id.pop);
        recyclerView = findViewById(R.id.recyclerView);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3); // Use the appropriate number of columns
        recyclerView.setLayoutManager(layoutManager);

        cardAdapter = new CardAdapter(this, cards);
        recyclerView.setAdapter(cardAdapter);


        pop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),PopupCard.class);
                startActivity(i);
            }
        });


    }

    private List<Card> generateCards(int size) {
        List<Card> cardList = new ArrayList<>();
        int[] imageResourceIds = {R.drawable.plant, R.drawable.heart_txt,
                R.drawable.cell,R.drawable.cell_txt,
                R.drawable.heart,R.drawable.heart_txt,
                R.drawable.lungs,R.drawable.lungs_txt,
                R.drawable.photosy,R.drawable.plant,
                R.drawable.heart,R.drawable.heart_txt}; // Add more IDs
        int imageIndex = 0;
        for (int i = 1; i <= size * 2; i++) {
            Card term_card = new Card(i,imageResourceIds[imageIndex++]);
            term_card.setExplanation(false);
            Card explain_card = new Card(i,imageResourceIds[imageIndex]);
            explain_card.setExplanation(true);
            cardList.add(term_card);
            cardList.add(explain_card);
            imageIndex = (imageIndex + 1) % imageResourceIds.length;
        }
//        Collections.shuffle(cardList);
        return cardList;
    }

    void onCardClick(int position) {
        if (!isClickable) {
            return;
        }

        clickedCard = cards.get(position);

        if (!clickedCard.isFlipped()) {
            clickedCard.flip();
            cardAdapter.notifyDataSetChanged();


            if (firstFlippedCard == null) {
                firstFlippedCard = clickedCard;
            } else {
                isClickable = false;
                if (firstFlippedCard.getId() == clickedCard.getId()) {
                    firstFlippedCard.setMatched(true);
                    clickedCard.setMatched(true);
                    isClickable = true;
                    cardAdapter.notifyDataSetChanged();
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
                            cardAdapter.notifyDataSetChanged();
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

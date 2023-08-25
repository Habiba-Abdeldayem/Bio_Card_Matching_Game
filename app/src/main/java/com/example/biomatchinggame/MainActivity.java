package com.example.biomatchinggame;

import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    private CardAdapter cardAdapter;
    private int score = 0;
    Button pop;

    private int gridSize = 3;
    private List<Card> cards;

    private Card firstFlippedCard = null;
    private boolean isClickable = true;
    Card clickedCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cards = generateCards(gridSize);
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

        Resources res = getResources();
        String[] termNames = getResources().getStringArray(R.array.term_names);
        TypedArray termImages = getResources().obtainTypedArray(R.array.image_resource_ids);
        int qImage = R.drawable.qmark;
        String[] termDescriptions = getResources().getStringArray(R.array.term_descriptions);

        List<Card> cardList = new ArrayList<>();
//        int[] imageResourceIds = {R.drawable.pic_animal_cell, R.drawable.pic_animal_cell,
//                R.drawable.pic_plant_cell,R.drawable.pic_plant_cell,
//                R.drawable.pic_cell_wall,R.drawable.pic_cell_wall,
//                R.drawable.pic_cell_membrane,R.drawable.pic_cell_membrane,
//                R.drawable.pic_chloroplasts,R.drawable.pic_chloroplasts,
//                R.drawable.pic_sap_vacuole,R.drawable.pic_sap_vacuole}; // Add more IDs
//        int imageIndex = 0;
        for (int i = 0; i < size * 2; i++) {
            String termName = termNames[i];
            int imageResourceId = termImages.getResourceId(i, -1);
            String description = i < termDescriptions.length ? termDescriptions[i] : "";

            // Term Card
            Card term_card = new Card(i, termName, qImage,"");
            term_card.setExplanation(false);

            // Explain card
            Card explain_card = new Card(i, "", imageResourceId,description);
            explain_card.setExplanation(true);

            cardList.add(term_card);
            cardList.add(explain_card);

        }
        termImages.recycle(); // Don't forget to recycle the TypedArray

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

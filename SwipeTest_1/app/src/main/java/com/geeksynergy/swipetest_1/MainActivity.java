package com.geeksynergy.swipetest_1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.lorentzos.flingswipe.SwipeFlingAdapterView;
import com.wenchao.cardstack.CardStack;

import java.util.ArrayList;

import butterknife.BindFloat;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.container)
    CardStack cardStack;

    ArrayList<SomeList> card_list;

    SwipeCardAdapter swipeCardAdapter;

    private final String TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.btn_okay)
    AppCompatButton btn_okay;

    @BindView(R.id.resetstack)
    AppCompatButton resetstack;

    @BindView(R.id.input_visible_card_num)
    AppCompatEditText input_visible_card_num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        card_list = new ArrayList<SomeList>();
        card_list.add(new SomeList("1"));
        card_list.add(new SomeList("2"));
        card_list.add(new SomeList("3"));
        card_list.add(new SomeList("4"));
        card_list.add(new SomeList("5"));
        card_list.add(new SomeList("6"));
        card_list.add(new SomeList("7"));
        card_list.add(new SomeList("8"));
        card_list.add(new SomeList("9"));

        cardStack.setContentResource(R.layout.layout_card);
        swipeCardAdapter = new SwipeCardAdapter(MainActivity.this,0, card_list );
        cardStack.setAdapter(swipeCardAdapter);
        cardStack.setStackMargin(18);

       cardStack.setListener(new CardStack.CardEventListener() {
            @Override
            public boolean swipeEnd(int section, float distance) {
                Log. e (TAG, "Onswipe End");
                return true;
            }

            @Override
            public boolean swipeStart(int section, float distance) {
                Log. e (TAG, "Onswipe Start");
                return true;
            }

            @Override
            public boolean swipeContinue(int section, float distanceX, float distanceY) {
                Log. e (TAG, "Onswipe Continue");
                return true;
            }

            @Override
            public void discarded(int mIndex, int direction) {

                Log.e(TAG, String.valueOf(mIndex));

                int swiped_card_postion = mIndex -1;

                if (swiped_card_postion >= card_list.size()) {
                    swiped_card_postion = 0;
                }
                 //getting the string attached with the card

                String swiped_card_text = card_list.get(swiped_card_postion).getData1().toString();

                if (direction == 1) {

                    Toast.makeText(getApplicationContext(),swiped_card_text+ " Swipped to Right",Toast.LENGTH_SHORT).show();

                } else if (direction == 0) {

                    Toast.makeText(getApplicationContext(),swiped_card_text+" Swipped to Left",Toast.LENGTH_SHORT).show();

                } else {

                    Toast.makeText(getApplicationContext(),swiped_card_text+" Swipped to Bottom",Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void topCardTapped() {
                Toast.makeText(getApplicationContext(),"Clicked top card",Toast.LENGTH_SHORT).show();
            }
        });

        btn_okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int num = Integer.parseInt(String.valueOf(input_visible_card_num.getText()));
                if (num <= card_list.size()) {
                    cardStack.setVisibleCardNum(num);
                }
            }
        });

    }
}

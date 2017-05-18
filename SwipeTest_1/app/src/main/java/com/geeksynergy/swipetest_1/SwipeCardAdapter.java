package com.geeksynergy.swipetest_1;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by Foolish_Guy on 5/18/2017.
 */

public class SwipeCardAdapter extends ArrayAdapter<SomeList> {

    List<SomeList> list;

    public SwipeCardAdapter(@NonNull Context context, @LayoutRes int resource, ArrayList<SomeList> list) {
        super(context, resource);
        this.list = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        AppCompatTextView textView = ButterKnife.findById(convertView, R.id.tv_card_data);
        textView.setText(list.get(position).getData1());
        return convertView;
    }

    @Override
    public int getCount() {
        return list.size();
    }
}

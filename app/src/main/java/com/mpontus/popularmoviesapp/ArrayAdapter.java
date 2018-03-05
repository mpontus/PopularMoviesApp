package com.mpontus.popularmoviesapp;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

/**
 * ListView Adapter with modifable rendering method
 */
abstract class ArrayAdapter<T> extends BaseAdapter {

    private final LayoutInflater mInflater;
    private final int mResource;
    private final int mTextViewId;
    private final List<T> mOptions;

    ArrayAdapter(Context context, @LayoutRes int resource, T[] options) {
        this(context, resource, 0, options);
    }

    ArrayAdapter(Context context, @LayoutRes int resource, @IdRes int textViewId, T[] options) {
        mInflater = LayoutInflater.from(context);
        mResource = resource;
        mTextViewId = textViewId;
        mOptions = Arrays.asList(options);
    }

    @Override
    public int getCount() {
        return mOptions.size();
    }

    @Override
    public T getItem(int position) {
        return mOptions.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String label = this.getLabel(position);
        final View view;
        final TextView text;

        if (convertView == null) {
            view = mInflater.inflate(mResource, parent, false);
        } else {
            view = convertView;
        }

        try {
            if (mTextViewId == 0) {
                text = (TextView) view;
            } else {
                text = view.findViewById(mTextViewId);

                if (text == null) {
                    throw new IllegalStateException("TextView id must reference a view in the layout");
                }
            }
        } catch (ClassCastException e) {
            throw new IllegalStateException("You must specify TextView id or make root view a TextView");
        }

        text.setText(label);

        return view;
    }

    int getPosition(T value) {
        return mOptions.indexOf(value);
    }

    abstract String getLabel(int position);
}

package com.example.mi_plataforma_conectbd;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mi_plataforma_conectbd.placeholder.PlaceholderContent.PlaceholderItem;
import com.example.mi_plataforma_conectbd.databinding.FragmentItemBinding;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link PlaceholderItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyinventarioRecyclerViewAdapter extends RecyclerView.Adapter<MyinventarioRecyclerViewAdapter.ViewHolder> {

    private final List<PlaceholderItem> mValues;

    public MyinventarioRecyclerViewAdapter(List<PlaceholderItem> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).id);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView mIdView;
        public PlaceholderItem mItem;

        public ViewHolder(FragmentItemBinding binding) {
            super(binding.getRoot());
            mIdView = binding.itemNumber;
        }

        @Override
        public String toString() {
            return super.toString() + " '" + "'";
        }
    }
}
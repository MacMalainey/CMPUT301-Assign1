package com.malainey.medbook.views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.malainey.medbook.R;

import java.util.function.Consumer;

public class TripleRowListItemAdapter extends RecyclerView.Adapter<TripleRowListItemAdapter.ViewHolder> {

    private final ContentProvider provider;
    private final Consumer<Integer> onItemAction;

    // Pass in the contact array into the constructor
    public TripleRowListItemAdapter(@NonNull ContentProvider provider, Consumer<Integer> onItemAction) {
        this.provider = provider;
        this.onItemAction = onItemAction;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View medicationItemView = inflater.inflate(R.layout.view_triple_row_list_item, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(medicationItemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TripleRowListItemAdapter.ViewHolder holder, int position) {
        // Set item views based on your views and data model
        holder.nameTextView.setText(provider.getTitle(position));
        holder.dosageTextView.setText(provider.getSecondRow(position));
        holder.dateStartedTextView.setText(provider.getThirdRow(position));
        holder.actionButton.setOnClickListener(view -> onItemAction.accept(position));
    }

    @Override
    public int getItemCount() {
        return provider.getItemCount();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public final TextView nameTextView;
        public final TextView dosageTextView;
        public final TextView dateStartedTextView;
        public final ImageButton actionButton;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(@NonNull View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            nameTextView = (TextView) itemView.findViewById(R.id.tripleListItemTitle);
            dosageTextView = (TextView) itemView.findViewById(R.id.tripleListItemSecondRow);
            dateStartedTextView = (TextView) itemView.findViewById(R.id.tripleListItemThirdRow);
            actionButton = (ImageButton) itemView.findViewById(R.id.actionButton);

        }

    }

    public interface ContentProvider {
        String getTitle(int pos);
        String getSecondRow(int pos);
        String getThirdRow(int pos);
        int getItemCount();
    }
}

package com.malainey.medbook.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.malainey.medbook.R;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.IntStream;

public class TripleRowListItemAdapter extends RecyclerView.Adapter<TripleRowListItemAdapter.ViewHolder> {

    /**
     * Content provider to get formatted data from
     */
    private final ContentProvider provider;

    /**
     * Item model to keep track of selected items
     */
    private final List<ItemModel> itemModelList = new ArrayList<>();

    /**
     * Whether or not to render checkboxes
     */
    private boolean renderCheckboxes = false;

    /**
     * Constructor for TripleRowListItemAdapter
     * @param provider for formatted data to display
     */
    public TripleRowListItemAdapter(@NonNull ContentProvider provider) {
        this.provider = provider;
    }

    /**
     * Set whether or not checkboxes should be shown
     * @param state if true checkboxes will be shown
     */
    @SuppressLint("NotifyDataSetChanged")
    public void showCheckboxes(boolean state) {
        renderCheckboxes = state;
        notifyDataSetChanged();
    }

    /**
     * Get the position of all selected items
     * @return array of positions for selected items
     */
    public int[] getSelectedItems() {
        return IntStream.range(0, itemModelList.size()).filter(i -> itemModelList.get(i).isChecked).toArray();
    }

    /**
     * Notify that an item has been removed at the given position
     *
     * WARNING - use this instead of {notifyItemRemoved} otherwise selected items lists won't be properly updated.
     * @param pos position of item removed
     */
    public void removeAt(int pos) {
        itemModelList.remove(pos);
        notifyItemRemoved(pos);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View medicationItemView = inflater.inflate(R.layout.view_triple_row_list_item, parent, false);

        // Return a new holder instance
        return new ViewHolder(medicationItemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TripleRowListItemAdapter.ViewHolder holder, int position) {
        // Set item views based on your views and data model
        holder.nameTextView.setText(provider.getTitle(position));
        holder.dosageTextView.setText(provider.getSecondRow(position));
        holder.dateStartedTextView.setText(provider.getThirdRow(position));

        ItemModel model = new ItemModel();
        itemModelList.add(position, model);

        final Consumer<Integer> action = provider.getAction(position);
        holder.actionButton.setOnClickListener(null);
        if (action != null) {
            holder.actionButton.setVisibility(View.VISIBLE);
            holder.actionButton.setImageResource(provider.getActionDrawableId(position));
            holder.actionButton.setOnClickListener(view -> action.accept(itemModelList.indexOf(model)));
        } else
            holder.actionButton.setVisibility(View.GONE);

        holder.checkBox.setOnCheckedChangeListener(null);
        holder.checkBox.setChecked(false);
        if (renderCheckboxes && provider.isSelectable(position)) {
            holder.checkBox.setVisibility(View.VISIBLE);
            holder.checkBox.setOnCheckedChangeListener((view, enabled) -> model.isChecked = enabled);
        }
        else
            holder.checkBox.setVisibility(View.GONE);
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
        public final CheckBox checkBox;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(@NonNull View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            nameTextView = itemView.findViewById(R.id.tripleListItemTitle);
            dosageTextView = itemView.findViewById(R.id.tripleListItemSecondRow);
            dateStartedTextView = itemView.findViewById(R.id.tripleListItemThirdRow);
            actionButton = itemView.findViewById(R.id.actionButton);
            checkBox = itemView.findViewById(R.id.itemCheckBox);
        }

    }

    private static class ItemModel {
        boolean isChecked;
    }

    public interface ContentProvider {
        /**
         * Get the main title for a given item
         * @param pos position of item
         * @return title to display
         */
        String getTitle(int pos);

        /**
         * Get the second row data for a given item
         * @param pos position of item
         * @return second row to display
         */
        String getSecondRow(int pos);

        /**
         * Get the third row data for a given item
         * @param pos position of item
         * @return third row to display
         */
        String getThirdRow(int pos);

        /**
         * Get the number of items to display
         */
        int getItemCount();

        /**
         * Set if the item should be selectable
         * @param pos position of item
         * @return boolean to set if the item should be selectable
         */
        boolean isSelectable(int pos);

        /**
         * Action to run if the item's button is selected
         *
         * If null is returned no action button is displayed
         * @param pos position of item
         * @return callback to run on action button clicked
         *      (callback is provided the will be current position of item)
         */
        Consumer<Integer> getAction(int pos);

        /**
         * Get the id of the drawable to display for the action button
         * @param pos position of item
         * @return id of drawable
         */
        int getActionDrawableId(int pos);
    }
}

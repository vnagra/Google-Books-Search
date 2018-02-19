package com.vijendernagra.googlebookssearch.view.adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.vijendernagra.googlebookssearch.R;
import com.vijendernagra.googlebookssearch.databinding.ListItemBookBinding;
import com.vijendernagra.googlebookssearch.models.pojo.Book;
import com.vijendernagra.googlebookssearch.view.listener.ClickListener;

import java.util.List;

/**
 * Created by vijendernagra on 2/17/18.
 */

public class GoogleBookAdapter extends RecyclerView.Adapter<GoogleBookAdapter.ViewHolder> {

    private List<Book> listBooks;
    private ClickListener listener;

    public GoogleBookAdapter(List<Book> listBooks, ClickListener listener) {
        this.listBooks = listBooks;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ListItemBookBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.list_item_book,
                parent,
                false
        );

        ViewHolder holder = new ViewHolder(binding);
        holder.itemView.setOnClickListener(view -> {
            if (listener != null) {
                int position = holder.getAdapterPosition();
                Book book = listBooks.get(position);
                listener.onBookClick(book, true);
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Book book = listBooks.get(position);
        holder.binding.setBook(book);
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return listBooks != null ? listBooks.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ListItemBookBinding binding;

        public ViewHolder(ListItemBookBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

    }
}

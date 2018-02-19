package com.vijendernagra.googlebookssearch.view.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.vijendernagra.googlebookssearch.R;
import com.vijendernagra.googlebookssearch.databinding.FragmentListBookBinding;
import com.vijendernagra.googlebookssearch.models.pojo.Book;
import com.vijendernagra.googlebookssearch.models.pojo.SearchResult;
import com.vijendernagra.googlebookssearch.mvp.GoogleBookMVP;
import com.vijendernagra.googlebookssearch.presenter.GoogleBooksPresenter;
import com.vijendernagra.googlebookssearch.view.adapter.GoogleBookAdapter;
import com.vijendernagra.googlebookssearch.view.listener.ClickListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vijendernagra on 2/17/18.
 */

public class GoogleBooksListFragment extends Fragment
        implements GoogleBookMVP.View {

    private static GoogleBookMVP.View viewInstance;
    private static GoogleBookMVP.Presenter presenter;
    private List<Book> listBooks;
    private GoogleBookAdapter adapter;
    private FragmentListBookBinding binding;

    public static GoogleBooksListFragment getViewInstance() {
        if (viewInstance == null) {
            viewInstance = new GoogleBooksListFragment();

            if (presenter == null) {
                presenter = new GoogleBooksPresenter(viewInstance);
            }
        }

        return (GoogleBooksListFragment) viewInstance;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        listBooks = new ArrayList<>();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_list_book,
                container,
                false);

        adapter = new GoogleBookAdapter(listBooks,
                (book, bundle) -> {
                    if (getActivity() instanceof ClickListener) {
                        ClickListener listener = (ClickListener) getActivity();
                        listener.onBookClick(book, bundle);
                    }
                });

        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void searhByQuery(String query){
        presenter.searchListOfBooks(query);
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onEvent(SearchResult event) {
        this.updateListView(event);
        EventBus.getDefault().removeStickyEvent(this);
    }

    @Override
    public void updateListView(SearchResult searchResult){
        if (searchResult != null) {
            this.listBooks.clear();

            if (searchResult.getListBooks() != null && searchResult.getListBooks().size() > 0) {
                this.listBooks.addAll(searchResult.getListBooks());
                binding.rvBooks.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            } else {
                this.showNoResults();
            }

            return;
        }

        this.showError();
    }

    @Override
    public void showError() {
        Toast.makeText(getActivity(), R.string.msg_error_search_books, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showNoResults() {
        Toast.makeText(getActivity(), R.string.msg_warning_no_results, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }
}


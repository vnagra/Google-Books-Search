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
import com.vijendernagra.googlebookssearch.databinding.FragmentMyCollectionBinding;
import com.vijendernagra.googlebookssearch.models.realm.po.RealmBook;
import com.vijendernagra.googlebookssearch.mvp.CollectionMVP;
import com.vijendernagra.googlebookssearch.presenter.MyCollectionPresenter;
import com.vijendernagra.googlebookssearch.view.adapter.CollectionRecyclerViewAdapter;
import com.vijendernagra.googlebookssearch.view.listener.ClickListener;
import com.vijendernagra.googlebookssearch.view.listener.LongClickListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import io.realm.RealmResults;

/**
 * Created by vijendernagra on 2/17/18.
 */

public class MyCollectionFragment extends Fragment
        implements CollectionMVP.GridView {

    private static CollectionMVP.GridView viewInstance;
    private static CollectionMVP.GridPresenter presenter;
    private RealmResults<RealmBook> realmBooks;
    private FragmentMyCollectionBinding binding;

    public static MyCollectionFragment getViewInstance() {
        if (viewInstance == null) {
            viewInstance = new MyCollectionFragment();

            if (presenter == null) {
                presenter = new MyCollectionPresenter(viewInstance);
            }
        }

        return (MyCollectionFragment) viewInstance;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.binding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_my_collection,
                container,
                false
        );

        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
        presenter.getMyCollection();
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onEvent(RealmResults<RealmBook> realmBooks) {
        this.realmBooks = realmBooks;
        this.updateGridView();
    }

    @Override
    public void updateGridView() {
        if (this.realmBooks != null) {

            CollectionRecyclerViewAdapter adapter = new CollectionRecyclerViewAdapter(
                    getActivity(), this.realmBooks, true, true,
                    realmBook -> {
                        if (getActivity() instanceof LongClickListener) {
                            LongClickListener listener = (LongClickListener) getActivity();
                            listener.onBookLongClick(realmBook);
                        }
                    },
                    (book, bundle) -> {
                        if (getActivity() instanceof ClickListener) {
                            ClickListener listener = (ClickListener) getActivity();
                            listener.onBookClick(book, bundle);
                        }
                    }
            );

            this.binding.rvCollection.setAdapter(adapter);
        }
    }

    @Override
    public void removeBookFromMyCollection(RealmBook realmBook) {
        presenter.removeBook(realmBook);
    }

    @Override
    public void showError() {
        Toast.makeText(getActivity(), R.string.msg_error_search_books, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.closeRealm();
    }

}


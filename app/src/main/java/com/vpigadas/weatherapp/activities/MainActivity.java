package com.vpigadas.weatherapp.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;

import com.vpigadas.weatherapp.R;
import com.vpigadas.weatherapp.database.LocationStore;
import com.vpigadas.weatherapp.ui.adapter.LocationAdapter;
import com.vpigadas.weatherapp.ui.viewmodel.MainViewModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MainActivity extends AbstractActivity {

    private static final int SEARCH_DELAY = 500;

    @Nullable
    private RecyclerView recyclerView;
    @NonNull
    private Handler mHandler = new Handler();
    @Nullable
    private String mQueryString;
    @NonNull
    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
    }

    @Override
    protected void initUi() {
        recyclerView = findViewById(R.id.recycler_view);

        viewModel.getSearchResult().observe(MainActivity.this, new Observer<List<Pair<String, String>>>() {
            @Override
            public void onChanged(@android.support.annotation.Nullable List<Pair<String, String>> strings) {

                if (strings == null || strings.size() == 0 || recyclerView == null) {
                    getSaved();

                    return;
                }

                if (recyclerView != null) {
                    RecyclerView.Adapter adapter = recyclerView.getAdapter();

                    if (adapter == null) {
                        adapter = LocationAdapter.getInstance();
                        recyclerView.setAdapter(adapter);
                    }

                    if (adapter instanceof LocationAdapter) {
                        ((LocationAdapter) adapter).addItems(MainActivity.this, getString(R.string.string_location), strings);
                    }
                }
            }
        });

        viewModel.searchFor(mQueryString);
    }

    private void getSaved() {
        appDatabase.userDao().getAll().observe(this, new Observer<List<LocationStore>>() {
            @Override
            public void onChanged(@android.support.annotation.Nullable List<LocationStore> locationStore) {
                if (locationStore != null && recyclerView != null) {

                    RecyclerView.Adapter adapter = recyclerView.getAdapter();

                    if (adapter == null) {
                        adapter = LocationAdapter.getInstance();
                        recyclerView.setAdapter(adapter);
                    }

                    if (adapter instanceof LocationAdapter) {
                        ((LocationAdapter) adapter).addItems(MainActivity.this, getString(R.string.string_favorite_location), locationStore);
                    }
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        MenuItem mSearch = menu.findItem(R.id.action_search);

        SearchView mSearchView = (SearchView) mSearch.getActionView();
        mSearchView.setQueryHint(getString(R.string.menu_search));

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(final String newText) {
                mQueryString = newText;
                mHandler.removeCallbacksAndMessages(null);

                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (TextUtils.isEmpty(newText) && recyclerView != null) {
                            recyclerView.setAdapter(null);
                        }
                        viewModel.searchFor(mQueryString);
                    }
                }, SEARCH_DELAY);
                return true;
            }
        });

        return super.onPrepareOptionsMenu(menu);
    }
}

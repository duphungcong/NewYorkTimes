package com.duphungcong.newyorktimes.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import com.duphungcong.newyorktimes.R;
import com.duphungcong.newyorktimes.model.ArticleFilter;

/**
 * Created by udcun on 2/26/2017.
 */

public class FilterFragment extends DialogFragment {

    private ArticleFilter articleFilter;
    private Spinner mSort;

    // Defines the listener interface with a method passing back data result.
    public interface SaveFilterListener {
        void onSaveFilter(ArticleFilter filter);
    }

    public FilterFragment() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }

    public static FilterFragment newInstance(ArticleFilter filter) {
        FilterFragment fragment = new FilterFragment();

        Bundle args = new Bundle();
        args.putSerializable("filter", filter);
        fragment.setArguments(args);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_filter, container);

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.search_setting_toolbar);
        toolbar.setTitle(getString(R.string.filter_dialog_title));

        toolbar.inflateMenu(R.menu.menu_search_setting);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_save:
                        saveFilter();
                        return true;
                    case R.id.action_cancel:
                        cancelFilter();
                        return true;
                    default:
                        return false;
                }
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        articleFilter = (ArticleFilter) getArguments().getSerializable("filter");

        mSort = (Spinner) view.findViewById(R.id.spSort);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.sort_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        mSort.setAdapter(adapter);
        setSpinnerToValue(mSort, articleFilter.getSort());

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);

        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        return dialog;
    }

    public void cancelFilter() {
        dismiss();
    }

    public void saveFilter() {
        SaveFilterListener listener = (SaveFilterListener) getActivity();

        articleFilter = new ArticleFilter();

        articleFilter.setSort(mSort.getSelectedItem().toString());

        listener.onSaveFilter(articleFilter);

        dismiss();
    }

    // Set spinner to value
    public void setSpinnerToValue(Spinner spinner, String value) {
        int index = 0;
        SpinnerAdapter adapter = spinner.getAdapter();

        for (int i = 0; i < adapter.getCount(); i ++) {
            if (adapter.getItem(i).equals(articleFilter.getSort())) {
                index = i;
                break;
            }
        }

        spinner.setSelection(index);
    }
}

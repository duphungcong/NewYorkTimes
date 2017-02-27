package com.duphungcong.newyorktimes.fragment;

import android.app.Dialog;
import android.databinding.DataBindingUtil;
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
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.Spinner;

import com.duphungcong.newyorktimes.R;
import com.duphungcong.newyorktimes.databinding.FragmentFilterBinding;
import com.duphungcong.newyorktimes.ulti.DateUlti;
import com.duphungcong.newyorktimes.viewmodel.ArticleFilter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by udcun on 2/26/2017.
 */

public class FilterFragment extends DialogFragment {

    private FragmentFilterBinding binding;

    private ArticleFilter articleFilter, backupFilter;
    private Spinner mSort;
    private DatePicker mBeginDate;
    private List<CheckBox> mNewsDesk;

    // Defines the listener interface with a method passing back data result.
    public interface FinishFilterListener {
        void onSaveFilter(ArticleFilter savedFilter);
        void onCancelFilter(ArticleFilter backupFilter);
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

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_filter, container, false);
        View view = binding.getRoot();

        //Toolbar toolbar = (Toolbar) view.findViewById(R.id.search_setting_toolbar);
        Toolbar toolbar = binding.searchSettingToolbar;
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
        // Backup filter in case of canceling
        backupFilter = new ArticleFilter(articleFilter);

        binding.setArticleFilter(articleFilter);

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);

        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        return dialog;
    }

    // Restore filter setting of previous session when user click cancel icon on toolbar
    public void cancelFilter() {
        // Restore filter setting of previous session
        FinishFilterListener listener = (FinishFilterListener) getActivity();

        listener.onCancelFilter(backupFilter);

        dismiss();
    }

    // Pass new filter setting when user click save icon on toolbar
    public void saveFilter() {
        FinishFilterListener listener = (FinishFilterListener) getActivity();

        articleFilter.setBeginDate(DateUlti.getDatePickerValue(binding.dpBeginDate));
        getCheckedCheckBoxes();
        articleFilter.setNewsDeskList(mNewsDesk);

        listener.onSaveFilter(articleFilter);

        dismiss();
    }

    public void getCheckedCheckBoxes() {
        mNewsDesk = new ArrayList<>();

        if (binding.cbArts.isChecked()) {
            mNewsDesk.add(binding.cbArts);
        }

        if (binding.cbFashion.isChecked()) {
            mNewsDesk.add(binding.cbFashion);
        }

        if (binding.cbSports.isChecked()) {
            mNewsDesk.add(binding.cbSports);
        }
    }
}

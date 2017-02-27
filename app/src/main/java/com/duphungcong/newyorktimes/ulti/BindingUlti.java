package com.duphungcong.newyorktimes.ulti;

import android.databinding.BindingAdapter;
import android.databinding.InverseBindingAdapter;
import android.databinding.InverseBindingListener;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

/**
 * Created by udcun on 2/26/2017.
 */

public class BindingUlti {

    @BindingAdapter(value = {"bind:selectedValue", "bind:selectedValueAttrChanged"}, requireAll = false)
    public static void bindSpinner(Spinner spinner, String newSelectedValue, final InverseBindingListener newValueAttrChanged) {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                newValueAttrChanged.onChange();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        if (newSelectedValue != null) {
            int pos = ((ArrayAdapter<String>) spinner.getAdapter()).getPosition(newSelectedValue);
            spinner.setSelection(pos, true);
        }
    }
    @InverseBindingAdapter(attribute = "bind:selectedValue", event = "bind:selectedValueAttrChanged")
    public static String captureSelectedValue(Spinner spinner) {
        return (String) spinner.getSelectedItem();
    }

   /* @BindingAdapter(value = {"bind:selectedValue", "bind:selectedValueAttrChange"}, requireAll = false)
    public static void bindDatePickerData(DatePicker datePicker, Date newSelectedValue, final InverseBindingListener newValueAttrChanged) {
        datePicker.init(datePicker.getYear(),
                        datePicker.getMonth(),
                        datePicker.getDayOfMonth(),
                        new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                newValueAttrChanged.onChange();
            }
        });
    }
    @InverseBindingAdapter(attribute = "bind:selectedValue", event = "bind:selectedValueAttrChanged")
    public static Date captureSelectedValue(DatePicker datePicker) {
        Calendar c= Calendar.getInstance();
        int year = datePicker.getYear();
        int month = datePicker.getMonth();
        int dayOfMonth = datePicker.getDayOfMonth();
        c.set(year, month, dayOfMonth);

        return c.getTime();
    }*/
}

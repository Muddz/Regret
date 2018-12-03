package com.muddzdev.regret.demo;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

public class EditTextViewModel extends ViewModel {

    private MutableLiveData<String> text;
    private MutableLiveData<Integer> textColor;
    private MutableLiveData<Integer> BackgroundColor;

    public MutableLiveData<String> getText() {
        return text;
    }

    public void setText(MutableLiveData<String> text) {
        this.text = text;
    }

    public MutableLiveData<Integer> getTextColor() {
        return textColor;
    }

    public void setTextColor(MutableLiveData<Integer> textColor) {
        this.textColor = textColor;
    }

    public MutableLiveData<Integer> getBackgroundColor() {
        return BackgroundColor;
    }

    public void setBackgroundColor(MutableLiveData<Integer> backgroundColor) {
        BackgroundColor = backgroundColor;
    }
}

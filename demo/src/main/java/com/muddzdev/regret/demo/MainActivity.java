package com.muddzdev.regret.demo;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.danielnilsson9.colorpickerview.dialog.ColorPickerDialogFragment;
import com.muddzdev.regret.Regret;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, ColorPickerDialogFragment.ColorPickerDialogListener, TextWatcher, Regret.RegretListener {

    private static final String KEY_TEXT = "KEY_TEXT";
    private static final String KEY_BACKGROUND_COLOR = "KEY_BACKGROUND_COLOR";
    private static final String KEY_TEXT_COLOR = "KEY_TEXT_COLOR";
    private static final int COLOR_PICKER_TEXT_COLOR = 111;
    private static final int COLOR_PICKER_BACKGROUND = 222;
    private boolean isUndoing;

    Toolbar toolbar;
    ImageView btnUndo;
    ImageView btnRedo;
    TextView btnClear;
    TextView txtColorBtn;
    TextView backgroundColorBtn;
    EditText editText;
    Regret regret;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtColorBtn = findViewById(R.id.txt_color_picker);
        backgroundColorBtn = findViewById(R.id.bcg_color_picker);
        btnUndo = findViewById(R.id.btn_undo);
        btnRedo = findViewById(R.id.btn_redo);
        btnClear = findViewById(R.id.btn_clear);
        editText = findViewById(R.id.edittext);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        loadLastEditTextStyle();

        editText.addTextChangedListener(this);
        btnRedo.setOnClickListener(this);
        btnUndo.setOnClickListener(this);
        btnClear.setOnClickListener(this);
        txtColorBtn.setOnClickListener(this);
        backgroundColorBtn.setOnClickListener(this);

        //Instantiate Regret with context and a listener
        regret = new Regret(this);
        if (regret.isEmpty()) {
            //Add some values to it. The if-check is to avoid adding the same values again and again in this demo
            regret.add(KEY_TEXT, editText.getText().toString());
            regret.add(KEY_BACKGROUND_COLOR, Color.WHITE);
            regret.add(KEY_TEXT_COLOR, Color.BLACK);
        }
    }


    //The returned values when regret.undo() or regret.redo() is called
    @Override
    public void onDo(String key, Object value) {
        switch (key) {
            case KEY_TEXT:
                editText.setText((CharSequence) value);
                saveText();
                break;
            case KEY_TEXT_COLOR:
                editText.setTextColor((Integer) value);
                saveTextColor();
                break;
            case KEY_BACKGROUND_COLOR:
                editText.setBackgroundColor((Integer) value);
                saveBackgroundColor();
                break;
        }
    }


    //This Regret callback keeps track of if you're able to undo or redo, every time a change happens in Regret's history.
    @Override
    public void onCanDo(boolean canUndo, boolean canRedo) {
        btnUndo.setAlpha(canUndo ? 1 : 0.4f);
        btnRedo.setAlpha(canRedo ? 1 : 0.4f);
        btnUndo.setEnabled(canUndo);
        btnRedo.setEnabled(canRedo);
    }


    @Override
    public void onColorSelected(int dialogId, int color) {
        switch (dialogId) {
            case COLOR_PICKER_TEXT_COLOR:
                editText.setTextColor(color);
                regret.add(KEY_TEXT_COLOR, color);
                saveTextColor();
                break;
            case COLOR_PICKER_BACKGROUND:
                editText.setBackgroundColor(color);
                regret.add(KEY_BACKGROUND_COLOR, color);
                saveBackgroundColor();
                break;
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
        if (!isUndoing) {
            String text = s.toString().trim();
            if (!text.isEmpty() || !text.equals("")) {
                regret.add(KEY_TEXT, text);
                saveText();
            }
        }
        isUndoing = false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_clear:
                regret.clear();
                PreferenceManager.getDefaultSharedPreferences(this).edit().clear().apply();
                Toast.makeText(this, "History cleared", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_undo:
                isUndoing = true;
                regret.undo();
                Toast.makeText(this, "UNDO", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_redo:
                isUndoing = true;
                regret.redo();
                Toast.makeText(this, "REDO", Toast.LENGTH_SHORT).show();
                break;
            case R.id.txt_color_picker:
                ColorPickerDialogFragment.newInstance(COLOR_PICKER_TEXT_COLOR, editText.getCurrentTextColor()).show(getFragmentManager(), null);
                break;
            case R.id.bcg_color_picker:
                ColorPickerDialogFragment.newInstance(COLOR_PICKER_BACKGROUND, getBackgroundColor()).show(getFragmentManager(), null);
                break;
        }
    }

    private int getBackgroundColor() {
        ColorDrawable colorDrawable = (ColorDrawable) editText.getBackground();
        return colorDrawable.getColor();
    }


    private void saveBackgroundColor() {
        PreferenceManager
                .getDefaultSharedPreferences(this)
                .edit()
                .putInt(KEY_BACKGROUND_COLOR, getBackgroundColor())
                .apply();
    }

    private void saveTextColor() {
        PreferenceManager.getDefaultSharedPreferences(this)
                .edit()
                .putInt(KEY_TEXT_COLOR, editText.getCurrentTextColor())
                .apply();
    }

    private void saveText() {
        PreferenceManager.getDefaultSharedPreferences(this)
                .edit()
                .putString(KEY_TEXT, editText.getText().toString())
                .apply();
    }

    private void loadLastEditTextStyle() {
        SharedPreferences preferenceManager = PreferenceManager.getDefaultSharedPreferences(this);
        String text = preferenceManager.getString(KEY_TEXT, "Hello World");
        int textColor = preferenceManager.getInt(KEY_TEXT_COLOR, Color.BLACK);
        int backgroundColor = preferenceManager.getInt(KEY_BACKGROUND_COLOR, Color.WHITE);
        editText.setText(text);
        editText.setTextColor(textColor);
        editText.setBackgroundColor(backgroundColor);
    }

    //Not in use
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void onDialogDismissed(int dialogId) {
    }

}

package com.muddzdev.regret.demo;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
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
import com.muddzdev.regret.OnRegretListener;
import com.muddzdev.regret.Regret;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, ColorPickerDialogFragment.ColorPickerDialogListener, TextWatcher, OnRegretListener {

    private static final String KEY_TEXT = "KEY_TEXT";
    private static final String KEY_BACKGROUND_COLOR = "KEY_BACKGROUND_COLOR";
    private static final String KEY_TEXT_COLOR = "KEY_TEXT_COLOR";
    private static final int COLOR_PICKER_TEXT = 111;
    private static final int COLOR_PICKER_BACKGROUND = 222;
    private boolean isUndoing;

    Toolbar toolbar;
    ImageView btnUndo;
    ImageView btnRedo;
    TextView btnClear;
    TextView txtColorPicker;
    TextView backgroundColorPicker;
    EditText editText;
    Regret regret;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnUndo = findViewById(R.id.btn_undo);
        btnRedo = findViewById(R.id.btn_redo);
        btnClear = findViewById(R.id.btn_clear);
        editText = findViewById(R.id.edittext);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        txtColorPicker = findViewById(R.id.txt_color_picker);
        backgroundColorPicker = findViewById(R.id.bcg_color_picker);

        editText.addTextChangedListener(this);
        btnRedo.setOnClickListener(this);
        btnUndo.setOnClickListener(this);
        btnClear.setOnClickListener(this);
        txtColorPicker.setOnClickListener(this);
        backgroundColorPicker.setOnClickListener(this);

        regret = new Regret(this, this);
        if (regret.isEmpty()) {
            regret.add(KEY_TEXT, editText.getText().toString());
            regret.add(KEY_BACKGROUND_COLOR, Color.WHITE);
            regret.add(KEY_TEXT_COLOR, Color.BLACK);
        }
    }


    //The returned values from the regret.undo() or regret.redo() calls
    @Override
    public void onDo(String key, Object value) {
        switch (key) {
            case KEY_TEXT:
                editText.setText((CharSequence) value);
                break;
            case KEY_TEXT_COLOR:
                editText.setTextColor((Integer) value);
                break;
            case KEY_BACKGROUND_COLOR:
                editText.setBackgroundColor((Integer) value);
                break;
        }
    }


    //Enable or disable Undo Redo
    @Override
    public void onCanDo(boolean canUndo, boolean canRedo) {
        btnUndo.setAlpha(canUndo ? 1 : 0.4f);
        btnRedo.setAlpha(canRedo ? 1 : 0.4f);
        btnUndo.setEnabled(canUndo);
        btnRedo.setEnabled(canRedo);
    }


    //Adding EditText Colors to Regret
    @Override
    public void onColorSelected(int dialogId, int color) {
        switch (dialogId) {
            case COLOR_PICKER_TEXT:
                editText.setTextColor(color);
                regret.add(KEY_TEXT_COLOR, color);
                break;
            case COLOR_PICKER_BACKGROUND:
                editText.setBackgroundColor(color);
                regret.add(KEY_BACKGROUND_COLOR, color);
                break;
        }
    }


    //Adding EditText text to Regret
    @Override
    public void afterTextChanged(Editable s) {
        if (!isUndoing) {
            String text = s.toString().trim();
            if (!text.isEmpty()) {
                regret.add(KEY_TEXT, text);
            }
        }
        isUndoing = false;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_clear:
                regret.clear();
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
                ColorPickerDialogFragment.newInstance(COLOR_PICKER_TEXT, editText.getCurrentTextColor()).show(getFragmentManager(), null);
                break;
            case R.id.bcg_color_picker:
                ColorPickerDialogFragment.newInstance(COLOR_PICKER_BACKGROUND, getBackgroundColor()).show(getFragmentManager(), null);
                break;
        }
    }

    private int getBackgroundColor() {
        ColorDrawable colorDrawable = (ColorDrawable) editText.getBackground().mutate();
        return colorDrawable.getColor();
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

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
import com.muddzdev.regret.Regret;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, ColorPickerDialogFragment.ColorPickerDialogListener, TextWatcher, Regret.OnRegretListener {

    private static final String OBJECT_NAME_TEXT = "OBJECT_NAME_TEXT";
    private static final String OBJECT_NAME_BACKGROUND_COLOR = "OBJECT_NAME_BACKGROUND_COLOR";
    private static final String OBJECT_NAME_TEXT_COLOR = "OBJECT_NAME_TEXT_COLOR";
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

        toolbar = findViewById(R.id.toolbar);
        btnUndo = findViewById(R.id.btn_undo);
        btnRedo = findViewById(R.id.btn_redo);
        btnClear = findViewById(R.id.btn_clear);
        editText = findViewById(R.id.edittext);

        txtColorPicker = findViewById(R.id.txt_color_picker);
        backgroundColorPicker = findViewById(R.id.bcg_color_picker);

        editText.addTextChangedListener(this);
        btnRedo.setOnClickListener(this);
        btnUndo.setOnClickListener(this);
        btnClear.setOnClickListener(this);
        txtColorPicker.setOnClickListener(this);
        backgroundColorPicker.setOnClickListener(this);

        regret = new Regret(this, this);
        regret.add(OBJECT_NAME_TEXT, editText.getText().toString());
        regret.add(OBJECT_NAME_BACKGROUND_COLOR, Color.WHITE);
        regret.add(OBJECT_NAME_TEXT_COLOR, Color.BLACK);
        setSupportActionBar(toolbar);
    }


    @Override
    public void onDo(String objectName, Object object) {
        switch (objectName) {
            case OBJECT_NAME_TEXT:
                editText.setText((CharSequence) object);
                break;
            case OBJECT_NAME_TEXT_COLOR:
                editText.setTextColor((Integer) object);
                break;
            case OBJECT_NAME_BACKGROUND_COLOR:
                editText.setBackgroundColor((Integer) object);
                break;
        }
    }

    @Override
    public void status(boolean canUndo, boolean canRedo) {
        btnUndo.setAlpha(canUndo ? 1 : 0.4f);
        btnRedo.setAlpha(canRedo ? 1 : 0.4f);
        btnUndo.setEnabled(canUndo);
        btnRedo.setEnabled(canRedo);
    }

    @Override
    public void onColorSelected(int dialogId, int color) {
        switch (dialogId) {
            case COLOR_PICKER_TEXT:
                editText.setTextColor(color);
                regret.add(OBJECT_NAME_TEXT_COLOR, color);
                break;
            case COLOR_PICKER_BACKGROUND:
                editText.setBackgroundColor(color);
                regret.add(OBJECT_NAME_BACKGROUND_COLOR, color);
                break;
        }
        showRegretCountToast();
    }


    @Override
    public void afterTextChanged(Editable s) {
        if (!isUndoing) {
            String text = s.toString().trim();
            if (!text.isEmpty()) {
                regret.add(OBJECT_NAME_TEXT, text);
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


    private void showRegretCountToast() {
        Toast.makeText(this, "Count: " + regret.getCount(), Toast.LENGTH_SHORT).show();
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

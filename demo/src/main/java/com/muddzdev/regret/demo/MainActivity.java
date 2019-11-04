package com.muddzdev.regret.demo;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.github.danielnilsson9.colorpickerview.dialog.ColorPickerDialogFragment;
import com.muddzdev.regret.Regret;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, ColorPickerDialogFragment.ColorPickerDialogListener, TextWatcher, Regret.RegretListener {

    private static final String KEY_TEXT = "KEY_TEXT";
    private static final String KEY_BACKGROUND_COLOR = "KEY_BACKGROUND_COLOR";
    private static final String KEY_TEXT_COLOR = "KEY_TEXT_COLOR";
    private static final int COLOR_PICKER_TEXT_COLOR = 111;
    private static final int COLOR_PICKER_BACKGROUND = 222;
    private boolean isUndoing;

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

        editText.addTextChangedListener(this);
        btnRedo.setOnClickListener(this);
        btnUndo.setOnClickListener(this);
        btnClear.setOnClickListener(this);
        txtColorBtn.setOnClickListener(this);
        backgroundColorBtn.setOnClickListener(this);

        //Instantiate Regret with context and a listener
        regret = new Regret(this);

        //Add some start/default values
        regret.add(KEY_TEXT_COLOR, Color.BLACK);
        regret.add(KEY_TEXT, editText.getText().toString());
        regret.add(KEY_BACKGROUND_COLOR, Color.WHITE);
    }


    //The listener returns Key-Pair when regret.undo() or regret.redo() is called
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


    //This listener returns whether its still possible to undo/redo after every add, undo or redo and clear operation
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
                break;
            case COLOR_PICKER_BACKGROUND:
                editText.setBackgroundColor(color);
                regret.add(KEY_BACKGROUND_COLOR, color);
                break;
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
        if (!isUndoing) {
            String text = s.toString().trim();
            if (!text.isEmpty() || !text.equals("")) {
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
                ColorDrawable colorDrawable = (ColorDrawable) editText.getBackground();
                ColorPickerDialogFragment.newInstance(COLOR_PICKER_BACKGROUND, colorDrawable.getColor()).show(getFragmentManager(), null);
                break;
        }
    }

    //Not in use
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    //Not in use
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    //Not in use
    @Override
    public void onDialogDismissed(int dialogId) {
    }

}

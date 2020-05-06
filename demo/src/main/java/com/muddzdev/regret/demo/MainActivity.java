package com.muddzdev.regret.demo;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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
    private String previousText;
    private Regret regret;

    ImageView btnUndo;
    ImageView btnRedo;
    TextView btnClear;
    TextView txtColorBtn;
    TextView backgroundColorBtn;
    EditText editText;

    View currentView;

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

        findViewById(R.id.btn_size).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int size = regret.getSize();
                Toast.makeText(MainActivity.this, size + "", Toast.LENGTH_LONG).show();
                Log.d("XXX", regret.toString());
            }
        });

        editText.addTextChangedListener(this);
        btnRedo.setOnClickListener(this);
        btnUndo.setOnClickListener(this);
        btnClear.setOnClickListener(this);
        txtColorBtn.setOnClickListener(this);
        backgroundColorBtn.setOnClickListener(this);



        //Instantiate Regret with context and a listener
        regret = new Regret(this);

        //Before we edit the text, lets store the current text in an object for later use
        previousText = editText.getText().toString();
    }


    //The listener returns Key-Pair when regret.undo() or regret.redo() is called
    @Override
    public void onDo(String key, Object value,View view) {
        switch (key) {
            case KEY_TEXT:
                ((EditText)view).setText((CharSequence) value);
                break;
            case KEY_TEXT_COLOR:
                ((EditText)view).setTextColor((Integer) value);
                break;
            case KEY_BACKGROUND_COLOR:
                view.setBackgroundColor((Integer) value);
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
    public void onColorSelected(int dialogId, int newColor) {
        switch (dialogId) {
            case COLOR_PICKER_TEXT_COLOR:
                regret.add(KEY_TEXT_COLOR, editText.getCurrentTextColor(), newColor,editText);
                editText.setTextColor(newColor);
                break;
            case COLOR_PICKER_BACKGROUND:
                ColorDrawable colorDrawable = (ColorDrawable) editText.getBackground();
                regret.add(KEY_BACKGROUND_COLOR, colorDrawable.getColor(), newColor,editText);
                editText.setBackgroundColor(newColor);
                break;
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
        if (!isUndoing) {
            regret.add(KEY_TEXT, previousText, s.toString(),editText);
            previousText = s.toString();
        }
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
                isUndoing = false;
                break;
            case R.id.btn_redo:
                isUndoing = true;
                regret.redo();
                Toast.makeText(this, "REDO", Toast.LENGTH_SHORT).show();
                isUndoing = false;
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

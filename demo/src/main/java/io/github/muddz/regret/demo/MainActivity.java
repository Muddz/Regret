package io.github.muddz.regret.demo;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import io.github.muddz.regret.demo.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity implements ColorPickerDialogFragment.ColorPickerDialogListener, TextWatcher, Regret.RegretListener {

    private static final String KEY_TEXT = "KEY_TEXT";
    private static final String KEY_BACKGROUND_COLOR = "KEY_BACKGROUND_COLOR";
    private static final String KEY_TEXT_COLOR = "KEY_TEXT_COLOR";
    private static final int COLOR_PICKER_TEXT_ID = 111;
    private static final int COLOR_PICKER_BACKGROUND_ID = 222;
    private boolean isUndoing;
    private String previousText;
    private Regret regret;
    private ActivityMainBinding layout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layout = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(layout.getRoot());

        layout.btnUndo.setOnClickListener(v -> undo());
        layout.btnRedo.setOnClickListener(v -> redo());
        layout.btnClear.setOnClickListener(v -> clearUndoRedo());
        layout.btnSize.setOnClickListener(v -> showUndoRedoSize());
        layout.txtColorPicker.setOnClickListener(v -> colorPickerText());
        layout.bcgColorPicker.setOnClickListener(v -> colorPickerBackground());
        layout.edittext.addTextChangedListener(this);

        //Instantiate Regret with context and a listener
        regret = new Regret(this);
        //Before we edit the text, lets store the current text in an object for later use
        previousText = layout.edittext.getText().toString();
    }


    //The listener returns Key-Pair when regret.undo() or regret.redo() is called
    @Override
    public void onDo(String key, Object value) {
        switch (key) {
            case KEY_TEXT:
                layout.edittext.setText((CharSequence) value);
                break;
            case KEY_TEXT_COLOR:
                layout.edittext.setTextColor((Integer) value);
                break;
            case KEY_BACKGROUND_COLOR:
                layout.edittext.setBackgroundColor((Integer) value);
                break;
        }
    }


    //This listener returns whether its still possible to undo/redo after every add, undo or redo and clear operation
    @Override
    public void onCanDo(boolean canUndo, boolean canRedo) {
        layout.btnUndo.setAlpha(canUndo ? 1 : 0.4f);
        layout.btnRedo.setAlpha(canRedo ? 1 : 0.4f);
        layout.btnUndo.setEnabled(canUndo);
        layout.btnRedo.setEnabled(canRedo);
    }


    @Override
    public void onColorSelected(int dialogId, int newColor) {
        switch (dialogId) {
            case COLOR_PICKER_TEXT_ID:
                regret.add(KEY_TEXT_COLOR, layout.edittext.getCurrentTextColor(), newColor);
                layout.edittext.setTextColor(newColor);
                break;
            case COLOR_PICKER_BACKGROUND_ID:
                ColorDrawable colorDrawable = (ColorDrawable) layout.edittext.getBackground();
                regret.add(KEY_BACKGROUND_COLOR, colorDrawable.getColor(), newColor);
                layout.edittext.setBackgroundColor(newColor);
                break;
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
        if (!isUndoing) {
            regret.add(KEY_TEXT, previousText, s.toString());
            previousText = s.toString();
        }
    }

    private void undo() {
        isUndoing = true;
        regret.undo();
        isUndoing = false;
    }

    private void redo() {
        isUndoing = true;
        regret.redo();
        isUndoing = false;
    }

    private void clearUndoRedo() {
        regret.clear();
        Toast.makeText(this, "History cleared", Toast.LENGTH_SHORT).show();
    }

    private void showUndoRedoSize() {
        Toast.makeText(MainActivity.this, regret.getSize() + "", Toast.LENGTH_LONG).show();
    }


    private void colorPickerText() {
        ColorPickerDialogFragment.newInstance(COLOR_PICKER_TEXT_ID, layout.edittext.getCurrentTextColor()).show(getFragmentManager(), null);
    }

    private void colorPickerBackground() {
        ColorDrawable colorDrawable = (ColorDrawable) layout.edittext.getBackground();
        ColorPickerDialogFragment.newInstance(COLOR_PICKER_BACKGROUND_ID, colorDrawable.getColor()).show(getFragmentManager(), null);
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

package com.example.notesapp;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Full screen mode

        EdgeToEdge.enable(this);

        // ViewGroups

        ScrollView scrollViewRoot = createRootScrollView();
        LinearLayout linearLayoutMain = createMainLinearLayout();

        ViewCompat.setOnApplyWindowInsetsListener(linearLayoutMain, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left + 32, systemBars.top, systemBars.right + 32, systemBars.bottom + 32);
            return insets;
        });

        // Views

        TextView tvTitle = createApplicationTitle();
        EditText etNote = createNoteEditText();
        Button btnAddNote = createAddNoteButton(linearLayoutMain, etNote);

        // Setting interface

        scrollViewRoot.addView(linearLayoutMain);

        linearLayoutMain.addView(tvTitle);
        linearLayoutMain.addView(etNote);
        linearLayoutMain.addView(btnAddNote);

        setContentView(scrollViewRoot);
    }

    /// Scroll to main container
    private ScrollView createRootScrollView() {
        ScrollView scrollView = new ScrollView(this);
        scrollView.setBackgroundColor(Color.rgb(255, 237, 250));
        scrollView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        ));
        return scrollView;
    }

    /// Main container
    private LinearLayout createMainLinearLayout() {
        LinearLayout linearLayoutRoot = new LinearLayout(this);
        linearLayoutRoot.setOrientation(LinearLayout.VERTICAL);
        linearLayoutRoot.setBackgroundColor(Color.rgb(255, 237, 250));
        linearLayoutRoot.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        ));

        return linearLayoutRoot;
    }

    /// Title for an application
    @SuppressLint("SetTextI18n")
    private TextView createApplicationTitle() {
        TextView tvTitle = new TextView(this);
        tvTitle.setText("Notes App");
        tvTitle.setTypeface(null, Typeface.BOLD);
        tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 28);
        tvTitle.setTextColor(Color.rgb(190, 89, 133));
        tvTitle.setTextAlignment(TextView.TEXT_ALIGNMENT_CENTER);
        LinearLayout.LayoutParams tvTitleLayoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        tvTitleLayoutParams.setMargins(0, 72, 0, 48);
        tvTitle.setLayoutParams(tvTitleLayoutParams);
        return tvTitle;
    }

    /// Field to enter the text for a note
    private EditText createNoteEditText() {
        EditText etNote = new EditText(this);
        etNote.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        etNote.setHint("Enter text for note");
        etNote.setTextColor(Color.rgb(190, 89, 133));
        etNote.setHintTextColor(Color.rgb(255, 184, 224));
        return etNote;
    }

    /// Button to add a note
    @SuppressLint("SetTextI18n")
    private Button createAddNoteButton(LinearLayout linearLayoutRoot, EditText etNote) {
        Button btnAddTextView = new Button(this);
        btnAddTextView.setText("Add a new note");
        btnAddTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        btnAddTextView.setBackgroundColor(Color.rgb(236, 127, 169));
        btnAddTextView.setTextColor(Color.rgb(255, 237, 250));
        btnAddTextView.setOnClickListener(v -> createNote(linearLayoutRoot, etNote));
        return btnAddTextView;
    }

    /// Create a note
    @SuppressLint("SetTextI18n")
    private void createNote(LinearLayout linearLayoutRoot, EditText etNote) {

        // Text from EditText

        String note = etNote.getText().toString();
        etNote.setText("");

        // LinearLayout for a note

        LinearLayout linearLayoutNote = new LinearLayout(this);
        linearLayoutNote.setOrientation(LinearLayout.HORIZONTAL);
        linearLayoutNote.setBackgroundColor(Color.rgb(255, 184, 224));
        LinearLayout.LayoutParams linearLayoutNoteLayoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        linearLayoutNoteLayoutParams.setMargins(0, 16, 0, 0);
        linearLayoutNote.setLayoutParams(linearLayoutNoteLayoutParams);

        // Button to remove a note

        Button btnRemoveNote = new Button(this);
        btnRemoveNote.setText("Done");
        btnRemoveNote.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        btnRemoveNote.setBackgroundColor(Color.rgb(236, 127, 169));
        btnRemoveNote.setTextColor(Color.rgb(255, 237, 250));
        btnRemoveNote.setOnClickListener(v -> removeNote(linearLayoutRoot, linearLayoutNote));
        LinearLayout.LayoutParams btnRemoveNoteLayoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        btnRemoveNoteLayoutParams.setMargins(16, 16, 16, 16);
        btnRemoveNote.setLayoutParams(btnRemoveNoteLayoutParams);

        // TextView to display the text of a note

        TextView tvNote = new TextView(this);
        tvNote.setText(note);
        tvNote.setTypeface(null, Typeface.BOLD);
        tvNote.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        tvNote.setTextColor(Color.rgb(190, 89, 133));
        tvNote.setSingleLine(true);
        tvNote.setEllipsize(TextUtils.TruncateAt.END);
        LinearLayout.LayoutParams tvNoteLayoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        tvNoteLayoutParams.setMargins(16, 16, 16, 16);
        tvNote.setLayoutParams(tvNoteLayoutParams);

        // Display a note

        linearLayoutNote.addView(btnRemoveNote);
        linearLayoutNote.addView(tvNote);

        linearLayoutRoot.addView(linearLayoutNote);
    }

    /// Remove a note
    private void removeNote(LinearLayout linearLayoutRoot, LinearLayout linearLayoutNote) {
        linearLayoutRoot.removeView(linearLayoutNote);
    }
}
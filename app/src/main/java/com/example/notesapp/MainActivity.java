package com.example.notesapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    long timePressed;
    Toast backToast;

    /// Interface initialization
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Full screen mode
        EdgeToEdge.enable(this);

        // ViewGroups
        ScrollView scrollViewRoot = createRootScrollView();
        LinearLayout linearLayoutMain = createMainLinearLayout();

        // Handling system indents
        ViewCompat.setOnApplyWindowInsetsListener(linearLayoutMain, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left + 32, systemBars.top, systemBars.right + 32, systemBars.bottom + 32);
            return insets;
        });

        // Views
        TextView tvTitle = createApplicationTitle();
        EditText etNote = createNoteEditText();
        Button btnAddNote = createAddNoteButton(linearLayoutMain, etNote);

        // Add elements to the root container
        scrollViewRoot.addView(linearLayoutMain);
        linearLayoutMain.addView(tvTitle);
        linearLayoutMain.addView(etNote);
        linearLayoutMain.addView(btnAddNote);

        // Setting the root container
        setContentView(scrollViewRoot);
    }

    /// Double press "Back" to exit
    @Override
    public void onBackPressed() {
        if (timePressed + 2000 > System.currentTimeMillis()) {
            backToast.cancel();
            super.onBackPressed();
        }
        else {
            backToast = Toast.makeText(getBaseContext(), "Press again to exit", Toast.LENGTH_SHORT);
            backToast.show();
        }
        timePressed = System.currentTimeMillis();
    }

    /// Creating the root ScrollView
    private ScrollView createRootScrollView() {
        ScrollView scrollView = new ScrollView(this);
        scrollView.setBackgroundColor(Color.rgb(255, 237, 250));
        scrollView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        ));
        return scrollView;
    }

    /// Creating the main LinearLayout
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

    /// Creating the title for an application
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

    /// Creating the field to enter the text for a note
    private EditText createNoteEditText() {
        EditText etNote = new EditText(this);
        etNote.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        etNote.setHint("Enter text for note");
        etNote.setTextColor(Color.rgb(190, 89, 133));
        etNote.setHintTextColor(Color.rgb(255, 184, 224));
        etNote.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        return etNote;
    }

    /// Creating the button to add a note
    @SuppressLint("SetTextI18n")
    private Button createAddNoteButton(LinearLayout linearLayoutRoot, EditText etNote) {
        Button btnAddTextView = new Button(this);
        btnAddTextView.setText("Add a new note");
        btnAddTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        btnAddTextView.setBackgroundColor(Color.rgb(236, 127, 169));
        btnAddTextView.setTextColor(Color.rgb(255, 237, 250));
        btnAddTextView.setOnClickListener(v -> createNote(linearLayoutRoot, etNote));
        btnAddTextView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        return btnAddTextView;
    }

    /// Creating a note
    @SuppressLint("SetTextI18n")
    private void createNote(LinearLayout linearLayoutRoot, EditText etNote) {
        // Note text check
        if (etNote.getText().toString().isEmpty()) {
            showToast(this, "First enter the note text");
            return;
        }

        // LinearLayout for a note
        LinearLayout linearLayoutNote = new LinearLayout(this);
        linearLayoutNote.setOrientation(LinearLayout.VERTICAL);
        linearLayoutNote.setBackgroundColor(Color.rgb(255, 184, 224));
        linearLayoutNote.setPadding(16, 16, 16, 16);
        LinearLayout.LayoutParams linearLayoutNoteLayoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        linearLayoutNoteLayoutParams.setMargins(0, 16, 0, 0);
        linearLayoutNote.setLayoutParams(linearLayoutNoteLayoutParams);

        // LinearLayout for buttons
        LinearLayout linearLayoutButtons = new LinearLayout(this);
        linearLayoutButtons.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams linearLayoutButtonsLayoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        linearLayoutButtonsLayoutParams.setMargins(0, 16, 0, 0);
        linearLayoutButtons.setLayoutParams(linearLayoutButtonsLayoutParams);

        // TextView to display the text of a note
        String note = etNote.getText().toString();
        etNote.setText("");

        TextView tvNote = new TextView(this);
        tvNote.setText(note);
        tvNote.setTypeface(null, Typeface.BOLD);
        tvNote.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        tvNote.setTextColor(Color.rgb(190, 89, 133));
        LinearLayout.LayoutParams tvNoteLayoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        tvNote.setLayoutParams(tvNoteLayoutParams);

        // Button to complete a note
        Button btnCompleteNote = new Button(this);
        btnCompleteNote.setText("Done");
        btnCompleteNote.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        btnCompleteNote.setBackgroundColor(Color.rgb(236, 127, 169));
        btnCompleteNote.setTextColor(Color.rgb(255, 237, 250));
        LinearLayout.LayoutParams btnCompleteNoteLayoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        btnCompleteNoteLayoutParams.setMargins(0, 0, 16, 0);
        btnCompleteNote.setLayoutParams(btnCompleteNoteLayoutParams);

        // Button to remove a note
        Button btnRemoveNote = new Button(this);
        btnRemoveNote.setText("Remove");
        btnRemoveNote.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        btnRemoveNote.setBackgroundColor(Color.rgb(236, 127, 169));
        btnRemoveNote.setTextColor(Color.rgb(255, 237, 250));
        LinearLayout.LayoutParams btnRemoveNoteLayoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        btnRemoveNote.setLayoutParams(btnRemoveNoteLayoutParams);

        // Button event listeners
        btnRemoveNote.setOnClickListener(v -> removeNote(linearLayoutRoot, linearLayoutNote));
        btnCompleteNote.setOnClickListener(v -> completeNote(linearLayoutNote, tvNote, btnCompleteNote, btnRemoveNote));

        // Add elements to the note container
        linearLayoutNote.addView(tvNote);
        linearLayoutButtons.addView(btnCompleteNote);
        linearLayoutButtons.addView(btnRemoveNote);
        linearLayoutNote.addView(linearLayoutButtons);

        // Add elements to the root container
        linearLayoutRoot.addView(linearLayoutNote);
    }

    /// Completing a note
    private void completeNote(LinearLayout linearLayoutNote, TextView tvNote, Button btnCompleteNote, Button btnRemoveNote) {
        linearLayoutNote.setBackgroundColor(Color.argb(70, 255, 184, 224));
        tvNote.setTextColor(Color.argb(70, 190, 89, 133));
        btnCompleteNote.setBackgroundColor(Color.argb(70, 236, 127, 169));
        btnRemoveNote.setBackgroundColor(Color.argb(70, 236, 127, 169));
    }

    /// Removing a note
    private void removeNote(LinearLayout linearLayoutRoot, LinearLayout linearLayoutNote) {
        linearLayoutRoot.removeView(linearLayoutNote);
    }

    /// Show a message
    public void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
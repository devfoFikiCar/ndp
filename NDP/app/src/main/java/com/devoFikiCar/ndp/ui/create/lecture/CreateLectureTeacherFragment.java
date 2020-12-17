package com.devoFikiCar.ndp.ui.create.lecture;

import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.brackeys.ui.editorkit.theme.EditorTheme;
import com.brackeys.ui.editorkit.widget.TextProcessor;
import com.brackeys.ui.language.java.JavaLanguage;
import com.brackeys.ui.language.markdown.MarkdownLanguage;
import com.devoFikiCar.ndp.R;

import br.tiagohm.markdownview.MarkdownView;
import br.tiagohm.markdownview.css.styles.Github;

public class CreateLectureTeacherFragment extends Fragment {

    private CreateLectureTeacherViewModel mViewModel;
    private TextProcessor etMarkdown;
    private Button btHelp;
    private Button btPreview;

    public static CreateLectureTeacherFragment newInstance() {
        return new CreateLectureTeacherFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.create_lecture_teacher_fragment, container, false);

        mViewModel = new ViewModelProvider(this).get(CreateLectureTeacherViewModel.class);

        etMarkdown = (TextProcessor) root.findViewById(R.id.etMarkdown);
        etMarkdown.setLanguage(new MarkdownLanguage());
        etMarkdown.setColorScheme(EditorTheme.INSTANCE.getVISUAL_STUDIO_2013());

        btHelp = (Button) root.findViewById(R.id.btHelp);
        btHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("https://github.com/adam-p/markdown-here/wiki/Markdown-Cheatsheet"));
                startActivity(intent);
            }
        });

        btPreview = (Button) root.findViewById(R.id.btPreview);
        btPreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                alert.setTitle("Preview");

                final MarkdownView markdownView = new MarkdownView(getContext());
                markdownView.addStyleSheet(new Github());
                markdownView.loadMarkdown(etMarkdown.getText().toString());
                alert.setView(markdownView);

                alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Nothing
                    }
                });

                alert.show();
            }
        });

        return root;
    }
}
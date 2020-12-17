package com.devoFikiCar.ndp.ui.create.lecture;

import androidx.lifecycle.ViewModelProvider;

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

import us.feras.mdv.MarkdownView;

public class CreateLectureTeacherFragment extends Fragment {

    private CreateLectureTeacherViewModel mViewModel;
    private TextProcessor etMarkdown;
    private Button btHelp;

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

        return root;
    }
}
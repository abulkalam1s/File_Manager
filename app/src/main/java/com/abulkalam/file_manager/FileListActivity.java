package com.abulkalam.file_manager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.io.File;

public class FileListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    TextView noFileTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_list);

        recyclerView = findViewById(R.id.recycler_view);
        noFileTv = findViewById(R.id.noFiles_textView);

        String path = getIntent().getStringExtra("path"); // getting the path sent from previous activity
        //storing the path in this root
        File root = new File(path);
        File[] fileAndFolders = root.listFiles(); //root have all the storage(we will give all the files inside that root)

        //If no files and folders found
        if(fileAndFolders == null  ||  fileAndFolders.length == 0){
            noFileTv.setVisibility(View.VISIBLE);
            return;
        }
        //otherwise
        noFileTv.setVisibility(View.INVISIBLE);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new MyAdapter(getApplicationContext(), fileAndFolders));


    }
}
package com.abulkalam.file_manager;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.io.File;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    Context context;
    File[] filesAndFolders;
    public MyAdapter(Context context, File[] filesAndFolders) {
        this.context = context;
        this.filesAndFolders = filesAndFolders;
    }


    @Override
    //We will pass our recycler_item layout over here so that recyclerView knows that we have this recycler_item layout
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(context).inflate(R.layout.recycler_item, parent, false);
       return  new ViewHolder(view);
    }

    @Override
    //Everything(remaining process) will be done by function->onBindViewHolder
    public void onBindViewHolder( ViewHolder holder, int position) {

        File selectedfile = filesAndFolders[position]; //if position is 1 it will take the 1st file, if position=2 it will take the second file
        holder.textView.setText(selectedfile.getName()); // naming files according to the position

        if (selectedfile.isDirectory()){     //If it is a folder- setting this icon
            holder.imageView.setImageResource(R.drawable.ic_baseline_folder_24);

        }else {          // if it is a file- setting this icon
            holder.imageView.setImageResource(R.drawable.ic_baseline_insert_drive_file_24);
        }
        // Opening on clicking
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if it is a folder it will open again and again until a file comes
                if (selectedfile.isDirectory()){
                    Intent intent = new Intent(context, FileListActivity.class);
                    String path = selectedfile.getAbsolutePath();
                    intent.putExtra("path", path);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }else {
                    //opening the file
                    try {
                        Intent intent = new Intent();
                        intent.setAction(android.content.Intent.ACTION_VIEW);
                        //to send the file - we have to pass the data and type(eg mp3, video, text etc.)
                        String type = "image/*";  // for now opening image file only
                        intent.setDataAndType(Uri.parse(selectedfile.getAbsolutePath()), type);

                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }catch (Exception e){
                        Toast.makeText(context.getApplicationContext(), "Cannot open the file", Toast.LENGTH_SHORT).show();
                    }

                }

            }
        });


        //setting long onClicklistener for selection
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                PopupMenu popupMenu = new PopupMenu(context, v);
                popupMenu.getMenu().add("Delete");
                popupMenu.getMenu().add("Move");
                popupMenu.getMenu().add("Rename");
                popupMenu.show();

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getTitle().equals("Delete")){
                            //delete
                           boolean deleted = selectedfile.delete();
                            if (deleted){
                                Toast.makeText(context.getApplicationContext(), "DELETED", Toast.LENGTH_SHORT).show();
                                v.setVisibility(View.GONE);
                            }
                        }
                        if (item.getTitle().equals("Move")){
                            //Move
                            Toast.makeText(context.getApplicationContext(), "MOVED", Toast.LENGTH_SHORT).show();

                        }  if (item.getTitle().equals("Rename")){
                            //Renaming
                            Toast.makeText(context.getApplicationContext(), "RENAME", Toast.LENGTH_SHORT).show();

                        }
                        return true;
                    }
                });  //menuItemclickListener ends

                popupMenu.show();
                return true;
            }
        }); // long clickListener ends
    }

    @Override
    public int getItemCount() {
        return filesAndFolders.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        // variables for referring of items declared inside recycler_item layout
        TextView textView;
        ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            //declared variables referring to the items of the recycler_item layout
            textView = itemView.findViewById(R.id.fileName);
            imageView = itemView.findViewById(R.id.icon_view);
        }
    }
}

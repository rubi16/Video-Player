package com.example.videoplayer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView mrecyclerview;
    Recycler_Adapter obj_adapter;
    public static int REQUEST_PERMISSION=1;
    File directory;
    Boolean bool_permission;
    public static ArrayList<File> fileArrayList= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mrecyclerview=(RecyclerView)findViewById(R.id.ListVideoRecycler);
        //phone memory & SD card
        directory = new File("/mnt/");

        GridLayoutManager manager = new GridLayoutManager(MainActivity.this,2);
        mrecyclerview.setLayoutManager(manager);

        permissionVideo();
    }

    private void permissionVideo() {
        if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,Manifest.permission.READ_EXTERNAL_STORAGE)){}

            else{
                ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},REQUEST_PERMISSION);
            }
            }
        else{
            bool_permission=true;
            getFile(directory);
            obj_adapter= new Recycler_Adapter(getApplicationContext(),fileArrayList);
            mrecyclerview.setAdapter(obj_adapter);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == REQUEST_PERMISSION){
            if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                bool_permission=true;
                getFile(directory);
                obj_adapter= new Recycler_Adapter(getApplicationContext(),fileArrayList);
                mrecyclerview.setAdapter(obj_adapter);
            }
            else{
                Toast.makeText(this,"Allow Permission",Toast.LENGTH_SHORT).show();
            }
        }
    }

    private ArrayList<File> getFile(File directory) {

        File listFile[] = directory.listFiles();
        if(listFile!= null && listFile.length>0){
            for(int i=0;i<listFile.length;i++){
                if(listFile[i].isDirectory()){
                    getFile(listFile[i]);
                }
                else{
                    bool_permission=false;
                    if(listFile[i].getName().endsWith(".mp4")){
                        for(int j=0;j<fileArrayList.size();j++){
                            if(fileArrayList.get(j).getName().equals(listFile[i].getName())){
                                bool_permission=true;
                            }
                        }
                    }
                }
            }
        }
        return  fileArrayList;
    }
}
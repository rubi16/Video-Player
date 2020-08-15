package com.example.videoplayer;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;

public class Recycler_Adapter extends RecyclerView.Adapter<VideoHolder> {
    private Context context;
    ArrayList<File> videoArraylist;

    public Recycler_Adapter(Context context, ArrayList<File> videoArraylist) {
        this.context = context;
        this.videoArraylist= videoArraylist;
    }

    @Override
    public VideoHolder onCreateViewHolder( ViewGroup viewGroup, int i) {

        View mView= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.video_item,viewGroup,false);
        return new VideoHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoHolder videoholder, int i) {

        videoholder.txtfilename.setText(MainActivity.fileArrayList.get(i).getName());
        Bitmap bitmap=ThumbnailUtils.createVideoThumbnail(videoArraylist.get(i).getPath(),MediaStore.Images.Thumbnails.MINI_KIND);
        videoholder.imagethumnail.setImageBitmap(bitmap);

    }

    @Override
    public int getItemCount() {
        if(videoArraylist.size()>0){
            return videoArraylist.size();
        }
        else
            return 1;
    }
}
class  VideoHolder extends RecyclerView.ViewHolder{

    TextView txtfilename;
    ImageView imagethumnail;
    CardView mCardView;

    VideoHolder(View view)
    {
        super(view);
        txtfilename= view.findViewById(R.id.txt_videofilename);
        imagethumnail =view.findViewById(R.id.iv_thumnail);
        mCardView= view.findViewById(R.id.mycardView);
    }

}
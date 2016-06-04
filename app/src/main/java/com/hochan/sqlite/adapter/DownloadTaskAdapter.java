package com.hochan.sqlite.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextClock;
import android.widget.TextView;

import com.hochan.multi_file_selector.adapter.ImageAdapter;
import com.hochan.sqlite.R;
import com.hochan.sqlite.data.DownloadInfo;
import com.hochan.sqlite.tools.Tool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2016/6/4.
 */
public class DownloadTaskAdapter extends RecyclerView.Adapter{

    private Context mContext;
    private List<String> mDownloadUrls = new ArrayList<>();
    private HashMap<String, DownloadInfo> mDownloadTasks = new HashMap<>();

    public DownloadTaskAdapter(Context context){
        this.mContext = context;
    }

    public void setDownloadTasks(List<String> downloadUrls, HashMap<String, DownloadInfo> downloadTaskList){
        this.mDownloadUrls = downloadUrls;
        this.mDownloadTasks = downloadTaskList;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_download_task, parent, false);
        return new DownloadTaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        DownloadTaskViewHolder viewHolder = (DownloadTaskViewHolder) holder;
        DownloadInfo downloadInfo = mDownloadTasks.get(mDownloadUrls.get(position));
        viewHolder.tvName.setText(downloadInfo.getmName());
        viewHolder.tvUrl.setText(downloadInfo.getmUrl());
        viewHolder.tvSize.setText(Tool.getSizeFormat(downloadInfo.getmTotalSize()));
        viewHolder.mProgressBar.setMax((int) downloadInfo.getmTotalSize());
        viewHolder.mProgressBar.setProgress((int) downloadInfo.getmDownloadedSize());
        if(downloadInfo.getmDownloadedSize() == downloadInfo.getmTotalSize()){
            viewHolder.mProgressBar.setVisibility(View.GONE);
            viewHolder.tvFinish.setVisibility(View.VISIBLE);
            viewHolder.tvUrl.setText(downloadInfo.getmStoragePath());
        }else{
            viewHolder.tvFinish.setVisibility(View.GONE);
            viewHolder.mProgressBar.setVisibility(View.VISIBLE);
            viewHolder.tvUrl.setText(downloadInfo.getmUrl());
        }
    }

    @Override
    public int getItemCount() {
        return mDownloadUrls.size();
    }

    class DownloadTaskViewHolder extends RecyclerView.ViewHolder {

        public ImageView ivIcon;
        public TextView tvName, tvUrl, tvSize, tvFinish;
        public ProgressBar mProgressBar;

        public DownloadTaskViewHolder(View itemView) {
            super(itemView);
            ivIcon = (ImageView) itemView.findViewById(R.id.iv_icon);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            tvSize = (TextView) itemView.findViewById(R.id.tv_size);
            tvUrl = (TextView) itemView.findViewById(R.id.tv_url);
            tvFinish = (TextView) itemView.findViewById(R.id.tv_finish);
            mProgressBar = (ProgressBar) itemView.findViewById(R.id.progress);
        }
    }
}

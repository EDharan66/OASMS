package com.demoapp.demo.HelperClass.RvAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JsResult;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.demoapp.demo.R;

import java.util.ArrayList;

public class ReportRvAdapter extends RecyclerView.Adapter<ReportRvAdapter.ReportRvViewHolder> {


    private ArrayList<ReportRvFullNameInfo> fullName_info;
    private ArrayList<ReportRvMailInfo> mail_info;
    private ArrayList<ReportRvNumaberInfo> serviceCenterNumberInfo;
    private ArrayList<ReportRvMessageInfo> message;
    private ArrayList<ReportRvDateInfo> dateinfo;

    public ReportRvAdapter(ArrayList<ReportRvFullNameInfo> fullName_info, ArrayList<ReportRvMailInfo> mail_info, ArrayList<ReportRvNumaberInfo> serviceCenterNumberInfo, ArrayList<ReportRvMessageInfo> message, ArrayList<ReportRvDateInfo> dateinfo) {
        this.fullName_info = fullName_info;
        this.mail_info = mail_info;
        this.serviceCenterNumberInfo = serviceCenterNumberInfo;
        this.message = message;
        this.dateinfo = dateinfo;
    }

    @NonNull
    @Override
    public ReportRvViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_report_layout,parent,false);
        ReportRvViewHolder reportRvViewHolder = new ReportRvViewHolder(view);
        return reportRvViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ReportRvViewHolder holder, int position) {
        ReportRvFullNameInfo currentItem = fullName_info.get(position);
        ReportRvMailInfo currentItem1 = mail_info.get(position);
        ReportRvNumaberInfo currentItem2 = serviceCenterNumberInfo.get(position);
        ReportRvMessageInfo currentItem3 = message.get(position);
        ReportRvDateInfo currentItem4 = dateinfo.get(position);
        holder.textview.setText(currentItem.getFullName_Info());
        holder.textview1.setText(currentItem1.getMail_Info());
        holder.textview2.setText(currentItem2.getNumber());
        holder.textview3.setText(currentItem3.getMessage());
        holder.textview4.setText(currentItem4.getDateInfo());

    }

    @Override
    public int getItemCount() {

        return fullName_info.size();
    }

    public static class ReportRvViewHolder extends RecyclerView.ViewHolder {

        TextView textview;
        TextView textview1;
        TextView textview2;
        TextView textview3;
        TextView textview4;


        public ReportRvViewHolder(@NonNull View itemView) {
            super(itemView);
            textview = itemView.findViewById(R.id.report_name_text_field);
            textview1 = itemView.findViewById(R.id.report_number_text_field);
            textview2 = itemView.findViewById(R.id.report_mail_text_field);
            textview3 = itemView.findViewById(R.id.report_message_text_field);
            textview4 = itemView.findViewById(R.id.report_date_text_field);

        }
    }
}

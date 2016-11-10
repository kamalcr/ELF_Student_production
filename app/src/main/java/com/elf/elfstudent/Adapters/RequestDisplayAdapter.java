package com.elf.elfstudent.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.elf.elfstudent.Fragments.RequestFragment;
import com.elf.elfstudent.Network.JsonProcessors.RequestdataProvider;
import com.elf.elfstudent.R;
import com.elf.elfstudent.model.StudentRequestModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by nandhu on 10/11/16.
 *
 */
public class RequestDisplayAdapter extends RecyclerView.Adapter <RequestDisplayAdapter.RequestHolder>{

    private List<StudentRequestModel> modelList = null;
    private Context mContext = null;


    private RequestCall mCallbacks  =  null;
    public RequestDisplayAdapter(Context mContext, List<StudentRequestModel> mList, RequestCall listener) {
        this.mContext = mContext;
        this.modelList = mList;
        this.mCallbacks  = listener;

    }

    @Override
    public RequestHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v  = LayoutInflater.from(mContext).inflate(R.layout.noti_item,parent,false);



        return new RequestHolder(v);
    }

    @Override
    public void onBindViewHolder(RequestHolder holder, int position) {

        holder.noti_text.setText("Ram Kurar");
        holder.acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mCallbacks!= null){
                    mCallbacks.AcceptRequest();
                }
            }
        });

        holder.rejectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mCallbacks!= null){
                    mCallbacks.RejectButtonClicked();
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }


    public static class RequestHolder extends RecyclerView.ViewHolder{


        @BindView(R.id.noti_propic)
        CircleImageView mPicture;

        @BindView(R.id.noti_request_text)
        TextView noti_text;

        @BindView(R.id.noti_accept_button)
        Button acceptButton;
        @BindView(R.id.noti_reject_button) Button rejectButton;

        public RequestHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
    public interface RequestCall{
        void AcceptRequest();
        void RejectButtonClicked();
    }
}

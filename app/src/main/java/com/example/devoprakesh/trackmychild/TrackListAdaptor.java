package com.example.devoprakesh.trackmychild;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class TrackListAdaptor extends RecyclerView.Adapter<TrackListAdaptor.ViewHolder> {

    Context context;
    List<UserData> childrens;

    public TrackListAdaptor(Context context, List<UserData> childrens) {
        this.context = context;
        this.childrens = childrens;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = (LayoutInflater)context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(childrens.isEmpty()){

            view = inflater.inflate(R.layout.nothingtodiplay,parent,false);
        }else {
            view = inflater.inflate(R.layout.listlayoutview,parent,false);
        }


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        UserData data = childrens.get(position);
        ViewHolder hold = (ViewHolder)holder;
        hold.phon.setText(data.getName());
    }

    @Override
    public int getItemCount() {
        return childrens.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView phon;

        public ViewHolder(View itemView) {
            super(itemView);

            phon = itemView.findViewById(R.id.numdisp);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            UserData data = childrens.get(getPosition());
            Intent intent = new Intent(context,Display.class);
            intent.putExtra("Number",data.getPhonenumber());
            context.startActivity(intent);
            ((Activity)context).finish();

        }
    }
}

package com.batchmates.android.bbvabacksearcher.view.secondactivity;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.batchmates.android.bbvabacksearcher.R;
import com.batchmates.android.bbvabacksearcher.model.personalpojo.BankPojo;
import com.batchmates.android.bbvabacksearcher.view.thirdactivity.Main2Activity;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android on 7/30/2017.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    List<BankPojo> bankPojos=new ArrayList<>();

    public RecyclerViewAdapter(List<BankPojo> bankPojos) {
        this.bankPojos = bankPojos;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final BankPojo bankPojo=bankPojos.get(position);
        holder.name.setText(bankPojo.getName());
        holder.address.setText(bankPojo.getAddress());
        Glide.with(holder.itemView.getContext()).load(bankPojo.getIcon()).into(holder.imageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), Main2Activity.class);
                intent.putExtra("BANKNAME",holder.name.getText());
                intent.putExtra("BANKADDRESS",holder.address.getText());
                intent.putExtra("PICTURE",bankPojo.getIcon());
                intent.putExtra("RATING",bankPojo.getRating());
                view.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return bankPojos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView name;
        TextView address;
        public ViewHolder(View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.ivSymbol);
            name=itemView.findViewById(R.id.tvName);
            address=itemView.findViewById(R.id.tvAddress);
        }
    }
}

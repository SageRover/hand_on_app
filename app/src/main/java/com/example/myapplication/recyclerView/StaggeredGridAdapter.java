/*
package com.example.myapplication;

import androidx.recyclerview.widget.RecyclerView;
        import android.content.Context;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.AdapterView;
        import android.widget.ImageView;

        import java.util.ArrayList;
        import java.util.List;

public class StaggeredGridAdapter extends RecyclerView.Adapter<StaggeredGridAdapter.LinearViewHolder>{

    private Context mContext;
    private AdapterView.OnItemClickListener mListener;
    private List<String> list=new ArrayList<>();

    public StaggeredGridAdapter(Context mContext) {
        this.mContext = mContext;
        for(int i=0;i<30;i++){
            list.add(String.format("%s-%s", i/10+1,i));
        }
    }

    @Override
    public StaggeredGridAdapter.LinearViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new LinearViewHolder(LayoutInflater.from(mContext).inflate(R.layout.layout_staggere_grid_item,parent,false));
    }

    @Override
    public void onBindViewHolder(StaggeredGridAdapter.LinearViewHolder holder, int position) {
        if(position%2==0){
            holder.mImageView.setImageResource(R.drawable.baby1);
        }
        else{
            holder.mImageView.setImageResource(R.drawable.baby2);
        }

    }

    @Override
    public int getItemCount() {
        return 30;
    }

    class LinearViewHolder extends RecyclerView.ViewHolder{
        private ImageView mImageView;
        public LinearViewHolder(View itemView){
            super(itemView);
            mImageView=(ImageView) itemView.findViewById(R.id.iv);
        }
    }
}*/

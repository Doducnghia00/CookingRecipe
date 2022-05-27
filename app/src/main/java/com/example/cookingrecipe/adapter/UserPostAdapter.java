package com.example.cookingrecipe.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cookingrecipe.R;
import com.example.cookingrecipe.model.UserPost;

import java.util.ArrayList;
import java.util.List;

public class UserPostAdapter extends RecyclerView.Adapter<UserPostAdapter.ViewHolder> {

    private Context context;
    private List<UserPost> postList;
    private ItemListener itemListener;

    public void setItemListener(ItemListener itemListener) {
        this.itemListener = itemListener;
    }

    public UserPostAdapter(Context context, List<UserPost> postList) {
        this.context = context;
        this.postList = postList;
    }

    public UserPostAdapter(Context context) {
        this.context = context;
    }

    public void setPostList(List<UserPost> postList) {
        this.postList = postList;
    }

    public List<UserPost> getPostList() {
        return postList;
    }

    public UserPostAdapter() {
        postList = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_post,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        UserPost userPost = postList.get(position);
        //holder.idPost.setText("(" + userPost.getId() + ")");
        holder.idPost.setText("");
        holder.namePost.setText(userPost.getPostName());
        holder.datePost.setText(userPost.getDate());
        holder.statusPost.setText(userPost.getStatus());

        switch (userPost.getStatus()){
            case "Đã Duyệt": holder.imageStatus.setImageResource(R.drawable.ic_check);
                break;
            case "Chờ Duyệt": holder.imageStatus.setImageResource(R.drawable.ic_more_horiz);
                break;
            case "Từ Chối": holder.imageStatus.setImageResource(R.drawable.ic_close);
                break;
        }
    }

    @Override
    public int getItemCount() {
        if(postList != null){
            return  postList.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView idPost, namePost,datePost,statusPost;
        ImageView imageStatus;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            idPost = itemView.findViewById(R.id.post_id);
            namePost = itemView.findViewById(R.id.post_name);
            datePost = itemView.findViewById(R.id.post_date);
            statusPost = itemView.findViewById(R.id.post_status);
            imageStatus = itemView.findViewById(R.id.post_status_icon);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(itemListener !=null){
                itemListener.OnItemClick(view,getBindingAdapterPosition());

            }
        }
    }
    public interface ItemListener{
        void OnItemClick(View view , int position);
    }
}

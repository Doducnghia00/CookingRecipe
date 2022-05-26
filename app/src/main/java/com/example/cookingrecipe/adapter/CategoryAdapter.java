package com.example.cookingrecipe.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cookingrecipe.R;
import com.example.cookingrecipe.model.Category;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class CategoryAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<Category> categoryList ;

    public CategoryAdapter(Context context, int layout, List<Category> categoryList) {
        this.context = context;
        this.layout = layout;
        this.categoryList = categoryList;
    }

    public CategoryAdapter(Context context, int layout) {
        this.context = context;
        this.layout = layout;
    }

    @Override
    public int getCount() {
        return 14;
    }

    @Override
    public Object getItem(int i) {
        return categoryList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    private class ViewHolder{
        ImageView img;
        TextView name;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if(view == null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout,null);
            holder.img = view.findViewById(R.id.img_category);
            holder.name = view.findViewById(R.id.name_category);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }
        categoryList = new ArrayList<>();
        setList();
        Category category = categoryList.get(i);
        holder.img.setImageResource(category.getImg());
        holder.name.setText(category.getName());
        return view;
    }

    private void setList() {
        categoryList.add(0, new Category("Món Ăn Vặt", R.drawable.category_an_vat)); //1
        categoryList.add(1, new Category("Món Bún", R.drawable.category_bun)); //2
        categoryList.add(2, new Category("Món Canh", R.drawable.category_mon_canh)); //3
        categoryList.add(3, new Category("Món Cháo", R.drawable.category_mon_chao)); //4
        categoryList.add(4, new Category("Món Chè", R.drawable.category_mon_che)); //5
        categoryList.add(5, new Category("Món Chiên", R.drawable.category_mon_chien)); //6
        categoryList.add(6, new Category("Món Gỏi", R.drawable.category_mon_goi)); //7
        categoryList.add(7, new Category("Món Hấp", R.drawable.category_mon_hap)); //8
        categoryList.add(8, new Category("Món Kho", R.drawable.category_mon_kho)); //9
        categoryList.add(9, new Category("Món Lẩu", R.drawable.category_mon_lau)); //10
        categoryList.add(10, new Category("Món Mứt", R.drawable.category_mon_mut)); //11
        categoryList.add(11, new Category("Món Nướng", R.drawable.category_mon_nuong)); //12
        categoryList.add(12, new Category("Món Xào", R.drawable.category_mon_xao)); //13
        categoryList.add(13, new Category("Món Xôi", R.drawable.category_mon_xoi)); //14

    }
}

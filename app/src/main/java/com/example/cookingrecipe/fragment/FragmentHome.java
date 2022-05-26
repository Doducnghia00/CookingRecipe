package com.example.cookingrecipe.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.cookingrecipe.Activity.SearchActivity;
import com.example.cookingrecipe.MainActivity;
import com.example.cookingrecipe.R;
import com.example.cookingrecipe.adapter.CategoryAdapter;
import com.example.cookingrecipe.model.Category;

import java.util.ArrayList;
import java.util.List;

public class FragmentHome extends Fragment implements View.OnClickListener {
    private GridView gridView;
    private List<Category> categoryList ;
    private Button btSearch;
    String[] arr = {"A","B","C","D","E","F","G"};
    private CategoryAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView(view);
        adapter = new CategoryAdapter(getActivity(),R.layout.item_category);
        gridView.setAdapter(adapter);
        btSearch.setOnClickListener(this);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(getActivity(),SearchActivity.class);
                String tag = "";
                switch (position){
                    case 0: // ăn vặt
                        //Toast.makeText(getContext(), "ăn vặt", Toast.LENGTH_SHORT).show();
                        tag = "an vat";
                        break;
                    case 1: //bún
                        //Toast.makeText(getContext(), "bún", Toast.LENGTH_SHORT).show();
                        tag = "bun";
                        break;
                    case 2: // canh
                        //Toast.makeText(getContext(), "canh", Toast.LENGTH_SHORT).show();
                        tag = "canh";
                        break;
                    case 3: //cháo
                        tag = "chao";
                        //Toast.makeText(getContext(), "cháo", Toast.LENGTH_SHORT).show();
                        break;
                    case 4: //chè
                        tag = "che";
                        //Toast.makeText(getContext(), "chè", Toast.LENGTH_SHORT).show();
                        break;
                    case 5: // chiên
                        tag = "chien";
                        //Toast.makeText(getContext(), "chiên", Toast.LENGTH_SHORT).show();
                        break;
                    case 6: //gỏi
                        tag = "goi";
                        //Toast.makeText(getContext(), "gỏi", Toast.LENGTH_SHORT).show();
                        break;
                    case 7://hấp
                        tag = "hap";
                        //Toast.makeText(getContext(), "hấp", Toast.LENGTH_SHORT).show();
                        break;
                    case 8: // kho
                        tag = "kho";
                        //Toast.makeText(getContext(), "kho", Toast.LENGTH_SHORT).show();
                        break;
                    case 9: // lẩu
                        tag = "lau";
                        //Toast.makeText(getContext(), "lẩu", Toast.LENGTH_SHORT).show();
                        break;
                    case 10: //mứt
                        tag = "mut";
                        //Toast.makeText(getContext(), "mứt", Toast.LENGTH_SHORT).show();
                        break;
                    case 11: //nướng
                        tag = "nuong";
                        //Toast.makeText(getContext(), "nướng", Toast.LENGTH_SHORT).show();
                        break;
                    case 12: //xào
                        tag = "xao";
                        break;
                    case 13: //xôi
                        tag = "xoi";
                        break;

                }
                intent.putExtra("tag",tag);
                startActivity(intent);
            }
        });
    }

    private void initView(View view) {
        gridView = view.findViewById(R.id.category);
        btSearch = view.findViewById(R.id.search_recipe);

    }

    @Override
    public void onClick(View view) {
        if(view == btSearch){
            Intent intent = new Intent(getActivity(), SearchActivity.class);
            startActivity(intent);
        }
    }
}

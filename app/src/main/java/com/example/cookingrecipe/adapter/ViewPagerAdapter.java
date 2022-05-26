package com.example.cookingrecipe.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.cookingrecipe.fragment.FragmentAccount;
import com.example.cookingrecipe.fragment.FragmentChat;
import com.example.cookingrecipe.fragment.FragmentFavorite;
import com.example.cookingrecipe.fragment.FragmentHome;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0: return  new FragmentHome();
            case 1: return  new FragmentFavorite();
            case 2: return  new FragmentChat();
            case 3: return  new FragmentAccount();
            default:return new FragmentHome();
        }
       // return new FragmentHome();
        //return  null;
    }

    @Override
    public int getCount() {
        return 4;
    }

//    @Nullable
//    @Override
//    public CharSequence getPageTitle(int position) {
//        switch (position){
//            case 0 : return "Trang Chủ";
//            case 1 : return "Yêu Thích";
//            case 2 : return "Trò Chuyện";
//            case 3 : return "Tài Khoản";
//            default: return "Trang Chủ";
//        }
       // return super.getPageTitle(position);
 //   }
}

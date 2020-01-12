package com.mushtaq.criminalintent;

import android.os.Bundle;
import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

abstract public class SingleFragmentActivity extends AppCompatActivity {


    abstract  protected Fragment createFragment();

    FragmentManager fm;

//    @LayoutRes
//    protected int getLayoutResId() {
//        return R.layout.activity_fragment;
//    }

    @LayoutRes
    protected int getLayoutResId() {
        return R.layout.activity_masterdetail;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(getLayoutResId());

        fm=getSupportFragmentManager();

        fm.beginTransaction().replace(R.id.fragment_container,createFragment())
                .commit();

    }

}

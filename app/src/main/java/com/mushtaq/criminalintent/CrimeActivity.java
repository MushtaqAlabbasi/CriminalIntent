package com.mushtaq.criminalintent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.util.UUID;

public class CrimeActivity extends SingleFragmentActivity {

    FragmentManager fm;
    public static final String EXTRA_CRIME_ID = "com.mushtaq.criminalintent.crime_id";

    @Override
    protected Fragment createFragment() {
        UUID crimeId = (UUID) getIntent().getSerializableExtra(CrimeActivity.EXTRA_CRIME_ID);
        return CrimeFragment.newInstance(crimeId);
    }

//    we stopped it because we used CrimePagerActivity instead

//
////    it is only declaration definition and whenever it's called it will work (in CrimeListFragment.onClick() ) however we stopped it
////    because we used CrimePagerActivity instead
//    public static Intent newInstanceOfCrimePager (Context packageContext, UUID crimeId) {
//        Intent intent = new Intent(packageContext, CrimeActivity.class);
//        intent.putExtra(EXTRA_CRIME_ID, crimeId);
//        return intent;
//    }

}
//    ---------------------------------------------

//        fm=getSupportFragmentManager();
//        fm.beginTransaction().add(R.id.fragment_container,new CrimeFragment()).commit();
//
//        b.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                fm.beginTransaction().replace(R.id.fragment_container,new SecondFragment()).commit();
//            }
//        });






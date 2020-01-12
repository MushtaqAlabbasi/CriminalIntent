package com.mushtaq.criminalintent;


import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;


public class CrimeListFragment extends Fragment {

    RecyclerView mCrimeRecyclerView;
    CrimeAdapterRecyclerView crimeAdapterRecyclerView;

    private boolean mSubtitleVisible;
    private static final String SAVED_SUBTITLE_VISIBLE = "subtitle";
    private int mLastClickedCrimePosition = -1;
    private Callbacks mCallbacks;

//    SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
//    SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");


    public interface Callbacks {
        void onCrimeSelected(Crime crime);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallbacks = (Callbacks) context;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_crime_list_menu, menu);

        MenuItem subtitleItem = menu.findItem(R.id.show_subtitle);
        if (mSubtitleVisible) {
            subtitleItem.setTitle(R.string.hide_subtitle);
        } else {
            subtitleItem.setTitle(R.string.show_subtitle);
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.new_crime:
                Crime crime = new Crime();
                CrimeLab.getCrimeLabInstance(getActivity()).addCrime(crime.getmId(), crime);
//                Intent intent = CrimePagerActivity.newInstanceOfCrimePager(getActivity(), crime.getmId());
//                startActivity(intent);
                updateUI();
                mCallbacks.onCrimeSelected(crime);
                return true;

            case R.id.show_subtitle:
                mSubtitleVisible = !mSubtitleVisible;
                getActivity().invalidateOptionsMenu();
                updateSubtitle();
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }


    private void updateSubtitle() {
        CrimeLab crimeLab = CrimeLab.getCrimeLabInstance(getActivity());
        int crimeCount = crimeLab.getCrimes().size();
        String subtitle = getString(R.string.subtitle_format, crimeCount);
        if (!mSubtitleVisible) {
            subtitle = null;
        }
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setSubtitle(subtitle);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_crime_list, container, false);

        mCrimeRecyclerView = view.findViewById(R.id.crime_recycler_view);

        mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //mCrimeRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),5));

        if (savedInstanceState != null) {
            mSubtitleVisible = savedInstanceState.getBoolean(SAVED_SUBTITLE_VISIBLE);
        }

        updateUI();

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN,
                        ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
                    @Override
                    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                        crimeAdapterRecyclerView.onItemDismiss(viewHolder.getAdapterPosition());
                    }
                }
        );

        itemTouchHelper.attachToRecyclerView(mCrimeRecyclerView);

        return view;
    } //end of onCreate


    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }


    @Override
    public void onDetach( ) {
        super.onDetach();
        mCallbacks = null;
    }


    // ------------------------


   public void updateUI() {

        CrimeLab crimeLab = CrimeLab.getCrimeLabInstance(getActivity());
        List<Crime> crimeList = crimeLab.getCrimes();
//        CrimeLab crimeLab = CrimeLab.getCrimeLabInstance(getActivity());

        if (crimeAdapterRecyclerView == null) {

            crimeAdapterRecyclerView = new CrimeAdapterRecyclerView(crimeList);
            mCrimeRecyclerView.setAdapter(crimeAdapterRecyclerView);

        }

        else {

            if (mLastClickedCrimePosition < 0) {
                crimeAdapterRecyclerView.notifyDataSetChanged();
            } else {
                crimeAdapterRecyclerView.setCrimes(crimeList);
                crimeAdapterRecyclerView.notifyItemChanged(mLastClickedCrimePosition);
                mLastClickedCrimePosition = -1;
            }

        }

        updateSubtitle();
    }

    //-------------------------------------------------------------------------

    class CrimeAdapterRecyclerView extends RecyclerView.Adapter<CrimeHolder> {

        private List<Crime> mCrimes;

        public CrimeAdapterRecyclerView(List<Crime> crimes) {
            this.mCrimes = crimes;
        }

        @NonNull
        @Override
        public CrimeHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new CrimeHolder(layoutInflater, viewGroup);
        }

        @Override
        public void onBindViewHolder(@NonNull CrimeHolder crimeHolder, int position) {

            crimeHolder.bind(mCrimes.get(position));
            mLastClickedCrimePosition = position;

        }

        @Override
        public int getItemCount() {

            return mCrimes.size();
        }

        public void onItemDismiss(int position) {
            Crime crime = mCrimes.get(position);
            CrimeLab.getCrimeLabInstance(getActivity()).deleteCrime(crime);
        }

        public void setCrimes(List<Crime> crimes) {
            mCrimes = crimes;
        }

    }//------------------end of adapter----------


    class CrimeHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView crimeTitle, crimeDate;
        Crime mCrime;
        private ImageView mSolvedImageView;

        public CrimeHolder(LayoutInflater inflater, ViewGroup parent) {
//            list_item_crime it's our template
            super(inflater.inflate(R.layout.list_item_crime, parent, false));

            crimeTitle = itemView.findViewById(R.id.c_title);
            crimeDate = itemView.findViewById(R.id.c_date);
            mSolvedImageView = itemView.findViewById(R.id.imageView);

            itemView.setOnClickListener(this);
        }

        public void bind(Crime crime) {
            mCrime = crime;
            crimeTitle.setText(mCrime.getmTitle());
//            String formatDate = DateFormat.getDateInstance(DateFormat.SHORT, DateFormat.SHORT).format(mCrime.getmDate());
//            crimeDate.setText(formatDate);
            DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.LONG, getCurrentLocale());
            crimeDate.setText(dateFormat.format(mCrime.getmDate()));

            mSolvedImageView.setVisibility(crime.ismSolved() ? View.VISIBLE : View.GONE);
//            String stringDate = dateFormat.format(mCrime.getmDate());
//            crimeDate.setText(stringDate);
//            String stringTime = timeFormat.format(mCrime.getmTime());
//            crimeTime.setText(stringTime);
        }


        @Override
        public void onClick(View view) {
            mLastClickedCrimePosition = getAdapterPosition();
//            Intent intent = CrimePagerActivity.newInstanceOfCrimePager(getActivity(), mCrime.getmId());
//            startActivity(intent);
            mCallbacks.onCrimeSelected(mCrime);
        }

        private Locale getCurrentLocale(){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                return getResources().getConfiguration().getLocales().get(0);
            } else{
                //noinspection deprecation
                return getResources().getConfiguration().locale;
            }
        }

    } //end of holder
}


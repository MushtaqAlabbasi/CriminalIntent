package com.mushtaq.criminalintent;


import java.text.SimpleDateFormat;
import java.util.Date;
//import java.sql.Date;
//  in use be specific      mDate = new java.util.Date();
import java.util.UUID;

public class Crime {

    private  UUID mId;
    private String mTitle;
    private Date mDate;
    private boolean mSolved;
    private Date mTime;
    private String mSuspect;
    private String mNumber;

    public Crime() {
        this(UUID.randomUUID());
    }

    public Crime(UUID id) {
        mId = id;
        mDate = new Date();
        mTime = new Date();
    }

    public UUID getmId() {
        return mId;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public Date getmDate()
    {
        return mDate;
    }

    public void setmDate(Date mDate) {

        this.mDate = mDate;
    }

    public Date getmTime() {
        return mTime;
    }

    public void setmTime(Date time) {
        mTime = time;
    }

    public boolean ismSolved() {
        return mSolved;
    }

    public void setmSolved(boolean mSolved) {

        this.mSolved = mSolved;
    }

    public String getSuspect() {
        return mSuspect;
    }
    public void setSuspect(String suspect) {
        mSuspect = suspect;
    }

    public String getSuspectNumber() {
        return mNumber;
    }

    public void setSuspectNumber(String number) {
        mNumber = number;
    }


    public String getPhotoFilename() {
        return "IMG_" + getmId().toString() + ".jpg";
    }

}
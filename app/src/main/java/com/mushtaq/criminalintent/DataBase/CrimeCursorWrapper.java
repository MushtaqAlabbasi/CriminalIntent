package com.mushtaq.criminalintent.DataBase;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.mushtaq.criminalintent.Crime;
import com.mushtaq.criminalintent.DataBase.CrimeDbSchema;

import java.util.Date;
import java.util.UUID;

public class CrimeCursorWrapper extends CursorWrapper {

    public CrimeCursorWrapper(Cursor cursor) {
        super(cursor);
    }


    public Crime getCrime() {

        String uuidString = getString(getColumnIndex(CrimeDbSchema.CrimeTable.Cols.UUID));
        String title = getString(getColumnIndex(CrimeDbSchema.CrimeTable.Cols.TITLE));
        long date = getLong(getColumnIndex(CrimeDbSchema.CrimeTable.Cols.DATE));
        int isSolved = getInt(getColumnIndex(CrimeDbSchema.CrimeTable.Cols.SOLVED));
        String suspect = getString(getColumnIndex(CrimeDbSchema.CrimeTable.Cols.SUSPECT));
        String suspectNumber = getString(getColumnIndex(CrimeDbSchema.CrimeTable.Cols.SUSPECT_NUMBER));


        Crime crime = new Crime(UUID.fromString(uuidString));
        crime.setmTitle(title);
        crime.setmDate(new Date(date));
        crime.setmSolved(isSolved != 0);
        crime.setSuspect(suspect);
        crime.setSuspectNumber(suspectNumber);

        return crime;

    }
}

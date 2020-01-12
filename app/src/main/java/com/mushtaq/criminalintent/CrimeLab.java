package com.mushtaq.criminalintent;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mushtaq.criminalintent.DataBase.CrimeBaseHelper;
import com.mushtaq.criminalintent.DataBase.CrimeCursorWrapper;
import com.mushtaq.criminalintent.DataBase.CrimeDbSchema;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CrimeLab {

    static private CrimeLab crimeLab;

//    private Map<UUID, Crime> mCrimes;

    private Context mContext;
    private SQLiteDatabase mDatabase;


    static CrimeLab getCrimeLabInstance(Context context) {
        if (crimeLab == null)
            crimeLab = new CrimeLab(context);
        return crimeLab;
    }

    private CrimeLab(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new CrimeBaseHelper(mContext).getWritableDatabase();
//        mCrimes = new LinkedHashMap<>();
    }


    //to return all
    public List<Crime> getCrimes() {
//        return new ArrayList<>(mCrimes.values());
        List<Crime> crimes = new ArrayList<>();
        CrimeCursorWrapper cursor = queryCrimes(null, null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                crimes.add(cursor.getCrime());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return crimes;
    }

    //to return all
//    ArrayList<Crime> getCrimes(){
//        return mCrimes;
//    }

//    to search by id in arrayList
//    Crime getCrime(UUID id){
//
//        for(Crime c: mCrimes){
//            if(c.getmId().equals(id))
//                return c;
//        }
//        return null;
//    }


    private CrimeCursorWrapper queryCrimes(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                CrimeDbSchema.CrimeTable.NAME,
                null, // columns - null selects all columns
                whereClause,
                whereArgs,
                null, // groupBy
                null, // having
                null // orderBy
        );

        return new CrimeCursorWrapper(cursor);
    }


    public Crime getCrime(UUID id) {

        CrimeCursorWrapper cursor = queryCrimes(
                CrimeDbSchema.CrimeTable.Cols.UUID + " = ?", new String[]{id.toString()}
        );
        try {
            if (cursor.getCount() == 0) {
                return null;
            }
            cursor.moveToFirst();
            return cursor.getCrime();
        } finally {
            cursor.close();
        }

        // to search directly with id in hashmap
        //return mCrimes.get(id);
    }


    public void addCrime(UUID u,Crime c) {
//        mCrimes.put(c.getmId(), c);
        ContentValues values = getContentValues(c);
        mDatabase.insert(CrimeDbSchema.CrimeTable.NAME, null, values);
    }


    public void updateCrime(Crime crime) {

        String uuidString = crime.getmId().toString();

        ContentValues values = getContentValues(crime);

        mDatabase.update(CrimeDbSchema.CrimeTable.NAME, values,
                CrimeDbSchema.CrimeTable.Cols.UUID + " = ?",
                new String[]{ uuidString });
    }


    private static ContentValues getContentValues(Crime crime) {
        ContentValues values = new ContentValues();
        values.put(CrimeDbSchema.CrimeTable.Cols.UUID, crime.getmId().toString());
        values.put(CrimeDbSchema.CrimeTable.Cols.TITLE, crime.getmTitle());
        values.put(CrimeDbSchema.CrimeTable.Cols.DATE, crime.getmDate().getTime());
        values.put(CrimeDbSchema.CrimeTable.Cols.SOLVED, crime.ismSolved() ? 1 : 0);
        values.put(CrimeDbSchema.CrimeTable.Cols.SUSPECT, crime.getSuspect());
        values.put(CrimeDbSchema.CrimeTable.Cols.SUSPECT_NUMBER, crime.getSuspectNumber());
        return values;
    }


    public void deleteCrime(Crime mCrime) {
        mDatabase.delete(CrimeDbSchema.CrimeTable.NAME,
                CrimeDbSchema.CrimeTable.Cols.UUID + " = ?",
                new String[]{ mCrime.getmId().toString() });
    }

    public File getPhotoFile(Crime crime) {
        File filesDir = mContext.getFilesDir();
        return new File(filesDir, crime.getPhotoFilename());
    }


}

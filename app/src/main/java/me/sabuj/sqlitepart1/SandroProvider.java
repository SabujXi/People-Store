package me.sabuj.sqlitepart1;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

public class SandroProvider extends ContentProvider {
    SQLiteOpenHelper dbHelper;
    // authority
    public static String AUTORITY = "me.sabuj.sandroprovider1.SANDRO_PROVIDER1";

    // paths
    public static String PERSONS_PATH = "persons";
    public static String SINGLE_PERSON_PATH = "persons/#";

    // types
    public static final int PERSONS_TYPE = 1;
    public static final int SINGLE_PERSON_TYPE = 2;

    // MIME TYPES
    public static String MIME_PERSONS = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + "vnd.me.sabuj.sqlitepart1.persons";
    public static String MIME_SINGLE_PERSON = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + "vnd.me.sabuj.sqlitepart1.singleperson";

    // The Matcher
    public static UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static{
        uriMatcher.addURI(AUTORITY, PERSONS_PATH, PERSONS_TYPE);
        uriMatcher.addURI(AUTORITY, SINGLE_PERSON_PATH, SINGLE_PERSON_TYPE);
    }


    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return dbHelper.getWritableDatabase().delete("persons", selection, selectionArgs);
    }

    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)){
            case(PERSONS_TYPE):
                return MIME_PERSONS;
            case(SINGLE_PERSON_TYPE):
                return MIME_SINGLE_PERSON;
            default:
                return null;
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long ret = dbHelper.getWritableDatabase().insert("persons", null, values);
        Uri insertUri = Uri.parse("content://" + AUTORITY + "/" + PERSONS_PATH + "/" + ret);
        return insertUri;
    }

    @Override
    public boolean onCreate() {
         dbHelper = new MyDbHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        return dbHelper.getWritableDatabase().query("persons", projection, selection, selectionArgs, null, null, sortOrder);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        return dbHelper.getWritableDatabase().update("persons", values, selection, selectionArgs);
    }
}

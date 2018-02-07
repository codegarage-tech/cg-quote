//package tech.codegarage.quotes.activity;
//
//import android.database.Cursor;
//import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
//import android.util.Log;
//
//import tech.codegarage.quotes.model.database.LitePalAuthor;
//import tech.codegarage.quotes.model.database.LitePalDataHandler;
//
//import org.litepal.crud.DataSupport;
//
///**
// * @author Md. Rashadul Alam
// *         Email: rashed.droid@gmail.com
// */
//public class LitePalTestActivity extends AppCompatActivity {
//
//    private String TAG = "LitePalTestRashed";
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        LitePalDataHandler.initAllQuotes();
//
//        getAllData();
//    }
//
//    private void getAllData() {
//        Cursor cursor = DataSupport.findBySQL("select * from litepalquotelanguageauthortag where authorid = ?", "2");
//        while (cursor.moveToNext()) {
//            Log.d(TAG, "CursorData: " + DataSupport.find(LitePalAuthor.class, cursor.getLong(cursor.getColumnIndex("authorid"))));
//        }
//    }
//}

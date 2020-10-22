package com.example.text;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.os.Bundle;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final String TAG="MyWordsTag";
    private ContentResolver resolver;
    Button buttonQuery = null;
    Button buttonInsert = null;
    Button  buttonDelete = null;
    Button  buttonUpdate = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resolver = this.getContentResolver();
        Button buttonQuery = (Button)findViewById(R.id.btn_edit_text);
        Button buttonInsert = (Button) findViewById(R.id.btn_add);
        Button buttonDelete = (Button) findViewById(R.id.btn_delete);
        Button buttonUpdate = (Button) findViewById(R.id.btn_edit);
        final TextView tvQuery=findViewById(R.id.tv_query);
        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("content://com.example.vocabularybook.provider/Database");
               String strWord = "TESTWORD";
               String strMeaning = "测试ContentProvider的单词";
               String strSample = "this is a TESTWORD";
               ContentValues values = new ContentValues();
               values.put("ENG",strWord);
               values.put("CHN",strMeaning);
               values.put("SAMPLE",strSample);
               Uri newUri = getContentResolver().insert(uri,values);
                Toast.makeText(MainActivity.this,"添加成功",Toast.LENGTH_SHORT).show();
            }
        });
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("content://com.example.vocabularybook.provider/Database");
                ContentValues values = new ContentValues();
                values.put("CHN","编辑过的测试单词");
                getContentResolver().update(uri,values,"ENG=?",new String[]{"TESTWORD"});
                Toast.makeText(MainActivity.this,"编辑成功！",Toast.LENGTH_SHORT).show();
            }
        });
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("content://com.example.vocabularybook.provider/Database");
                getContentResolver().delete(uri,"ENG = ?",new String []{"TESTWORD"});
                Toast.makeText(MainActivity.this,"删除成功！请在单词本中查看是否成功删除",Toast.LENGTH_SHORT).show();
            }
        });
        buttonQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("content://com.example.vocabularybook.provider/Database");
                Cursor cursor = getContentResolver().query(uri,null,null,new String[]{},null);
               StringBuilder stringBuilder =new StringBuilder();
                while(cursor.moveToNext()){
                    Log.e("ENG",cursor.getString(1));
                    Log.e("CHN", cursor.getString(2));
                    Log.e("SAMPLE", cursor.getString(3));
                    stringBuilder.append(cursor.getString(1));
                    stringBuilder.append("\n");
                    stringBuilder.append(cursor.getString(2));
                    stringBuilder.append("\n");
                    stringBuilder.append(cursor.getString(3));
                    stringBuilder.append("\n");

                }
                tvQuery.setText(stringBuilder.toString());
            }
        });
    }
}

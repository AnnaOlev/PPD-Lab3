package com.example.lab3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class SQLiteHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Student";
    private static final String TABLE_STUDENT = "student";
    private static final String KEY_ID = "id";
    private static final String KEY_FIO = "fio";
    private static final String KEY_TIME = "time_of_writing";

    private static final String DB_CREATE = "create table " + TABLE_STUDENT + "(" + KEY_ID  + " integer primary key autoincrement NOT NULL," + KEY_FIO
            + " text," + KEY_TIME + " text" + ")";

    private final String format = "dd.MM.yy HH:mm";
    private ArrayList<String> list = new ArrayList<>();

    SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        list.add("Абдуалимов Амирхон Рустамович"); list.add("Акжигитов Радмир Русланович"); list.add("Артемов Александр Андреевич"); list.add("Аскандер Стивен Самех Саад"); list.add("Болдырев Григорий Михайлович"); list.add("Гарянин Никита Андреевич"); list.add("Гриценко Александр Сергеевич"); list.add("Звенигородская Арина Александровна"); list.add("Зекирьяев Руслан Тимурович"); list.add("Исхак Керолос Камал");
        list.add("Коватьев Иван Михайлович"); list.add("Костина Оксана Владимировна"); list.add("Кузьмин Кирилл Дмитриевич"); list.add("Миночкин Константин Даниилович"); list.add("Кузьмин Кирилл Дмитриевич"); list.add("Миночкин Константин Даниилович");list.add("Нгуен Куок Ань"); list.add("Олевская Анна Леонидовна"); list.add("Рабочих Андрей Юрьевич"); list.add("Сторожук Даниил Сергеевич"); list.add("Терентьев Павел Владимирович");
        list.add("Турсунов Парвиз Бахоралиевич"); list.add("Флоря Дмитрий"); list.add("Чимидов Эльвек Эренцович"); list.add("Шатров Савелий Иванович");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DB_CREATE);
        String date = new SimpleDateFormat(format).format(Calendar.getInstance().getTime());
        for (int i =0; i < 5; i++) {
            int rand = 0 + (int) (Math.random() * list.size());
            ContentValues initialValues = createContentValues(list.get(rand), date);
            db.insert(TABLE_STUDENT, null, initialValues);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (db.getVersion() != 1) {
            db.execSQL("drop table if exists " + TABLE_STUDENT);
            onCreate(db);
        }
    }

    void addValues(SQLiteDatabase db){
        String date = new SimpleDateFormat(format).format(Calendar.getInstance().getTime());
        for (int i =0; i < 5; i++) {
            int rand = 0 + (int) (Math.random() * list.size());
            ContentValues initialValues = createContentValues(list.get(rand), date);
            db.insert(TABLE_STUDENT, null, initialValues);
        }
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("drop table if exists " + TABLE_STUDENT);
        onCreate(db);
    }

    private ContentValues createContentValues(String FIO, String TIME) {
        ContentValues values = new ContentValues();
        values.put(KEY_FIO, FIO);
        values.put(KEY_TIME, TIME);
        return values;
    }

    void update() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor mCursor = db.query(true, TABLE_STUDENT, new String[] { KEY_ID, KEY_FIO, KEY_TIME}, null,
                null, null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToLast();
        }
        String date = new SimpleDateFormat(format).format(Calendar.getInstance().getTime());
        db.execSQL("UPDATE " + TABLE_STUDENT + " SET " + KEY_FIO + " = 'Иванов Иван Иванович', " + KEY_TIME + " = '" + date +
                "' WHERE " + KEY_ID + " = (SELECT MAX(" + KEY_ID + ") FROM " + TABLE_STUDENT + ")");
    }

    void add(String FIO) {
        SQLiteDatabase db = this.getWritableDatabase();
        String date = new SimpleDateFormat(format).format(Calendar.getInstance().getTime());
        ContentValues values = createContentValues(FIO, date);
        db.insert(TABLE_STUDENT, null, values);
        db.close();
    }

    Cursor getStudentTable() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_STUDENT, new String[] { KEY_ID, KEY_FIO, KEY_TIME }, null, null, null,
                null, null);
    }

}

# Android SQLite Multiple (Two) Tables CRUD Tutorial

We'll design this type of sample Android App using `SQLite` database to store data. Here I've created two tables `student` and `subject`. A student has one or multiple subjects. The relation between these tables is implemented by `foreign key` with `ON UPDATE CASCADE ON DELETE CASCADE`.

<img src="https://raw.githubusercontent.com/hasancse91/Android-SQLite-Tutorial/master/data/Android-SQLite-Multiple-table-CRUD.gif" width="250" height="444" />

### DatabaseHelper.java class
```java
public class DatabaseHelper extends SQLiteOpenHelper {

    private static DatabaseHelper databaseHelper;

    // All Static variables
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = Config.DATABASE_NAME;

    // Constructor
    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Logger.addLogAdapter(new AndroidLogAdapter());
    }

    public static synchronized DatabaseHelper getInstance(Context context){
        if(databaseHelper==null){
            databaseHelper = new DatabaseHelper(context);
        }
        return databaseHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // Create tables SQL execution
        String CREATE_STUDENT_TABLE = "CREATE TABLE " + Config.TABLE_STUDENT + "("
                + Config.COLUMN_STUDENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Config.COLUMN_STUDENT_NAME + " TEXT NOT NULL, "
                + Config.COLUMN_STUDENT_REGISTRATION + " INTEGER NOT NULL UNIQUE, "
                + Config.COLUMN_STUDENT_PHONE + " TEXT, " //nullable
                + Config.COLUMN_STUDENT_EMAIL + " TEXT " //nullable
                + ")";

        String CREATE_SUBJECT_TABLE = "CREATE TABLE " + Config.TABLE_SUBJECT + "("
                + Config.COLUMN_SUBJECT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Config.COLUMN_REGISTRATION_NUMBER + " INTEGER NOT NULL, "
                + Config.COLUMN_SUBJECT_NAME + " TEXT NOT NULL, "
                + Config.COLUMN_SUBJECT_CODE + " INTEGER NOT NULL, "
                + Config.COLUMN_SUBJECT_CREDIT + " REAL, " //nullable
                + "FOREIGN KEY (" + Config.COLUMN_REGISTRATION_NUMBER + ") REFERENCES " + Config.TABLE_STUDENT + "(" + Config.COLUMN_STUDENT_REGISTRATION + ") ON UPDATE CASCADE ON DELETE CASCADE, "
                + "CONSTRAINT " + Config.STUDENT_SUB_CONSTRAINT + " UNIQUE (" + Config.COLUMN_REGISTRATION_NUMBER + "," + Config.COLUMN_SUBJECT_CODE + ")"
                + ")";

        db.execSQL(CREATE_STUDENT_TABLE);
        db.execSQL(CREATE_SUBJECT_TABLE);

        Logger.d("DB created!");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + Config.TABLE_STUDENT);
        db.execSQL("DROP TABLE IF EXISTS " + Config.TABLE_SUBJECT);

        // Create tables again
        onCreate(db);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);

        //enable foreign key constraints like ON UPDATE CASCADE, ON DELETE CASCADE
        db.execSQL("PRAGMA foreign_keys=ON;");
    }

}
```

### DatabaseQueryClass.java
```java
public class DatabaseQueryClass {

    private Context context;

    public DatabaseQueryClass(Context context){
        this.context = context;
        Logger.addLogAdapter(new AndroidLogAdapter());
    }

    public long insertStudent(Student student){

        long id = -1;
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Config.COLUMN_STUDENT_NAME, student.getName());
        contentValues.put(Config.COLUMN_STUDENT_REGISTRATION, student.getRegistrationNumber());
        contentValues.put(Config.COLUMN_STUDENT_PHONE, student.getPhoneNumber());
        contentValues.put(Config.COLUMN_STUDENT_EMAIL, student.getEmail());

        try {
            id = sqLiteDatabase.insertOrThrow(Config.TABLE_STUDENT, null, contentValues);
        } catch (SQLiteException e){
            Logger.d("Exception: " + e.getMessage());
            Toast.makeText(context, "Operation failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            sqLiteDatabase.close();
        }

        return id;
    }

    public List<Student> getAllStudent(){

        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();

        Cursor cursor = null;
        try {

            cursor = sqLiteDatabase.query(Config.TABLE_STUDENT, null, null, null, null, null, null, null);

            /**
                 // If you want to execute raw query then uncomment below 2 lines. And comment out above line.

                 String SELECT_QUERY = String.format("SELECT %s, %s, %s, %s, %s FROM %s", Config.COLUMN_STUDENT_ID, Config.COLUMN_STUDENT_NAME, Config.COLUMN_STUDENT_REGISTRATION, Config.COLUMN_STUDENT_EMAIL, Config.COLUMN_STUDENT_PHONE, Config.TABLE_STUDENT);
                 cursor = sqLiteDatabase.rawQuery(SELECT_QUERY, null);
             */

            if(cursor!=null)
                if(cursor.moveToFirst()){
                    List<Student> studentList = new ArrayList<>();
                    do {
                        int id = cursor.getInt(cursor.getColumnIndex(Config.COLUMN_STUDENT_ID));
                        String name = cursor.getString(cursor.getColumnIndex(Config.COLUMN_STUDENT_NAME));
                        long registrationNumber = cursor.getLong(cursor.getColumnIndex(Config.COLUMN_STUDENT_REGISTRATION));
                        String email = cursor.getString(cursor.getColumnIndex(Config.COLUMN_STUDENT_EMAIL));
                        String phone = cursor.getString(cursor.getColumnIndex(Config.COLUMN_STUDENT_PHONE));

                        studentList.add(new Student(id, name, registrationNumber, email, phone));
                    }   while (cursor.moveToNext());

                    return studentList;
                }
        } catch (Exception e){
            Logger.d("Exception: " + e.getMessage());
            Toast.makeText(context, "Operation failed", Toast.LENGTH_SHORT).show();
        } finally {
            if(cursor!=null)
                cursor.close();
            sqLiteDatabase.close();
        }

        return Collections.emptyList();
    }

    public Student getStudentByRegNum(long registrationNum){

        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();

        Cursor cursor = null;
        Student student = null;
        try {

            cursor = sqLiteDatabase.query(Config.TABLE_STUDENT, null,
                    Config.COLUMN_STUDENT_REGISTRATION + " = ? ", new String[]{String.valueOf(registrationNum)},
                    null, null, null);

            /**
                 // If you want to execute raw query then uncomment below 2 lines. And comment out above sqLiteDatabase.query() method.

                 String SELECT_QUERY = String.format("SELECT * FROM %s WHERE %s = %s", Config.TABLE_STUDENT, Config.COLUMN_STUDENT_REGISTRATION, String.valueOf(registrationNum));
                 cursor = sqLiteDatabase.rawQuery(SELECT_QUERY, null);
             */

            if(cursor.moveToFirst()){
                int id = cursor.getInt(cursor.getColumnIndex(Config.COLUMN_STUDENT_ID));
                String name = cursor.getString(cursor.getColumnIndex(Config.COLUMN_STUDENT_NAME));
                long registrationNumber = cursor.getLong(cursor.getColumnIndex(Config.COLUMN_STUDENT_REGISTRATION));
                String phone = cursor.getString(cursor.getColumnIndex(Config.COLUMN_STUDENT_PHONE));
                String email = cursor.getString(cursor.getColumnIndex(Config.COLUMN_STUDENT_EMAIL));

                student = new Student(id, name, registrationNumber, phone, email);
            }
        } catch (Exception e){
            Logger.d("Exception: " + e.getMessage());
            Toast.makeText(context, "Operation failed", Toast.LENGTH_SHORT).show();
        } finally {
            if(cursor!=null)
                cursor.close();
            sqLiteDatabase.close();
        }

        return student;
    }

    public long updateStudentInfo(Student student){

        long rowCount = 0;
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Config.COLUMN_STUDENT_NAME, student.getName());
        contentValues.put(Config.COLUMN_STUDENT_REGISTRATION, student.getRegistrationNumber());
        contentValues.put(Config.COLUMN_STUDENT_PHONE, student.getPhoneNumber());
        contentValues.put(Config.COLUMN_STUDENT_EMAIL, student.getEmail());

        try {
            rowCount = sqLiteDatabase.update(Config.TABLE_STUDENT, contentValues,
                    Config.COLUMN_STUDENT_ID + " = ? ",
                    new String[] {String.valueOf(student.getId())});
        } catch (SQLiteException e){
            Logger.d("Exception: " + e.getMessage());
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            sqLiteDatabase.close();
        }

        return rowCount;
    }

    public long deleteStudentByRegNum(long registrationNum) {
        long deletedRowCount = -1;
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        try {
            deletedRowCount = sqLiteDatabase.delete(Config.TABLE_STUDENT,
                                    Config.COLUMN_STUDENT_REGISTRATION + " = ? ",
                                    new String[]{ String.valueOf(registrationNum)});
        } catch (SQLiteException e){
            Logger.d("Exception: " + e.getMessage());
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            sqLiteDatabase.close();
        }

        return deletedRowCount;
    }

    public boolean deleteAllStudents(){
        boolean deleteStatus = false;
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        try {
            //for "1" delete() method returns number of deleted rows
            //if you don't want row count just use delete(TABLE_NAME, null, null)
            sqLiteDatabase.delete(Config.TABLE_STUDENT, null, null);

            long count = DatabaseUtils.queryNumEntries(sqLiteDatabase, Config.TABLE_STUDENT);

            if(count==0)
                deleteStatus = true;

        } catch (SQLiteException e){
            Logger.d("Exception: " + e.getMessage());
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            sqLiteDatabase.close();
        }

        return deleteStatus;
    }

    public long getNumberOfStudent(){
        long count = -1;
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        try {
            count = DatabaseUtils.queryNumEntries(sqLiteDatabase, Config.TABLE_STUDENT);
        } catch (SQLiteException e){
            Logger.d("Exception: " + e.getMessage());
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            sqLiteDatabase.close();
        }

        return count;
    }

    // subjects
    public long insertSubject(Subject subject, long registrationNo){
        long rowId = -1;
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Config.COLUMN_SUBJECT_NAME, subject.getName());
        contentValues.put(Config.COLUMN_SUBJECT_CODE, subject.getCode());
        contentValues.put(Config.COLUMN_SUBJECT_CREDIT, subject.getCredit());
        contentValues.put(Config.COLUMN_REGISTRATION_NUMBER, registrationNo);

        try {
            rowId = sqLiteDatabase.insertOrThrow(Config.TABLE_SUBJECT, null, contentValues);
        } catch (SQLiteException e){
            Logger.d(e);
        } finally {
            sqLiteDatabase.close();
        }

        return rowId;
    }

    public Subject getSubjectById(long subjectId){
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();

        Subject subject = null;

        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(Config.TABLE_SUBJECT, null,
                    Config.COLUMN_SUBJECT_ID + " = ? ", new String[] {String.valueOf(subjectId)},
                    null, null, null);

            if(cursor!=null && cursor.moveToFirst()){
                String subjectName = cursor.getString(cursor.getColumnIndex(Config.COLUMN_SUBJECT_NAME));
                int subjectCode = cursor.getInt(cursor.getColumnIndex(Config.COLUMN_SUBJECT_CODE));
                double subjectCredit = cursor.getDouble(cursor.getColumnIndex(Config.COLUMN_SUBJECT_CREDIT));

                subject = new Subject(subjectId, subjectName, subjectCode, subjectCredit);
            }
        } catch (SQLiteException e){
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            if(cursor!=null)
                cursor.close();
            sqLiteDatabase.close();
        }

        return subject;
    }

    public long updateSubjectInfo(Subject subject){

        long rowCount = 0;
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Config.COLUMN_SUBJECT_NAME, subject.getName());
        contentValues.put(Config.COLUMN_SUBJECT_CODE, subject.getCode());
        contentValues.put(Config.COLUMN_SUBJECT_CREDIT, subject.getCredit());

        try {
            rowCount = sqLiteDatabase.update(Config.TABLE_SUBJECT, contentValues,
                    Config.COLUMN_SUBJECT_ID + " = ? ",
                    new String[] {String.valueOf(subject.getId())});
        } catch (SQLiteException e){
            Logger.d("Exception: " + e.getMessage());
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            sqLiteDatabase.close();
        }

        return rowCount;
    }

    public List<Subject> getAllSubjectsByRegNo(long registrationNo){
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();

        List<Subject> subjectList = new ArrayList<>();
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(Config.TABLE_SUBJECT,
                    new String[] {Config.COLUMN_SUBJECT_ID, Config.COLUMN_SUBJECT_NAME, Config.COLUMN_SUBJECT_CODE, Config.COLUMN_SUBJECT_CREDIT},
                    Config.COLUMN_REGISTRATION_NUMBER + " = ? ",
                    new String[] {String.valueOf(registrationNo)},
                    null, null, null);

            if(cursor!=null && cursor.moveToFirst()){
                subjectList = new ArrayList<>();
                do {
                    int id = cursor.getInt(cursor.getColumnIndex(Config.COLUMN_SUBJECT_ID));
                    String subjectName = cursor.getString(cursor.getColumnIndex(Config.COLUMN_SUBJECT_NAME));
                    int subjectCode = cursor.getInt(cursor.getColumnIndex(Config.COLUMN_SUBJECT_CODE));
                    double subjectCredit = cursor.getDouble(cursor.getColumnIndex(Config.COLUMN_SUBJECT_CREDIT));

                    subjectList.add(new Subject(id, subjectName, subjectCode, subjectCredit));
                } while (cursor.moveToNext());
            }
        } catch (SQLiteException e){
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            if(cursor!=null)
                cursor.close();
            sqLiteDatabase.close();
        }

        return subjectList;
    }

    public boolean deleteSubjectById(long subjectId) {
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        int row = sqLiteDatabase.delete(Config.TABLE_SUBJECT,
                Config.COLUMN_SUBJECT_ID + " = ? ", new String[]{String.valueOf(subjectId)});

        return row > 0;
    }

    public boolean deleteAllSubjectsByRegNum(long registrationNum) {
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        int row = sqLiteDatabase.delete(Config.TABLE_SUBJECT,
                Config.COLUMN_REGISTRATION_NUMBER + " = ? ", new String[]{String.valueOf(registrationNum)});

        return row > 0;
    }

    public long getNumberOfSubject(){
        long count = -1;
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        try {
            count = DatabaseUtils.queryNumEntries(sqLiteDatabase, Config.TABLE_SUBJECT);
        } catch (SQLiteException e){
            Logger.d("Exception: " + e.getMessage());
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            sqLiteDatabase.close();
        }

        return count;
    }

}
```

### Config.java class
```java
public class Config {

    public static final String DATABASE_NAME = "student-db";

    //column names of student table
    public static final String TABLE_STUDENT = "student";
    public static final String COLUMN_STUDENT_ID = "_id";
    public static final String COLUMN_STUDENT_NAME = "name";
    public static final String COLUMN_STUDENT_REGISTRATION = "registration_no";
    public static final String COLUMN_STUDENT_PHONE = "phone";
    public static final String COLUMN_STUDENT_EMAIL = "email";

    //column names of subject table
    public static final String TABLE_SUBJECT = "subject";
    public static final String COLUMN_SUBJECT_ID = "_id";
    public static final String COLUMN_REGISTRATION_NUMBER = "fk_registration_no";
    public static final String COLUMN_SUBJECT_NAME = "name";
    public static final String COLUMN_SUBJECT_CODE = "subject_code";
    public static final String COLUMN_SUBJECT_CREDIT = "credit";
    public static final String STUDENT_SUB_CONSTRAINT = "student_sub_unique";

    //others for general purpose key-value pair data
    public static final String TITLE = "title";
    public static final String CREATE_STUDENT = "create_student";
    public static final String UPDATE_STUDENT = "update_student";
    public static final String CREATE_SUBJECT = "create_subject";
    public static final String UPDATE_SUBJECT = "update_subject";
    public static final String STUDENT_REGISTRATION = "student_registration";
}
```

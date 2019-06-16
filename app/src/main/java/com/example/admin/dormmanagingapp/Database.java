package com.example.admin.dormmanagingapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.CursorAdapter;

import com.example.admin.dormmanagingapp.Model.Equipment;
import com.example.admin.dormmanagingapp.Model.Event;
import com.example.admin.dormmanagingapp.Model.EventType;
import com.example.admin.dormmanagingapp.Model.Personnel;
import com.example.admin.dormmanagingapp.Model.Resident;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

public class Database extends SQLiteOpenHelper {

    public static final String TAG = " >>>> Database() ";

    public static final String DATABASE_NAME = "dbDormee";
    public static final Integer DATABASE_VERSION = 14;

    /* table names */
    public static final String TABLE_RESIDENTS = "tbResidents";
    public static final String TABLE_PERSONNEL = "tbPersonnel";
    public static final String TABLE_EQUIPMENT = "tbEquipment";
    public static final String TABLE_EVENTS = "tbEvents";
    public static final String TABLE_EVENT_TYPES = "tbEventTypes";

    /* TABLE_RESIDENTS column names */
    public static final String COLUMN_RESIDENTS_ID = "residentId";
    public static final String COLUMN_RESIDENTS_FIRSTNAME = "residentFirstname";
    public static final String COLUMN_RESIDENTS_LASTNAME = "residentLastname";
    public static final String COLUMN_RESIDENTS_ROOM_NUMBER = "residentRoomNumber";
    public static final String COLUMN_RESIDENTS_ADDRESS_STREET = "residentAddressStreet";
    public static final String COLUMN_RESIDENTS_ADDRESS_CITY = "residentAddressCity";
    public static final String COLUMN_RESIDENTS_ORIGIN_COUNTRY = "residentOriginCountry";
    public static final String COLUMN_RESIDENTS_MOBILE_NUMBER = "residentMobileNumber";
    public static final String COLUMN_RESIDENTS_EMAIL_ADDRESS = "residentEmailAddress";
    public static final String COLUMN_RESIDENTS_PHOTO = "residentPhoto";
    public static final String COLUMN_RESIDENTS_STUDENT_ID = "residentStudentId";
    public static final String COLUMN_RESIDENTS_PROGRAMME = "residentProgramme";
    public static final String COLUMN_RESIDENTS_MOVE_IN_DATE = "residentMoveInDate";
    public static final String COLUMN_RESIDENTS_MOVE_OUT_DATE = "residentMoveOutDate";
    public static final String COLUMN_RESIDENTS_GENDER = "residentGender";
    public static final String COLUMN_RESIDENTS_BIRTHDATE = "residentBirthdate";

    /* TABLE_PERSONNEL column names */
    public static final String COLUMN_PERSONNEL_ID = "personnelId";
    public static final String COLUMN_PERSONNEL_EMPLOYEE_ID = "personnelEmployeeId";
    public static final String COLUMN_PERSONNEL_FIRSTNAME = "personnelFirstname";
    public static final String COLUMN_PERSONNEL_LASTNAME = "personnelLastname";
    public static final String COLUMN_PERSONNEL_MOBILE_NUMBER = "personnelMobileNumber";
    public static final String COLUMN_PERSONNEL_EMAIL_ADDRESS = "personnelEmailAddress";
    public static final String COLUMN_PERSONNEL_GENDER = "personnelGender";
    public static final String COLUMN_PERSONNEL_TYPE = "personnelType";

    /* TABLE_EQUIPMENT column names */
    public static final String COLUMN_EQUIPMENT_ID = "equipmentId";
    public static final String COLUMN_EQUIPMENT_CODE = "equipmentCode";
    public static final String COLUMN_EQUIPMENT_NAME = "equipmentName";
    public static final String COLUMN_EQUIPMENT_SERIAL_NUMBER = "equipmentSerialNumber";
    public static final String COLUMN_EQUIPMENT_YEAR_PURCHASED = "equipmentYearPurchased";
    public static final String COLUMN_EQUIPMENT_BRAND = "equipmentBrand";

    /* TABLE_EVENTS column names */
    public static final String COLUMN_EVENTS_ID = "eventId";
    public static final String COLUMN_EVENTS_MODEL_ID = "modelId";
    public static final String COLUMN_EVENTS_MODEL_TBL = "modelTbl";
    public static final String COLUMN_EVENTS_TYPE_ID = "eventTypeId";
    public static final String COLUMN_EVENTS_TITLE = "eventTitle";
    public static final String COLUMN_EVENTS_DATE = "eventDate";
    public static final String COLUMN_EVENTS_TIME = "eventTime";
    public static final String COLUMN_EVENTS_DETAILS = "eventDetails";

    /* TABLE_EVENT_TYPES column names */
    public static final String COLUMN_EVENT_TYPE_ID = "eventTypeId";
    public static final String COLUMN_EVENT_TYPE_NAME = "eventTypeName";
    public static final String COLUMN_EVENT_TYPE_MODELTBL = "eventTypeModelTblAllowed";

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.d(TAG, "Database: Constructor");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.createEventTypesTable(db);
        this.createEventsTable(db);
        this.createResidentsTable(db);
        this.createPersonnelTable(db);
        this.createEquipmentTable(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RESIDENTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENT_TYPES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PERSONNEL);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EQUIPMENT);
        onCreate(db);
    }

    private void createResidentsTable(SQLiteDatabase sqLiteDatabase){
        Log.d(TAG, "createResidentsTable: start");
        String createTableQuery = String.format("CREATE TABLE %s (" +
                "%s INTEGER PRIMARY KEY, " + //id
                "%s TEXT, " +  //firstname
                "%s TEXT, " +   //lastname
                "%s INTEGER, " +    //room number
                "%s TEXT, " +   //street address
                "%s TEXT, " +   //city
                "%s TEXT, " +   //origin country
                "%s TEXT, " +   //mobile number
                "%s TEXT, " +   //email address
                "%s BLOB, " +   //photo
                "%s TEXT, " +   //student id
                "%s TEXT, " +   //programme
                "%s TEXT, " +   //move in date
                "%s TEXT, " +   //move out date
                "%s TEXT, " +   //gender
                "%s TEXT )",    //birthdate
                TABLE_RESIDENTS, COLUMN_RESIDENTS_ID,
                COLUMN_RESIDENTS_FIRSTNAME, COLUMN_RESIDENTS_LASTNAME, COLUMN_RESIDENTS_ROOM_NUMBER,
                COLUMN_RESIDENTS_ADDRESS_STREET, COLUMN_RESIDENTS_ADDRESS_CITY,
                COLUMN_RESIDENTS_ORIGIN_COUNTRY, COLUMN_RESIDENTS_MOBILE_NUMBER,
                COLUMN_RESIDENTS_EMAIL_ADDRESS, COLUMN_RESIDENTS_PHOTO, COLUMN_RESIDENTS_STUDENT_ID,
                COLUMN_RESIDENTS_PROGRAMME, COLUMN_RESIDENTS_MOVE_IN_DATE,
                COLUMN_RESIDENTS_MOVE_OUT_DATE, COLUMN_RESIDENTS_GENDER, COLUMN_RESIDENTS_BIRTHDATE);
        sqLiteDatabase.execSQL(createTableQuery);
        Log.d(TAG, "createResidentsTable: end");
    }

    private void createEventTypesTable(SQLiteDatabase sqLiteDatabase){
        Log.d(TAG, "createEventTypesTable: start");
        String createEventTypeTableQuery = String.format("CREATE TABLE %s (" +
                "%s INTEGER PRIMARY KEY, " +    //id
                "%s TEXT, " +       //name
                "%s TEXT " +        //model table allowed
                ")", TABLE_EVENT_TYPES,
                COLUMN_EVENT_TYPE_ID, COLUMN_EVENT_TYPE_NAME, COLUMN_EVENT_TYPE_MODELTBL);
        sqLiteDatabase.execSQL(createEventTypeTableQuery);

        //initialize values
        String insertValue = String.format("INSERT INTO %s ( %s, %s )",
                TABLE_EVENT_TYPES, COLUMN_EVENT_TYPE_NAME, COLUMN_EVENT_TYPE_MODELTBL)
                .concat("VALUES( '%s', '%s' ) ");
        sqLiteDatabase.execSQL(String.format(insertValue, "Birthday", TABLE_RESIDENTS + ", " + TABLE_PERSONNEL));
        sqLiteDatabase.execSQL(String.format(insertValue, "Move In", TABLE_RESIDENTS));
        sqLiteDatabase.execSQL(String.format(insertValue, "Move Out", TABLE_RESIDENTS));
        sqLiteDatabase.execSQL(String.format(insertValue, "Off Duty", TABLE_PERSONNEL));
        sqLiteDatabase.execSQL(String.format(insertValue, "Maintenance", TABLE_EQUIPMENT));
        sqLiteDatabase.execSQL(String.format(insertValue, "Meeting", ""));
        sqLiteDatabase.execSQL(String.format(insertValue, "Orientation", ""));

        Log.d(TAG, "createEventTypesTable: end");
    }

    private void createEventsTable(SQLiteDatabase sqLiteDatabase){
        Log.d(TAG, "createEventsTable: start");
        String createTableQuery = String.format("CREATE TABLE %s (" +
                "%s INTEGER PRIMARY KEY, " +    //event id
                "%s INTEGER, " +    //model id
                "%s TEXT, " +       //model table
                "%s INTEGER, " +       // event type
                "%s TEXT, " +   //title
                "%s TEXT, " +   //date
                "%s TEXT, " +   //time
                "%s TEXT )",    //details
                TABLE_EVENTS, COLUMN_EVENTS_ID, COLUMN_EVENTS_MODEL_ID,
                COLUMN_EVENTS_MODEL_TBL, COLUMN_EVENTS_TYPE_ID,
                COLUMN_EVENTS_TITLE, COLUMN_EVENTS_DATE,
                COLUMN_EVENTS_TIME, COLUMN_EVENTS_DETAILS);
        sqLiteDatabase.execSQL(createTableQuery);
        Log.d(TAG, "createEventsTable: end");
    }

    private void createPersonnelTable(SQLiteDatabase sqLiteDatabase){
        Log.d(TAG, "createPersonnelTable: start");
        String createQuery = String.format("CREATE TABLE %s (" +
                "%s INTEGER PRIMARY KEY, " + //personnel id
                "%s TEXT, " +       //employee id
                "%s TEXT, " +       //firstname
                "%s TEXT, " +       //lastname
                "%s TEXT, " +       //gender
                "%s TEXT, " +       //type
                "%s TEXT, " +       //mobile number
                "%s TEXT " +        //email address
                ")", TABLE_PERSONNEL,
                COLUMN_PERSONNEL_ID,
                COLUMN_PERSONNEL_EMPLOYEE_ID,
                COLUMN_PERSONNEL_FIRSTNAME,
                COLUMN_PERSONNEL_LASTNAME,
                COLUMN_PERSONNEL_GENDER,
                COLUMN_PERSONNEL_TYPE,
                COLUMN_PERSONNEL_MOBILE_NUMBER,
                COLUMN_PERSONNEL_EMAIL_ADDRESS);
        sqLiteDatabase.execSQL(createQuery);
        Log.d(TAG, "createPersonnelTable: end");
    }

    public void createEquipmentTable(SQLiteDatabase sqLiteDatabase){
        Log.d(TAG, "createEquipmentTable: start");
        String createEquipmentQuery = String.format( "CREATE TABLE %s (" +
                "%s INTEGER PRIMARY KEY, " +    //id
                "%s TEXT, " +       //code
                "%s TEXT, " +       //name
                "%s TEXT, " +       //serial number
                "%s INTEGER, " +    //year purchased
                "%s TEXT " +        //brand
                ")", TABLE_EQUIPMENT,
                COLUMN_EQUIPMENT_ID,
                COLUMN_EQUIPMENT_CODE,
                COLUMN_EQUIPMENT_NAME,
                COLUMN_EQUIPMENT_SERIAL_NUMBER,
                COLUMN_EQUIPMENT_YEAR_PURCHASED,
                COLUMN_EQUIPMENT_BRAND);
        sqLiteDatabase.execSQL(createEquipmentQuery);
        Log.d(TAG, "createEquipmentTable: end");
    }

    public List<Resident> getAllCurrentResidents(){
        Calendar today = Calendar.getInstance();
        Date date = today.getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String query = String.format("SELECT %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s " +
                        "FROM %s WHERE (%s <= '%s' AND %s >= '%s') OR %s IS NULL OR %s IS NULL ",
                COLUMN_RESIDENTS_ID, COLUMN_RESIDENTS_FIRSTNAME, COLUMN_RESIDENTS_LASTNAME,
                COLUMN_RESIDENTS_ROOM_NUMBER,
                COLUMN_RESIDENTS_ADDRESS_STREET, COLUMN_RESIDENTS_ADDRESS_CITY,
                COLUMN_RESIDENTS_ORIGIN_COUNTRY, COLUMN_RESIDENTS_MOBILE_NUMBER,
                COLUMN_RESIDENTS_EMAIL_ADDRESS, COLUMN_RESIDENTS_STUDENT_ID,
                COLUMN_RESIDENTS_PROGRAMME, COLUMN_RESIDENTS_MOVE_IN_DATE,
                COLUMN_RESIDENTS_MOVE_OUT_DATE, COLUMN_RESIDENTS_GENDER, COLUMN_RESIDENTS_BIRTHDATE,
                TABLE_RESIDENTS,
                COLUMN_RESIDENTS_MOVE_IN_DATE, dateFormat.format(date),
                COLUMN_RESIDENTS_MOVE_OUT_DATE, dateFormat.format(date),
                COLUMN_RESIDENTS_MOVE_IN_DATE, COLUMN_RESIDENTS_MOVE_OUT_DATE);
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        List<Resident> residentList = new ArrayList<>();

        Cursor cursor = sqLiteDatabase.rawQuery(query, null);

        if(cursor.moveToFirst()){
            do {
                Resident resident = new Resident();
                resident.setId(cursor.getInt(0));
                resident.setFirstname(cursor.getString(1));
                resident.setLastname(cursor.getString(2));
                resident.setRoomNumber(cursor.getInt(3));
                resident.setAddressStreet(cursor.getString(4));
                resident.setAddressCity(cursor.getString(5));
                resident.setOriginCountry(cursor.getString(6));
                resident.setMobileNumber(cursor.getString(7));
                resident.setEmailAddress(cursor.getString(8));
                resident.setStudentId(cursor.getString(9));
                resident.setProgramme(cursor.getString(10));
                resident.setMoveInDate(cursor.getString(11));
                resident.setMoveOutDate(cursor.getString(12));
                resident.setGender(cursor.getString(13));
                resident.setBirthdate(cursor.getString(14));
                residentList.add(resident);
            }while (cursor.moveToNext());
        }

        cursor.close();
        sqLiteDatabase.close();

        return residentList;
    }

    public Long createResident(Resident resident){
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_RESIDENTS_STUDENT_ID, resident.getStudentId());
        contentValues.put(COLUMN_RESIDENTS_FIRSTNAME, resident.getFirstname());
        contentValues.put(COLUMN_RESIDENTS_LASTNAME, resident.getLastname());
        contentValues.put(COLUMN_RESIDENTS_BIRTHDATE, resident.getBirthdate());
        contentValues.put(COLUMN_RESIDENTS_GENDER, resident.getGender());

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Long insertedRow = sqLiteDatabase.insert(TABLE_RESIDENTS, null, contentValues);

        sqLiteDatabase.close();
        return insertedRow;
    }

    public Integer updateResident(Resident resident){
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_RESIDENTS_STUDENT_ID, resident.getStudentId());
        contentValues.put(COLUMN_RESIDENTS_FIRSTNAME, resident.getFirstname());
        contentValues.put(COLUMN_RESIDENTS_LASTNAME, resident.getLastname());
        contentValues.put(COLUMN_RESIDENTS_BIRTHDATE, resident.getBirthdate());
        contentValues.put(COLUMN_RESIDENTS_GENDER, resident.getGender());

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Integer updatedRows = sqLiteDatabase.update(TABLE_RESIDENTS, contentValues, COLUMN_RESIDENTS_ID + " = ? ",
                new String[]{ Integer.toString(resident.getId()) });

        sqLiteDatabase.close();
        return updatedRows;
    }

    public Integer updateResidentDetails(Resident resident){
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_RESIDENTS_ROOM_NUMBER, resident.getRoomNumber());
        contentValues.put(COLUMN_RESIDENTS_PROGRAMME, resident.getProgramme());
        contentValues.put(COLUMN_RESIDENTS_MOVE_IN_DATE, resident.getMoveInDate());
        contentValues.put(COLUMN_RESIDENTS_MOVE_OUT_DATE, resident.getMoveOutDate());

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Integer updatedRows = sqLiteDatabase.update(TABLE_RESIDENTS, contentValues, COLUMN_RESIDENTS_ID + " = ? ",
                new String[]{ Integer.toString(resident.getId()) });

        sqLiteDatabase.close();
        return updatedRows;
    }

    public Integer updateResidentContact(Resident resident){
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_RESIDENTS_MOBILE_NUMBER, resident.getMobileNumber());
        contentValues.put(COLUMN_RESIDENTS_EMAIL_ADDRESS, resident.getEmailAddress());
        contentValues.put(COLUMN_RESIDENTS_ADDRESS_STREET, resident.getAddressStreet());
        contentValues.put(COLUMN_RESIDENTS_ADDRESS_CITY, resident.getAddressCity());
        contentValues.put(COLUMN_RESIDENTS_ORIGIN_COUNTRY, resident.getOriginCountry());

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Integer updatedRows = sqLiteDatabase.update(TABLE_RESIDENTS, contentValues, COLUMN_RESIDENTS_ID + " = ? ",
                new String[]{ Integer.toString(resident.getId()) });

        sqLiteDatabase.close();
        return updatedRows;
    }

    public Integer getResidentId (Resident resident){
        Integer id = 0;
        String query = String.format("SELECT %s FROM %s WHERE %s = '%s'",
                COLUMN_RESIDENTS_ID, TABLE_RESIDENTS, COLUMN_RESIDENTS_STUDENT_ID, resident.getStudentId());

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                id = cursor.getInt(0);
                break;
            }while(cursor.moveToNext());
        }

        cursor.close();
        sqLiteDatabase.close();
        return id;
    }

    public Integer getResidentIdByStudentIdAndNotSameId(Resident resident){
        Integer id = 0;
        String query = String.format("SELECT %s FROM %s WHERE %s = '%s' AND %s != %d ",
                COLUMN_RESIDENTS_ID, TABLE_RESIDENTS, COLUMN_RESIDENTS_STUDENT_ID, resident.getStudentId(),
                COLUMN_RESIDENTS_ID, resident.getId());

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                id = cursor.getInt(0);
                break;
            }while(cursor.moveToNext());
        }

        cursor.close();
        sqLiteDatabase.close();
        return id;
    }

    public Resident getResidentById(Integer id){
        Log.d(TAG, "getResidentById: " + id);
        Resident resident = new Resident();
        String query = String.format("SELECT %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s " +
                        "FROM %s WHERE %s = %d",
                COLUMN_RESIDENTS_ID,
                COLUMN_RESIDENTS_FIRSTNAME, COLUMN_RESIDENTS_LASTNAME, COLUMN_RESIDENTS_ROOM_NUMBER,
                COLUMN_RESIDENTS_ADDRESS_STREET, COLUMN_RESIDENTS_ADDRESS_CITY,
                COLUMN_RESIDENTS_ORIGIN_COUNTRY, COLUMN_RESIDENTS_MOBILE_NUMBER,
                COLUMN_RESIDENTS_EMAIL_ADDRESS, COLUMN_RESIDENTS_STUDENT_ID,
                COLUMN_RESIDENTS_PROGRAMME, COLUMN_RESIDENTS_MOVE_IN_DATE,
                COLUMN_RESIDENTS_MOVE_OUT_DATE, COLUMN_RESIDENTS_GENDER, COLUMN_RESIDENTS_BIRTHDATE,
                TABLE_RESIDENTS, COLUMN_RESIDENTS_ID, id);
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                Log.d(TAG, "getResidentById: " + cursor.getInt(0) + " - " + cursor.getString(1));
                resident.setId(cursor.getInt(0));
                resident.setFirstname(cursor.getString(1));
                resident.setLastname(cursor.getString(2));
                resident.setRoomNumber(cursor.getInt(3));
                resident.setAddressStreet(cursor.getString(4));
                resident.setAddressCity(cursor.getString(5));
                resident.setOriginCountry(cursor.getString(6));
                resident.setMobileNumber(cursor.getString(7));
                resident.setEmailAddress(cursor.getString(8));
                resident.setStudentId(cursor.getString(9));
                resident.setProgramme(cursor.getString(10));
                resident.setMoveInDate(cursor.getString(11));
                resident.setMoveOutDate(cursor.getString(12));
                resident.setGender(cursor.getString(13));
                resident.setBirthdate(cursor.getString(14));
                break;
            }while(cursor.moveToNext());
        }

        cursor.close();
        sqLiteDatabase.close();

        return resident;
    }

    public Long createEvent(Event event){
        Integer eventId = this.findEventIdByTypeAndModel(event.getModelId(), event.getModelTableName(), event.getEventType());
        Long createdRow = -1L;

        if(eventId == 0) { //not existing... continue insert

            ContentValues contentValues = new ContentValues();
            contentValues.put(COLUMN_EVENTS_MODEL_ID, event.getModelId());
            contentValues.put(COLUMN_EVENTS_MODEL_TBL, event.getModelTableName());
            contentValues.put(COLUMN_EVENTS_TYPE_ID, event.getEventType().getId());
            contentValues.put(COLUMN_EVENTS_TITLE, event.getTitle());
            contentValues.put(COLUMN_EVENTS_DATE, event.getDate());
            contentValues.put(COLUMN_EVENTS_TIME, event.getTime());
            contentValues.put(COLUMN_EVENTS_DETAILS, event.getDetails());

            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            createdRow = sqLiteDatabase.insert(TABLE_EVENTS, null, contentValues);

            sqLiteDatabase.close();
        }

        return createdRow;
    }

    public Long updateEvent(Event event){
        //check if event of type for model id and model table exists
        Integer eventId = this.findEventIdByTypeAndModel(event.getModelId(), event.getModelTableName(), event.getEventType());
        Log.d(TAG, "updateEvent: eventId [" + eventId + "], model id [" + event.getModelId() + "], table [" + event.getModelTableName() + "], type [" + event.getEventType() + "]");
        if( event.getId() != null && event.getId().intValue() > 0 && eventId > 0){ //already existing... update
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

            ContentValues contentValues = new ContentValues();
            contentValues.put(COLUMN_EVENTS_TITLE, event.getTitle());
            contentValues.put(COLUMN_EVENTS_DATE, event.getDate());
            contentValues.put(COLUMN_EVENTS_TIME, event.getTime());
            contentValues.put(COLUMN_EVENTS_TYPE_ID, event.getEventType().getId());
            contentValues.put(COLUMN_EVENTS_MODEL_TBL, event.getModelTableName());
            contentValues.put(COLUMN_EVENTS_MODEL_ID, event.getModelId());
            contentValues.put(COLUMN_EVENTS_DETAILS, event.getDetails());

            Integer updatedRows = sqLiteDatabase.update(TABLE_EVENTS, contentValues, COLUMN_EVENTS_ID + " = ? ",
                    new String[]{ Integer.toString(eventId) });

            sqLiteDatabase.close();
            return new Long(updatedRows);
        }else{ //not existing... insert
            return this.createEvent(event);
        }
    }

    public Long updateEventDate(Event event){
        //check if event of type for model id and model table exists
        Integer eventId = this.findEventIdByTypeAndModel(event.getModelId(), event.getModelTableName(), event.getEventType());
        if(eventId > 0){ //already existing... update
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

            ContentValues contentValues = new ContentValues();
            contentValues.put(COLUMN_EVENTS_DATE, event.getDate());

            Integer updatedRows = sqLiteDatabase.update(TABLE_EVENTS, contentValues, COLUMN_EVENTS_ID + " = ? ",
                    new String[]{ Integer.toString(eventId) });

            sqLiteDatabase.close();
            return new Long(updatedRows);
        }else{ //not existing... insert
            return this.createEvent(event);
        }
    }

    public Integer getEventByDateTypeTableAndModelId(String date, Integer typeId, String table, Integer modelId, Integer id){
        String query = String.format("" +
                "SELECT %s FROM %s " +
                        "WHERE %s = '%s' " +
                        "AND %s = %d " +
                        "AND %s = '%s' " +
                        "AND %s = %d ",
                COLUMN_EVENTS_ID, TABLE_EVENTS,
                COLUMN_EVENTS_DATE, date,
                COLUMN_EVENTS_TYPE_ID, typeId,
                COLUMN_EVENTS_MODEL_TBL, table,
                COLUMN_EVENTS_MODEL_ID, modelId);

        if(id != null){
            query += String.format(" AND %s != %d ", COLUMN_EVENTS_ID, id);
        }

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);

        Integer eventId = null;
        if(cursor.moveToFirst()){
            eventId = cursor.getInt(0);
        }

        cursor.close();
        sqLiteDatabase.close();

        return eventId;
    }

    public Event getEventById(Integer eventId){
        String query = String.format("" +
                "SELECT * FROM %s AS e " +
                        "JOIN %s AS t ON t.%s = e.%s " +
                        "WHERE e.%s = %d ",
                TABLE_EVENTS,
                TABLE_EVENT_TYPES, COLUMN_EVENT_TYPE_ID, COLUMN_EVENTS_TYPE_ID,
                COLUMN_EVENTS_ID, eventId);
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);

        Event event = null;
        if(cursor.moveToFirst()){
            EventType eventType = new EventType(cursor.getInt(cursor.getColumnIndex(COLUMN_EVENT_TYPE_ID)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_EVENT_TYPE_NAME)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_EVENT_TYPE_MODELTBL)));
            event = new Event(
                    cursor.getInt(cursor.getColumnIndex(COLUMN_EVENTS_ID)),
                    cursor.getInt(cursor.getColumnIndex(COLUMN_EVENTS_MODEL_ID)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_EVENTS_MODEL_TBL)),
                    eventType,
                    cursor.getString(cursor.getColumnIndex(COLUMN_EVENTS_TITLE)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_EVENTS_DATE)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_EVENTS_TIME)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_EVENTS_DETAILS)));
        }

        cursor.close();
        sqLiteDatabase.close();
        return event;
    }

    public Integer findEventIdByTypeAndModel(Integer modelId, String modelTbl, EventType eventType){
        Integer eventId = 0;

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String query = String.format("SELECT %s FROM %s WHERE %s = %d AND %s = '%s' AND %s = '%s' ",
                COLUMN_EVENTS_ID, TABLE_EVENTS,
                COLUMN_EVENTS_MODEL_ID, modelId,
                COLUMN_EVENTS_MODEL_TBL, modelTbl,
                COLUMN_EVENTS_TYPE_ID, eventType.getId());
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);

        if(cursor.moveToFirst()){
            eventId = cursor.getInt(0);
        }

        cursor.close();
        sqLiteDatabase.close();

        return eventId;
    }

    public Integer uploadImage(String table, String column, Bitmap bitmap, Integer residentId){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);

        ContentValues contentValues = new ContentValues();
        contentValues.put(column, stream.toByteArray());

        String whereClause = String.format(" %s = ? ", COLUMN_RESIDENTS_ID);

        Integer updatedRows = sqLiteDatabase.update(TABLE_RESIDENTS, contentValues, whereClause,
                new String[]{ Integer.toString(residentId) });

        sqLiteDatabase.close();
        return updatedRows;
    }

    public Bitmap getResidentPhoto(int id){

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String query = String.format("SELECT %s FROM %s WHERE %s = %d ", COLUMN_RESIDENTS_PHOTO, TABLE_RESIDENTS, COLUMN_RESIDENTS_ID, id);
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        Bitmap bitmap = null;

        if (cursor.moveToFirst()){
            byte[] imgByte = cursor.getBlob(0);
            if(imgByte != null) {
                Log.d(TAG, "getResidentPhoto: " + imgByte + " : " + imgByte.length);
                bitmap = BitmapFactory.decodeByteArray(imgByte, 0, imgByte.length);
            }
        }

        cursor.close();
        sqLiteDatabase.close();
        return bitmap ;
    }

    public Personnel getPersonnelById(Integer personnelId){
        String query = String.format("SELECT %s, %s, %s, %s, %s, %s, %s, %s FROM %s WHERE %s = %d",
                COLUMN_PERSONNEL_ID,
                COLUMN_PERSONNEL_EMPLOYEE_ID,
                COLUMN_PERSONNEL_FIRSTNAME,
                COLUMN_PERSONNEL_LASTNAME,
                COLUMN_PERSONNEL_GENDER,
                COLUMN_PERSONNEL_TYPE,
                COLUMN_PERSONNEL_MOBILE_NUMBER,
                COLUMN_PERSONNEL_EMAIL_ADDRESS,
                TABLE_PERSONNEL,
                COLUMN_PERSONNEL_ID, personnelId);

        Personnel personnel = null;

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);

        if(cursor.moveToFirst()){
            personnel = new Personnel(cursor.getInt(cursor.getColumnIndex(COLUMN_PERSONNEL_ID)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_PERSONNEL_EMPLOYEE_ID)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_PERSONNEL_FIRSTNAME)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_PERSONNEL_LASTNAME)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_PERSONNEL_GENDER)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_PERSONNEL_TYPE)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_PERSONNEL_MOBILE_NUMBER)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_PERSONNEL_EMAIL_ADDRESS)));
        }

        cursor.close();
        sqLiteDatabase.close();

        return personnel;
    }

    public Long createPersonnel(Personnel personnel){
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_PERSONNEL_EMPLOYEE_ID, personnel.getEmployeeId());
        contentValues.put(COLUMN_PERSONNEL_FIRSTNAME, personnel.getFirstname());
        contentValues.put(COLUMN_PERSONNEL_LASTNAME, personnel.getLastname());
        contentValues.put(COLUMN_PERSONNEL_GENDER, personnel.getGender());
        contentValues.put(COLUMN_PERSONNEL_TYPE, personnel.getType());
        contentValues.put(COLUMN_PERSONNEL_MOBILE_NUMBER, personnel.getMobileNumber());
        contentValues.put(COLUMN_PERSONNEL_EMAIL_ADDRESS, personnel.getEmailAddress());

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Long insertedRowId = sqLiteDatabase.insert(TABLE_PERSONNEL, null, contentValues);

        sqLiteDatabase.close();
        return insertedRowId;
    }

    public Integer updatePersonnel(Personnel personnel){
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_PERSONNEL_EMPLOYEE_ID, personnel.getEmployeeId());
        contentValues.put(COLUMN_PERSONNEL_FIRSTNAME, personnel.getFirstname());
        contentValues.put(COLUMN_PERSONNEL_LASTNAME, personnel.getLastname());
        contentValues.put(COLUMN_PERSONNEL_GENDER, personnel.getGender());
        contentValues.put(COLUMN_PERSONNEL_TYPE, personnel.getType());
        contentValues.put(COLUMN_PERSONNEL_MOBILE_NUMBER, personnel.getMobileNumber());
        contentValues.put(COLUMN_PERSONNEL_EMAIL_ADDRESS, personnel.getEmailAddress());

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        Integer updatedRows = sqLiteDatabase.update(TABLE_PERSONNEL, contentValues, COLUMN_PERSONNEL_ID + " = ? ",
                new String[]{ Integer.toString(personnel.getId()) });

        sqLiteDatabase.close();
        return updatedRows;
    }

    public List<Personnel> getAllPersonnel(){
        List<Personnel> personnelList = new ArrayList<>();

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String query = String.format("SELECT %s, %s, %s, %s, %s, %s, %s, %s FROM %s ORDER BY %s, %s",
                COLUMN_PERSONNEL_ID, COLUMN_PERSONNEL_EMPLOYEE_ID, COLUMN_PERSONNEL_FIRSTNAME,
                COLUMN_PERSONNEL_LASTNAME, COLUMN_PERSONNEL_GENDER, COLUMN_PERSONNEL_TYPE,
                COLUMN_PERSONNEL_MOBILE_NUMBER, COLUMN_PERSONNEL_EMAIL_ADDRESS,
                TABLE_PERSONNEL, COLUMN_PERSONNEL_LASTNAME, COLUMN_PERSONNEL_FIRSTNAME);

        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                Personnel personnel = new Personnel(cursor.getInt(cursor.getColumnIndex(COLUMN_PERSONNEL_ID)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_PERSONNEL_EMPLOYEE_ID)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_PERSONNEL_FIRSTNAME)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_PERSONNEL_LASTNAME)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_PERSONNEL_GENDER)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_PERSONNEL_TYPE)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_PERSONNEL_MOBILE_NUMBER)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_PERSONNEL_EMAIL_ADDRESS)));
                personnelList.add(personnel);
            }while (cursor.moveToNext());
        }

        cursor.close();
        sqLiteDatabase.close();
        return personnelList;
    }

    public EventType getEventTypeByName(String eventName){
        EventType eventType = null;

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        String query = String.format("SELECT * FROM %s WHERE UPPER(%s) = '%s'", TABLE_EVENT_TYPES, COLUMN_EVENT_TYPE_NAME, eventName.toUpperCase());
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);

        if(cursor.moveToFirst()){
            eventType = new EventType(cursor.getInt(cursor.getColumnIndex(COLUMN_EVENT_TYPE_ID)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_EVENT_TYPE_NAME)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_EVENT_TYPE_MODELTBL)));
        }

        cursor.close();
        sqLiteDatabase.close();

        return eventType;
    }

    public List<Event> getAllActiveEvents(){
        List<Event> eventList = new ArrayList<>();

        Calendar today = Calendar.getInstance();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formattedTodayDate = dateFormat.format(today.getTime());

        /*
        SELECT *
        FROM events AS e
        JOIN type AS t ON t.id = e.eventTypeId
        WHERE e.eventdate >= today
         */
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String query = String.format("SELECT * FROM %s AS e " +
                "JOIN %s AS t ON t.%s = e.%s " +
                "WHERE e.%s >= '%s'",
                TABLE_EVENTS,
                TABLE_EVENT_TYPES, COLUMN_EVENT_TYPE_ID, COLUMN_EVENTS_TYPE_ID,
                COLUMN_EVENTS_DATE, formattedTodayDate);
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);

        if(cursor.moveToFirst()){
            do{
                EventType eventType = new EventType(cursor.getInt(cursor.getColumnIndex(COLUMN_EVENT_TYPE_ID)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_EVENT_TYPE_NAME)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_EVENT_TYPE_MODELTBL)));
                Event event = new Event(
                        cursor.getInt(cursor.getColumnIndex(COLUMN_EVENTS_ID)),
                        cursor.getInt(cursor.getColumnIndex(COLUMN_EVENTS_MODEL_ID)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_EVENTS_MODEL_TBL)),
                        eventType,
                        cursor.getString(cursor.getColumnIndex(COLUMN_EVENTS_TITLE)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_EVENTS_DATE)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_EVENTS_TIME)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_EVENTS_DETAILS)));
                eventList.add(event);
            }while (cursor.moveToNext());
        }

        cursor.close();
        sqLiteDatabase.close();

        return eventList;
    }

    public Cursor getAllEventTypesSortedByName(){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(String.format(
                "SELECT %s as _id, %s, %s FROM %s ORDER BY %s ",
                COLUMN_EVENT_TYPE_ID, COLUMN_EVENT_TYPE_NAME, COLUMN_EVENT_TYPE_MODELTBL,
                TABLE_EVENT_TYPES, COLUMN_EVENT_TYPE_NAME), null);
        return  cursor;
    }

    public EventType getEventTypeById(Integer eventTypeId){
        EventType eventType = null;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        String query = String.format("SELECT * FROM %s WHERE %s = %d", TABLE_EVENT_TYPES, COLUMN_EVENT_TYPE_ID, eventTypeId);
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        if(cursor.moveToFirst()) {
            eventType = new EventType(cursor.getInt(cursor.getColumnIndex(COLUMN_EVENT_TYPE_ID)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_EVENT_TYPE_NAME)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_EVENT_TYPE_MODELTBL)));
        }
        sqLiteDatabase.close();
        return eventType;
    }

    public List<Resident> searchResidents(String searchString){
        List<Resident> residentList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        String query = String.format("SELECT * FROM %s " +
                "WHERE %s LIKE '%%s%' " +
                        "OR %s LIKE '%%s%' " +
                        "OR %s LIKE '%%s%'",
                TABLE_RESIDENTS,
                COLUMN_RESIDENTS_FIRSTNAME, searchString,
                COLUMN_RESIDENTS_LASTNAME, searchString,
                COLUMN_RESIDENTS_STUDENT_ID, searchString);
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);

        if(cursor.moveToFirst()) {
            do {
                residentList.add(new Resident(
                        cursor.getInt(cursor.getColumnIndex(COLUMN_RESIDENTS_ID)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_RESIDENTS_FIRSTNAME)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_RESIDENTS_LASTNAME)),
                        cursor.getInt(cursor.getColumnIndex(COLUMN_RESIDENTS_ROOM_NUMBER)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_RESIDENTS_ADDRESS_STREET)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_RESIDENTS_ADDRESS_CITY)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_RESIDENTS_ORIGIN_COUNTRY)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_RESIDENTS_MOBILE_NUMBER)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_RESIDENTS_EMAIL_ADDRESS)),
                        null,
                        cursor.getString(cursor.getColumnIndex(COLUMN_RESIDENTS_STUDENT_ID)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_RESIDENTS_PROGRAMME)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_RESIDENTS_MOVE_IN_DATE)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_RESIDENTS_MOVE_OUT_DATE)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_RESIDENTS_GENDER)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_RESIDENTS_BIRTHDATE))));
            }while (cursor.moveToNext());
        }
        cursor.close();
        sqLiteDatabase.close();
        return residentList;
    }

    public Cursor search(String tableName, String searchString){
        Log.d(TAG, "search: start with tablename [" + tableName + "] and searchString [" + searchString + "]");
        //List<FilterString> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = null;

        if(tableName.equalsIgnoreCase("Resident")){
            String query = String.format("SELECT 0 AS _id, '' AS _name UNION " +
                            "SELECT %s as _id, '%s' || ': ' || %s || ' ' || %s AS _name FROM %s " +
                            "WHERE %s LIKE '%s' " +
                            "OR %s LIKE '%s' " +
                            "OR %s LIKE '%s'",
                    COLUMN_RESIDENTS_ID, tableName, COLUMN_RESIDENTS_FIRSTNAME, COLUMN_RESIDENTS_LASTNAME,
                    TABLE_RESIDENTS,
                    COLUMN_RESIDENTS_FIRSTNAME, "%" + searchString + "%",
                    COLUMN_RESIDENTS_LASTNAME, "%" + searchString + "%",
                    COLUMN_RESIDENTS_STUDENT_ID, "%" + searchString + "%");
            Log.d(TAG, "search: query [" + query + "]");
            if(searchString != null ) {
                cursor = sqLiteDatabase.rawQuery(query, null);
            }else{
                cursor = sqLiteDatabase.rawQuery("", null);
            }
        }else if(tableName.equalsIgnoreCase("Personnel")){
            String query = String.format("SELECT 0 AS _id, '' AS _name UNION " +
                            "SELECT %s as _id, '%s' || ': ' || %s || ' ' || %s AS _name FROM %s " +
                            "WHERE %s LIKE '%s' " +
                            "OR %s LIKE '%s' " +
                            "OR %s LIKE '%s'",
                    COLUMN_PERSONNEL_ID, tableName, COLUMN_PERSONNEL_FIRSTNAME, COLUMN_PERSONNEL_LASTNAME,
                    TABLE_PERSONNEL,
                    COLUMN_PERSONNEL_FIRSTNAME, "%" + searchString + "%",
                    COLUMN_PERSONNEL_LASTNAME, "%" + searchString + "%",
                    COLUMN_PERSONNEL_EMPLOYEE_ID, "%" + searchString + "%");
            Log.d(TAG, "search: query [" + query + "]");
            if(searchString != null) {
                cursor = sqLiteDatabase.rawQuery(query, null);
            }else{
                cursor = sqLiteDatabase.rawQuery("", null);
            }
        }else if(tableName.equalsIgnoreCase("Equipment")){
            String query = String.format("SELECT 0 AS _id, '' AS _name UNION " +
                            "SELECT %s as _id, '%s' || ': ' || %s || ' ' || %s AS _name FROM %s " +
                            "WHERE %s LIKE '%s' " +
                            "OR %s LIKE '%s' " +
                            "OR %s LIKE '%s'",
                    COLUMN_EQUIPMENT_ID, tableName, COLUMN_EQUIPMENT_BRAND, COLUMN_EQUIPMENT_NAME,
                    TABLE_EQUIPMENT,
                    COLUMN_EQUIPMENT_NAME, "%" + searchString + "%",
                    COLUMN_EQUIPMENT_BRAND, "%" + searchString + "%",
                    COLUMN_EQUIPMENT_CODE, "%" + searchString + "%");
            Log.d(TAG, "search: query [" + query + "]");
            if(searchString != null) {
                cursor = sqLiteDatabase.rawQuery(query, null);
            }else{
                cursor = sqLiteDatabase.rawQuery("", null);
            }
        }

        // Log.d(TAG, "search: end with cursor count [" + cursor.getCount() + "]");
        return cursor;
    }

    public Long createEquipment(Equipment equipment) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_EQUIPMENT_CODE, equipment.getCode());
        contentValues.put(COLUMN_EQUIPMENT_NAME, equipment.getName());
        contentValues.put(COLUMN_EQUIPMENT_SERIAL_NUMBER, equipment.getSerialNumber());
        contentValues.put(COLUMN_EQUIPMENT_YEAR_PURCHASED, equipment.getYearPurchased());
        contentValues.put(COLUMN_EQUIPMENT_BRAND, equipment.getBrand());

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Long insertedRow = sqLiteDatabase.insert(TABLE_EQUIPMENT, null, contentValues);

        sqLiteDatabase.close();
        return insertedRow;
    }

    public Integer getUniqueEquipment(String code, String serialNumber, Integer id){

        Log.d(TAG, "getUniqueEquipment: equipment id -> " + id);
        String query = String.format("SELECT %s FROM %s WHERE (%s = '%s' OR %s = '%s') ",
                COLUMN_EQUIPMENT_ID, TABLE_EQUIPMENT,
                COLUMN_EQUIPMENT_CODE, code, COLUMN_EQUIPMENT_SERIAL_NUMBER, serialNumber);

        if(id != null){
            query += String.format(" AND %s != %d ", COLUMN_EQUIPMENT_ID, id);
        }

        Log.d(TAG, "getUniqueEquipment: query [" + query + "]");
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);

        Integer equipmentId = 0;
        if(cursor.moveToFirst()){
            equipmentId = cursor.getInt(0);
        }

        cursor.close();
        sqLiteDatabase.close();
        return equipmentId;
    }

    public List<Equipment> getAllEquipment(){
        List<Equipment> equipmentList = new ArrayList<>();
        String query = String.format("SELECT * FROM %s ORDER BY %s", TABLE_EQUIPMENT, COLUMN_EQUIPMENT_NAME);

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);

        if(cursor.moveToFirst()){
            do{
                equipmentList.add(new Equipment(
                        cursor.getInt(cursor.getColumnIndex(COLUMN_EQUIPMENT_ID)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_EQUIPMENT_CODE)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_EQUIPMENT_NAME)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_EQUIPMENT_SERIAL_NUMBER)),
                        cursor.getInt(cursor.getColumnIndex(COLUMN_EQUIPMENT_YEAR_PURCHASED)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_EQUIPMENT_BRAND))));
            }while(cursor.moveToNext());
        }

        cursor.close();
        sqLiteDatabase.close();

        return equipmentList;
    }

    public Equipment getEquipmentById(Integer equipmentId){
        Equipment equipment = null;
        String query = String.format("SELECT * FROM %s WHERE %s = %d", TABLE_EQUIPMENT, COLUMN_EQUIPMENT_ID, equipmentId);

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);

        if(cursor.moveToFirst()){
            equipment = new Equipment(
                cursor.getInt(cursor.getColumnIndex(COLUMN_EQUIPMENT_ID)),
                cursor.getString(cursor.getColumnIndex(COLUMN_EQUIPMENT_CODE)),
                cursor.getString(cursor.getColumnIndex(COLUMN_EQUIPMENT_NAME)),
                cursor.getString(cursor.getColumnIndex(COLUMN_EQUIPMENT_SERIAL_NUMBER)),
                cursor.getInt(cursor.getColumnIndex(COLUMN_EQUIPMENT_YEAR_PURCHASED)),
                cursor.getString(cursor.getColumnIndex(COLUMN_EQUIPMENT_BRAND)));
        }

        cursor.close();
        sqLiteDatabase.close();

        return equipment;
    }

    public Integer updateEquipment(Equipment equipment){
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_EQUIPMENT_CODE, equipment.getCode());
        contentValues.put(COLUMN_EQUIPMENT_NAME, equipment.getName());
        contentValues.put(COLUMN_EQUIPMENT_SERIAL_NUMBER, equipment.getSerialNumber());
        contentValues.put(COLUMN_EQUIPMENT_YEAR_PURCHASED, equipment.getYearPurchased());
        contentValues.put(COLUMN_EQUIPMENT_BRAND, equipment.getBrand());

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Integer updatedRows = sqLiteDatabase.update(TABLE_EQUIPMENT, contentValues, COLUMN_EQUIPMENT_ID + " = ? ",
                new String[]{ Integer.toString(equipment.getId()) });

        sqLiteDatabase.close();
        return updatedRows;
    }

    public List<Event> getEventsByDateForHome(){
        List<Event> eventList = new ArrayList<>();

        TimeZone timeZone = TimeZone.getTimeZone("GMT+13");
        Date today = new Date(Calendar.getInstance(timeZone).getTimeInMillis());
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Log.d(TAG, "getEventsByDateForHome: get events for date [" + dateFormat.format(today) + "]");
        /*
        SELECT * FROM tbEvents where eventDate = '2018-10-26'
         */

        String query = String.format("SELECT * FROM %s AS e JOIN %s AS t ON t.%s = e.%s " +
                "WHERE e.%s = '%s'", TABLE_EVENTS, TABLE_EVENT_TYPES,
                COLUMN_EVENT_TYPE_ID, COLUMN_EVENTS_TYPE_ID, COLUMN_EVENTS_DATE, dateFormat.format(today.getTime()));

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);

        if(cursor.moveToFirst()){
            do {
                EventType eventType = new EventType(cursor.getInt(cursor.getColumnIndex(COLUMN_EVENT_TYPE_ID)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_EVENT_TYPE_NAME)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_EVENT_TYPE_MODELTBL)));
                eventList.add(new Event(
                        cursor.getInt(cursor.getColumnIndex(COLUMN_EVENTS_ID)),
                        cursor.getInt(cursor.getColumnIndex(COLUMN_EVENTS_MODEL_ID)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_EVENTS_MODEL_TBL)),
                        eventType,
                        cursor.getString(cursor.getColumnIndex(COLUMN_EVENTS_TITLE)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_EVENTS_DATE)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_EVENTS_TIME)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_EVENTS_DETAILS))));
            }while (cursor.moveToNext());
        }

        cursor.close();
        sqLiteDatabase.close();

        return eventList;
    }
}

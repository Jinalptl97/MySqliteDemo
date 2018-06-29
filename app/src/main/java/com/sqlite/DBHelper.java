package com.sqlite;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.ViewPager;

import java.io.File;
import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    public static Context context;
    public static SQLiteDatabase db = null;
    private static final String DB_PATH_SUFFIX = "/databases/";
    private static final String DATABASE_NAME = "chat.db";
    NotificationManager mNotifyManager;
    NotificationCompat.InboxStyle inboxStyle;
    ArrayList<Model_user> chatBeanArrayList;
    ArrayList<Model_user> chatBeanArrayList1;
    Model_user chatBean;
    ViewPager mViewPager;

    NotificationCompat.Builder builder;

    public DBHelper(Context context) {
        super(context, "chat.db", null, 33);
        this.context = context;
        chatBeanArrayList = new ArrayList<Model_user>();
        chatBeanArrayList1 = new ArrayList<Model_user>();

    }


    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private static void copyDataBase() {

        try {
//            // Open your local db as the input stream
//            InputStream myInput = context.getAssets().open(DATABASE_NAME);
//            // Path to the just created empty db
//            String outFileName = getDatabasePath();
//
//            // if the path doesn't exist first, create it
//            File f = new File(context.getApplicationInfo().dataDir
//                    + DB_PATH_SUFFIX);
//            if (!f.exists())
//                f.mkdir();
//
//            // Open the empty db as the output stream
//            OutputStream myOutput = new FileOutputStream(outFileName);
//
//            // transfer bytes from the inputfile to the outputfile
//            byte[] buffer = new byte[1024];
//            int length;
//            while ((length = myInput.read(buffer)) > 0) {
//                myOutput.write(buffer, 0, length);
//            }
//
//            // Close the streams
//            myOutput.flush();
//            myOutput.close();
//            myInput.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static boolean checkDatabase() {
        SQLiteDatabase checkDB = null;

        checkDB = null;
        try {
            try {
                String myPath = getDatabasePath();
                File file = new File(myPath);
                if (file.exists() && !file.isDirectory()) {
                    checkDB = SQLiteDatabase.openDatabase(myPath, null,
                            SQLiteDatabase.OPEN_READONLY);
                    checkDB.close();
                }
            } catch (Exception e) {
            }
        } catch (Throwable ex) {
        }
        return checkDB != null ? true : false;
    }

    private static String getDatabasePath() {
        return context.getApplicationInfo().dataDir + DB_PATH_SUFFIX
                + DATABASE_NAME;
    }

    public void execute(String statment) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            System.out.println("===statment===" + statment);
            db.execSQL(statment);
        } catch (Exception e) {
            /* FirebaseCrash.report(new Exception(e.toString()));*/
            System.out.println(e);
        } finally {
            db.close();
            db = null;
        }
    }


    public static String getDBStr(String str) {

        str = str != null ? str.replaceAll("'", "''") : null;
        str = str != null ? str.replaceAll("&#039;", "''") : null;
        str = str != null ? str.replaceAll("&amp;", "&") : null;

        return str;

    }

    public void upgrade() {

        doUpdate1();
    }

    private void doUpdate1() {

        this.execute("CREATE TABLE del_list (id INTEGER PRIMARY KEY AUTOINCREMENT,name TEXT NOT NULL)");

        this.execute("CREATE TABLE order_list (id INTEGER PRIMARY KEY AUTOINCREMENT,orderno TEXT NOT NULL,orderdate TEXT NOT NULL,name TEXT NOT NULL,amount TEXT NOT NULL)");

        //this.execute("CREATE TABLE message (id INTEGER PRIMARY KEY AUTOINCREMENT,message TEXT NOT NULL,time TEXT NOT NULL,is_read TEXT NOT NULL,image TEXT NOT NULL,user_id INTEGER NOT NULL)");


    }


    public void insert(Model_user chatBean) {
        String sql = null;

        try {
            sql = "INSERT INTO del_list (name) VALUES ('" + DBHelper.getDBStr(chatBean.name) + "')";

            execute(sql);
            // CreateNotification();


        } catch (Exception e) {
            // Log.error(this.getClass() + " :: insert()", e);

        } finally {

            sql = null;

            System.gc();
        }

    }

    public void insertt(Model_order order) {
        String sql = null;

        sql = "INSERT INTO order_list(orderno,orderdate,name,amount) VALUES ('" + DBHelper.getDBStr(order.order_no) + "','" + DBHelper.getDBStr(order.order_date) + "','" + DBHelper.getDBStr(order.fname) + "','" + DBHelper.getDBStr(order.amount) + "')";

        execute(sql);

    }

    public ArrayList<Model_user> getChatBeanArrayList() {

        String sql = "";
        Cursor cursor = null;
        ArrayList<Model_user> arrayList = new ArrayList<>();


        sql = "SELECT * FROM del_list";

        cursor = query(sql);

        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                Model_user user = new Model_user();
                user.id = cursor.getString(0);
                user.name = cursor.getString(1);
                arrayList.add(user);
            }
        }

        return arrayList;
    }


    public ArrayList<Model_order> getorderlist() {
        String sql = "";

        Cursor cursor = null;
        ArrayList<Model_order> arrayList = new ArrayList<>();

        sql = "SELECT * FROM order_list";

        cursor = query(sql);

        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                Model_order model_order = new Model_order();
                model_order.order_no = cursor.getString(0);
                model_order.order_date = cursor.getString(1);
                model_order.order_date = cursor.getString(2);
                model_order.amount = cursor.getString(3);
                arrayList.add(model_order);
            }
        }


        return arrayList;
    }

    public Cursor query(String value) {

        Cursor cursor = null;

        SQLiteDatabase db = this.getReadableDatabase();
        cursor = db.rawQuery(value, null);
        cursor.moveToPosition(-1);


        db.close();


        return cursor;

    }

    //    public void update(ChatBean chatBean) {
//
//        String sql = null;
//        try {
//
//            sql = "UPDATE message SET is_read = '" + chatBean.is_read + "' WHERE id = " + chatBean.id;
//            execute(sql);
//
//        } catch (Exception e) {
//            // Log.error(this.getClass() + " :: update()", e);
//
//        } finally {
//
//            // release
//
//            sql = null;
//            System.gc();
//        }
//
//
//    }


//    public void CreateNotification() {
//
//        mNotifyManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
//        chatBeanArrayList = getListdata();
//        Intent intent = new Intent(context, MainActivity.class);
//        System.out.println("inside new task");
//        PendingIntent pendingIntent = PendingIntent.getActivity(context, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//        builder.setContentIntent(pendingIntent);
//        builder.setContentTitle("unread message").setSmallIcon(R.drawable.ic_fab_add);
//        inboxStyle = new NotificationCompat.InboxStyle();
//        inboxStyle.setBigContentTitle("Conversation:");
//        inboxStyle.setSummaryText("unread message" + chatBeanArrayList.size());
//
//        if (chatBeanArrayList != null && chatBeanArrayList.size() > 0) {
//            for (int i = (chatBeanArrayList.size() - 1); i > Math.max(chatBeanArrayList.size() - 6, 0); i--) {
//                inboxStyle.addLine(chatBeanArrayList.get(i - 1).message);
//                System.out.println("**********notificationBeanArrayList.get(i).message******" + chatBeanArrayList.get(i).message);
//            }
//
//            builder.setStyle(inboxStyle);
//            mNotifyManager.notify(1, builder.build());
//
//
//            BadgeUtils.setBadge(context, chatBeanArrayList.size());
//
//
//        }
//
//    }

    public void delete(String str) {


        Cursor cursor = null;
        String sql = null;


        try {
            String strdelete1 = "delete from message where id='" + str + " '";

            String strdelete = "delete from message where Id in (" + str + ")";
            System.out.println("=============query=============" + strdelete);

            cursor = query(strdelete);

        } catch (Exception e) {
            System.out.println("==========Exception=====" + e.toString());

        } finally {
            if (cursor != null && !cursor.isClosed())
                cursor.close();

            // release

            sql = null;
            cursor = null;

            System.gc();
        }


    }
}


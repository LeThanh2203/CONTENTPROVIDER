package com.example.contentprovider;

import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CallLog;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Date;

public class Calllog_Layout extends AppCompatActivity {

    private final int REQUEST_CALL_LOG_PERMISSIONS = 1001;

    ListView callLogListView;
    ArrayList<Calllog> callLogList;
    ArrayAdapter<Calllog> callLogAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_calllog);
        addControls();

        if (checkSelfPermission(android.Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{android.Manifest.permission.READ_CALL_LOG}, REQUEST_CALL_LOG_PERMISSIONS);
        } else {
            showAllCallLogsFromDevice();
        }
    }

    private void showAllCallLogsFromDevice() {
        Uri uri = CallLog.Calls.CONTENT_URI;
        Cursor cursor = null;

        try {
            cursor = getContentResolver().query(uri, null, null, null, null);
            if (cursor != null) {
                callLogList.clear();
                while (cursor.moveToNext()) {
                    // Retrieve column indices
                    int viTriSoDienThoai = cursor.getColumnIndex(CallLog.Calls.NUMBER);
                    int viTriLoaiCuocGoi = cursor.getColumnIndex(CallLog.Calls.TYPE);
                    int viTriNgayGoi = cursor.getColumnIndex(CallLog.Calls.DATE);
                    int viTriThoiLuong = cursor.getColumnIndex(CallLog.Calls.DURATION);

                    // Check if indices are valid
                    if (viTriSoDienThoai != -1 && viTriLoaiCuocGoi != -1 && viTriNgayGoi != -1 && viTriThoiLuong != -1) {
                        String soDienThoai = cursor.getString(viTriSoDienThoai);
                        String loaiCuocGoi = cursor.getString(viTriLoaiCuocGoi);
                        long ngayGoiMillis = cursor.getLong(viTriNgayGoi); // Get the date as a long
                        Date ngayGoi = new Date(ngayGoiMillis); // Convert to Date
                        String thoiLuong = cursor.getString(viTriThoiLuong);

                        Calllog callLogEntry = new Calllog(soDienThoai, loaiCuocGoi, ngayGoi, thoiLuong); // Pass Date object
                        callLogList.add(callLogEntry);
                    }
                }
                callLogAdapter.notifyDataSetChanged();
            }
        } finally {
            if (cursor != null) {
                cursor.close(); // Ensure cursor is closed
            }
        }
    }

    private void addControls() {
        callLogListView = findViewById(R.id.lvNkiCgoi);
        callLogList = new ArrayList<>();
        callLogAdapter = new ArrayAdapter<>(Calllog_Layout.this, android.R.layout.simple_list_item_1, callLogList);
        callLogListView.setAdapter(callLogAdapter);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CALL_LOG_PERMISSIONS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                showAllCallLogsFromDevice();
            } else {
                // Notify the user that the permission was denied
            }
        }
    }
}

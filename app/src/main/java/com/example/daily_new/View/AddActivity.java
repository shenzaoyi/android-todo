package com.example.daily_new.View;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.daily_new.Controllers.AddController;
import com.example.daily_new.DAO.Event;
import com.example.daily_new.R;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

public class AddActivity extends AppCompatActivity {
    private String TAG = "ERROR_ADD";
    private AddController addController;
    public void  datePicker() {
        Button dateButton = findViewById(R.id.add_date);
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 获取当前日期
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                // 创建日期选择器对话框
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                String selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
                                dateButton.setText(selectedDate);
                            }
                        }, year, month, day);
                // 显示日期选择器对话框
                datePickerDialog.show();
            }
        });
    }
    public void timePicker() {
        Button timepicker = findViewById(R.id.add_time);
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        timepicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(AddActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                // 处理用户选择的时间
                                String selectedTime = hourOfDay + ":" + minute;
                                timepicker.setText(selectedTime);
                            }
                        }, hour, minute, true); // true 表示24小时制
                    // 显示时间选择器对话框
                    timePickerDialog.show();
            }
        });

    }
    public Event handleData() {
        EditText addTitle = findViewById(R.id.add_title);
        String title = String.valueOf(addTitle.getText());
        CheckBox checkBox = findViewById(R.id.im);
        boolean im = false;
        if (checkBox.isChecked()) {
            im = true;
        }
        // 无法解析： 报错，tobe continued
        Button addDate = findViewById(R.id.add_date);
        LocalDate localDate = null;
        DateTimeFormatter dateTimeFormatter = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        }
        String dateTime = (String) addDate.getText();
        String[] dates = dateTime.split("/");
        String day = dates[0];
        String month = dates[1];
        if (day.length() != 2) {
            day = "0" + day;
        }
        day += "/";
        if (month.length() != 2) {
            month = "0" + month;
        }
        month += "/";
        String newData = day + month + dates[2];
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            localDate = LocalDate.parse(newData,dateTimeFormatter);
        }
        Button addTime = findViewById(R.id.add_time);
        LocalTime localTime  = null;
        String Time = (String) addTime.getText();
        String[] times = Time.split(":");
        String m = "";
        String h = "";
        if (times[0].length() != 2) {
            m = "0" + times[0];
        }else{
            m = times[0];
        }
        if (times[1].length() != 2) {
            h = "0" + times[1];
        }else{
            h = times[1];
        }
        String timeNew = m + ":" + h;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            localTime = LocalTime.parse(timeNew);
        }
        LocalDateTime localDateTime = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            localDateTime = LocalDateTime.of(localDate,localTime);
        }
        Long exactTime = Long.valueOf(0);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            exactTime = localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        }
        TextView addcontent = findViewById(R.id.add_content);
        Event event = new Event(title,exactTime,im,addcontent.getText().toString());
        if (event.isImportant()) {
            addController.setAlarm(event.getEndtime());
        }
        return event;
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        // init
        addController = new AddController(AddActivity.this);
        super.onCreate(savedInstanceState);
        // set header style
        setContentView(R.layout.add_event);
        TextView tx = findViewById(R.id.add_header);
        tx.setTextSize(TypedValue.COMPLEX_UNIT_PX,150);
        // date & time
        datePicker();
        timePicker();
        // handle value
        Button submit = findViewById(R.id.add_submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Event data = handleData();
                addController.Store(data);
                Toast.makeText(AddActivity.this,"添加成功",Toast.LENGTH_SHORT).show();
            }
        });
    }
}

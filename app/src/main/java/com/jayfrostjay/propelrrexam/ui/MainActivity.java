package com.jayfrostjay.propelrrexam.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputLayout;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import com.jayfrostjay.propelrrexam.R;
import com.jayfrostjay.propelrrexam.api.ApiCallback;
import com.jayfrostjay.propelrrexam.manager.ApiManager;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    private static String LOG_TAG = MainActivity.class.getSimpleName();
    private PhoneNumberUtil phoneNumberUtils = PhoneNumberUtil.getInstance();
    private ApiManager apiManager;

    private TextView fullName;
    private TextView emailAddress;
    private TextView mobileNumber;
    private TextView dateOfBirth;
    private TextView age;
    private Button submit;
    private Context mContext;
    private AlertDialog mAlertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        apiManager = new ApiManager();
        mContext = getApplicationContext();

        initListeners();
    }

    private void initListeners(){
        fullName = findViewById(R.id.fullname);
        emailAddress = findViewById(R.id.emailaddress);
        mobileNumber = findViewById(R.id.mobilenumber);
        dateOfBirth = findViewById(R.id.dateofbirth);
        age = findViewById(R.id.age);
        submit = findViewById(R.id.submit);

        emailAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.e(LOG_TAG, s.toString());
                if( !isValidEmail(s.toString()) ){
                    emailAddress.setError("Not valid email address");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mobileNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.e(LOG_TAG, s.toString());
                if( !isValidPhoneNumber(s.toString()) ){
                    mobileNumber.setError("Not valid PH phone number");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        dateOfBirth.setOnClickListener(v -> {
            MaterialDatePicker.Builder<Long> builder = MaterialDatePicker.Builder.datePicker();
            builder.setTitleText(R.string.date_of_birth);
            MaterialDatePicker<Long> picker = builder.setTheme(R.style.CustomThemeOverlay_MaterialCalendar_Fullscreen).build();
            picker.show(getSupportFragmentManager(), picker.toString());

            picker.addOnPositiveButtonClickListener(selection -> {
                Date rawDateSelected = getRawDateSelected(selection);
                String dateSelected = getDateSelected(rawDateSelected);
                int currentAge = getCurrentAge(rawDateSelected);
                if( currentAge >= 18 ){
                    dateOfBirth.setText(dateSelected);
                    changeAgeContent(String.valueOf(currentAge));
                }else{
                    createDialog("Age Input Error", "Age must be 18 years old and above");
                }
            });
        });

        submit.setOnClickListener(v -> {
            apiManager.executeApiCall(new ApiCallback() {
                @Override
                public void onSuccess(String data) {createDialog("Api Call Result", data); }

                @Override
                public void onFailure(String data) {
                    createDialog("Api Call Result", data);
                }
            });
        });
    }

    private Date getRawDateSelected(Long selected){
        TimeZone timeZoneUTC = TimeZone.getDefault();
        int offsetFromUTC = timeZoneUTC.getOffset(new Date().getTime()) * -1;
        return new Date(selected + offsetFromUTC);
    }

    private String getDateSelected(Date date){
        SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return simpleFormat.format(date);
    }

    private int getCurrentAge(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return generateAge(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
    }

    public void changeAgeContent(String ageContent){
        age.setText(ageContent);
    }

    private boolean isValidEmail(CharSequence email){
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private boolean isValidPhoneNumber(CharSequence phone){
        try {
            Phonenumber.PhoneNumber phPhoneNumber = phoneNumberUtils.parse(phone.toString(), "PH");
            return true;
        }catch (NumberParseException e){
            return false;
        }
    }

    private int generateAge(int year, int month, int day){
        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();
        dob.set(year, month, day);
        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);
        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)){
            age--;
        }

        Log.e("TESTING", String.valueOf(age));
        return age;
    }

    private void createDialog(String title, String text){
        mAlertDialog = new AlertDialog.Builder(MainActivity.this)
                .setTitle(title)
                .setMessage(text)
                .setPositiveButton("Ok", null)
                .show();
    }

}

package com.example.ontimetourismrecommender;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.time.LocalDate;
import java.time.Period;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_u_p);


        //for displaying the spiner maege // the drop down menu
        Spinner maregStatSpiner = (Spinner) findViewById(R.id.spinnerMareg);
        ArrayAdapter<String> maregstatAdapter = new ArrayAdapter<String>(MainActivity.this
                , android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.MaritalStatus));
        maregstatAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        maregStatSpiner.setAdapter(maregstatAdapter);


        // for displaying the spiner amiunt // the drop down menu
        Spinner AmountSpiner = (Spinner) findViewById(R.id.spinnerAmount);
        ArrayAdapter<String> AmountAdapter = new ArrayAdapter<String>(MainActivity.this
                , android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.AmountOfSpending));
        AmountAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        AmountSpiner.setAdapter(AmountAdapter);


        //for displaying the spiner education // the drop down menu
        Spinner EducationSpiner = (Spinner) findViewById(R.id.spinnerEducation);
        ArrayAdapter<String> EducationAdapter = new ArrayAdapter<String>(MainActivity.this
                , android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.EducationLevel));
        EducationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        EducationSpiner.setAdapter(EducationAdapter);


        //for displaying the spiner Weather // the drop down menu
        Spinner WeatherSpiner = (Spinner) findViewById(R.id.spinnerWeather);
        ArrayAdapter<String> WeatherAdapter = new ArrayAdapter<String>(MainActivity.this
                , android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.PreferdWather));
        WeatherAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        WeatherSpiner.setAdapter(WeatherAdapter);


        //for displaying the spiner Wondring As // the drop down menu
        Spinner WondringAsSpiner = (Spinner) findViewById(R.id.spinnerWondringAs);
        ArrayAdapter<String> WondringAsAdapter = new ArrayAdapter<String>(MainActivity.this
                , android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.WandringAs));
        WondringAsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        WondringAsSpiner.setAdapter(WondringAsAdapter);


        //----------------------------------BUTTON Sign up---------------------------------------
        Button button = (Button) findViewById(R.id.SignUp);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//-------------------------------------------------------------------------------text enterd attrbuts


                //to get user FULL NAME
                TextInputLayout UserFullName = findViewById(R.id.USERFullName);
                String UserFullNameToBeStoredinDB = UserFullName.getEditText().getText().toString();

                //to get User Name
                TextInputLayout UserName = findViewById(R.id.USERname);
                String UserNameToBeStoredinDB = UserName.getEditText().getText().toString();


                // to get user phone
                TextInputLayout UserPhoneNo = findViewById(R.id.UserPhone);
                String UserPhoneNumToBeStoredinDB = UserPhoneNo.getEditText().getText().toString();
                //Toast.makeText(UserPhoneNo.getContext(),UserPhoneNumToBeStoredinDB, Toast.LENGTH_SHORT).show();

                // to get user passowrd
                TextInputLayout UserPassowrd = findViewById(R.id.USERPass);
                String UserPassowrdToBeStoredinDB = UserPassowrd.getEditText().getText().toString();


                //to get user City
                TextInputLayout UserCity = findViewById(R.id.USERCity);
                String UserCityToBeStoredinDB = UserCity.getEditText().getText().toString();
                //Toast.makeText(UserCity.getContext(),UserCityToBeStoredinDB, Toast.LENGTH_SHORT).show();


                //to get user birth date and fined their age
                TextInputLayout UserAge = findViewById(R.id.USERAge);
                int UserAgeToBeStoredinDB = Integer.parseInt(UserAge.getEditText().getText().toString());
                //Toast.makeText(UserAge.getContext()," "+UserAgeToBeStoredinDB, Toast.LENGTH_SHORT).show();

//---------------------------------------------------------------------------------------------


//-------------------------------------------------------------------------------------- selected attribut

                //  To GET THE selected marage ansower from the spiner
                String str1 = maregStatSpiner.getSelectedItem().toString();
                //Toast.makeText(maregstatAdapter.getContext(),str1, Toast.LENGTH_SHORT).show();

                //  To GET THE selected amount ansower from the spiner
                String str2 = AmountSpiner.getSelectedItem().toString();
                //Toast.makeText(AmountAdapter.getContext(),str2, Toast.LENGTH_SHORT).show();

                //To GET THE selected education level ansower from the spiner
                String str3 = EducationSpiner.getSelectedItem().toString();
                //Toast.makeText(EducationAdapter.getContext(),str3, Toast.LENGTH_SHORT).show();


                //To GET THE selected Weather ansower from the spiner
                String str4 = WeatherSpiner.getSelectedItem().toString();
                //Toast.makeText(WeatherAdapter.getContext(),str4, Toast.LENGTH_SHORT).show();

                //To GET THE selected Wandring As ansower from the spiner
                String str5 = WondringAsSpiner.getSelectedItem().toString();
                //Toast.makeText(WondringAsAdapter.getContext(),str5, Toast.LENGTH_SHORT).show();

//---------------------------------------------------------------------------------------------------------


                boolean FirstValidation = VaildateItNotEmptyandUnder10Char(UserNameToBeStoredinDB, UserName);
                boolean SecondValidation = VaildateItNotEmptyandUnder10Char(UserPassowrdToBeStoredinDB, UserPassowrd);
                boolean ThredValidation = VaildateItNotEmptyandUnder10Char(UserPhoneNumToBeStoredinDB, UserPhoneNo);
                boolean FourthValidation = VaildateItNotEmptyandUnder10Char(UserCityToBeStoredinDB, UserCity);
                boolean FifthValidation = VaildatForUnder20CharAndNotEmpty(UserFullNameToBeStoredinDB, UserFullName);
                boolean SeventhValidation = VaildatOnlyHaveLetter(UserFullNameToBeStoredinDB, UserFullName);
                boolean EigthValidation = VaildatOnlyHaveLetter(UserCityToBeStoredinDB, UserCity);
                boolean NineVildate = vildateAge(UserAgeToBeStoredinDB, UserAge);

                //After vildation we can enter into DB--------------------------------------------
                if (FirstValidation && SecondValidation && ThredValidation && FourthValidation
                        && FifthValidation && SeventhValidation && EigthValidation && NineVildate ) {


                    Enter_intoDB(UserFullNameToBeStoredinDB,
                            UserNameToBeStoredinDB, UserPhoneNumToBeStoredinDB, UserPassowrdToBeStoredinDB
                            , UserCityToBeStoredinDB, UserAgeToBeStoredinDB, str1, str2, str3, str4, str5);

                } else {

                    Toast.makeText(getApplicationContext(), "Can not complete the registration ", Toast.LENGTH_LONG).show();
                }

                //----------------------------------------------------------------------------------------------



            }
        });


    }

    // vialdate to must not be empty or not over 10 char--- for  user name ,pass ,phone, city
    private boolean VaildateItNotEmptyandUnder10Char(String Value, TextInputLayout TobeUsedInSittingError) {

        if (Value.isEmpty()) {
            TobeUsedInSittingError.setError("Field cannot be empty");
            return false;
        } else if (Value.length() > 10) {
            TobeUsedInSittingError.setError("Only enter 10 character Pleas");
            return false;
        } else {
            TobeUsedInSittingError.setError(null);
            TobeUsedInSittingError.setErrorEnabled(false);
            return true;
        }


    }

    //vildate to must consist with letters only--- for city and full name
    private boolean VaildatOnlyHaveLetter(String Value, TextInputLayout TobeUsedInSittingError) {

        if (!Value.matches("^[a-zA-Z]*$")) {
            TobeUsedInSittingError.setError("Only enter letters pleas");
            return false;
        } else {
            TobeUsedInSittingError.setError(null);
            TobeUsedInSittingError.setErrorEnabled(false);
            return true;
        }


    }

    //vildat for under 20 char And Not Empty--- for FULL name
    private boolean VaildatForUnder20CharAndNotEmpty(String Value, TextInputLayout TobeUsedInSittingError) {

        if (Value.isEmpty()) {
            TobeUsedInSittingError.setError("Field cannot be empty");
            return false;
        } else if (Value.length() >= 20) {
            TobeUsedInSittingError.setError("Only enter 20 character Pleas");
            return false;
        } else {
            TobeUsedInSittingError.setError(null);
            TobeUsedInSittingError.setErrorEnabled(false);
            return true;
        }

    }

    // age must be over 1 and under 100 numbers
    private boolean vildateAge(int Value, TextInputLayout TobeUsedInSittingError){

        if (Value==0) {
            TobeUsedInSittingError.setError("You can not be 0 yer old");
            return false;
        } else if (Value >= 100) {
            TobeUsedInSittingError.setError("You can not be over 100!");
            return false;
        } else {
            TobeUsedInSittingError.setError(null);
            TobeUsedInSittingError.setErrorEnabled(false);
            return true;
        }


    }


    public void Enter_intoDB(String UserFullNameToBeStoredinDB, String UserNameToBeStoredinDB,String UserPhoneNumToBeStoredinDB,String UserPassowrdToBeStoredinDB
            ,String UserCityToBeStoredinDB,int UserAgeToBeStoredinDB
            ,String str1,String str2,String str3,String str4,String str5){




        //------------اكتبي كود الخاص با الادخال هنا ----------------------------------

        Toast.makeText(getApplicationContext(), "Registration compleat welcom", Toast.LENGTH_SHORT).show();




    }





}
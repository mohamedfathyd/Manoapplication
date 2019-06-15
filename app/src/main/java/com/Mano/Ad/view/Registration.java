package com.Mano.Ad.view;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.Mano.Ad.R;
import com.Mano.Ad.model.Apiclient_home;
import com.Mano.Ad.model.apiinterface_home;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.hbb20.CountryCodePicker;

import java.util.concurrent.TimeUnit;


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Registration extends AppCompatActivity {
    TextInputLayout textInputLayoutname,textInputLayoutaddress,textInputLayoutphone,textInputLayoutcountry,textInputLayoutage,
            textInputLayoutpassword,textInputLayoutconfirmpassword,textInputLayoutr;
    TextInputEditText textInputEditTextname,textInputEditTextaddress,textInputEditTextphone,textInputEditTextcountry,textInputEditTextage,
            textInputEditTextpassword,textInputEditTextconfirmpassword,textInputEditTextr;
    AppCompatButton regesiter;
    AppCompatTextView openlogin;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    Call<ResponseBody> call = null;
    String code,mVerificationId;
    ProgressDialog progressDialog;
    ProgressDialog progressDialog1;
    login_ login_;
    String codee =null;
    private FirebaseAuth mAuth;
    private apiinterface_home apiinterface;
    CountryCodePicker ccp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);


        inisialize();

        openlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Registration.this,LoginActivity.class));
            }
        });
        regesiter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                regesiter.setClickable(false);
                if (textInputEditTextaddress.getText().toString().equals("") || textInputEditTextaddress.getText().toString() == null) {

                    textInputLayoutaddress.setError("أدخل البريد الألكترونى");

                } else if (textInputEditTextname.getText().toString().equals("") || textInputEditTextname.getText().toString() == null) {

                    textInputLayoutname.setError("أدخل اسم المستخدم");

                } else if (textInputEditTextphone.getText().toString().equals("") || textInputEditTextphone.getText().toString() == null) {

                    textInputLayoutphone.setError("أدخل رقم الموبيل");

                } else if (textInputEditTextpassword.getText().toString().equals("") || textInputEditTextpassword.getText().toString() == null) {

                    textInputLayoutpassword.setError("أدخل كلمة المرور");

                } else if (textInputEditTextconfirmpassword.getText().toString().equals("") || textInputEditTextconfirmpassword.getText().toString() == null) {

                    textInputLayoutconfirmpassword.setError("أدخل  تأكيد كلمة المرور");

                } else if (!textInputEditTextconfirmpassword.getText().toString().equals(textInputEditTextpassword.getText().toString())) {
                    textInputLayoutconfirmpassword.setError("كلمة تأكيد مختلفة");
                } else {
                    String phone="+2"+textInputEditTextphone.getText().toString();
                    //  Toast.makeText(Registration.this,"a",Toast.LENGTH_LONG).show();
                    PhoneAuthProvider.getInstance().verifyPhoneNumber(
                          phone,        // Phone number to verify
                            60,                 // Timeout duration
                            TimeUnit.SECONDS,   // Unit of timeout
                            Registration.this,               // Activity (for callback binding)
                            mCallbacks);

                    progressDialog1 = ProgressDialog.show(Registration.this, "انتظر قليلا للتاكد من صحة البيانات", "Please wait...", false, false);
                    progressDialog1.show();

                    // fetchInfo();
                }
            }
        });
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {

            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.


                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                    // ...
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                    // ...
                }

                // Show a message and update the UI
                // ...
            }

            @Override
            public void onCodeSent(final String verificationId,
                                   PhoneAuthProvider.ForceResendingToken token) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                progressDialog1.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(Registration.this);
                builder.setTitle("أدخل كود الذى وصلك فى رسالة نصية :");

// Set up the input
                final EditText input = new EditText(Registration.this);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                builder.setView(input);

// Set up the buttons
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        code = input.getText().toString();
                        mVerificationId = verificationId;
                        verifyVerificationCode(code);

                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();


                // Save verification ID and resending token so we can use them later

                // ...
            }
        };

    }

    private void verifyVerificationCode(String code) {
        //creating the credential
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, code);

        //signing the user
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(Registration.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            fetchInfo();
                        } else {

                            //verification unsuccessful.. display an error message
                            Toast.makeText(Registration.this,"كود التاكيد خاطئ",Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }
    public void fetchInfo() {
        progressDialog = ProgressDialog.show(Registration.this, "جاري انشاء الحساب", "Please wait...", false, false);
        progressDialog.show();
        String phone="+2"+textInputEditTextphone.getText().toString();
        int x=0;
      //  int x= Integer.parseInt(textInputEditTextr.getText().toString());
        if(textInputEditTextr.getText().toString().equals("")||textInputEditTextr.getText().toString()==null){
            x=0;
        }
        else{
            x= Integer.parseInt(textInputEditTextr.getText().toString());
        }
        apiinterface = Apiclient_home.getapiClient().create(apiinterface_home.class);
        Call<ResponseBody> call = apiinterface.getcontacts_newaccount(textInputEditTextname.getText().toString(),
                textInputEditTextpassword.getText().toString(), textInputEditTextaddress.getText().toString()
                ,phone,textInputEditTextcountry.getText().toString(), Integer.parseInt(textInputEditTextage.getText().toString()),
               x);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressDialog.dismiss();
            AlertDialog.Builder dlgAlert = new AlertDialog.Builder(Registration.this);
                dlgAlert.setMessage("تم أنشاء حساب جديد بنجاح");
                dlgAlert.setTitle("مشوارى");
                dlgAlert.setPositiveButton("OK", null);
                dlgAlert.setCancelable(true);
                dlgAlert.create().show();
                login_=new login_();
                String phone="+2"+textInputEditTextphone.getText().toString();
                login_.fetchInfo(Registration.this,phone,textInputEditTextpassword.getText().toString());
                login_.fetchInfo_share(Registration.this, Integer.parseInt(textInputEditTextr.getText().toString()));
                finish();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(Registration.this, t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void inisialize() {
        textInputLayoutname = (TextInputLayout) findViewById(R.id.textInputLayoutName);
        textInputLayoutaddress = (TextInputLayout) findViewById(R.id.textInputLayoutaddress);
        textInputLayoutphone = (TextInputLayout) findViewById(R.id.textInputLayoutphone);
        textInputLayoutpassword = (TextInputLayout) findViewById(R.id.textInputLayoutPassword);
        textInputLayoutcountry = (TextInputLayout) findViewById(R.id.textInputLayoutcountry);
        textInputLayoutr = (TextInputLayout) findViewById(R.id.textInputLayoutr);
        textInputLayoutage = (TextInputLayout) findViewById(R.id.textInputLayoutage);
        textInputLayoutconfirmpassword = (TextInputLayout) findViewById(R.id.textInputLayoutConfirmPassword);
        textInputEditTextname = (TextInputEditText) findViewById(R.id.textInputEditTextName);
        textInputEditTextphone = (TextInputEditText) findViewById(R.id.textInputEditTextphone);
        textInputEditTextaddress = (TextInputEditText) findViewById(R.id.textInputEditTextaddress);
        textInputEditTextpassword = (TextInputEditText) findViewById(R.id.textInputEditTextPassword);
        textInputEditTextcountry = (TextInputEditText) findViewById(R.id.textInputEditTextcountry);
        textInputEditTextage = (TextInputEditText) findViewById(R.id.textInputEditTextage);
        textInputEditTextr = (TextInputEditText) findViewById(R.id.textInputEditTextr);
        textInputEditTextconfirmpassword = (TextInputEditText) findViewById(R.id.textInputEditTextConfirmPassword);
        regesiter = (AppCompatButton) findViewById(R.id.appCompatButtonRegister);
        openlogin = (AppCompatTextView) findViewById(R.id.appCompatTextViewLoginLink);
        mAuth = FirebaseAuth.getInstance();
    }
}
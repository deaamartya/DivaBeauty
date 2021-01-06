package com.dad.divabeauty.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.dad.divabeauty.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import static com.dad.divabeauty.activity.MainActivity.PREFS_NAME;

public class LoginActivity extends AppCompatActivity {

    private TextInputLayout input_username;
    private TextInputLayout input_password;
    private MaterialAlertDialogBuilder loginGagalDialog;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference usersRef;
    private boolean ada = false;
    private SharedPreferences settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        settings = getSharedPreferences(PREFS_NAME, 0);

        usersRef = db.collection("user");
        boolean loggedIn = settings.getBoolean("loggedIn", false); // Is it first run? If not specified, use "true"

        if (loggedIn) {
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(intent);
        }

        input_username = findViewById(R.id.input_username);
        input_password = findViewById(R.id.input_password);
        loginGagalDialog = new MaterialAlertDialogBuilder(this);
        loginGagalDialog
            .setTitle("Gagal Login!")
            .setMessage("Pastikan anda telah memasukkan username dan password yang benar.")
            .setCancelable(true)
            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });

        Button login = findViewById(R.id.btn_login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = input_username.getEditText().getText().toString();
                String password = input_password.getEditText().getText().toString();
                boolean cek = cekLogin(username,password);
                if(cek) {
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(intent);
                }
                else{
                    loginGagalDialog.show();
                }
            }
        });

        Button register = findViewById(R.id.btn_login_register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

    }

    public boolean cekLogin(final String username, String password){
        usersRef.whereEqualTo("username", username).whereEqualTo("password", password).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if(!queryDocumentSnapshots.getDocuments().isEmpty()) {
                            // simpan sebagai obyek user
                            DocumentSnapshot user = queryDocumentSnapshots.getDocuments().get(0);

                            // simpan jika loggedin adalah true dan menyimpan id_user yang login.
                            SharedPreferences.Editor editor = settings.edit();
                            editor.putBoolean("loggedIn", true);
                            editor.putInt("id_user", Integer.valueOf(user.get("id_user").toString()));
                            editor.commit();
                            ada = true;
                        }
                        else{
                            ada = false;
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        ada = false;
                    }
                });
        return ada;
    }

}
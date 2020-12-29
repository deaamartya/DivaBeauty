package com.dad.divabeauty.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.dad.divabeauty.R;
import com.dad.divabeauty.model.Pasien;
import com.dad.divabeauty.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.core.OrderBy;

public class RegisterActivity extends AppCompatActivity {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference usersRef;
    public MaterialAlertDialogBuilder regisGagalDialog;
    private RadioButton laki_laki;
    private RadioButton perempuan;
    private TextInputLayout input_nama;
    private TextInputLayout input_email;
    private TextInputLayout input_no_telp;
    private TextInputLayout input_noktp;
    private TextInputLayout input_nokk;
    private TextInputLayout input_riwayat;
    private TextInputLayout input_username;
    private TextInputLayout input_password;
    private TextInputLayout input_konf_password;
    private TextInputLayout tgl_lahir;
    private Button btn_tgl_lahir;
    private Integer last_id = 0;
    private boolean user_registered= false;
    private boolean registered = false;
    private boolean error = false;
    private Integer last_id_pasien = 0;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Button register = findViewById(R.id.register_btn_create);
        laki_laki = findViewById(R.id.radio_lakilaki);
        perempuan = findViewById(R.id.radio_perempuan);
        input_nama = findViewById(R.id.register_input_nama);
        input_email = findViewById(R.id.register_input_email);
        input_no_telp = findViewById(R.id.register_input_notelp);
        input_noktp = findViewById(R.id.register_input_noktp);
        input_nokk = findViewById(R.id.register_input_nokk);
        input_riwayat = findViewById(R.id.register_input_riwayat);
        input_username = findViewById(R.id.register_input_username);
        input_password = findViewById(R.id.register_input_password);
        input_konf_password = findViewById(R.id.register_input_konfirmasipassword);
        tgl_lahir = findViewById(R.id.register_input_tgl_lahir);
        btn_tgl_lahir = findViewById(R.id.button_tgl_lahir);
        username = input_username.getEditText().getText().toString();

        usersRef = db.collection("user");

        MaterialDatePicker.Builder materialDateBuilder = MaterialDatePicker.Builder.datePicker();
        materialDateBuilder.setTitleText("Pilih tanggal lahir Anda");
        final MaterialDatePicker materialDatePicker = materialDateBuilder.build();
        btn_tgl_lahir.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // getSupportFragmentManager() to
                        // interact with the fragments
                        // associated with the material design
                        // date picker tag is to get any error
                        // in logcat
                        materialDatePicker.show(getSupportFragmentManager(), "MATERIAL_DATE_PICKER");
                    }
                });
        materialDatePicker.addOnPositiveButtonClickListener(
                new MaterialPickerOnPositiveButtonClickListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onPositiveButtonClick(Object selection) {

                        // if the user clicks on the positive
                        // button that is ok button update the
                        // selected date
                        tgl_lahir.getEditText().setText(materialDatePicker.getHeaderText());
                        // in the above statement, getHeaderText
                        // is the selected date preview from the
                        // dialog
                    }
                });

        regisGagalDialog = new MaterialAlertDialogBuilder(this);
        regisGagalDialog
                .setTitle("Gagal Registrasi!")
                .setMessage("")
                .setCancelable(true)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean registered = register();
                if(registered){
                    Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
                    startActivity(intent);
                }
                else
                {
                    regisGagalDialog.show();
                }
            }
        });

        Button login = findViewById(R.id.register_btn_login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        laki_laki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                perempuan.setChecked(false);
            }
        });

        perempuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                laki_laki.setChecked(false);
            }
        });
    }

    public boolean validate() {
        error = false;
        String nama = input_nama.getEditText().getText().toString();
        String ttl = tgl_lahir.getEditText().getText().toString();
        String email = input_email.getEditText().getText().toString();
        String notlp = input_no_telp.getEditText().getText().toString();
        String nik = input_noktp.getEditText().getText().toString();
        String nokk = input_nokk.getEditText().getText().toString();
        String password = input_password.getEditText().getText().toString();
        String konf_password = input_konf_password.getEditText().getText().toString();
        username = input_username.getEditText().getText().toString();

        if (nama.length() < 3) {
            input_nama.setError("Nama minimal 3 karakter");
            error = true;
        } else {
            input_nama.setError("");
        }

        if (email.length() < 3) {
            input_email.setError("Email harus diisi");
            error = true;
        } else {
            input_email.setError("");
        }

        if (notlp.length() < 3) {
            input_no_telp.setError("Nomor Telepon harus diisi");
            error = true;
        } else {
            input_no_telp.setError("");
        }

        if (nokk.length() < 3) {
            input_nokk.setError("Nomor KK harus diisi");
            error = true;
        } else {
            input_nokk.setError("");
        }

        if (nik.length() < 3) {
            input_noktp.setError("Nomor KTP/NIK harus diisi");
            error = true;
        } else {
            input_noktp.setError("");
        }

        if (ttl.length() < 3) {
            tgl_lahir.setError("Klik tombol dibawah untuk mengisi tanggal lahir Anda");
            error = true;
        } else {
            tgl_lahir.setError("");
        }

        if (password.length() < 6) {
            input_password.setError("Password minimal 6 karakter");
            error = true;
            Log.d("e","Password minimal 6 karakter");
        } else if (konf_password.length() < 6) {
            input_konf_password.setError("Password minimal 6 karakter");
            error = true;
            Log.d("e","Konfirmasi Password minimal 6 karakter");
        } else if (!password.equals(konf_password)) {
            input_konf_password.setError("Konfirmasi Password harus sama dengan Password");
            error = true;
            Log.d("e","Konfirmasi Password harus sama dengan Password");
        } else {
            input_konf_password.setError("");
        }

        if (username.length() < 1) {
            input_username.setError("Username harus diisi minimal 6 karakter");
        } else if (username.length() > 5) {
            usersRef.whereEqualTo("username", username).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (DocumentSnapshot documentSnapshot : task.getResult()) {
                            String user = documentSnapshot.getString("username");

                            if (user.equals(username)) {
                                input_username.setError("Username telah dipakai. Gunakan username lain");
                                error = true;
                                Log.d("error","username ditemukan");
                            }
                        }
                    }

                    if (task.getResult().size() == 0) {
                        input_username.setError("");
                    }
                }
            });
        }

        return error;
    }

    public boolean register(){
        boolean validated = validate();
        String nama = input_nama.getEditText().getText().toString();
        boolean jenis_kelamin = false;
        if (laki_laki.isChecked()) {
            jenis_kelamin = true;
        }
        String ttl = tgl_lahir.getEditText().getText().toString();
        String email = input_email.getEditText().getText().toString();
        String notlp = input_no_telp.getEditText().getText().toString();
        String nik = input_noktp.getEditText().getText().toString();
        String nokk = input_nokk.getEditText().getText().toString();
        String riwayat = input_riwayat.getEditText().getText().toString();
        String password = input_password.getEditText().getText().toString();
        username = input_username.getEditText().getText().toString();

        if(validated){
            usersRef.orderBy("id_user", Query.Direction.DESCENDING).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    last_id = Integer.valueOf(queryDocumentSnapshots.getDocuments().get(0).get("id_user").toString());
                }
            });
            User user = new User(last_id+1,1,username,email,password,nama,notlp,jenis_kelamin);
            db.collection("user").add(user).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {
                    user_registered = true;
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    user_registered = false;
                    regisGagalDialog.setMessage("Silahkan cek kembali data Anda");
                }
            });

            db.collection("pasien").orderBy("id_pasien", Query.Direction.DESCENDING).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    last_id_pasien = Integer.valueOf(queryDocumentSnapshots.getDocuments().get(0).get("id_pasien").toString());
                }
            });

            if(user_registered){
                Pasien pasien = new Pasien(last_id_pasien+1,last_id+1,nik,nokk,riwayat);
                db.collection("pasien").add(pasien).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        registered = true;
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        registered = false;
                        regisGagalDialog.setMessage("Silahkan cek kembali data Anda");
                    }
                });
            }
        }
        else{
            regisGagalDialog.setMessage("Pastikan data yang Anda masukkan adalah benar");
        }
        return registered;
    }
}
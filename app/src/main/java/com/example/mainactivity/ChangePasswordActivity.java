package com.example.mainactivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordActivity extends AppCompatActivity {

    private static final String API_KEY = "c2e3832fda787846d7fb855a3123b1962dce3";

    private EditText currentPasswordEditText, newPasswordEditText, confirmPasswordEditText;
    private Button changePasswordButton;
    private String savedUserEmail;
    private RestDbAPI apiService; // Variável da classe

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        currentPasswordEditText = findViewById(R.id.currentPasswordEditText);
        newPasswordEditText = findViewById(R.id.newPasswordEditText);
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText);
        changePasswordButton = findViewById(R.id.changePasswordButton);

        apiService = RetrofitClient.getRestDbApi(); // Inicializa o serviço

        SharedPreferences prefs = getSharedPreferences("AppPrefs", Context.MODE_PRIVATE);
        savedUserEmail = prefs.getString("USER_EMAIL", null);

        changePasswordButton.setOnClickListener(v -> handleChangePassword());
    }

    private void handleChangePassword() {
        String currentPass = currentPasswordEditText.getText().toString().trim();
        String newPass = newPasswordEditText.getText().toString().trim();
        String confirmPass = confirmPasswordEditText.getText().toString().trim();

        if (currentPass.isEmpty() || newPass.isEmpty() || confirmPass.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!newPass.equals(confirmPass)) {
            Toast.makeText(this, "New passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }
        if (savedUserEmail == null) {
            Toast.makeText(this, "Error: Not logged in", Toast.LENGTH_SHORT).show();
            return;
        }

        String jsonQuery = "{\"email\":\"" + savedUserEmail + "\"}";

        // 1. BUSCAR O USER (E O SEU _ID)
        apiService.loginUser(API_KEY, jsonQuery).enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful() && response.body() != null && !response.body().isEmpty()) {
                    User userFromDb = response.body().get(0);

                    // Apanha o _id!
                    String userId = userFromDb.get_id();

                    // 2. VERIFICAR A PASSWORD ANTIGA
                    if (userFromDb.getPassword().equals(currentPass)) {

                        // 3. PREPARAR O UPDATE
                        // Cria o objeto User com o email e a *nova* password
                        User userBody = new User(savedUserEmail, newPass);

                        // 4. ATUALIZAR
                        updatePasswordInRestDb(userId, userBody);

                    } else {
                        Toast.makeText(ChangePasswordActivity.this, "Current password incorrect", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ChangePasswordActivity.this, "Error fetching user data", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Toast.makeText(ChangePasswordActivity.this, "Connection failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Função de update corrigida para usar o _id e o body User
    private void updatePasswordInRestDb(String userId, User userBody) {

        // Chama o *teu* método updateUser da API
        apiService.updateUser(API_KEY, userId, userBody).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(ChangePasswordActivity.this, "Password updated successfully!", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(ChangePasswordActivity.this, "Update failed: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(ChangePasswordActivity.this, "Update connection failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
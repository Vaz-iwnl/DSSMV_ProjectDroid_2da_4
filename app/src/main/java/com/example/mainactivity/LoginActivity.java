package com.example.mainactivity;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login); // Liga ao activity_login.xml

        // Encontra os componentes do layout
        EditText emailEditText = findViewById(R.id.emailEditText);
        EditText passwordEditText = findViewById(R.id.passwordEditText);
        Button loginButton = findViewById(R.id.loginButton);
        TextView forgotPasswordText = findViewById(R.id.forgotPasswordText);

        // Ação do botão "Log in"
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                // TODO: Adicionar lógica real de autenticação aqui (ex: com Firebase)

                // Por enquanto, vamos apenas verificar se não está vazio e simular o login
                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                } else {
                    // Login simulado com sucesso!
                    Toast.makeText(LoginActivity.this, "Login Successful!", Toast.LENGTH_SHORT).show();

                    // Navega para a MainActivity (a tela principal do seu app)
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    // Adicionamos estas "flags" para limpar o histórico de navegação.
                    // Isso impede que o usuário pressione "Voltar" e retorne à tela de login.
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish(); // Fecha a LoginActivity
                }
            }
        });

        // Ação para o texto "Forgot my password"
        forgotPasswordText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Criar uma tela ou diálogo de recuperação de senha
                Toast.makeText(LoginActivity.this, "Forgot password clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
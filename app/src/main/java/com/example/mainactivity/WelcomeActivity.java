package com.example.mainactivity;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome); // Liga esta classe ao layout XML

        // Encontra os botões no layout pelo ID que definimos no XML
        Button loginButton = findViewById(R.id.loginButton);
        Button createAccountButton = findViewById(R.id.createAccountButton);

        // Define o que acontece quando o botão "Log in" é clicado
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Cria uma "Intenção" para ir da tela Atual (WelcomeActivity) para a LoginActivity
                Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
                startActivity(intent); // Executa a intenção (muda de tela)
            }
        });

        // Define o que acontece quando o botão "Create account" é clicado
        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Cria uma "Intenção" para ir para a RegisterActivity
                Intent intent = new Intent(WelcomeActivity.this, RegisterActivity.class);
                startActivity(intent); // Executa a intenção
            }
        });
    }
}
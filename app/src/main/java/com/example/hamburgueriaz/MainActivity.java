package com.example.hamburgueriaz;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private int quantidade = 0;
    private TextView textQuantidade, textResumo;
    private EditText editNome;
    private CheckBox checkBacon, checkQueijo, checkOnionRings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textQuantidade = findViewById(R.id.textQuantidade);
        textResumo = findViewById(R.id.textResumo);
        editNome = findViewById(R.id.editNome);
        checkBacon = findViewById(R.id.checkBacon);
        checkQueijo = findViewById(R.id.checkQueijo);
        checkOnionRings = findViewById(R.id.checkOnionRings);
    }

    public void somar(View view) {
        quantidade++;
        textQuantidade.setText(String.valueOf(quantidade));
    }

    public void subtrair(View view) {
        if (quantidade > 0) {
            quantidade--;
            textQuantidade.setText(String.valueOf(quantidade));
        }
    }

    private int calcularPreco(boolean temBacon, boolean temQueijo, boolean temOnion) {
        int precoBase = 20;
        if (temBacon) precoBase += 2;
        if (temQueijo) precoBase += 2;
        if (temOnion) precoBase += 3;

        return quantidade * precoBase;
    }

    public void enviarPedido(View view) {
        String nome = editNome.getText().toString();
        boolean temBacon = checkBacon.isChecked();
        boolean temQueijo = checkQueijo.isChecked();
        boolean temOnion = checkOnionRings.isChecked();

        int precoTotal = calcularPreco(temBacon, temQueijo, temOnion);

        String resumo = "Nome do cliente: " + nome +
                "\nTem Bacon? " + (temBacon ? "Sim" : "Não") +
                "\nTem Queijo? " + (temQueijo ? "Sim" : "Não") +
                "\nTem Onion Rings? " + (temOnion ? "Sim" : "Não") +
                "\nQuantidade: " + quantidade +
                "\nPreço final: R$ " + precoTotal;

        textResumo.setText(resumo);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT, "Pedido de " + nome);
        intent.putExtra(Intent.EXTRA_TEXT, resumo);

        try {
            startActivity(Intent.createChooser(intent, "Escolha o app de e-mail:"));
        } catch (Exception e) {
            //  caso não haja app de e-mail instalado
        }
    }
}
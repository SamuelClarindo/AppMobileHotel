package com.example.apphotel;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import java.util.Locale; // Para formatar o valor total

public class ResponseActivity extends AppCompatActivity {

    TextView textViewDetails, textViewResponseTitle;
    Button btnOkResponse, btnCancelResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_response);

        textViewResponseTitle = findViewById(R.id.textViewResponseTitle);
        textViewDetails = findViewById(R.id.textViewDetails);
        btnOkResponse = findViewById(R.id.btnOkResponse);
        btnCancelResponse = findViewById(R.id.btnCancelResponse);

        Intent intent = getIntent();
        String nome = intent.getStringExtra("nome");
        String cpf = intent.getStringExtra("cpf");
        String email = intent.getStringExtra("email");
        String telefone = intent.getStringExtra("telefone");
        String dataEntrada = intent.getStringExtra("dataEntrada");
        String dataSaida = intent.getStringExtra("dataSaida");
        String suite = intent.getStringExtra("suite");
        long dias = intent.getLongExtra("dias", 0);
        double totalReserva = intent.getDoubleExtra("totalReserva", 0.0);

        String bandeiraCartao = intent.getStringExtra("bandeiraCartao");
        String numeroCartao = intent.getStringExtra("numeroCartao");
        String nomeTitularCartao = intent.getStringExtra("nomeTitularCartao");
        boolean salvarCartao = intent.getBooleanExtra("salvarCartao", false);

        StringBuilder mensagemBuilder = new StringBuilder();
        mensagemBuilder.append("CONFIRME SEUS DADOS:\n\n");
        mensagemBuilder.append("--- Dados da Reserva ---\n");
        mensagemBuilder.append("Hóspede: ").append(nome != null ? nome : "N/A").append("\n");
        mensagemBuilder.append("CPF: ").append(cpf != null ? cpf : "N/A").append("\n");
        mensagemBuilder.append("Email: ").append(email != null ? email : "N/A").append("\n");
        mensagemBuilder.append("Telefone: ").append(telefone != null ? telefone : "N/A").append("\n\n");
        mensagemBuilder.append("Check-in: ").append(dataEntrada != null ? dataEntrada : "N/A").append("\n");
        mensagemBuilder.append("Check-out: ").append(dataSaida != null ? dataSaida : "N/A").append("\n");
        mensagemBuilder.append("Suíte: ").append(suite != null ? suite : "N/A").append("\n");
        mensagemBuilder.append("Noites: ").append(dias).append("\n");
        mensagemBuilder.append("Valor Total da Reserva: R$").append(String.format(Locale.getDefault(), "%.2f", totalReserva)).append("\n\n");

        mensagemBuilder.append("--- Dados do Pagamento ---\n");
        mensagemBuilder.append("Bandeira: ").append(bandeiraCartao != null ? bandeiraCartao : "N/A").append("\n");
        if (numeroCartao != null && numeroCartao.length() > 4) {
            mensagemBuilder.append("Cartão Final: **** ").append(numeroCartao.substring(numeroCartao.length() - 4)).append("\n");
        } else if (numeroCartao != null && !numeroCartao.isEmpty()) {
            mensagemBuilder.append("Cartão: (Protegido)").append("\n");
        } else {
            mensagemBuilder.append("Cartão: N/A").append("\n");
        }
        mensagemBuilder.append("Titular: ").append(nomeTitularCartao != null ? nomeTitularCartao : "N/A").append("\n");
        mensagemBuilder.append(salvarCartao ? "Opção: Salvar dados do cartão selecionada.\n" : "Opção: Salvar dados do cartão NÃO selecionada.\n");

        textViewDetails.setText(mensagemBuilder.toString());

        btnOkResponse.setOnClickListener(view -> {
            Toast.makeText(ResponseActivity.this, "Dados Corretos!", Toast.LENGTH_LONG).show();
            Intent mainIntent = new Intent(ResponseActivity.this, HomeActivity.class);
            mainIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(mainIntent);
            finish();
        });

        btnCancelResponse.setOnClickListener(view -> {
            finish();
        });
    }
}
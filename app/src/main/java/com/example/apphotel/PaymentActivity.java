package com.example.apphotel;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioGroup;
import android.widget.Toast;

// Se usar TextInputEditText do Material Design
import com.google.android.material.textfield.TextInputEditText;

import java.util.Locale; // Para formatar o valor total, se for exibir aqui

public class PaymentActivity extends AppCompatActivity {

    TextInputEditText editTextPhonePayment, editTextEmailPayment, editTextCardNumber, editTextCardHolderName, editTextExpiryDate, editTextCvv;
    RadioGroup radioGroupCardBrand;
    CheckBox checkBoxSaveCard;
    Button btnProcessPayment;

    // Variáveis para guardar dados da ReservationActivity
    String nomeCompletoReserva, cpfReserva, emailReserva, telefoneReserva, dataEntradaReserva, dataSaidaReserva, suiteNomeReserva;
    long diasReserva;
    double totalReservaValor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        // Inicializar Views
        editTextPhonePayment = findViewById(R.id.editTextPhonePayment);
        editTextEmailPayment = findViewById(R.id.editTextEmailPayment);
        radioGroupCardBrand = findViewById(R.id.radioGroupCardBrand);
        editTextCardNumber = findViewById(R.id.editTextCardNumber);
        editTextCardHolderName = findViewById(R.id.editTextCardHolderName);
        editTextExpiryDate = findViewById(R.id.editTextExpiryDate);
        editTextCvv = findViewById(R.id.editTextCvv);
        checkBoxSaveCard = findViewById(R.id.checkBoxSaveCard);
        btnProcessPayment = findViewById(R.id.btnProcessPayment);

        // --- RECEBER DADOS DA RESERVATIONACTIVITY ---
        Intent intent = getIntent();
        if (intent != null) {
            nomeCompletoReserva = intent.getStringExtra("nome");
            cpfReserva = intent.getStringExtra("cpf");
            emailReserva = intent.getStringExtra("email");
            telefoneReserva = intent.getStringExtra("telefone");
            dataEntradaReserva = intent.getStringExtra("dataEntrada");
            dataSaidaReserva = intent.getStringExtra("dataSaida");
            suiteNomeReserva = intent.getStringExtra("suite");
            diasReserva = intent.getLongExtra("dias", 0);
            totalReservaValor = intent.getDoubleExtra("total", 0.0); // A chave era "total" em ReservationActivity

            // Pré-preencher campos de e-mail e telefone (opcional)
            if (emailReserva != null) {
                editTextEmailPayment.setText(emailReserva);
            }
            if (telefoneReserva != null) {
                editTextPhonePayment.setText(telefoneReserva);
            }
        }

        btnProcessPayment.setOnClickListener(view -> {
            // Coletar dados do pagamento
            String telefonePagamento = editTextPhonePayment.getText().toString().trim();
            String emailPagamento = editTextEmailPayment.getText().toString().trim();
            String numeroCartao = editTextCardNumber.getText().toString().trim();
            String nomeTitular = editTextCardHolderName.getText().toString().trim();
            String dataValidade = editTextExpiryDate.getText().toString().trim();
            String cvv = editTextCvv.getText().toString().trim();
            boolean salvarCartao = checkBoxSaveCard.isChecked();

            int selectedBrandId = radioGroupCardBrand.getCheckedRadioButtonId();
            String bandeiraCartao = "";
            if (selectedBrandId == R.id.radioButtonVisa) {
                bandeiraCartao = "Visa";
            } else if (selectedBrandId == R.id.radioButtonMastercard) {
                bandeiraCartao = "Mastercard";
            } else if (selectedBrandId == R.id.radioButtonElo) {
                bandeiraCartao = "Elo";
            } else if (selectedBrandId == -1) { // Nenhuma bandeira selecionada
                Toast.makeText(PaymentActivity.this, "Selecione a bandeira do cartão!", Toast.LENGTH_LONG).show();
                return;
            }
            // Adicione mais 'else if' se tiver outras bandeiras no seu layout activity_payment.xml

            // --- VALIDAÇÃO DOS CAMPOS (Exemplo básico) ---
            if (telefonePagamento.isEmpty() || emailPagamento.isEmpty() || numeroCartao.isEmpty() ||
                    nomeTitular.isEmpty() || dataValidade.isEmpty() || cvv.isEmpty() ) { // bandeiraCartao já validado acima
                Toast.makeText(PaymentActivity.this, "Preencha todos os campos de pagamento obrigatórios!", Toast.LENGTH_LONG).show();
                return;
            }
            // Adicionar validações mais específicas (ex: formato do email, número de dígitos do cartão/CVV, validade)

            Intent responseIntent = new Intent(PaymentActivity.this, ResponseActivity.class);

            responseIntent.putExtra("nome", nomeCompletoReserva);
            responseIntent.putExtra("cpf", cpfReserva);
            responseIntent.putExtra("email", emailReserva);
            responseIntent.putExtra("telefone", telefoneReserva);
            responseIntent.putExtra("dataEntrada", dataEntradaReserva);
            responseIntent.putExtra("dataSaida", dataSaidaReserva);
            responseIntent.putExtra("suite", suiteNomeReserva);
            responseIntent.putExtra("dias", diasReserva);
            responseIntent.putExtra("totalReserva", totalReservaValor);

            responseIntent.putExtra("bandeiraCartao", bandeiraCartao);
            responseIntent.putExtra("numeroCartao", numeroCartao);
            responseIntent.putExtra("nomeTitularCartao", nomeTitular);
            responseIntent.putExtra("salvarCartao", salvarCartao);

            startActivity(responseIntent);
            // NÃO CHAME finish() ou finishAffinity() AQUI.
        });
    }
}
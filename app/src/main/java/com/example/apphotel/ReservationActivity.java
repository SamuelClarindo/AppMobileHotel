package com.example.apphotel;

import static com.example.apphotel.R.layout.activity_reservation;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast; // Adicionado para a item 6 do trabalho

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale; // Para formatar o valor total

public class ReservationActivity extends AppCompatActivity {

    TextView txtEntrada, txtSaida;
    EditText nome, sobrenome, email, cpf, telefone; // Adicionando as declarações que faltavam
    RadioGroup radioGroup; // Adicionando a declaração
    Button btnConfirmar; // Adicionando a declaração

    Calendar dataEntrada = Calendar.getInstance();
    Calendar dataSaida = Calendar.getInstance();
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_reservation); // Usando o layout correto importado

        radioGroup = findViewById(R.id.radioGroupSuites); // Corrigido para radioGroup
        nome = findViewById(R.id.inputNome);
        sobrenome = findViewById(R.id.inputSobrenome);
        email = findViewById(R.id.inputEmail);
        cpf = findViewById(R.id.inputCpf);
        telefone = findViewById(R.id.inputTelefone);
        txtEntrada = findViewById(R.id.txtEntrada);
        txtSaida = findViewById(R.id.txtSaida);
        btnConfirmar = findViewById(R.id.btnConfirmar);

        txtEntrada.setOnClickListener(v -> showDatePicker(dataEntrada, txtEntrada, true));
        txtSaida.setOnClickListener(v -> showDatePicker(dataSaida, txtSaida, false));

        btnConfirmar.setOnClickListener(v -> {
            int idSelecionado = radioGroup.getCheckedRadioButtonId();

            if (idSelecionado == -1) {
                Toast.makeText(this, "Selecione uma suíte!", Toast.LENGTH_SHORT).show();
                return;
            }

            // Validação de campos de texto
            if (nome.getText().toString().trim().isEmpty() ||
                    sobrenome.getText().toString().trim().isEmpty() ||
                    email.getText().toString().trim().isEmpty() ||
                    cpf.getText().toString().trim().isEmpty() ||
                    telefone.getText().toString().trim().isEmpty() ||
                    txtEntrada.getText().toString().equals("Clique para selecionar") ||
                    txtSaida.getText().toString().equals("Clique para selecionar")) {
                Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_LONG).show();
                return;
            }


            Calendar hoje = Calendar.getInstance();
            // Zera horas, minutos, segundos e milissegundos de 'hoje' para comparar apenas as datas
            hoje.set(Calendar.HOUR_OF_DAY, 0);
            hoje.set(Calendar.MINUTE, 0);
            hoje.set(Calendar.SECOND, 0);
            hoje.set(Calendar.MILLISECOND, 0);

            Calendar dataEntradaSelecionada = (Calendar) dataEntrada.clone();
            dataEntradaSelecionada.set(Calendar.HOUR_OF_DAY, 0);
            dataEntradaSelecionada.set(Calendar.MINUTE, 0);
            dataEntradaSelecionada.set(Calendar.SECOND, 0);
            dataEntradaSelecionada.set(Calendar.MILLISECOND, 0);


            if (dataEntradaSelecionada.before(hoje)) {
                Toast.makeText(this, "Data de entrada não pode ser anterior a hoje!", Toast.LENGTH_LONG).show();
                return;
            }

            long diff = dataSaida.getTimeInMillis() - dataEntrada.getTimeInMillis();
            long dias = diff / (1000 * 60 * 60 * 24);

            if (dias <= 0) {
                Toast.makeText(this, "Data de saída deve ser após a data de entrada!", Toast.LENGTH_LONG).show();
                return;
            }

            double preco;
            String suiteNome;
            if (idSelecionado == R.id.suite1) {
                preco = 850;
                suiteNome = "Suíte Imperial Atlântica (Cobertura - Vista Mar)";
            } else if (idSelecionado == R.id.suite2) {
                preco = 650;
                suiteNome = "Suíte Horizonte Azul (Luxo - Vista Mar)";
            } else if (idSelecionado == R.id.suite3) {
                preco = 700;
                suiteNome = "Suíte Cidade Maravilhosa (Cobertura - Vista Cidade)";
            } else { // Assume-se R.id.suite4 ou qualquer outro ID que possa existir
                preco = 500;
                suiteNome = "Suíte Jardim Carioca (Conforto - Vista Cidade)";
            }

            double total = preco * dias;

            Intent intent = new Intent(ReservationActivity.this, PaymentActivity.class);
            intent.putExtra("nome", nome.getText().toString() + " " + sobrenome.getText().toString());
            intent.putExtra("cpf", cpf.getText().toString());
            intent.putExtra("email", email.getText().toString());
            intent.putExtra("telefone", telefone.getText().toString());
            intent.putExtra("dataEntrada", txtEntrada.getText().toString());
            intent.putExtra("dataSaida", txtSaida.getText().toString());
            intent.putExtra("suite", suiteNome);
            intent.putExtra("dias", dias);
            intent.putExtra("total", total); // Chave "total" para o valor da reserva

            startActivity(intent);
        });
    }

    private void showDatePicker(Calendar calendar, TextView textView, boolean isEntrada) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
            calendar.set(year, month, dayOfMonth);
            textView.setText(sdf.format(calendar.getTime()));
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        if (isEntrada) {
            datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000); // Permite selecionar o dia de hoje
        } else {
            // Data de saída deve ser pelo menos um dia depois da data de entrada
            // ou um dia depois de hoje se a data de entrada ainda não foi definida ou é hoje.
            long minCheckoutDateMillis = dataEntrada.getTimeInMillis() + (24 * 60 * 60 * 1000);
            if (dataEntrada.getTimeInMillis() < System.currentTimeMillis() - (24*60*60*1000) /* se entrada for antes de hoje, o que não deveria acontecer */ ) {
                minCheckoutDateMillis = System.currentTimeMillis() + (24 * 60 * 60 * 1000) - 1000; // um dia a partir de hoje
            }
            datePickerDialog.getDatePicker().setMinDate(minCheckoutDateMillis);
        }
        datePickerDialog.show();
    }
}
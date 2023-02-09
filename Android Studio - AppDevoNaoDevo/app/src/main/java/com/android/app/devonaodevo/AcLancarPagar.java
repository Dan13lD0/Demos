package com.android.app.devonaodevo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.santalu.maskedittext.MaskEditText;

import java.text.DecimalFormat;
import java.text.ParseException;

import Controle.Funcoes;
import Dal.PagarDal;
import Dto.ContaDto;
import Dto.PagarDto;

public class AcLancarPagar extends AppCompatActivity {
    private TextView txtdescricao;
    private TextView txtparcela;
    private TextView txtvencimento;
    private TextView txtvalor;
    private MaskEditText txtdataPagamento;
    private MaskEditText txtvalorPagamento;
    private Button btnPaga;
    private Button btnVoltar;
    private Long idCompra;
    private ImageView NovoImageData;
    private int NroParcela;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ac_lancar_pagar);
        NovoImageData = (ImageView)findViewById(R.id.imgDate);
        txtdescricao = (TextView)findViewById(R.id.txtDescricao);
        txtparcela = (TextView)findViewById(R.id.txtParcela);
        txtvencimento = (TextView)findViewById(R.id.txtVencimento);
        txtvalor = (TextView)findViewById(R.id.txtValorPagamento);
        txtdataPagamento = (MaskEditText)findViewById(R.id.txtDataPagamento);
        txtvalorPagamento = (MaskEditText)findViewById(R.id.txtValorPagar);
        btnPaga = (Button)findViewById(R.id.btnPagar);
        btnVoltar = (Button)findViewById(R.id.btnVoltar);

        txtvalorPagamento.setSelectAllOnFocus(true);

        Bundle bundle = getIntent().getExtras();
        if((bundle != null) && (bundle.containsKey("Alterar"))) {
            ContaDto alterar = (ContaDto) bundle.getSerializable("Alterar");
            new Funcoes().setDate(txtdataPagamento,NovoImageData);
            txtdescricao.setText(alterar.Descricao);
            txtdataPagamento.setText(new Funcoes().getDateNow());
            txtvalorPagamento.setText(new Funcoes().RetornaValorString(new DecimalFormat("###,###,###,###,##0.00").format(alterar.Valor)));
            txtvalor.setText(new Funcoes().RetornaValorString(new DecimalFormat("###,###,###,###,##0.00").format(alterar.Valor)));
            txtvencimento.setText(new Funcoes().DateToString(alterar.DataCompra));
            if (alterar.ParcelaAtual > 0){
                txtparcela.setText(alterar.ParcelaAtual.toString() + "/" + alterar.QtdParcela);
                NroParcela = alterar.ParcelaAtual;
            }
            else{
                txtparcela.setText("A vista");
            }

            idCompra = alterar.Id;
        }
        btnPaga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Pagar(v);
            }
        });

        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private String ValidaCampos(){

        if (txtdataPagamento.getText().toString().trim().length() <= 0){
            return "Data pagamento não informado!";
        }

        if (txtvalorPagamento.getText().toString().trim().length() <= 0){
            if (new Funcoes().RetornaValorDecimal(txtvalorPagamento.getText().toString().trim()) == 0.0){
                return "Valor de pagamento deve ser maior que ZERO!";
            }
        }
        else
        {
            return "Valor do Pagamento não informado!";
        }
        return "";
    }

    private PagarDto CarregaObj()  {
        PagarDto obj = new PagarDto();
        obj.IdCompra = idCompra;
        String u =  txtparcela.getText().toString().trim();
        int y = txtparcela.getText().toString().trim().length();
        if(txtparcela.getText().toString().trim() == "A vista"){
            obj.NroParcela = 0;
        }
        else{
            obj.NroParcela = NroParcela;
        }

        try {
            obj.DataPagamento = new Funcoes().ToDate(txtdataPagamento.getText().toString().trim());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        obj.ValorPagamento = new Funcoes().RetornaValorDecimal(txtvalorPagamento.getText().toString());
        return obj;
    }

    private void Pagar(View view){
        String resultado = ValidaCampos();

        if (resultado.trim().length() > 0){

            PagarDto obj = CarregaObj();

            new PagarDal(this).Insert(obj);
            new PagarDal(this).Update(obj);

            finish();
        }
        else{
            new Funcoes().MessageBox(view,"Aviso",resultado);
        }
    }
}

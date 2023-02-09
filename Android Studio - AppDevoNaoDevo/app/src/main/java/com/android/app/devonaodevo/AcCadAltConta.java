package com.android.app.devonaodevo;

import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;

import com.santalu.maskedittext.MaskEditText;

import java.text.DecimalFormat;
import java.text.ParseException;

import Controle.Funcoes;
import Dal.NovaCompraDal;
import Dal.PagoDal;
import Dto.ContaDto;
import Dto.PagoDto;

public class AcCadAltConta extends AppCompatActivity {
    private EditText NovoDescricao;
    private  EditText NovoDataCompra;
    private Switch NovoParcelado;
    private MaskEditText NovoQuantidade;
    private  MaskEditText NovoEntrada;
    private  MaskEditText NovoValor;
    private ImageView NovoImageData;
    private String Tela;
    private Long idConta;
    private NovaCompraDal novaCompraDal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ac_cad_alt_conta);

        final Button btnBuscar = (Button) findViewById(R.id.btnPagar) ;
        final Button btnVoltar = (Button) findViewById(R.id.btnVoltar) ;
        NovoDescricao = (EditText)findViewById(R.id.txtDescrica) ;
        NovoDataCompra = (EditText)findViewById(R.id.txtNameDate) ;
        NovoParcelado = (Switch)findViewById(R.id.stParcelado) ;
        NovoQuantidade = (MaskEditText)findViewById(R.id.txtQtde2) ;
        NovoEntrada = (MaskEditText)findViewById(R.id.txtEntrada) ;
        NovoValor = (MaskEditText)findViewById(R.id.txtValor2) ;
        NovoImageData = (ImageView)findViewById(R.id.imgDate) ;

        NovoDescricao.setSelectAllOnFocus(true);
        NovoQuantidade.setSelectAllOnFocus(true);
        NovoEntrada.setSelectAllOnFocus(true);
        NovoValor.setSelectAllOnFocus(true);

        NovoParcelado.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    NovoQuantidade.setEnabled(isChecked);
                    NovoEntrada.setEnabled(isChecked);
                }
                else{
                    NovoQuantidade.setText("");
                    NovoEntrada.setText("");
                    NovoQuantidade.setEnabled(isChecked);
                    NovoEntrada.setEnabled(isChecked);
                }
            }
        });
        new Funcoes().FormataCampoValor(NovoEntrada);
        new Funcoes().FormataCampoValor(NovoValor);
        new Funcoes().setDate(NovoDataCompra,NovoImageData);

        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        Bundle bundle = getIntent().getExtras();
        final AlertDialog alert = Wait();
        if((bundle != null) && (bundle.containsKey("Alterar"))){
            Tela = "Alterar";
            ContaDto alterar = (ContaDto)bundle.getSerializable("Alterar");
            idConta = alterar.Id;
            NovoDescricao.setText(alterar.Descricao);
            NovoDataCompra.setText(new Funcoes().DateToString(alterar.DataCompra));
            if(alterar.ValorParcela > 0){
                NovoParcelado.setChecked(true);
                if(alterar.Entrada > 0 ){

                    ((EditText)NovoEntrada).setText(new Funcoes().RetornaValorString(new DecimalFormat("###,###,###,###,###.00").format(Double.parseDouble(alterar.Entrada.toString()))));
                }
                ((EditText)NovoQuantidade).setText(alterar.QtdParcela.toString());
            }
            else{
                NovoParcelado.setChecked(false);
            }
            ((EditText)NovoValor).setText(new Funcoes().RetornaValorString(new DecimalFormat("###,###,###,###,###.00").format(Double.parseDouble(alterar.Valor.toString()))));
            btnBuscar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String resposta = ValidaCampos();
                    if (resposta.trim().length() <= 0){

                        final ContaDto item = CarregaObj();

                        new NovaCompraDal(v.getContext()).DeletaLancamento(item.Id.toString());
                        new NovaCompraDal(v.getContext()).DeletaLancamentosPago(item.Id.toString());

                        new NovaCompraDal(v.getContext()).Update(item);

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                InserirPago(item);
                            }
                        }).start();



                        finish();
                    }
                    else{
                        new Funcoes().MessageBox(v,"Aviso",resposta);
                    }
                }
            });
        }
        else{
            idConta = new NovaCompraDal(this).ProximoCodigo();
            NovoDataCompra.setText(new Funcoes().getDateNow());
            btnBuscar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String resposta = ValidaCampos();
                    if (resposta.trim().length() <= 0){
                        final ContaDto item = CarregaObj();

                        new NovaCompraDal(v.getContext()).Insert(item);
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                InserirPago(item);
                            }
                        }).start();

                        finish();
                    }
                    else{
                        new Funcoes().MessageBox(v,"Aviso",resposta);
                    }
                }
            });
        }
    }

    private void InserirPago(ContaDto obj) {
        int count = 1;
        long idPago = new PagoDal(this).ProximoCodigo();
        PagoDto pago;
        do {
            pago = new PagoDto();
            pago.PagoId = idPago;
            pago.PagoIdConta = obj.Id;
            if (obj.QtdParcela != null){
                pago.PagoDataVencimento = new Funcoes().addMonth(obj.DataCompra,count);
                pago.PagoValor = obj.ValorParcela;
                pago.PagoNumeroParcela = count;
                count = count +1;
            }
            else{
                pago.PagoNumeroParcela = 0;
                pago.PagoDataVencimento = obj.DataCompra;
                pago.PagoValor = obj.Valor;
                obj.QtdParcela = 0;
                count = 1;
            }


            pago.PagoStatus = "ATIVO";
            new PagoDal(this).Insert(pago);
        } while (count <= obj.QtdParcela);
    }

    private String ValidaCampos(){
        if (NovoDescricao.getText().toString().trim().length() <= 0){
            return "Descrição da compra não informada!";
        }

        if (NovoDataCompra.getText().toString().trim().length() <= 0){
            return "Data da compra não informada!";
        }


        if (NovoParcelado.isChecked()){
            if ((NovoQuantidade.getText().toString().trim().length() <= 0) || NovoQuantidade.getText() == null){
                return "Usuário informou que compra foi parcelada, informe o número de parcelas!";
            }
            else{
                if (Integer.parseInt(NovoQuantidade.getText().toString().trim()) == Integer.parseInt("0")){
                    return "O número de parcelas deve ser maior que ZERO!";
                }
            }
        }

        if (NovoValor.getText().toString().trim().length() <= 0){
            return "Informe o valor da compra!";
        }
        else{
            if (new Funcoes().RetornaValorDecimal(NovoValor.getText().toString().trim()) == 0.0){
                return "Valor deve ser maior que ZERO!";
            }
        }
        return "";
    }

    private ContaDto CarregaObj() {
        ContaDto obj = new ContaDto();

        obj.Id = idConta;

        obj.Descricao = NovoDescricao.getText().toString().trim().toUpperCase();


        try {
            obj.DataCompra = new Funcoes().ToDate(NovoDataCompra.getText().toString().trim());
        } catch (ParseException e) {
            e.printStackTrace();
        }


        obj.Valor = new Funcoes().RetornaValorDecimal(NovoValor.getText().toString().trim());
        if (NovoParcelado.isChecked()) {
            {
                obj.QtdParcela = Integer.parseInt(NovoQuantidade.getText().toString().trim());

                if (new Funcoes().RetornaValorDecimal(NovoEntrada.getText().toString().trim()) > 0.0){
                    obj.Entrada = new Funcoes().RetornaValorDecimal(NovoEntrada.getText().toString().trim());
                }

                if (obj.Entrada == null){
                    obj.Entrada = 0.0;
                }
                obj.ValorParcela = (obj.Valor - obj.Entrada) / obj.QtdParcela;

            }
        }
        obj.Status = "ATIVO";
        return obj;
    }

    private android.app.AlertDialog Wait(){
        View bview = getLayoutInflater().inflate(R.layout.layout_wait,null);
        return new Funcoes().Wait(bview,this);
    }
}

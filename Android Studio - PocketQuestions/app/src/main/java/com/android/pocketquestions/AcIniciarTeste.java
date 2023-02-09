package com.android.pocketquestions;

import android.app.Activity;
import android.app.AlertDialog;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;

import Dto.EstudarDto;
import Funcoes.Funcoes;

public class AcIniciarTeste extends AppCompatActivity {

    private FloatingActionButton btnParapTeste;
    private ArrayList<EstudarDto> lista = new ArrayList<>();
    private EstudarDto obj;
    private RadioButton rbResposta1;
    private RadioButton rbResposta2;
    private RadioButton rbResposta3;
    private RadioButton rbResposta4;
    private RadioButton rbResposta5;
    private TextView txtCategoria;
    private TextView txtPergunta;
    private Button btnProximo;
    private int index;
    private int indexMax;
    private  AlertDialog.Builder builder;
    private  AlertDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ac_iniciar_teste);
        txtCategoria = (TextView)findViewById(R.id.txtCategoria);
        txtPergunta = (TextView)findViewById(R.id.txtPergunta);
        btnProximo = (Button)findViewById(R.id.btnConfirmar);
        rbResposta1 = (RadioButton)findViewById(R.id.rbResposta1);
        rbResposta2 = (RadioButton)findViewById(R.id.rbResposta2);
        rbResposta3 = (RadioButton)findViewById(R.id.rbResposta3);
        rbResposta4 = (RadioButton)findViewById(R.id.rbResposta4);
        rbResposta5 = (RadioButton)findViewById(R.id.rbResposta5);

        index = 0;
        Bundle bundle = getIntent().getExtras();
        if((bundle != null) && (bundle.containsKey("Perguntas"))){
            lista = (ArrayList<EstudarDto>)bundle.getSerializable("Perguntas");
            obj = lista.get(index);
            CarregaPergunta(obj);
            indexMax = lista.size();
            if(lista.size() == 1){
                btnProximo.setText("Finalizar");
            }
        }

        btnParapTeste = (FloatingActionButton)findViewById(R.id.flPararTeste);
        btnParapTeste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MessageFinaliza("Aviso","Deseja realmente para o teste?");
            }
        });

        btnProximo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                View view = getLayoutInflater().inflate(R.layout.dialog_message_question,null);

                final Button btnSim = (Button) view.findViewById(R.id.btnSim) ;
                final Button btnNao = (Button) view.findViewById(R.id.btnNao) ;

                TextView txtTitulo = view.findViewById(R.id.txtTitulo);
                TextView txtDescricao = view.findViewById(R.id.txtDescricao);
                txtTitulo.setText("Confirmar");
                txtDescricao.setText("Confirma sua resposta?");

                builder.setView(view);
                final AlertDialog dialog = builder.create();

                btnSim.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                        index++;
                        if (index != indexMax ){
                            if ((indexMax - 1) == index){
                                btnProximo.setText("Finalizar");
                            }
                            String respostaSelecionada = RespostaSelecionada();
                            if(respostaSelecionada.trim().equals(obj.RespostaCerta)){
                                MessageAvisoResposta("Sucesso","Sua resposta esta correta!");
                            }
                            else{
                                MessageAvisoResposta("Erro","Sua resposta esta errada!");
                            }
                            try{
                                obj = lista.get(index);
                            }
                            catch(Exception e){
                                MessageAviso("Fim","Todas perguntas respondidas!");
                            }
                            CarregaPergunta(obj);
                        }
                        else
                        {
                            String respostaSelecionada = RespostaSelecionada();
                            if(respostaSelecionada.trim().equals(obj.RespostaCerta)){
                                MessageAvisoRespostafim("Sucesso","Sua resposta esta correta!");
                            }
                            else{
                                MessageAvisoRespostafim("Erro","Sua resposta esta errada!");
                            }

                        }
                    }
                });

                btnNao.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });
                dialog.show();

            }
        });
    }

    private void MessageAviso(String titulo,String msg){
        dialog = null;
        View view = getLayoutInflater().inflate(R.layout.dialog_message,null);
        Button acao = view.findViewById(R.id.btnAcao);
        TextView txtTitulo = view.findViewById(R.id.txtTitulo);
        TextView txtDescricao = view.findViewById(R.id.txtDescricao);
        acao.setText("ok");
        txtTitulo.setText(titulo);
        txtDescricao.setText(msg);

        dialog = new Funcoes().MessageBox(view);
        acao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
                finish();
            }
        });
    }

    private void MessageAvisoResposta(String titulo,String msg){
        dialog = null;
        View view = getLayoutInflater().inflate(R.layout.dialog_message,null);
        Button acao = view.findViewById(R.id.btnAcao);
        TextView txtTitulo = view.findViewById(R.id.txtTitulo);
        TextView txtDescricao = view.findViewById(R.id.txtDescricao);
        acao.setText("ok");
        txtTitulo.setText(titulo);
        txtDescricao.setText(msg);

        dialog = new Funcoes().MessageBox(view);
        acao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
    }

    private void MessageAvisoRespostafim(String titulo,String msg){
        dialog = null;
        View view = getLayoutInflater().inflate(R.layout.dialog_message,null);
        Button acao = view.findViewById(R.id.btnAcao);
        TextView txtTitulo = view.findViewById(R.id.txtTitulo);
        TextView txtDescricao = view.findViewById(R.id.txtDescricao);
        acao.setText("ok");
        txtTitulo.setText(titulo);
        txtDescricao.setText(msg);

        dialog = new Funcoes().MessageBox(view);
        acao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
                MessageAviso("Fim","Todas perguntas respondidas!");
            }
        });
    }

    private void MessageFinaliza(String titulo,String msg){
        builder = new AlertDialog.Builder(this);
        dialog = null;
        View view = getLayoutInflater().inflate(R.layout.dialog_message_question,null);

        final Button btnSim = (Button) view.findViewById(R.id.btnSim) ;
        final Button btnNao = (Button) view.findViewById(R.id.btnNao) ;

        TextView txtTitulo = view.findViewById(R.id.txtTitulo);
        TextView txtDescricao = view.findViewById(R.id.txtDescricao);
        txtTitulo.setText(titulo);
        txtDescricao.setText(msg);

        builder.setView(view);
        dialog = builder.create();

        btnSim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
                finish();
            }
        });

        btnNao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        dialog.show();
    }

    private void CarregaPergunta(EstudarDto obj){
        txtCategoria.setText(obj.Categoria);
        txtPergunta.setText(obj.Pergunta);

        if(obj.Resposta1.trim().length() > 0){
            rbResposta1.setVisibility(View.VISIBLE);
            rbResposta1.setText(obj.Resposta1.trim());
        }
        else{
            rbResposta1.setVisibility(View.GONE);
        }

        if(obj.Resposta2.trim().length() > 0){
            rbResposta2.setVisibility(View.VISIBLE);
            rbResposta2.setText(obj.Resposta2.trim());
        }
        else{
            rbResposta2.setVisibility(View.GONE);
        }

        if(obj.Resposta3.trim().length() > 0){
            rbResposta3.setVisibility(View.VISIBLE);
            rbResposta3.setText(obj.Resposta3.trim());
        }
        else{
            rbResposta3.setVisibility(View.GONE);
        }

        if(obj.Resposta4.trim().length() > 0){
            rbResposta4.setVisibility(View.VISIBLE);
            rbResposta4.setText(obj.Resposta4.trim());
        }
        else{
            rbResposta4.setVisibility(View.GONE);
        }

        if(obj.Resposta5.trim().length() > 0){
            rbResposta5.setVisibility(View.VISIBLE);
            rbResposta5.setText(obj.Resposta5.trim());
        }
        else{
            rbResposta5.setVisibility(View.GONE);
        }
    }

    private String RespostaSelecionada(){
        boolean select = false;

        select = rbResposta1.isChecked();
        if(select == true){
            return "1";
        }

        select = rbResposta2.isChecked();
        if(select == true){
            return "2";
        }

        select = rbResposta3.isChecked();
        if(select == true){
            return "3";
        }

        select = rbResposta4.isChecked();
        if(select == true){
            return "4";
        }

        select = rbResposta5.isChecked();
        if(select == true){
            return "5";
        }

        return "";
    }
}

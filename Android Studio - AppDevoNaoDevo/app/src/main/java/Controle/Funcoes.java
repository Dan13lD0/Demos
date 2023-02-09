package Controle;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.app.devonaodevo.R;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static android.content.Context.NOTIFICATION_SERVICE;


public class Funcoes {

    public void SendNotification(String titulo,String msg,Context principal, Intent intent){
        final String CHANNEL_ID = "001";
        PendingIntent pi= PendingIntent.getActivity(principal, 0, intent, 0);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID,"001", NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.setDescription("Devo e Não Devo");

            NotificationManager notificationManager = (NotificationManager)principal.getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);

            Notification.Builder builder= new Notification.Builder(principal,CHANNEL_ID);
            builder.setSmallIcon(R.drawable.ic_cifrao)
                    .setContentText(msg)
                    .setContentTitle(titulo)
                    .setContentIntent(pi)
                    .setPriority(Notification.PRIORITY_DEFAULT);

            NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(principal);
            notificationManagerCompat.notify(001,builder.build());

        }
        else{
            Notification.Builder builder = new Notification.Builder(principal);
            builder.setSmallIcon(R.mipmap.ic_launcher)
                    .setContentText(msg)
                    .setContentTitle(titulo)
                    .setContentIntent(pi)
                    .setPriority(Notification.PRIORITY_DEFAULT);

            NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(principal);
            notificationManagerCompat.notify(001,builder.build());

        }
    }

    public AlertDialog Wait(View view, Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(view);
        return builder.create();

    }

    public AlertDialog ChamarJanelaDialog(View view, Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(view);
        final AlertDialog dialog = builder.create();
        dialog.show();
        return dialog;
    }

    private DatePickerDialog.OnDateSetListener finalMSetDateListener = null;

    public void MessageBox(View view,String Titulo,String Mensagem){
            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
            View bview = ((Activity)view.getContext()).getLayoutInflater().inflate(R.layout.dialog_mensagem,null);
            final Button btnSim = (Button) bview.findViewById(R.id.btnSim) ;
            final TextView txtTitulo = (TextView) bview.findViewById(R.id.txtTitle) ;
            final TextView txtMsg= (TextView) bview.findViewById(R.id.txtMsg) ;

            txtTitulo.setText(Titulo);
            txtMsg.setText(Mensagem);

            builder.setView(bview);
            final AlertDialog dialog = builder.create();
            btnSim.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.cancel();
                }
            });

            dialog.show();

    }

    public void CarregaRecyclerViewSimple(RecyclerView recycler, Context context, RecyclerView.Adapter adapeter)  {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        recycler.setLayoutManager(linearLayoutManager);
        recycler.setAdapter(adapeter);
    }

    public String RightPad(String input, int length, String fill){
        if(input.trim().length() < length){
            String pad = input.trim() + String.format("%"+length+"s", "").replace(" ", fill);
            return pad.substring(0, length);
        }
        return input;
    }

    private  String WhereOrAnd(String sql){
        if (sql.trim().toUpperCase().contains("WHERE")){
            return " WHERE ";
        }
        return " AND ";
    }

    public String leftPad(String input, int length, String fill){

        if (input.trim().length() < length){
            for (int i = 0;i < (length - input.trim().length());i++){
                input = fill.trim() + input;
            }
        }
        return input.trim();
    }

    public void setDate(final EditText field, final ImageView btn){
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                DatePickerDialog dialog = new DatePickerDialog(v.getContext(),
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        finalMSetDateListener,
                        cal.get(Calendar.YEAR),
                        cal.get(Calendar.MONTH),
                        cal.get(Calendar.DAY_OF_MONTH));
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                dialog.show();
            }
        });



        finalMSetDateListener = new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                field.setText(new Funcoes().leftPad(String.valueOf(dayOfMonth), 2,"0") + "/" + new Funcoes().leftPad(String.valueOf(month + 1), 2,"0") + "/" + new Funcoes().leftPad(String.valueOf(year), 4,"0"));
            }
        };
    }

    public String FormataCampoValor(Double valor){
        return new DecimalFormat("###,###,###,###,###.00").format(valor);
    }

    public void FormataCampoValor(final EditText editText){
       editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
           @Override
           public void onFocusChange(View v, boolean hasFocus) {
               if (hasFocus == false){
                   if(editText.getText().toString().trim().length() > 0){
                       editText.setText(RetornaValorString(new DecimalFormat("###,###,###,###,###.00").format(Double.parseDouble(editText.getText().toString()))));
                   }
               }
           }
       });
   }

    public Double RetornaValorDecimal(String value){
        String valor = "";
        if(value.contains(",")){
            for (char a : value.toCharArray()) {
                if ((a == '0') || (a == '1') || (a == '2') || (a == '3') || (a == '4') || (a == '5') || (a == '6') || (a == '7') || (a == '8') || (a == '9') || (a == ',') ){
                    if (a == ','){
                        valor = valor + '.';
                    }
                    else
                    {
                        valor = valor + a;
                    }

                }
            }
        }
        else {
            for (char a : value.toCharArray()) {
                if ((a == '0') || (a == '1') || (a == '2') || (a == '3') || (a == '4') || (a == '5') || (a == '6') || (a == '7') || (a == '8') || (a == '9') || (a == '.') ){
                    valor = valor + a;
                }
            }
        }

        if (valor.length() > 0){
            return Double.parseDouble(valor);
        }
        return 0.0;
    }

    public String RetornaValorString(String value){
        String valor = "";
        for (char a : value.toCharArray()) {
            if ((a == '0') || (a == '1') || (a == '2') || (a == '3') || (a == '4') || (a == '5') || (a == '6') || (a == '7') || (a == '8') || (a == '9') || (a == ',') || (a == '.')){

                valor = valor + a;
            }
            else {
                valor = valor + '.';
            }
        }
        if (valor.length() > 0){
            return valor;
        }
        return "";
    }

    public Date ToDateSqLite(String value) throws ParseException {

        Date retorna = null;
        if (value.trim().length() > 0){
            try
            {
                retorna = new SimpleDateFormat("dd/MM/yyyy").parse(value.trim().substring(8,10) + "/" + value.trim().substring(5,7) + "/" + value.trim().substring(0,4));
            }
            catch (ParseException e){
                e.printStackTrace();
                String d  = e.getMessage();
            }
        }


        return retorna;
    }

    public Date ToDate(String value) throws ParseException {

        Date retorna = null;
        if (value.trim().length() > 0){
            try
            {
                retorna = new SimpleDateFormat("dd/MM/yyyy").parse(value.trim());
            }
            catch (ParseException e){
                e.printStackTrace();
                String d  = e.getMessage();
            }
        }


        return retorna;
    }

    public String getDateNow(){
        return new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime());
    }

    public String getDayNow(){
        return new SimpleDateFormat("dd").format(Calendar.getInstance().getTime());
    }


    public String getMonthNow(){
        return new SimpleDateFormat("MM").format(Calendar.getInstance().getTime());
    }


    public String getYearNow(){
        return new SimpleDateFormat("yyyy").format(Calendar.getInstance().getTime());
    }


    public String DateToString(Date date){
        if (date != null){
            return (String) DateFormat.format("dd/MM/yyyy",date.getTime());
        }
        return "";
    }

    public String DateSqliteToString(Date date){
        if (date != null){
            return (String) DateFormat.format("yyyy-MM-dd",date.getTime());
        }
        return "";
    }

    public Date addDay(Date date, int i) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_YEAR, i);
        return cal.getTime();
    }

    public Date addMonth(Date date, int i) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, i);
        return cal.getTime();
    }

    public Date addYear(Date date, int i) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.YEAR, i);
        return cal.getTime();
    }
}

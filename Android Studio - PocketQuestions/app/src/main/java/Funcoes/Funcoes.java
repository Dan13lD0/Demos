package Funcoes;

import android.app.AlertDialog;
import android.view.View;


public class Funcoes {

    public String toInitCap(String param) {
        if (param != null && param.length() > 0) {
            char[] charArray = param.toCharArray();
            charArray[0] = Character.toUpperCase(charArray[0]);
            return new String(charArray);
        } else {
            return "";
        }
    }

    public AlertDialog MessageBox(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setView(view);
        final AlertDialog dialog = builder.create();
        dialog.show();
        return dialog;
    }
}

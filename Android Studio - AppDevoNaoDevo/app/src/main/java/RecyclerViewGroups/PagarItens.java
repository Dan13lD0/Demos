package RecyclerViewGroups;



import android.os.Parcel;
import android.os.Parcelable;

public class PagarItens implements Parcelable {

    public final Long id;
    public final String descricao;
    public final long qtdParcelas;
    public final long parcelaAtual;
    public final String vlrPago;
    public final String vlrPagar;
    public final String vencimento;
    public final String total;
    public final Double totalGeral;


    public PagarItens(Long id,String descricao, String qtdParcelas, long parcelaAtual, String vlrPago, String vlrPagar, String vencimento, String total,Double totalGeral) {
       this.id = id;
        if(totalGeral <= 0.0){
            this.descricao = descricao;
            this.qtdParcelas = Long.parseLong(qtdParcelas);
            this.parcelaAtual = parcelaAtual;
            this.vlrPago = vlrPago;
            this.vlrPagar = vlrPagar;
            this.vencimento = vencimento;
            this.total = total;
        }
        else {
            this.descricao = "";
            this.qtdParcelas = 0;
            this.parcelaAtual = 0;
            this.vlrPago = "";
            this.vlrPagar = "";
            this.vencimento = "";
            this.total = "";
        }
        this.totalGeral = totalGeral;
    }


    protected PagarItens(Parcel in) {
        id = Long.parseLong(in.readString());
        descricao = in.readString();
        qtdParcelas = Long.parseLong(in.readString());
        parcelaAtual = Long.parseLong(in.readString());
        vlrPago = in.readString();
        vlrPagar = in.readString();
        vencimento = in.readString();
        total = in.readString();
        totalGeral = Double.parseDouble(in.readString());
    }

    public static final Creator<PagarItens> CREATOR = new Creator<PagarItens>() {
        @Override
        public PagarItens createFromParcel(Parcel in) {
            return new PagarItens(in);
        }

        @Override
        public PagarItens[] newArray(int size) {
            return new PagarItens[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(String.valueOf(id));
        dest.writeString(descricao);
        dest.writeString(String.valueOf(qtdParcelas));
        dest.writeString(String.valueOf(parcelaAtual));
        dest.writeString(vlrPago);
        dest.writeString(vlrPagar);
        dest.writeString(vencimento);
        dest.writeString(total);
        dest.writeString(String.valueOf(totalGeral));
    }
}



package RecyclerHistoricoDetalhado;

import android.os.Parcel;
import android.os.Parcelable;

import Dto.GrupoMesDto;

public class DetalhadoItens implements Parcelable {

    public final String IdCompra;
    public final String NroParcela;
    public final String DataCompra;
    public final String Descricao;
    public final String Parcelado;
    public final String DataVencimento;
    public final String DataPagamento ;
    public final Double ValorOriginal;
    public final Double ValorPagamento;
    public final Double TotalPagamento;
    public final Double Entrada;
    public final String Index;
    public final String Param1;
    public final String Param2;


    public DetalhadoItens(String idCompra,String nroParcela,String dataCompra, String descricao, String parcelado, String dataVencimento, String dataPagamento, Double valorOriginal, Double valorPagamento, Double totalPagamento,Double entrada,String index, String param1,String param2) {
        this.DataCompra = dataCompra;
        this.Descricao = descricao;
        this.Parcelado = parcelado;
        this.DataVencimento = dataVencimento;
        this.DataPagamento = dataPagamento;
        this.ValorOriginal = valorOriginal;
        this.ValorPagamento = valorPagamento;
        this.TotalPagamento = totalPagamento;
        this.Entrada = entrada;
        this.Index = index;
        this.IdCompra = idCompra;
        this.NroParcela = nroParcela;
        this.Param1 = param1;
        this.Param2 = param2;
    }

    protected DetalhadoItens(Parcel in) {
        DataCompra = in.readString();
        Descricao = in.readString();
        Parcelado = in.readString();
        DataVencimento = in.readString();
        DataPagamento = in.readString();
        ValorOriginal = in.readDouble();
        ValorPagamento = in.readDouble();
        TotalPagamento = in.readDouble();
        Entrada = in.readDouble();
        Index = in.readString();
        IdCompra= in.readString();
        NroParcela= in.readString();
        Param1 = in.readString();
        Param2= in.readString();
    }

    public static final Creator<DetalhadoItens> CREATOR = new Creator<DetalhadoItens>() {
        @Override
        public DetalhadoItens createFromParcel(Parcel in) {
            return new DetalhadoItens(in);
        }

        @Override
        public DetalhadoItens[] newArray(int size) {
            return new DetalhadoItens[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(DataCompra);
        dest.writeString(Descricao);
        dest.writeString(Parcelado);
        dest.writeString(DataVencimento);
        dest.writeString(DataPagamento);
        dest.writeDouble(ValorOriginal);
        dest.writeDouble(ValorPagamento);
        dest.writeDouble(TotalPagamento);
        dest.writeDouble(Entrada);
        dest.writeString(Index);
        dest.writeString(IdCompra);
        dest.writeString(NroParcela);
        dest.writeString(Param1);
        dest.writeString(Param2);
    }
}

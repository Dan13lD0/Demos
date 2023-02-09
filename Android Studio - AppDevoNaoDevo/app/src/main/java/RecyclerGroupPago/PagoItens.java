package RecyclerGroupPago;

import android.os.Parcel;
import android.os.Parcelable;

public class PagoItens implements Parcelable {

    public final String data;
    public final String tipo;

    public PagoItens(String data,String tipo) {
        this.data = data;
        this.tipo = tipo;
    }

    protected PagoItens(Parcel in) {
        data = in.readString();
        tipo = in.readString();
    }

    public static final Creator<PagoItens> CREATOR = new Creator<PagoItens>() {
        @Override
        public PagoItens createFromParcel(Parcel in) {
            return new PagoItens(in);
        }

        @Override
        public PagoItens[] newArray(int size) {
            return new PagoItens[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(data);
        dest.writeString(tipo);
    }
}

package Dto;

import java.io.Serializable;
import java.util.Date;

public class ContaDto implements Serializable {
    public Long Id;
    public String Descricao;
    public Date DataCompra;
    public String Parcelado;
    public Integer QtdParcela;
    public Integer ParcelaAtual;
    public Double Entrada;
    public Double Valor;
    public Double ValorParcela;
    public String Status;

}

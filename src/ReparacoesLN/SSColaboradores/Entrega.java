package ReparacoesLN.SSColaboradores;

import java.io.Serializable;

import ReparacoesLN.SSClientes.Equipamento;

public class Entrega extends Balcao {

    /**
     * Construtor para a classe entrega
     * Relativa à interação entre funcionário de balcão e cliente -> Entrega
     * 
     * @param equipamento Equipamento correspondente
     * @param funcionario Funcionário que efetoou
     */
    public Entrega(Equipamento equipamento, FuncionarioBalcao funcionario) {
        super(equipamento, funcionario);
    }
}
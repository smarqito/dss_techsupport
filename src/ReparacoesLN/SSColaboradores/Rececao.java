package ReparacoesLN.SSColaboradores;

import ReparacoesLN.SSClientes.Equipamento;

public class Rececao extends Balcao {

    /**
     * Construtor para a classe Rececao
     * Relativa à interação entre funcionário de balcão e cliente -> Receção
     * 
     * @param equipamento Equipamento correspondente
     * @param funcionario Funcionário que efetoou
     */
    public Rececao(Equipamento equipamento, FuncionarioBalcao funcionario) {
        super(equipamento, funcionario);
    }
}
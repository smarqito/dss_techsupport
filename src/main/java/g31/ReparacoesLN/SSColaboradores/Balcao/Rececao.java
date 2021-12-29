package g31.ReparacoesLN.SSColaboradores.Balcao;

import g31.ReparacoesLN.SSClientes.Equipamento;
import g31.ReparacoesLN.SSColaboradores.Colaboradores.FuncionarioBalcao;

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
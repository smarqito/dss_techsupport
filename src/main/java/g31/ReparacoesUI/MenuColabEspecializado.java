package g31.ReparacoesUI;

import g31.Middleware.OrcamentoNaoExisteException;
import g31.Middleware.PassoJaExisteException;
import g31.Middleware.ReparacaoExpressoJaExisteException;
import g31.ReparacoesLN.SSReparacoes.Orcamento.Orcamento;

import static g31.ReparacoesUI.MyUI.model;
import static g31.ReparacoesUI.MyUI.scin;

public class MenuColabEspecializado {
    public void menuColabEspecial() {
        Menu menu = new Menu(new String[] {
                "Criar Reparação Expresso",
                "Criar Passo de Reparação"
        });

        menu.setPreCondition(2, () -> model.getOrcamentosAtivos().size() > 0);

        // Registar os handlers das transições
        menu.setHandler(1, this::criarRepXpresso);
        menu.setHandler(2, this::criarPassoRep);

        menu.runOnce();
    }

    private void criarRepXpresso() {

        try {
            System.out.println("Insira o nome da reparação expresso a adicionar: ");
            String nome = scin.nextLine();
            System.out.println("Insira o preço: ");
            double preco = scin.nextDouble();
            System.out.println("Insira o tempo estimado");
            int tempo = scin.nextInt();
            model.registarRepExpresso(nome, tempo, preco);
            System.out.println("Reparação expresso criada com sucesso!");

        } catch (ReparacaoExpressoJaExisteException e) {
            System.out.println("A reparação expresso já existe!");
        }
    }

    private void criarPassoRep() {

        System.out.println("Insira o identificador do orçamento a atualizar: ");
        String orcId = scin.nextLine();

        try {
            Orcamento orc = model.getOrcamento(orcId);
            System.out.println("Plano de trabalhos do orçamento: "+orc.getPT().toString());

        } catch (OrcamentoNaoExisteException e) {

            System.out.println("O orçamento com identificador"+orcId+"não existe!");            
        }

        this.adicionarPasso(orcId);
    }

    public void adicionarPasso(String orcId) {

        try {
            System.out.println("Insira nome do passo a adicionar: ");
            String nome = scin.nextLine();
            System.out.println("Insira o material a adicionar: ");
            String material = scin.nextLine();
            System.out.println("Insira quantidade de material: ");
            int quantidade = scin.nextInt();
            System.out.println("Insira custo total do material: ");
            double custo = scin.nextDouble();
            System.out.println("Insira o tempo estimado: ");
            int tempo = scin.nextInt();
            model.criarPasso(orcId, nome, material, tempo, quantidade, custo);
            System.out.println("Passo criado com sucesso");
        } catch (PassoJaExisteException e) {
            System.out.println("O passo a adicionar já existe no plano de trabalhos!");
        }

    }
}

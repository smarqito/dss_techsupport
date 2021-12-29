package g31.ReparacoesUI;

import g31.Middleware.*;
import g31.ReparacoesLN.SSClientes.EstadoEquipamento;
import g31.ReparacoesLN.SSReparacoes.OrcamentoEstado;
import g31.ReparacoesLN.SSReparacoes.ReparacaoEstado;
import static g31.ReparacoesUI.MyUI.model;
import static g31.ReparacoesUI.MyUI.scin;

public class MenuFuncBalcao {
    public void menuFuncionarioBalcao() {
        Menu menu = new Menu(new String[] {
                "Registar cliente",
                "Registar Equipamento",
                "Registar pedido de orçamento",
                "Confirmar orçamento",
                "Registar pedido de Reparação Expresso",
                "Registar entrega do equipamento"
        });

        // Registar os handlers das transições
        menu.setHandler(1, this::registaCliente);
        menu.setHandler(2, this::registaEquipamento);
        menu.setHandler(3, this::pedidoOrcamento);
        menu.setHandler(4, this::confirmarOrcamento);
        menu.setHandler(5, this::pedidoRepXpresso);
        menu.setHandler(6, this::registarEntregaEquip);

        menu.run();
    }

    private void registaCliente() {
        try {
            System.out.println("Insira o nif do cliente: ");
            String nif = scin.nextLine();
            System.out.println("Insira o numero do cliente: ");
            String numero = scin.nextLine();
            System.out.println("Insira o email do cliente: ");
            String email = scin.nextLine();
            model.registaCliente(nif, numero, email);
            System.out.println("Cliente registado!");
        } catch (ClienteJaExisteException e) {
            e.printStackTrace();
        }
    }

    private void registaEquipamento() {
        try {
            System.out.println("Insira o nif do cliente: ");
            String nif = scin.nextLine();
            System.out.println("Insira a marca do equipamento: ");
            String marca = scin.nextLine();
            System.out.println("Insira o código de registo do equipamento: ");
            String codR = scin.nextLine();
            model.registaEquipamento(codR, marca, nif);
        } catch (ClienteNaoExisteException | EquipamentoJaAssociadoException e) {
            e.printStackTrace();
        }
    }

    private void pedidoOrcamento() {
        try {
            System.out.println("Insira o nif do cliente: ");
            String nif = scin.nextLine();
            System.out.println("Insira o identificador do equipamento: ");
            String eqId = scin.nextLine();
            System.out.println("Insira a descrição do orçamento: ");
            String desc = scin.nextLine();
            model.registarOrcamento(nif, eqId, desc);
            System.out.println("Pedido de Orcamento efetuado");
        } catch (EquipamentoNaoExisteException e) {
            e.printStackTrace();
        }
    }

    private void confirmarOrcamento() {
        System.out.println("Insira o identificador do Orcamento: ");
        String id = scin.nextLine();
        Menu menu = new Menu(new String[] {
                "Confirmar Orcamento",
                "Recusar Orcamento"
        });

        menu.setHandler(1, () -> confirmaOrc(id));
        menu.setHandler(2, () -> recusaOrc(id));

        menu.runOnce();
    }

    private void confirmaOrc(String id) {
        try {
            model.alterarEstadoOrc(id, OrcamentoEstado.aceite);
        } catch (EstadoOrcNaoEValidoException | ColaboradorNaoExisteException | ColaboradorNaoTecnicoException
                | OrcamentoNaoExisteException | NaoExisteDisponibilidadeException | TecnicoNaoTemAgendaException e) {
            e.printStackTrace();
        }
    }

    private void recusaOrc(String id) {
        try {
            model.alterarEstadoOrc(id, OrcamentoEstado.arquivado);
        } catch (EstadoOrcNaoEValidoException | OrcamentoNaoExisteException | ColaboradorNaoTecnicoException
                | ColaboradorNaoExisteException | NaoExisteDisponibilidadeException | TecnicoNaoTemAgendaException e) {
            e.printStackTrace();
        }
    }

    private void pedidoRepXpresso() {
        try {
            System.out.println("Insira o tipo de reparação expresso: ");
            String tipo = scin.nextLine();
            System.out.println("Insira o identificador do equipamento: ");
            String eqId = scin.nextLine();
            model.addRepExpresso(eqId, tipo);
        } catch (EquipamentoNaoExisteException | ReparacaoNaoExisteException | NaoExisteDisponibilidadeException
                | ColaboradorNaoTecnicoException | ColaboradorNaoExisteException | TecnicoNaoTemAgendaException e) {
            e.printStackTrace();
        }
    }

    private void registarEntregaEquip() {
        try {
            System.out.println("Insira o identificador do equipamento: ");
            String eqId = scin.nextLine();
            System.out.println(("Insira identificador da reparação"));
            String repId = scin.nextLine();
            model.alteraEstadoEq(eqId, EstadoEquipamento.entregue);
            model.alterarEstadoRep(repId, ReparacaoEstado.pago);
        } catch (EquipamentoNaoExisteException | ReparacaoNaoExisteException e) {
            e.printStackTrace();
        }
    }
}

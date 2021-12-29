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
        String nif = null;
        try {
            System.out.println("Insira o nif do cliente: ");
            nif = scin.nextLine();
            System.out.println("Insira o numero do cliente: ");
            String numero = scin.nextLine();
            System.out.println("Insira o email do cliente: ");
            String email = scin.nextLine();
            model.registaCliente(nif, numero, email);
            System.out.println("Cliente registado!");
        } catch (ClienteJaExisteException e) {
            System.out.println("Já existe um cliente registado com o nif: " + nif);
        }
    }

    private void registaEquipamento() {
        String nif = null, codR = null;
        try {
            System.out.println("Insira o nif do cliente: ");
            nif = scin.nextLine();
            System.out.println("Insira a marca do equipamento: ");
            String marca = scin.nextLine();
            System.out.println("Insira o código de registo do equipamento: ");
            codR = scin.nextLine();
            model.registaEquipamento(codR, marca, nif);
        } catch (ClienteNaoExisteException e) {
            System.out.println("Não existe cliente com nif: " + nif);
        } catch (EquipamentoJaAssociadoException e) {
            System.out.println("Já existe um equipamento registado com o codigo de registo: " + codR);
        }
    }

    private void pedidoOrcamento() {
        String eqId = null;
        try {
            System.out.println("Insira o nif do cliente: ");
            String nif = scin.nextLine();
            System.out.println("Insira o identificador do equipamento: ");
            eqId = scin.nextLine();
            System.out.println("Insira a descrição do orçamento: ");
            String desc = scin.nextLine();
            model.registarOrcamento(nif, eqId, desc);
            System.out.println("Pedido de Orcamento efetuado");
        } catch (EquipamentoNaoExisteException e) {
            System.out.println("Não existe equipamento com o identificador: " + eqId);
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
        } catch (ColaboradorNaoExisteException e) {
            e.printStackTrace();
        } catch (EstadoOrcNaoEValidoException e) {
            e.printStackTrace();
        } catch (ColaboradorNaoTecnicoException e) {
            e.printStackTrace();
        } catch (OrcamentoNaoExisteException e) {
            e.printStackTrace();
        } catch (NaoExisteDisponibilidadeException e) {
            e.printStackTrace();
        } catch (TecnicoNaoTemAgendaException e) {
            e.printStackTrace();
        }
    }

    private void recusaOrc(String id) {
        try {
            model.alterarEstadoOrc(id, OrcamentoEstado.arquivado);
        } catch (ColaboradorNaoExisteException e) {
            e.printStackTrace();
        } catch (EstadoOrcNaoEValidoException e) {
            e.printStackTrace();
        } catch (ColaboradorNaoTecnicoException e) {
            e.printStackTrace();
        } catch (OrcamentoNaoExisteException e) {
            e.printStackTrace();
        } catch (NaoExisteDisponibilidadeException e) {
            e.printStackTrace();
        } catch (TecnicoNaoTemAgendaException e) {
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
        } catch (ColaboradorNaoExisteException e) {
            e.printStackTrace();
        } catch (EquipamentoNaoExisteException e) {
            e.printStackTrace();
        } catch (ColaboradorNaoTecnicoException e) {
            e.printStackTrace();
        } catch (NaoExisteDisponibilidadeException e) {
            e.printStackTrace();
        } catch (TecnicoNaoTemAgendaException e) {
            e.printStackTrace();
        } catch (ReparacaoNaoExisteException e) {
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
        } catch (EquipamentoNaoExisteException e) {
            e.printStackTrace();
        } catch (ReparacaoNaoExisteException e) {
            e.printStackTrace();
        }
    }
}

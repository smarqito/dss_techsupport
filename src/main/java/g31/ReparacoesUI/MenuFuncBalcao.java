package g31.ReparacoesUI;

import g31.Middleware.*;
import g31.ReparacoesLN.SSClientes.EstadoEquipamento;
import g31.ReparacoesLN.SSReparacoes.Orcamento.OrcamentoEstado;
import g31.ReparacoesLN.SSReparacoes.Reparacao.ReparacaoEstado;

import static g31.ReparacoesUI.MyUI.model;
import static g31.ReparacoesUI.MyUI.scin;

public class MenuFuncBalcao {
    public void menuFuncionarioBalcao(String id) {
        Menu menu = new Menu(new String[] {
                "Registar Cliente",
                "Registar Equipamento",
                "Registar Pedido de Orçamento",
                "Confirmar Orçamento",
                "Registar Pedido de Reparação Expresso",
                "Registar Entrega do Equipamento"
        });

        // Registar os handlers das transições
        menu.setHandler(1, this::registaCliente);
        menu.setHandler(2, this::registaEquipamento);
        menu.setHandler(3, () -> pedidoOrcamento(id));
        menu.setHandler(4, this::confirmarOrcamento);
        menu.setHandler(5, () -> pedidoRepXpresso(id));
        menu.setHandler(6, () -> registarEntregaEquip(id));

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
            System.out.println("Cliente registado com o nif:" + nif);
        } catch (ClienteJaExisteException e) {
            System.out.println("Já existe um cliente registado com o nif: " + nif);
        }
    }

    private void registaEquipamento() {
        String nif = null, codR = null, id = null;
        try {
            System.out.println("Insira o nif do cliente: ");
            nif = scin.nextLine();
            System.out.println("Insira a marca do equipamento: ");
            String marca = scin.nextLine();
            System.out.println("Insira o código de registo do equipamento: ");
            codR = scin.nextLine();
            id = model.registaEquipamento(codR, marca, nif);
            System.out.println("Equipamento registado com o identificador: " + id);
        } catch (ClienteNaoExisteException e) {
            System.out.println("Não existe cliente com nif: " + nif);
        } catch (EquipamentoJaAssociadoException e) {
            System.out.println("Já existe um equipamento registado com o codigo de registo: " + codR);
        }
    }

    private void pedidoOrcamento(String funcId) {
        String eqId = null, nif = null;
        try {
            System.out.println("Insira o nif do cliente: ");
            nif = scin.nextLine();
            System.out.println("Insira o identificador do equipamento: ");
            eqId = scin.nextLine();
            System.out.println("Insira a descrição do orçamento: ");
            String desc = scin.nextLine();
            String orcId = model.registarOrcamento(nif, eqId, desc, funcId);
            System.out.println("Pedido de Orcamento registado com o id: " + orcId);
        } catch (EquipamentoNaoExisteException e) {
            System.out.println("Não existe equipamento com o identificador: " + eqId);
        } catch (EquipamentoNaoAssociadoAoCliente e) {
            System.out.println("Equipamento  " + eqId + " não está associado ao cliente com o nif: " + nif);
        } catch (ColaboradorNaoExisteException e) {
            System.out.println("Não existe o colaborador");
        } catch (ColaboradorNaoFuncBalcao e) {
            System.out.println("O id inserido nao corresponde a um funcionario de balcao: " + funcId);
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
            System.out.println("Orçamento " + id + " confirmado!");
        } catch (ColaboradorNaoExisteException e) {
            System.out.println("Não existe o colaborador");
        } catch (EstadoOrcNaoEValidoException e) {
            System.out.println("Estado de orçamento não é válido");
        } catch (ColaboradorNaoTecnicoException e) {
            System.out.println("Colaborador não é um técnico");
        } catch (OrcamentoNaoExisteException e) {
            System.out.println("Não existe orçamento com o identificador: " + id);
        } catch (NaoExisteDisponibilidadeException e) {
            System.out.println("Não existe disponiblidade para realizar o orçamento");
        } catch (TecnicoNaoTemAgendaException e) {
            System.out.println("Técnico não tem agenda");
        }
    }

    private void recusaOrc(String id) {
        try {
            model.alterarEstadoOrc(id, OrcamentoEstado.arquivado);
            System.out.println("Orçamento " + id + " recusado!");
        } catch (ColaboradorNaoExisteException e) {
            System.out.println("Não existe o colaborador");
        } catch (EstadoOrcNaoEValidoException e) {
            System.out.println("Estado de orçamento não é válido");
        } catch (ColaboradorNaoTecnicoException e) {
            System.out.println("Colaborador não é um técnico");
        } catch (OrcamentoNaoExisteException e) {
            System.out.println("Não existe orçamento com o identificador: " + id);
        } catch (NaoExisteDisponibilidadeException e) {
            System.out.println("Não existe disponiblidade para realizar o orçamento");
        } catch (TecnicoNaoTemAgendaException e) {
            System.out.println("Técnico não tem agenda");
        }
    }

    private void pedidoRepXpresso(String funcId) {
        String eqId = null, tipo = null, id = null;
        try {
            System.out.println("Insira o tipo de reparação expresso: ");
            tipo = scin.nextLine();
            System.out.println("Insira o identificador do equipamento: ");
            eqId = scin.nextLine();
            id = model.addRepExpresso(eqId, tipo, funcId);
            System.out.println("Pedido de reparação com o id " + id + " efetuado com sucesso!");
        } catch (ColaboradorNaoExisteException e) {
            System.out.println("Não existe o colaborador");
        } catch (EquipamentoNaoExisteException e) {
            System.out.println("Não existe equipamento com o identificador: " + eqId);
        } catch (ColaboradorNaoTecnicoException e) {
            System.out.println("Colaborador não é um técnico");
        } catch (NaoExisteDisponibilidadeException e) {
            System.out.println("Não existe disponiblidade para realizar a reparação de imediato");
        } catch (TecnicoNaoTemAgendaException e) {
            System.out.println("Técnico não tem agenda");
        } catch (ReparacaoNaoExisteException e) {
            System.out.println("Não existe a reparação expresso: " + tipo);
        } catch (ColaboradorNaoFuncBalcao e) {
            System.out.println("O id inserido nao corresponde a um funcionario de balcao: " + funcId);
        }
    }

    private void registarEntregaEquip(String funcId) {
        String eqId = null, repId = null;
        try {
            System.out.println("Insira o identificador do equipamento: ");
            eqId = scin.nextLine();
            System.out.println(("Insira identificador da reparação"));
            repId = scin.nextLine();
            model.alteraEstadoEq(eqId, EstadoEquipamento.entregue, funcId);
            model.alterarEstadoRep(repId, ReparacaoEstado.pago);
            System.out.println("Equipamento " + eqId + " entregue e reparação " + repId + " paga!");
        } catch (EquipamentoNaoExisteException e) {
            System.out.println("Não existe equipamento com o identificador: " + eqId);
        } catch (ReparacaoNaoExisteException e) {
            System.out.println("Não existe a reparação: " + repId);
        } catch (ColaboradorNaoExisteException e) {
            System.out.println("Não existe o colaborador");
        }
    }
}

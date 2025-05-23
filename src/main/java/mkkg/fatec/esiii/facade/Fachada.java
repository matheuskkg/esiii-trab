package mkkg.fatec.esiii.facade;

import mkkg.fatec.esiii.daos.*;
import mkkg.fatec.esiii.domain.EntidadeDominio;
import mkkg.fatec.esiii.domain.FachadaRequestDTO;
import mkkg.fatec.esiii.domain.FachadaResponseDTO;
import mkkg.fatec.esiii.strategies.IStrategy;
import mkkg.fatec.esiii.strategies.cartao.DefinirCartaoCreditoPreferencial;
import mkkg.fatec.esiii.strategies.cliente.CriptografarSenha;
import mkkg.fatec.esiii.strategies.cliente.ValidarConfirmarSenha;
import mkkg.fatec.esiii.strategies.cliente.ValidarExistenciaCliente;
import mkkg.fatec.esiii.strategies.cliente.ValidarForcaSenha;
import mkkg.fatec.esiii.strategies.endereco.ValidarMinimoEnderecoCobranca;
import mkkg.fatec.esiii.strategies.endereco.ValidarMinimoEnderecoEntrega;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Fachada extends AbstractFachada implements IFachada {

    private List<String> mensagens;

    private FachadaResponseDTO response;

    public Fachada(BandeiraDAO bandeiraDAO, CartaoCreditoDAO cartaoCreditoDAO, CidadeDAO cidadeDAO, ClienteDAO clienteDAO, EnderecoDAO enderecoDAO, EstadoDAO estadoDAO, PaisDAO paisDAO, TelefoneDAO telefoneDAO, DefinirCartaoCreditoPreferencial definirCartaoCreditoPreferencial, CriptografarSenha criptografarSenha, ValidarConfirmarSenha validarConfirmarSenha, ValidarExistenciaCliente validarExistenciaCliente, ValidarForcaSenha validarForcaSenha, ValidarMinimoEnderecoCobranca validarMinimoEnderecoCobranca, ValidarMinimoEnderecoEntrega validarMinimoEnderecoEntrega) {
        super(bandeiraDAO, cartaoCreditoDAO, cidadeDAO, clienteDAO, enderecoDAO, estadoDAO, paisDAO, telefoneDAO, definirCartaoCreditoPreferencial, criptografarSenha, validarConfirmarSenha, validarExistenciaCliente, validarForcaSenha, validarMinimoEnderecoCobranca, validarMinimoEnderecoEntrega);
    }

    private void processarRegras(EntidadeDominio entidade, List<IStrategy> regrasEntidade) {
        for (IStrategy rn : regrasEntidade) {
            String res = rn.processar(entidade);

            if (res != null) {
                mensagens.add(res);
            }
        }
    }

    @Override
    public FachadaResponseDTO salvar(FachadaRequestDTO request) {
        super.inicializarSalvar();

        EntidadeDominio entidade = request.getEntidade();

        mensagens = new ArrayList<>();
        response = new FachadaResponseDTO();

        String nomeEntidade = entidade.getClass().getName();
        List<IStrategy> regrasEntidade = rns.get(nomeEntidade);
        IDAO dao = daos.get(nomeEntidade);

        processarRegras(entidade, regrasEntidade);

        if (mensagens.isEmpty()) {
            dao.salvar(entidade);
        } else {
            response.setMensagens(mensagens);
        }

        return response;
    }

    @Override
    public FachadaResponseDTO alterar(FachadaRequestDTO request) {
        return null;
    }

    @Override
    public FachadaResponseDTO excluir(FachadaRequestDTO request) {
        return null;
    }

    @Override
    public FachadaResponseDTO consultar(FachadaRequestDTO request) {
        return null;
    }
}

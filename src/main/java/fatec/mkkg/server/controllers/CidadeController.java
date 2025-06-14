package fatec.mkkg.server.controllers;

import fatec.mkkg.server.domain.FachadaRequestDTO;
import fatec.mkkg.server.domain.FachadaResponseDTO;
import fatec.mkkg.server.domain.OperacaoCRUD;
import fatec.mkkg.server.domain.endereco.Cidade;
import fatec.mkkg.server.domain.endereco.Estado;
import fatec.mkkg.server.facade.Fachada;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cidade")
@CrossOrigin
public class CidadeController {

    @Autowired
    private Fachada fachada;

    @GetMapping
    public ResponseEntity consultar(@RequestParam(name = "estado") String nomeEstado) {
        Estado estado = new Estado(nomeEstado);
        Cidade cidade = new Cidade(estado);

        FachadaRequestDTO fachadaRequestDTO = new FachadaRequestDTO(cidade, OperacaoCRUD.CONSULTAR);

        FachadaResponseDTO fachadaResponseDTO = fachada.consultar(fachadaRequestDTO);

        HttpStatus responseStatus = HttpStatus.OK;

        return ResponseEntity.status(responseStatus).body(fachadaResponseDTO);
    }
}
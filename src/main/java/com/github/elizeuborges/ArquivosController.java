package com.github.elizeuborges;

import java.io.IOException;
import java.net.URI;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/{appId}/arquivo")
public class ArquivosController {

	@Autowired
	private ArquivoRepository arquivoRepository;

	@Autowired
	private AplicacaoRepository aplicacaoRepository;
	
	@GetMapping
	public Collection<Arquivo> getAll(@PathVariable String appId){
		this.validarAplicacao(appId);
		return arquivoRepository.findByAplicacaoNome(appId);
	}

	@GetMapping("/{id}")
	public Arquivo getById(@PathVariable String appId, @PathVariable Long id){
		this.validarAplicacao(appId);
		return arquivoRepository.findOne(id);
	}
	
	@PostMapping
	public ResponseEntity<Arquivo> add(@PathVariable String appId, @RequestParam("file") MultipartFile file) throws IOException{
		Aplicacao aplicacao = validarAplicacao(appId);
        Arquivo arquivo = arquivoRepository.save(new Arquivo(aplicacao, file.getOriginalFilename(), file.getBytes()));
        
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                	.path("/{id}")
                	.port(8080)
                	.build()
                .expand(arquivo.getId())
                .toUri();
        return ResponseEntity
        		.created(location)
        		.body(arquivo);
	}

	private Aplicacao validarAplicacao(String nome) {
		return this.aplicacaoRepository.findByNome(nome)
			.orElseThrow(() -> new AplicacaoNaoEncontradaException(nome));
	}
	
}

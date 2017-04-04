package com.github.elizeuborges;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.startsWith;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.File;
import java.nio.file.Files;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
public class ArquivosControllerTest {

	@Autowired
    private MockMvc mockMvc;

    @Autowired
    private MediaType mediaType;
    
    @Autowired
    private AplicacaoRepository aplicacaoRepository;

    @Autowired
    private ArquivoRepository arquivoRepository;

    @Autowired 
    private TermoRepository termoRepository;
    
	private String appName = "appTest";

	private List<Arquivo> arquivos;

	@Value("classpath:first.txt")
	private File arquivoUm;

	@Value("classpath:second.txt")
	private File arquivoDois;
	
	@Before
	public void setUp() throws Exception {
		arquivoRepository.deleteAllInBatch();
		termoRepository.deleteAllInBatch();
		aplicacaoRepository.deleteAllInBatch();
		
		Aplicacao aplicacao = new Aplicacao(appName);
		aplicacaoRepository.save(aplicacao);
		
		arquivos = Stream.of(arquivoUm, arquivoDois)
				.map(toArquivo(aplicacao))
				.collect(toList());
		
		arquivoRepository.save(arquivos);
	}

	private Function<File, Arquivo> toArquivo(Aplicacao aplicacao) {
		return Execute.unchecked((path) -> new Arquivo(aplicacao, path.getName(), Files.readAllBytes(path.toPath())));
	}

	@Test
    public void shouldListAllFiles() throws Exception {
        this.mockMvc.perform(get("/{appId}/arquivo", appName))
                .andExpect(status().isOk())
                .andExpect(content().contentType(mediaType))
                .andExpect(jsonPath("$", hasSize(2)))

                .andExpect(jsonPath("$[0].id", is(this.arquivos.get(0).getId().intValue())))
                .andExpect(jsonPath("$[0].nome", is(this.arquivos.get(0).getNome())))

                .andExpect(jsonPath("$[1].id", is(this.arquivos.get(1).getId().intValue())))
                .andExpect(jsonPath("$[1].nome", is(this.arquivos.get(1).getNome())))
                ;
    }

	@Test
	public void deveSerPossivelCriarNovoArquivo() throws Exception {
		this.mockMvc.perform(fileUpload("/{appId}/arquivo", appName)
				.file(new MockMultipartFile("file", arquivoUm.getName(), "text/plain", Files.readAllBytes(arquivoUm.toPath()))))
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.id", is(notNullValue())))
			.andExpect(jsonPath("$.nome", is(arquivoUm.getName())))
			.andExpect(header().string("Location", startsWith("http://localhost:8080/"+appName+"/arquivo/")))
			;
	}
	
	@Test
	public void deveRetornarArquivoPorId() throws Exception {
		Arquivo arquivo = arquivos.get(0);
		this.mockMvc.perform(get("/{appId}/arquivo/{id}", appName, arquivo.getId()))
		.andExpect(status().isOk())
		.andExpect(content().contentType(mediaType))
		.andExpect(jsonPath("$.id", is(this.arquivos.get(0).getId().intValue())))
		.andExpect(jsonPath("$.nome", is(this.arquivos.get(0).getNome())))
		;
	}
	
}

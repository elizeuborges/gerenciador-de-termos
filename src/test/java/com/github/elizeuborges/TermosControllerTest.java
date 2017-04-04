package com.github.elizeuborges;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@WebAppConfiguration
public class TermosControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MediaType mediaType;
    
    @Autowired
    private MessageConverter messageConverter;
    
    @Autowired
    private AplicacaoRepository aplicacaoRepository;
    
    @Autowired 
    private TermoRepository termoRepository;
    
	private String appName = "appTeste";

	private List<Termo> termos;
	
	@Before
	public void setUp() throws Exception {
		
		termoRepository.deleteAllInBatch();
		aplicacaoRepository.deleteAllInBatch();

		Aplicacao aplicacao = new Aplicacao(appName);
		aplicacaoRepository.save(aplicacao);
		termos = Arrays.asList(new Termo(aplicacao, "Termo Um", null), new Termo(aplicacao, "Termo Dois", null));
		termoRepository.save(termos);
	}
	
	@Test
	public void applicationNotFound() throws Exception {
		mockMvc.perform(get("/aplicacaoquenaoexiste/termo/")
				.content(json(new Termo()))
                .contentType(mediaType))
				.andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is("não foi encontrada a aplicacao 'aplicacaoquenaoexiste'.")))
                ;
		;
	}

	@Test
    public void lerTodosTermos() throws Exception {
        mockMvc.perform(get("/" + appName + "/termo").accept(mediaType))
		        .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(mediaType))
                .andExpect(jsonPath("$", hasSize(2)))

                .andExpect(jsonPath("$[0].id", is(this.termos.get(0).getId().intValue())))
                .andExpect(jsonPath("$[0].descricao", is("Termo Um")))

                .andExpect(jsonPath("$[1].id", is(this.termos.get(1).getId().intValue())))
                .andExpect(jsonPath("$[1].descricao", is("Termo Dois")))
                ;
    }

	private String json(Object o) throws IOException {
       return messageConverter.toJson(o);
    }
	
}

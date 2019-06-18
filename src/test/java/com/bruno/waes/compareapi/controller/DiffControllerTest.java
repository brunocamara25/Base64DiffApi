package com.bruno.waes.compareapi.controller;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.bruno.waes.compareapi.entity.DataEntity;
import com.bruno.waes.compareapi.repository.DataRepositoryInterface;
import static com.bruno.waes.compareapi.util.Constants.*;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class DiffControllerTest {

	@Autowired
	private MockMvc mvc;

	@Autowired
	public DataRepositoryInterface dataRepository;

	@Autowired
	private WebApplicationContext webApplicationContext;
	
	
	@Before
	public void setup() throws Exception {
		this.mvc = webAppContextSetup(webApplicationContext).build();
		this.dataRepository.deleteAll();
	}
	
	
	@Test
	public void left() throws Exception {
		mvc.perform(MockMvcRequestBuilders.post("/v1/diff/1/left").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\n" + "  \"data\": " + "  \"ZGlmZiB0ZXN0ZSBmcm9tIGpzb24=\"" + "}")).andExpect(status().isOk()).andReturn();
		DataEntity data = dataRepository.findById(1L);
		Assert.assertThat(data.getId(), Matchers.is(1L));
		Assert.assertThat(data.getLeft(), Matchers.is("ZGlmZiB0ZXN0ZSBmcm9tIGpzb24="));
		Assert.assertThat(data.getRight(), Matchers.isEmptyOrNullString());
	}
	@Test
	public void right() throws Exception {
		mvc.perform(MockMvcRequestBuilders.post("/v1/diff/1/right").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\n" + "  \"data\": " + "  \"dGVzdGUgdGVzdGV0ZXN0ZSB0ZXN0ZXRlc3\"" + "}")).andExpect(status().isOk()).andReturn();
		DataEntity data = dataRepository.findById(1L);
		Assert.assertThat(data.getId(), Matchers.is(1L));
		Assert.assertThat(data.getRight(), Matchers.is("dGVzdGUgdGVzdGV0ZXN0ZSB0ZXN0ZXRlc3"));
		Assert.assertThat(data.getLeft(), Matchers.isEmptyOrNullString());
	}
	
	@Test
	public void leftAndRight() throws Exception {
		mvc.perform(MockMvcRequestBuilders.post("/v1/diff/1/left").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\n" + "  \"data\": " + "  \"ZGlmZiB0ZXN0ZSBmcm9tIGpzb24=\"" + "}")).andExpect(status().isOk()).andReturn();
		mvc.perform(MockMvcRequestBuilders.post("/v1/diff/1/right").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\n" + "  \"data\": " + "  \"dGVzdGUgdGVzdGV0ZXN0ZSB0ZXN0ZXRlc3\"" + "}")).andExpect(status().isOk()).andReturn();
		DataEntity data = dataRepository.findById(1L);
		Assert.assertThat(data.getId(), Matchers.is(1L));
		Assert.assertThat(data.getLeft(), Matchers.is("ZGlmZiB0ZXN0ZSBmcm9tIGpzb24="));
		Assert.assertThat(data.getRight(), Matchers.is("dGVzdGUgdGVzdGV0ZXN0ZSB0ZXN0ZXRlc3"));
		Assert.assertNotEquals(data.getLeft(), Matchers.isEmptyString());
		Assert.assertNotEquals(data.getRight(), Matchers.isEmptyString());
	}
	
	
	@Test
	public void isNotBase64Content() throws Exception {
		mvc.perform(MockMvcRequestBuilders.post("/v1/diff/1/right").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\n" + "  \"data\": " + "  \"164 145 163 164 145\"" + "}")).andExpect(status().isBadRequest()).andReturn();		
	}
	
	
	@Test
	public void equal() throws Exception {
		dataRepository.save(new DataEntity(1l, "dGVzdGUgdGVzdGV0ZXN0ZSB0ZXN0ZXRlc2", "dGVzdGUgdGVzdGV0ZXN0ZSB0ZXN0ZXRlc2"));
		mvc.perform(MockMvcRequestBuilders.get("/v1/diff/1").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.message", is(BASE64_EQUAL)))
				.andReturn();		
	}
	
	@Test
	public void different() throws Exception {
		dataRepository.save(new DataEntity(1l, "dGVzdGUgdGVzdGV0ZXN0ZSB0ZXN0ZXRlc3", "GV0ZXN0ZSB0ZXN0ZX"));
		mvc.perform(MockMvcRequestBuilders.get("/v1/diff/1").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
		.andExpect(jsonPath("$.message", is(BASE64_NOT_SAME_SIZE)))
		.andReturn();		
	}
	
	
	
	
}

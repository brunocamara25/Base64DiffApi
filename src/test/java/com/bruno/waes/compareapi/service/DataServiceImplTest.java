package com.bruno.waes.compareapi.service;

import static org.mockito.AdditionalAnswers.returnsFirstArg;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.bruno.waes.compareapi.entity.DataEntity;
import com.bruno.waes.compareapi.repository.DataRepositoryInterface;
import com.bruno.waes.compareapi.util.SideEnum;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class DataServiceImplTest {
	
	
	@InjectMocks
	private DataServiceImpl dataService;

	@Mock
	public DataRepositoryInterface repository;
	
	DataEntity data;
	
	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
	}
	

	@Test
	public void saveLeftTest() throws Exception {
		data = new DataEntity();
		data.setLeft("ZGlmZiB0ZXN0ZSBmcm9tIGpzb24=");
		Mockito.doReturn(null).when(repository).findById(Mockito.eq(1L));
		Mockito.doAnswer(returnsFirstArg()).when(repository).save(Mockito.any(DataEntity.class));
		String result = dataService.saveOrUpdate(data, SideEnum.LEFT);
		Assert.assertThat(result, Matchers.is("Data Sucessfuly Saved"));
		Assert.assertThat(data.getLeft(), Matchers.notNullValue());
	}
	
	@Test
	public void saveRightTest() throws Exception {
		data = new DataEntity();
		data.setRight("ZGlmZiB0ZXN0ZSBmcm9tIGpzb24=");
		Mockito.doReturn(null).when(repository).findById(Mockito.eq(1L));
		Mockito.doAnswer(returnsFirstArg()).when(repository).save(Mockito.any(DataEntity.class));
		String result = dataService.saveOrUpdate(data, SideEnum.RIGHT);
		Assert.assertThat(result, Matchers.is("Data Sucessfuly Saved"));
		Assert.assertThat(data.getRight(), Matchers.notNullValue());
	}
	
	@Test
	public void isNotValidBase64() throws Exception {
		Mockito.doReturn(null).when(repository).findById(Mockito.eq(1L));
		boolean result = dataService.isContentAValidBase64("164 145 163 164 145");
		Assert.assertFalse(result);
	}
	
	@Test
	public void isValidBase64() throws Exception {
		Mockito.doReturn(null).when(repository).findById(Mockito.eq(1L));
		boolean result = dataService.isContentAValidBase64("ZGlmZiB0ZXN0ZSBmcm9tIGpzb24=");
		Assert.assertTrue(result);
	}
	
	@Test
	public void diffNoDataFound() throws Exception {
		data = new DataEntity();
		data.setId(0);
		Mockito.doReturn(null).when(repository).findById(Mockito.eq(0L));
		String result = dataService.baseDataDiff(data);
		Assert.assertThat(result, Matchers.is("No data found"));
	}
	
	@Test
	public void diffMissingRightData() throws Exception {
		data = new DataEntity();
		data.setId(1);
		data.setRight(null);
		Mockito.doReturn(data).when(repository).findById(Mockito.eq(1L));
		String result = dataService.baseDataDiff(data);
		Assert.assertThat(result, Matchers.is("Base64 data missing"));
	}
	
	@Test
	public void diffMissingLeftData() throws Exception {
		data = new DataEntity();
		data.setId(1);
		data.setLeft(null);
		Mockito.doReturn(data).when(repository).findById(Mockito.eq(1L));
		String result = dataService.baseDataDiff(data);
		Assert.assertThat(result, Matchers.is("Base64 data missing"));
	}
	
	@Test
	public void diffEqualData() throws Exception {
		data = new DataEntity();
		data.setId(1);
		data.setLeft("DBVsbG8gd29ybGJK=");
		data.setRight("DBVsbG8gd29ybGJK=");
		Mockito.doReturn(data).when(repository).findById(Mockito.eq(1L));
		String result = dataService.baseDataDiff(data);
		Assert.assertThat(result, Matchers.is("Base64 data are equal"));
	}
	
	@Test
	public void differentSizeData() throws Exception {
		data = new DataEntity();
		data.setId(1);
		data.setLeft("DBVsbG8gd29ybGJK");
		data.setRight("DBVsbG8gd29ybGJK=");
		Mockito.doReturn(data).when(repository).findById(Mockito.eq(1L));
		String result = dataService.baseDataDiff(data);
		Assert.assertThat(result, Matchers.is("Base64 data have not same size."));
	}
	
	@Test
	public void differentOffSetData() throws Exception {
		data = new DataEntity();
		data.setId(1);
		data.setLeft("DBVsbG8gd29ybGJK=");
		data.setRight("dGVzdGUgdGVzdGV0Z");
		Mockito.doReturn(data).when(repository).findById(Mockito.eq(1L));
		String result = dataService.baseDataDiff(data);
		Assert.assertThat(result, Matchers.is("Base64 data got the same size, but their offsets are different: 0 1 3 4 6 9 10 11 12 14 15 16"));
	}
	
		
}

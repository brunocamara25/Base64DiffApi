package com.bruno.waes.compareapi.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bruno.waes.compareapi.entity.DataEntity;
import com.bruno.waes.compareapi.exception.InvalidContentException;
import com.bruno.waes.compareapi.exception.InvalidSideException;
import com.bruno.waes.compareapi.repository.DataRepositoryInterface;
import com.bruno.waes.compareapi.util.SideEnum;
import static com.bruno.waes.compareapi.util.Constants.*;



@Service
public class DataServiceImpl implements DataService{

    @Autowired
    DataRepositoryInterface dataRepository;

    //private static final Logger LOG = LoggerFactory.getLogger(DataServiceImpl.class);
    
    
    /**
	 * 
	 * Get all data from database
	 *
	 * @author Bruno Camara
	 * @return List<DataEntity>
	 * @throws Exception
	 */
    public List<DataEntity> getAllData() throws Exception{
        List<DataEntity> data = new ArrayList<DataEntity>();
        dataRepository.findAll().forEach(person -> data.add(person));
        return data;
    }

    /**
	 * 
	 * Get data from a specific Id on database
	 *
	 * @author Bruno Camara
	 * @param Id
	 * @return DataEntity
	 * @throws Exception
	 */
    public DataEntity getDataById(long id) throws Exception{
        return dataRepository.findById(id);
    }

    /**
	 * 
	 * Save or update data on database
	 *
	 * @author Bruno Camara
	 * @param dataInput
	 * @param side
	 * @return String
	 * @throws Exception
	 */
    public String saveOrUpdate(DataEntity dataInput, SideEnum side)throws Exception {

    	DataEntity dataBaseData = new DataEntity();
    	dataBaseData = dataRepository.findById(dataInput.getId());
    	if (dataBaseData == null) {
    		dataBaseData = new DataEntity();
    		dataBaseData.setId(dataInput.getId());
		}
    	if(SideEnum.LEFT.equals(side)) {
    		if (isContentAValidBase64(dataInput.getLeft())) {
    			dataBaseData.setLeft(dataInput.getLeft());
			} else {
				throw new InvalidContentException();
			}
		} else if(SideEnum.RIGHT.equals(side)) {
			if (isContentAValidBase64(dataInput.getRight())) {
    			dataBaseData.setRight(dataInput.getRight());
			} else {
				throw new InvalidContentException();
			}
		}else{
			throw new InvalidSideException();
		}
    	
    	dataRepository.save(dataBaseData);
    	return DATA_SUCESSFULY_SAVED;
    }

    /**
	 * 
	 * Delete data on database
	 *
	 * @author Bruno Camara
	 * @param Id
	 * @throws Exception
	 */
    public void delete(int id) {
    	dataRepository.deleteById(id);
    }
    
    
    /**
	 * 
	 * Find a difference on a base 64 data
	 *
	 * @author Bruno Camara
	 * @param dataInput
	 * @return String
	 * @throws Exception
	 */
    public String baseDataDiff(DataEntity dataInput) throws Exception{
		
		DataEntity document = getDataById(dataInput.getId());
		if (document == null) {
			return NO_DATA_FOUND;
		}

		if (!StringUtils.isNotBlank(document.getLeft()) || !StringUtils.isNotBlank(document.getRight())) {
			return BASE64_MISSING;
		}
		byte[] bytesLeft = document.getLeft().getBytes();
		byte[] bytesRight = document.getRight().getBytes();

		boolean blnResult = Arrays.equals(bytesLeft, bytesRight);

		String offsets = "";

		if (blnResult) {
			return BASE64_EQUAL;
		} else if (bytesLeft.length != bytesRight.length) {
			return BASE64_NOT_SAME_SIZE;
		} else {
			byte difference = 0;
			for (int index = 0; index < bytesLeft.length; index++) {
				difference = (byte) (bytesLeft[index] ^ bytesRight[index]);
				if (difference != 0) {
					offsets = offsets + " " + index;
				}
			}
		}
		return BASE64_SAME_SIZE_BUT_DIFFERENT_OFFSET + offsets;
	}
    
    /**
	 * 
	 * Verify if the data is based 64
	 *
	 * @author Bruno Camara
	 * @param content
	 * @return boolean
	 * @throws Exception
	 */
    public boolean isContentAValidBase64(String content) throws Exception{
        try {
            Base64.getDecoder().decode(content);
        } catch (IllegalArgumentException e) {
            return false;
        }
        return true;
    }
   

}
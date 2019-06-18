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
    
    public List<DataEntity> getAllData() {
        List<DataEntity> data = new ArrayList<DataEntity>();
        dataRepository.findAll().forEach(person -> data.add(person));
        return data;
    }

    public DataEntity getDataById(long id) {
        return dataRepository.findById(id);
    }

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

    public void delete(int id) {
    	dataRepository.deleteById(id);
    }
    
    public String baseDataDiff(DataEntity dataInput) {
		
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
    
    
    private boolean isContentAValidBase64(String content) {
        try {
            Base64.getDecoder().decode(content);
        } catch (IllegalArgumentException e) {
            return false;
        }
        return true;
    }
   

}
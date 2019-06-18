package com.bruno.waes.compareapi.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bruno.waes.compareapi.entity.DataEntity;
import com.bruno.waes.compareapi.entity.DataEntityResponse;
import com.bruno.waes.compareapi.entity.ObjectDataTransfer;
import com.bruno.waes.compareapi.service.DataServiceImpl;
import com.bruno.waes.compareapi.util.SideEnum;
import com.wordnik.swagger.annotations.ApiImplicitParam;
import com.wordnik.swagger.annotations.ApiImplicitParams;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;
import static com.bruno.waes.compareapi.util.Constants.*;

@RestController
public class DiffController {
	
	@Autowired
    DataServiceImpl dataServiceImpl;

	@RequestMapping("/test")
	@ResponseBody
	public String getUsers() {
		return "WAES Challenge :) !";
	}
	

  @ApiOperation(value = " ", notes = "Save a base64 data by id on the right size", response = DiffController.class, responseContainer = "List", tags = {"WaesJsonCompareApiChallenge"})
  @ApiImplicitParams(
		  {@ApiImplicitParam(name = "Content-Type", required = true, dataType = "Long", paramType = "header", defaultValue = "application/json"),
		   @ApiImplicitParam(name = "Content-Type", required = true, dataType = "String", paramType = "header", defaultValue = "application/json")}   
  )
  @ApiResponses(value = {
  		@ApiResponse(code = 200, message = "OK", response = DiffController.class),
          @ApiResponse(code = 201, message = "Created", response = DiffController.class),
          @ApiResponse(code = 400, message = "Bad request", response = DiffController.class),
          @ApiResponse(code = 401, message = "Unauthorized", response = DiffController.class),
          @ApiResponse(code = 403, message = "Forbidden", response = DiffController.class),
          @ApiResponse(code = 404, message = "Not Found", response = DiffController.class),
          @ApiResponse(code = 500, message = "The server encountered an unexpected condition which prevented it from fulfilling the request", response =  DiffController.class)})
    @PostMapping("/v1/diff/{id}/right")
	@PutMapping(produces="application/json")
    private @ResponseBody DataEntityResponse rightSide(@PathVariable Long id, @RequestBody ObjectDataTransfer data) throws Exception {
    
	  	DataEntity dataInput = new DataEntity(); 
	  	DataEntityResponse dataOutput = new DataEntityResponse(); 
    	
    	dataInput.setId(id);
    	dataInput.setRight(data.getData());
    	dataOutput.setMessage(dataServiceImpl.saveOrUpdate(dataInput,SideEnum.RIGHT));
    	dataOutput.setId(id);
    	dataOutput.setSide(RIGHT);
    	
		return dataOutput;
	}

  @ApiOperation(value = " ", notes = "Save a base64 data by id on the left size", response = DiffController.class, responseContainer = "List", tags = {"WaesJsonCompareApiChallenge"})
  @ApiImplicitParams(
		  {@ApiImplicitParam(name = "Content-Type", required = true, dataType = "Long", paramType = "header", defaultValue = "application/json"),
		   @ApiImplicitParam(name = "Content-Type", required = true, dataType = "String", paramType = "header", defaultValue = "application/json")}   
  )
  @ApiResponses(value = {
  		@ApiResponse(code = 200, message = "OK", response = DiffController.class),
          @ApiResponse(code = 201, message = "Created", response = DiffController.class),
          @ApiResponse(code = 400, message = "Bad request", response = DiffController.class),
          @ApiResponse(code = 401, message = "Unauthorized", response = DiffController.class),
          @ApiResponse(code = 403, message = "Forbidden", response = DiffController.class),
          @ApiResponse(code = 404, message = "Not Found", response = DiffController.class),
          @ApiResponse(code = 500, message = "The server encountered an unexpected condition which prevented it from fulfilling the request", response =  DiffController.class)})
    @PostMapping("/v1/diff/{id}/left")
	@PutMapping(produces="application/json")
    private @ResponseBody DataEntityResponse leftSide(@PathVariable Long id, @RequestBody ObjectDataTransfer data) throws Exception {
    
    	DataEntity dataInput = new DataEntity(); 
    	DataEntityResponse dataOutput = new DataEntityResponse(); 
    	
    	dataInput.setId(id);
    	dataInput.setLeft(data.getData());
    	dataOutput.setMessage(dataServiceImpl.saveOrUpdate(dataInput, SideEnum.LEFT));
    	dataOutput.setId(id);
    	dataOutput.setSide(LEFT);
    	
		return dataOutput;
	}
  
   	
  @ApiOperation(value = " ", notes = "List all diffs for a base64 string", response = DiffController.class, responseContainer = "List", tags = {"WaesJsonCompareApiChallenge"})
  @ApiImplicitParams(
		  {@ApiImplicitParam(name = "Content-Type", required = true, dataType = "Long", paramType = "header", defaultValue = "application/json")}   
  )
  @ApiResponses(value = {
  		@ApiResponse(code = 200, message = "OK", response = DiffController.class),
          @ApiResponse(code = 201, message = "Created", response = DiffController.class),
          @ApiResponse(code = 400, message = "Bad request", response = DiffController.class),
          @ApiResponse(code = 401, message = "Unauthorized", response = DiffController.class),
          @ApiResponse(code = 403, message = "Forbidden", response = DiffController.class),
          @ApiResponse(code = 404, message = "Not Found", response = DiffController.class),
          @ApiResponse(code = 500, message = "The server encountered an unexpected condition which prevented it from fulfilling the request", response =  DiffController.class)})
    @GetMapping("/v1/diff/{id}")
	@PutMapping(produces="application/json")
    private @ResponseBody DataEntityResponse diff(@PathVariable Long id) throws Exception {
    
    	DataEntity dataInput = new DataEntity(); 
    	DataEntityResponse dataOutput = new DataEntityResponse(); 
    	
    	dataInput.setId(id);
    	dataOutput.setMessage(dataServiceImpl.baseDataDiff(dataInput));
    	dataOutput.setId(id);
    	dataOutput.setSide("--");
    	
		return dataOutput;
	}
  
}

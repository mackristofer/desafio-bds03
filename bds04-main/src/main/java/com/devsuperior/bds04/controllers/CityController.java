package com.devsuperior.bds04.controllers;


import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.devsuperior.bds04.dto.CityDTO;
import com.devsuperior.bds04.services.CityService;

@RestController
@RequestMapping(value = "/cities")
public class CityController {
	
	@Autowired
	private CityService service;
	
	@GetMapping
	public ResponseEntity<Page<CityDTO>> findAll( 
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "12") Integer linesPerPage,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction,
			@RequestParam(value = "orderBy", defaultValue = "name") String orderBy
			){	
		
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		
		Page<CityDTO> list  = service.findAllPaged(pageRequest);
		
		return ResponseEntity.ok().body(list);
	}

	@PostMapping
	public ResponseEntity<CityDTO> insert(@Valid @RequestBody CityDTO dto){
		CityDTO newDto = service.insert(dto);
  		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newDto.getId()).toUri();
  		return ResponseEntity.created(uri).body(newDto);
  	}
	
}


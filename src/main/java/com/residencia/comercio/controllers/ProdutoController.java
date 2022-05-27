package com.residencia.comercio.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.residencia.comercio.entities.Produto;
import com.residencia.comercio.exceptions.NoSuchElementFoundException;
import com.residencia.comercio.services.ArquivoService;
import com.residencia.comercio.services.ProdutoService;

@RestController
@RequestMapping("/produto")
public class ProdutoController {
	@Autowired
	ProdutoService produtoService;
	
	@Autowired
    ArquivoService arquivoService;

	@PostMapping(value = "/produto-com-foto", consumes = { MediaType.APPLICATION_JSON_VALUE,
			 MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<Produto> saveProdutoComFoto(@RequestPart("produto") String produto, @RequestPart("file") MultipartFile file) {
    	Produto novoProduto = produtoService.saveProdutoComFoto(produto, file);
    	if(null == novoProduto)
    		return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    	else
    		return new ResponseEntity<>(novoProduto, HttpStatus.CREATED);
    }
	
	@GetMapping
	public ResponseEntity<List<Produto>> findAll(){
		List<Produto> produtoList = produtoService.findAll();
		if(produtoList.isEmpty())
			throw new NoSuchElementFoundException("Não foram encontrados Produtos");
		else
			return new ResponseEntity<>(produtoService.findAll(), HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Produto> findById(@PathVariable Integer id){
		Produto produto = produtoService.findById(id);
		if(null == produto)
			throw new NoSuchElementFoundException("Não foi encontrado Produto com o id: " + id);
		else
			return new ResponseEntity<>(produto, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<Produto> save(@RequestBody Produto produto){
		return new ResponseEntity<>(produtoService.save(produto), HttpStatus.CREATED);
	}
	
	@PutMapping
	public ResponseEntity<Produto> update(@RequestBody Produto produto){
		Produto produtoAtualizado = produtoService.update(produto);
		return new ResponseEntity<>(produtoAtualizado, HttpStatus.OK);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Produto> update(@PathVariable Integer id, @RequestBody Produto produto){
		Produto produtoAtualizado = produtoService.updateComId(produto, id);
		if(null == produtoAtualizado)
			return new ResponseEntity<>(produtoAtualizado, HttpStatus.BAD_REQUEST);
		else
			return new ResponseEntity<>(produtoAtualizado, HttpStatus.OK);
	}
	
	@DeleteMapping 
	public ResponseEntity<String> delete(Produto produto){
		produtoService.delete(produto);
		return new ResponseEntity<>("", HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}") 
	public ResponseEntity<String> deletePorId(Integer id){
		produtoService.deletePorId(id);
		return new ResponseEntity<>("", HttpStatus.OK);
	}
	
}

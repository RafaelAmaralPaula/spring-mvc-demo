package com.springmvcdemo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.springmvcdemo.model.Pessoa;
import com.springmvcdemo.repository.PessoaRepository;

@Component
@RequestMapping("/pessoas")
public class PessoaController {
		
	@Autowired
	private PessoaRepository pessoaRepository;
	
	
	@GetMapping("/listar")
	public ModelAndView listar(){
		ModelAndView view = new ModelAndView("listar.html");
		List<Pessoa> pessoa = pessoaRepository.findAll();
		
		view.addObject("pessoas", pessoa );
		
		return view;
	}
	
	@GetMapping("/salvar")
	public String paginaCadastro() {
		return "cadastroPessoas.html";
	}
	
	
	@PostMapping("/salvar")
	public String salvar(Pessoa pessoa) {
		 pessoaRepository.save(pessoa);
		
		return "redirect:/pessoas/listar";
	}
	
}

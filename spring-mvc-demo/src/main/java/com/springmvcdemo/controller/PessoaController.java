package com.springmvcdemo.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.springmvcdemo.model.Pessoa;
import com.springmvcdemo.repository.PessoaRepository;

@Component
@RequestMapping("/pessoas")
public class PessoaController {

	@Autowired
	private PessoaRepository pessoaRepository;

	@GetMapping("/listar")
	public ModelAndView listar() {
		ModelAndView view = new ModelAndView("listar.html");
		List<Pessoa> pessoa = pessoaRepository.findAll();

		view.addObject("pessoas", pessoa);

		return view;
	}

	@GetMapping("/paginaCadastro")
	public ModelAndView paginaCadastro() {
		ModelAndView view = new ModelAndView("cadastroPessoas.html");
		view.addObject("pessoaobj", new Pessoa());
		return view;
	}

	@PostMapping("**/salvar")
	public ModelAndView salvar(@Valid Pessoa pessoa , BindingResult bindingResult) {
		
		if(bindingResult.hasErrors()) {
			ModelAndView  modelAndView = new ModelAndView("cadastroPessoas");
			
			List<String> listErros = new ArrayList<>();
			
			for (ObjectError erros : bindingResult.getAllErrors()) {
				listErros.add(erros.getDefaultMessage());
			}
			modelAndView.addObject("mensagemerros", listErros);
			modelAndView.addObject("pessoaobj", pessoa);
			
			return modelAndView;
			
		}
		
		pessoaRepository.save(pessoa);
		
		ModelAndView  modelAndView = new ModelAndView("listar");
		modelAndView.addObject("pessoas", pessoaRepository.findAll());
		modelAndView.addObject("pessoaobj", new Pessoa());

		return modelAndView;
	}

	@GetMapping("/editar/{codigo}")
	public ModelAndView atualizar(@PathVariable Integer codigo) {
		ModelAndView view = new ModelAndView("cadastroPessoas.html");

		Pessoa pessoaEncontrada = pessoaRepository.getOne(codigo);

		view.addObject("pessoaobj", pessoaEncontrada);

		return view;

	}

	@GetMapping("/excluir/{codigo}")
	public String excluir(@PathVariable Integer codigo) {

		pessoaRepository.deleteById(codigo);

		return "redirect:/pessoas/listar";
	}
	
	@PostMapping("**/pesquisa")
	public ModelAndView buscarPeloNome(@RequestParam("nome") String nome) {
		ModelAndView view = new ModelAndView("listar.html");
		
		view.addObject("pessoas", pessoaRepository.findByNome(nome));
		
		return view;
	}

}

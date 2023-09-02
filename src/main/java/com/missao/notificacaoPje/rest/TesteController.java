/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.missao.notificacaoPje.rest;


import com.missao.notificacaoPje.jpa.model.Pessoa;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TesteController {

	private static final String template = "Hello, %s!";

	@GetMapping("/greeting")
	public Pessoa greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
		return new Pessoa("12345678900", String.format(template, name));
	}
}
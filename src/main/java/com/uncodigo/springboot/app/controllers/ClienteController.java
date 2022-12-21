package com.uncodigo.springboot.app.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.uncodigo.springboot.app.models.entity.Cliente;
import com.uncodigo.springboot.app.models.service.IClienteService;
import com.uncodigo.springboot.app.util.paginator.PageRender;

import jakarta.validation.Valid;

@Controller
@SessionAttributes("cliente")
public class ClienteController {

	@Autowired
	@Qualifier("clienteService")
	private IClienteService clienteService;

	@RequestMapping(value = "/listar", method = RequestMethod.GET)
	public String listar(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {

		Pageable pageRequest = PageRequest.of(page, 4);

		Page<Cliente> clientes = clienteService.findAll(pageRequest);

		PageRender<Cliente> pageRender = new PageRender<>("/listar", clientes);

		model.addAttribute("titulo", "Listado de Clientes");
		model.addAttribute("clientes", clientes);
		model.addAttribute("page", pageRender);

		return "listar";
	}

	@RequestMapping(value = "/form")
	public String crear(Map<String, Object> model) {

		Cliente cliente = new Cliente();

		model.put("titulo", "Formulario de Cliente");
		model.put("cliente", cliente);

		return "form";
	}

	@RequestMapping(value = "/form", method = RequestMethod.POST)
	public String guardar(@Valid Cliente cliente, BindingResult result, Model model,
			@RequestParam("file") MultipartFile foto, RedirectAttributes flash, SessionStatus status) {

		if (result.hasErrors()) {

			model.addAttribute("titulo", "Formulario de Cliente");

			return "form";
		}

		if (!foto.isEmpty()) {

			String rootPath = "/Users/felipe/Desktop/uploads";

			try {

				byte[] bytes = foto.getBytes();

				Path rutaCompleta = Paths.get(rootPath + "//" + foto.getOriginalFilename());

				Files.write(rutaCompleta, bytes);

				cliente.setFoto(foto.getOriginalFilename());

				flash.addFlashAttribute("info", "La imagen se subió correctamente: " + foto.getOriginalFilename());

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		String mensajeFlash = (cliente.getId() != null) ? "El cliente ha sido EDITADO con éxito!"
				: "El cliente ha sido CREADO con éxito!";

		clienteService.save(cliente);

		status.setComplete();

		flash.addFlashAttribute("success", mensajeFlash);

		return "redirect:listar";
	}

	@RequestMapping(value = "/form/{id}")
	public String editar(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {

		Cliente cliente = null;

		if (id > 0) {

			cliente = clienteService.findOne(id);

			if (cliente == null) {
				flash.addFlashAttribute("error", "El cliente no existe!");
				return "redirect:/listar";
			}

		} else {
			flash.addFlashAttribute("warning", "El ID del cliente no puede ser cero!");
			return "redirect:/listar";
		}

		model.put("titulo", "Editar cliente");

		model.put("cliente", cliente);

		return "form";
	}

	@RequestMapping(value = "/eliminar/{id}")
	public String eliminar(@PathVariable(value = "id") Long id, RedirectAttributes flash) {

		if (id > 0) {
			clienteService.delete(id);
		}
		flash.addFlashAttribute("info", "El cliente ha sido elimiando con éxito!");

		return "redirect:/listar";
	}

	@GetMapping("/ver/{id}")
	public String ver(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {

		Cliente cliente = clienteService.findOne(id);

		if (cliente == null) {
			flash.addFlashAttribute("error", "El cliente no existe en la base de datos");
			return "redirect:/listar";
		}
		
		model.put("cliente", cliente);
		model.put("titulo", "Detalles del cliente: " + cliente.getNombre());

		return "ver";
	}

}

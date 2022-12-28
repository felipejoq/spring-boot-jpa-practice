package com.uncodigo.springboot.app.controllers;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Collection;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
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
import com.uncodigo.springboot.app.models.service.IUploadFileService;
import com.uncodigo.springboot.app.util.paginator.PageRender;

import jakarta.validation.Valid;

@Controller
@SessionAttributes("cliente")
public class ClienteController {
    protected final Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    private IUploadFileService uploadFileService;

    @Autowired
    @Qualifier("clienteService")
    private IClienteService clienteService;

    @GetMapping(value = "/uploads/{filename:.+}")
    public ResponseEntity<Resource> verFoto(@PathVariable String filename) {

        Resource recurso = null;
        try {
            recurso = uploadFileService.load(filename);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment: filename=\"" + recurso.getFilename() + "\"")
                .body(recurso);
    }

    @RequestMapping(value = {"/listar", "/"}, method = RequestMethod.GET)
    public String listar(@RequestParam(name = "page", defaultValue = "0") int page, Model model, Authentication authentication, HttpServletRequest request) {

        /* Authentication puede ser obtenido de forma estática en cualquier parte de la aplicación de esta forma:
         * Authentication auth = SecurityContextHolder.getContext().getAuthentication();
         */

        if (authentication != null) {
            // Aquí podemos hacer algo.
            logger.info("Hola ".concat(authentication.getName()).concat(" usted está viendo la vista listar con login."));
        }

        if (hasRole("ROLE_ADMIN")) {
            logger.info("Hola ".concat(authentication.getName()).concat(" tienes acceso."));
        } else {
            logger.info("No tienes acceso.");
        }

        SecurityContextHolderAwareRequestWrapper securityContext = new SecurityContextHolderAwareRequestWrapper(request, "ROLE_");

        if (securityContext.isUserInRole("ADMIN")) {
            logger.info("Hola ".concat(authentication.getName()).concat(" Eres admin."));
        } else {
            logger.info("Hola ".concat(authentication.getName()).concat(" No eres admin."));
        }

        if (request.isUserInRole("ROLE_ADMIN")) {
            logger.info("Hola ".concat(authentication.getName()).concat(" HttpServletRequest Eres admin."));
        } else {
            logger.info("Hola ".concat(authentication.getName()).concat(" HttpServletRequest No eres admin."));
        }

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

            if (cliente.getId() != null && cliente.getId() > 0 && cliente.getFoto() != null
                    && cliente.getFoto().length() > 0) {

                uploadFileService.delete(cliente.getFoto());

            }

            String nomUnico = null;

            try {
                nomUnico = uploadFileService.copy(foto);
            } catch (IOException e) {
                e.printStackTrace();
            }

            cliente.setFoto(nomUnico);

            flash.addFlashAttribute("info", "La imagen se subió correctamente: " + nomUnico);

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

            Cliente cliente = clienteService.findOne(id);

            clienteService.delete(id);
            flash.addFlashAttribute("info", "El cliente ha sido elimiando con éxito!");

            if (uploadFileService.delete(cliente.getFoto())) {
                flash.addFlashAttribute("info", "Foto: " + cliente.getFoto() + " Eliminada con éxito!");
            }

        }

        return "redirect:/listar";
    }

    @GetMapping("/ver/{id}")
    public String ver(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {

        Cliente cliente = clienteService.fetchByIdWithFacturas(id); // clienteService.findOne(id);

        if (cliente == null) {
            flash.addFlashAttribute("error", "El cliente no existe en la base de datos");
            return "redirect:/listar";
        }

        model.put("cliente", cliente);
        model.put("titulo", "Detalles del cliente: " + cliente.getNombre());

        return "ver";
    }

    private boolean hasRole(String role) {

        SecurityContext context = SecurityContextHolder.getContext();

        if (context == null) {
            return false;
        }

        Authentication auth = context.getAuthentication();

        if (auth == null) {
            return false;
        }

        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();

        return authorities.contains(new SimpleGrantedAuthority(role));

        /* for (GrantedAuthority authority : authorities) {
            if (role.equals(authority.getAuthority())) {
                logger.info("Hola ".concat(auth.getName()).concat(" tu rol es: ").concat(authority.getAuthority()));
                return true;
            }
        }

        return false; */

    }

}

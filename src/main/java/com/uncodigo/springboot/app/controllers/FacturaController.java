package com.uncodigo.springboot.app.controllers;

import com.uncodigo.springboot.app.models.entity.Cliente;
import com.uncodigo.springboot.app.models.entity.Factura;
import com.uncodigo.springboot.app.models.entity.ItemFactura;
import com.uncodigo.springboot.app.models.entity.Producto;
import com.uncodigo.springboot.app.models.service.IClienteService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;

// Forma de proteger rutas del controlador: @Secured("ROLE_ADMIN")
@Controller
@RequestMapping("/factura")
@SessionAttributes("factura")
public class FacturaController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private IClienteService clienteService;

    @GetMapping("/form/{clienteId}")
    public String crear(@PathVariable(value = "clienteId") Long Id, Map<String, Object> model, RedirectAttributes flash) {

        Cliente cliente = clienteService.findOne(Id);

        if (cliente == null) {
            flash.addFlashAttribute("error", "El cliente no existe en la DB");
            return "redirect:/listar";
        }

        Factura factura = new Factura();
        factura.setCliente(cliente);

        model.put("factura", factura);
        model.put("titulo", "Crear factura");

        return "facturas/form";
    }

    @GetMapping(value = "/cargar-productos/{term}", produces = {"application/json"})
    public @ResponseBody List<Producto> cargarProductos(@PathVariable String term) {
        return clienteService.findByNombre("%" + term + "%");
    }

    @PostMapping("/form")
    public String guardar(@Valid Factura factura,
                          BindingResult result,
                          Model model,
                          @RequestParam(value = "item_id[]", required = false) Long[] itemId,
                          @RequestParam(value = "cantidad[]", required = false) Integer[] cantidad,
                          RedirectAttributes flash,
                          SessionStatus status) {

        if (result.hasErrors()) {
            model.addAttribute("titulo", "Crear Factura");
            return "facturas/form";
        }

        if (itemId == null || itemId.length == 0) {
            model.addAttribute("titulo", "Crear Factura");
            model.addAttribute("error", "Error: La factura debe tener al menos 1 l??nea de producto.");
            return "facturas/form";
        }

        for (int i = 0; i < itemId.length; i++) {
            Producto producto = clienteService.findProductoById(itemId[i]);
            ItemFactura linea = new ItemFactura();
            linea.setCantidad(cantidad[i]);
            linea.setProducto(producto);
            factura.addItemFactura(linea);

            log.info("ID: " + itemId[i].toString() + " CANTIDAD: " + cantidad[i].toString());
        }

        clienteService.saveFactura(factura);

        status.setComplete();

        flash.addFlashAttribute("success", "La factura fue agregada con ??xito!");

        return "redirect:/ver/" + factura.getCliente().getId();

    }

    @GetMapping("/ver/{id}")
    public String ver(@PathVariable(value = "id") Long id,
                      Model model,
                      RedirectAttributes flash) {
        Factura factura = clienteService.fetchByIdWithClienteWithItemFacturaWithProducto(id); // clienteService.findFacturaById(id);
        if (factura == null) {
            flash.addFlashAttribute("error", "La factura no existe en la DB");
            return "redirect:/listar";
        }

        model.addAttribute("factura", factura);
        model.addAttribute("titulo", "Factura: ".concat(factura.getDescripcion()));

        return "facturas/ver";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable(value = "id") Long id, RedirectAttributes flash) {

        Factura factura = clienteService.findFacturaById(id);

        if (factura != null) {
            clienteService.deleteFactura(factura.getId());
            flash.addFlashAttribute("success", "La factura fue eliminada con ??xito.");
            return "redirect:/ver/" + factura.getCliente().getId();
        }

        flash.addFlashAttribute("error", "La factura no existe en la DB. No se borr??.");

        return "redirect:/listar";
    }
}

package com.gestion.de.inventario.controller; // Reemplaza con tu package si es diferente

import com.gestion.de.inventario.model.Producto;
import com.gestion.de.inventario.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*; // Importación general para anotaciones de mapping
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/v1/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping
    public ResponseEntity<List<Producto>> listarProductos() {
        return ResponseEntity.ok(productoService.listarProductos());
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Producto> obtenerProductoPorId(@PathVariable Long id) {
        return productoService.obtenerProductoPorId(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado con ID: " + id));
    }

    @GetMapping("/marca/{marca}")
    public ResponseEntity<List<Producto>> buscarProductosPorMarca(@PathVariable String marca) {
        List<Producto> productos = productoService.buscarProductosPorMarca(marca);
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/modelo/{modelo}")
    public ResponseEntity<List<Producto>> buscarProductosPorModelo(@PathVariable String modelo) {
        List<Producto> productos = productoService.buscarProductosPorModelo(modelo);
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<Producto>> buscarProductosPorTipo(@PathVariable String tipo) {
        List<Producto> productos = productoService.buscarProductosPorTipo(tipo);
        return ResponseEntity.ok(productos);
    }

    @PostMapping
    public ResponseEntity<Producto> crearProducto(@RequestBody Producto producto) {
        Producto nuevoProducto = productoService.guardarProducto(producto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoProducto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizarProducto(@PathVariable Long id, @RequestBody Producto productoDetalles) {
        Producto productoActualizado = productoService.actualizarProducto(id, productoDetalles);
        return ResponseEntity.ok(productoActualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Long id) {
        productoService.eliminarProducto(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/descontarstock")
    public ResponseEntity<Producto> descontarStock(@PathVariable Long id, @RequestParam int cantidad) {
        try {
            Producto productoActualizado = productoService.descontarStock(id, cantidad);
            return ResponseEntity.ok(productoActualizado);
        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            System.err.println("Error inesperado al descontar stock para el producto " + id + ": " + e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error interno al descontar stock: " + e.getMessage(), e);
        }
    }

    // Endpoint para aumentar stock
    @PatchMapping("/{id}/aumentarstock")
    public ResponseEntity<Producto> aumentarStock(@PathVariable Long id, @RequestParam int cantidad) {
        try {
            Producto productoActualizado = productoService.aumentarStock(id, cantidad);
            return ResponseEntity.ok(productoActualizado);
        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            System.err.println("Error inesperado al aumentar stock para el producto " + id + ": " + e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error interno al aumentar stock.", e);
        }
    }
}
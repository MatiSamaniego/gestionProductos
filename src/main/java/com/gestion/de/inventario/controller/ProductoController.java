package com.gestion.de.inventario.controller;

import com.gestion.de.inventario.model.Producto;
import org.springframework.http.HttpStatus;
import com.gestion.de.inventario.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping
    public ResponseEntity<List<Producto>> listarProductos() {
        List<Producto> productos = productoService.obtenerTodos();
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Long id) {
        Producto producto = productoService.obtenerPorId(id);
        if (producto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Producto no encontrado con ID: " + id);
        }
        return ResponseEntity.ok(producto);
    }

    @GetMapping("/marca/{marca}")
    public ResponseEntity<List<Producto>> buscarPorMarca(@PathVariable String marca) {
        List<Producto> productos = productoService.buscarPorMarcaLike(marca);
        if (productos.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/modelo/{modelo}")
    public ResponseEntity<List<Producto>> buscarPorModelo(@PathVariable String modelo) {
        List<Producto> productos = productoService.buscarPorModeloLike(modelo);
        if (productos.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/tipo/{tipoProducto}")
    public ResponseEntity<List<Producto>> buscarPorTipoProducto(@PathVariable String tipoProducto) {
        List<Producto> productos = productoService.buscarPorTipoProductoLike(tipoProducto);
        if (productos.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(productos);
    }

    @PostMapping
    public ResponseEntity<?> crearProducto(@RequestBody Producto producto) {
        if (producto.getModelo() == null || producto.getModelo().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("El modelo no puede estar vacío.");
        }
        if (producto.getStock() <= 0) {
            return ResponseEntity.badRequest().body("El stock debe ser mayor a cero.");
        }
        if (producto.getPrecio() <= 0) {
            return ResponseEntity.badRequest().body("El precio debe ser mayor a cero.");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(productoService.guardarProducto(producto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarProducto(@PathVariable Long id, @RequestBody Producto productoActualizado) {
        if (productoActualizado.getModelo() == null || productoActualizado.getModelo().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("El modelo no puede estar vacío.");
        }
        if (productoActualizado.getStock() <= 0) {
            return ResponseEntity.badRequest().body("El stock debe ser mayor a cero.");
        }
        if (productoActualizado.getPrecio() <= 0) {
            return ResponseEntity.badRequest().body("El precio debe ser mayor a cero.");
        }

        Producto producto = productoService.obtenerPorId(id);
        if (producto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Producto no encontrado con ID: " + id);
        }

        producto.setModelo(productoActualizado.getModelo());
        producto.setStock(productoActualizado.getStock());
        producto.setMarca(productoActualizado.getMarca());
        producto.setPrecio(productoActualizado.getPrecio());
        producto.setTipoProducto(productoActualizado.getTipoProducto());
        return ResponseEntity.ok(productoService.guardarProducto(producto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarProducto(@PathVariable Long id) {
        try {
            productoService.eliminarProducto(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar el producto");
        }
    }
}

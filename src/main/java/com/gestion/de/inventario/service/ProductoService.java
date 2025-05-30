package com.gestion.de.inventario.service; // Reemplaza con tu package si es diferente

import com.gestion.de.inventario.model.Producto;
import com.gestion.de.inventario.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    public List<Producto> listarProductos() {
        return productoRepository.findAll(); // Usa el findAll de JpaRepository o tu @Query
    }

    public Optional<Producto> obtenerProductoPorId(Long id) {
        Producto producto = productoRepository.findProductoById(id); // O productoRepository.findById(id) si prefieres Optional directo del repo
        return Optional.ofNullable(producto);
    }

    public List<Producto> buscarProductosPorMarca(String marca) {
        return productoRepository.findByMarcaLike(marca);
    }

    public List<Producto> buscarProductosPorModelo(String modelo) {
        return productoRepository.findByModeloLike(modelo);
    }

    public List<Producto> buscarProductosPorTipo(String tipoProducto) {
        return productoRepository.findByTipoProductoLike(tipoProducto);
    }

    @Transactional
    public Producto guardarProducto(Producto producto) {
        return productoRepository.save(producto);
    }

    @Transactional
    public Producto actualizarProducto(Long id, Producto productoDetalles) {
        return obtenerProductoPorId(id) // Reutiliza tu método que devuelve Optional
                .map(producto -> {
                    producto.setModelo(productoDetalles.getModelo());
                    producto.setStock(productoDetalles.getStock());
                    producto.setMarca(productoDetalles.getMarca());
                    producto.setTipoProducto(productoDetalles.getTipoProducto());
                    producto.setPrecio(productoDetalles.getPrecio());
                    return productoRepository.save(producto);
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado con ID: " + id));
    }

    @Transactional
    public void eliminarProducto(Long id) {
        if (!productoRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado con ID: " + id);
        }
        productoRepository.deleteById(id);
    }

    @Transactional
    public Producto descontarStock(Long id, int cantidad) {
        if (cantidad < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La cantidad a descontar no puede ser negativa.");
        }
        return obtenerProductoPorId(id) // Reutiliza tu método que devuelve Optional
                .map(producto -> {
                    if (producto.getStock() < cantidad) {
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Stock insuficiente para el producto con ID: " + id + ". Stock actual: " + producto.getStock() + ", cantidad solicitada: " + cantidad);
                    }
                    producto.setStock(producto.getStock() - cantidad);
                    return productoRepository.save(producto);
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado con ID: " + id));
    }

    @Transactional
    public Producto aumentarStock(Long id, int cantidad) {
        if (cantidad < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La cantidad a aumentar no puede ser negativa.");
        }
        return obtenerProductoPorId(id) // Reutiliza tu método que devuelve Optional
                .map(producto -> {
                    producto.setStock(producto.getStock() + cantidad);
                    return productoRepository.save(producto);
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado con ID: " + id));
    }
}
package com.gestion.de.inventario.service;

import com.gestion.de.inventario.model.Producto;
import com.gestion.de.inventario.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    public List<Producto> obtenerTodos() {
        return productoRepository.findAll();
    }

    public Producto obtenerPorId(Long id) {
        return productoRepository.findProductoById(id);
    }

    public Producto guardarProducto(Producto producto) {
        return productoRepository.save(producto);
    }

    public void eliminarProducto(Long id) {
        productoRepository.deleteById(id);
    }

    public List<Producto> buscarPorModeloLike(String modelo) {
        return productoRepository.findByModeloLike(modelo);
    }

    public List<Producto> buscarPorMarcaLike(String marca) {
        return productoRepository.findByMarcaLike(marca);
    }

    public List<Producto> buscarPorTipoProductoLike(String tipoProducto) {
        return productoRepository.findByTipoProductoLike(tipoProducto);
    }
}
package com.colegio.controller;

import com.colegio.model.Clase;
import com.colegio.repository.ClaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/clases")
public class ClaseController {

    @Autowired
    private ClaseRepository claseRepository;

    @GetMapping
    public List<Clase> listarClases() {
        return claseRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Clase> obtenerClase(@PathVariable Long id) {
        Optional<Clase> clase = claseRepository.findById(id);
        return clase.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Clase crearClase(@RequestBody Clase clase) {
        return claseRepository.save(clase);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Clase> actualizarClase(@PathVariable Long id, @RequestBody Clase claseDetalles) {
        Optional<Clase> optionalClase = claseRepository.findById(id);
        if (!optionalClase.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        Clase clase = optionalClase.get();
        clase.setNombre(claseDetalles.getNombre());
        clase.setDescripcion(claseDetalles.getDescripcion());
        clase.setCodigo(claseDetalles.getCodigo());
        clase.setProfesor(claseDetalles.getProfesor());

        Clase claseActualizada = claseRepository.save(clase);
        return ResponseEntity.ok(claseActualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarClase(@PathVariable Long id) {
        if (!claseRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        claseRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

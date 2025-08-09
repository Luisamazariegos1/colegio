package com.colegio.controller;

import com.colegio.model.Inscripcion;
import com.colegio.repository.InscripcionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/inscripciones")
public class InscripcionController {

    @Autowired
    private InscripcionRepository inscripcionRepository;

    @GetMapping
    public List<Inscripcion> listarInscripciones() {
        return inscripcionRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Inscripcion> obtenerInscripcion(@PathVariable Long id) {
        Optional<Inscripcion> inscripcion = inscripcionRepository.findById(id);
        return inscripcion.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Inscripcion crearInscripcion(@RequestBody Inscripcion inscripcion) {
        return inscripcionRepository.save(inscripcion);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Inscripcion> actualizarInscripcion(@PathVariable Long id, @RequestBody Inscripcion inscripcionDetalles) {
        Optional<Inscripcion> optionalInscripcion = inscripcionRepository.findById(id);
        if (!optionalInscripcion.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        Inscripcion inscripcion = optionalInscripcion.get();
        inscripcion.setAlumno(inscripcionDetalles.getAlumno());
        inscripcion.setClase(inscripcionDetalles.getClase());
        inscripcion.setFechaInscripcion(inscripcionDetalles.getFechaInscripcion());
        inscripcion.setEstado(inscripcionDetalles.getEstado());

        Inscripcion inscripcionActualizada = inscripcionRepository.save(inscripcion);
        return ResponseEntity.ok(inscripcionActualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarInscripcion(@PathVariable Long id) {
        if (!inscripcionRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        inscripcionRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

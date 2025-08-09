package com.colegio.controller;

import com.colegio.model.Alumno;
import com.colegio.repository.AlumnoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/alumnos")
public class AlumnoController {

    @Autowired
    private AlumnoRepository alumnoRepository;

    // Listar todos los alumnos
    @GetMapping
    public List<Alumno> listarAlumnos() {
        return alumnoRepository.findAll();
    }

    // Obtener alumno por ID
    @GetMapping("/{id}")
    public ResponseEntity<Alumno> obtenerAlumno(@PathVariable Long id) {
        Optional<Alumno> alumno = alumnoRepository.findById(id);
        return alumno.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Crear nuevo alumno
    @PostMapping
    public Alumno crearAlumno(@RequestBody Alumno alumno) {
        return alumnoRepository.save(alumno);
    }

    // Actualizar alumno existente
    @PutMapping("/{id}")
    public ResponseEntity<Alumno> actualizarAlumno(@PathVariable Long id, @RequestBody Alumno alumnoDetalles) {
        Optional<Alumno> optionalAlumno = alumnoRepository.findById(id);

        if (!optionalAlumno.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Alumno alumno = optionalAlumno.get();
        alumno.setNombre(alumnoDetalles.getNombre());
        alumno.setApellido(alumnoDetalles.getApellido());
        alumno.setFechaNacimiento(alumnoDetalles.getFechaNacimiento());
        alumno.setCorreo(alumnoDetalles.getCorreo());
        alumno.setMatricula(alumnoDetalles.getMatricula());

        Alumno alumnoActualizado = alumnoRepository.save(alumno);
        return ResponseEntity.ok(alumnoActualizado);
    }

    // Eliminar alumno por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarAlumno(@PathVariable Long id) {
        if (!alumnoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        alumnoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

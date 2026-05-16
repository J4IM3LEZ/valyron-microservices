package com.realmofvalyron.ms_objetos.service;

import com.realmofvalyron.ms_objetos.dto.ObjetoRequest;
import com.realmofvalyron.ms_objetos.dto.ObjetoResponse;
import com.realmofvalyron.ms_objetos.entity.Objeto;
import com.realmofvalyron.ms_objetos.repository.ObjetoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ObjetoService {

    private final ObjetoRepository objetoRepository;

    public ObjetoResponse crear(ObjetoRequest request) {
        if (objetoRepository.existsByNombre(request.getNombre())) {
            throw new RuntimeException("Ya existe un objeto con ese nombre");
        }

        Objeto objeto = Objeto.builder()
                .nombre(request.getNombre())
                .tipo(Objeto.TipoObjeto.valueOf(request.getTipo()))
                .rareza(Objeto.Rareza.valueOf(request.getRareza()))
                .bonusFuerza(request.getBonusFuerza() != null ? request.getBonusFuerza() : 0)
                .bonusDestreza(request.getBonusDestreza() != null ? request.getBonusDestreza() : 0)
                .bonusSabiduría(request.getBonusSabiduría() != null ? request.getBonusSabiduría() : 0)
                .bonusVida(request.getBonusVida() != null ? request.getBonusVida() : 0)
                .nivelMinimo(request.getNivelMinimo())
                .razaRequerida(request.getRazaRequerida() != null ?
                        Objeto.RazaRequerida.valueOf(request.getRazaRequerida()) :
                        Objeto.RazaRequerida.NINGUNA)
                .peso(request.getPeso() != null ? request.getPeso() : 0)
                .descripcion(request.getDescripcion())
                .build();

        objetoRepository.save(objeto);
        return mapToResponse(objeto);
    }

    public List<ObjetoResponse> listarTodos() {
        return objetoRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public ObjetoResponse buscarPorId(Long id) {
        Objeto objeto = objetoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Objeto no encontrado con id: " + id));
        return mapToResponse(objeto);
    }

    public List<ObjetoResponse> buscarPorTipo(String tipo) {
        return objetoRepository.findByTipo(Objeto.TipoObjeto.valueOf(tipo))
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<ObjetoResponse> buscarPorNivel(Integer nivel) {
        return objetoRepository.findByNivelMinimoLessThanEqual(nivel)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public ObjetoResponse actualizar(Long id, ObjetoRequest request) {
        Objeto objeto = objetoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Objeto no encontrado con id: " + id));

        objeto.setNombre(request.getNombre());
        objeto.setTipo(Objeto.TipoObjeto.valueOf(request.getTipo()));
        objeto.setRareza(Objeto.Rareza.valueOf(request.getRareza()));
        objeto.setBonusFuerza(request.getBonusFuerza() != null ? request.getBonusFuerza() : 0);
        objeto.setBonusDestreza(request.getBonusDestreza() != null ? request.getBonusDestreza() : 0);
        objeto.setBonusSabiduría(request.getBonusSabiduría() != null ? request.getBonusSabiduría() : 0);
        objeto.setBonusVida(request.getBonusVida() != null ? request.getBonusVida() : 0);
        objeto.setNivelMinimo(request.getNivelMinimo());
        objeto.setRazaRequerida(request.getRazaRequerida() != null ?
                Objeto.RazaRequerida.valueOf(request.getRazaRequerida()) :
                Objeto.RazaRequerida.NINGUNA);
        objeto.setPeso(request.getPeso() != null ? request.getPeso() : 0);
        objeto.setDescripcion(request.getDescripcion());

        objetoRepository.save(objeto);
        return mapToResponse(objeto);
    }

    public void eliminar(Long id) {
        if (!objetoRepository.existsById(id)) {
            throw new RuntimeException("Objeto no encontrado con id: " + id);
        }
        objetoRepository.deleteById(id);
    }

    private ObjetoResponse mapToResponse(Objeto objeto) {
        return ObjetoResponse.builder()
                .id(objeto.getId())
                .nombre(objeto.getNombre())
                .tipo(objeto.getTipo().name())
                .rareza(objeto.getRareza().name())
                .bonusFuerza(objeto.getBonusFuerza())
                .bonusDestreza(objeto.getBonusDestreza())
                .bonusSabiduría(objeto.getBonusSabiduría())
                .bonusVida(objeto.getBonusVida())
                .nivelMinimo(objeto.getNivelMinimo())
                .razaRequerida(objeto.getRazaRequerida() != null ? objeto.getRazaRequerida().name() : null)
                .peso(objeto.getPeso())
                .descripcion(objeto.getDescripcion())
                .build();
    }

}
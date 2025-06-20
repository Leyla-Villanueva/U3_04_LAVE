package utez.edu.mx.almacenes.service;

import mx.edu.utez.almacenes.models.Cede;
import mx.edu.utez.almacenes.exception.DuplicateResourceException;
import mx.edu.utez.almacenes.exception.ResourceNotFoundException;
import mx.edu.utez.almacenes.dto.CedeRequestDto;
import mx.edu.utez.almacenes.dto.CedeResponseDto;
import mx.edu.utez.almacenes.repository.CedeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CedeService {

    private final CedeRepository cedeRepository;


    public CedeResponseDto createCede(CedeRequestDto request) {
        if (cedeRepository.existsByEstadoAndMunicipio(request.getState(), request.getCity())) {
            throw new DuplicateResourceException("Cede", "state and city",
                    request.getState() + ", " + request.getCity());
        }

        Cede cede = Cede.builder()
                .estado(request.getState().trim())
                .municipio(request.getCity().trim())
                .build();

        Cede savedCede = cedeRepository.save(cede);

        String dateStr = LocalDate.now().format(DateTimeFormatter.ofPattern("ddMMyyyy"));
        String randomDigits = String.format("%04d", new Random().nextInt(10000));
        savedCede.setClave(String.format("C%d-%s-%s", savedCede.getId(), dateStr, randomDigits));

        savedCede = cedeRepository.save(savedCede);

        return mapToResponseDTO(savedCede);
    }

    @Transactional(readOnly = true)
    public List<CedeResponseDto> getAllCedes() {
        return cedeRepository.findAll()
                .stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CedeResponseDto getCedeById(Long id) {
        Cede cede = cedeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cede", "id", id));
        return mapToResponseDTO(cede);
    }

    @Transactional(readOnly = true)
    public CedeResponseDto getCedeByKey(String key) {
        Cede cede = cedeRepository.findByClave(key)
                .orElseThrow(() -> new ResourceNotFoundException("Cede", "key", key));
        return mapToResponseDTO(cede);
    }

    public CedeResponseDto updateCede(Long id, CedeRequestDto request) {
        Cede existingCede = cedeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cede", "id", id));

        // Check for duplicate if state or city changed
        if (!existingCede.getEstado().equals(request.getState()) ||
                !existingCede.getMunicipio().equals(request.getCity())) {
            if (cedeRepository.existsByEstadoAndMunicipio(request.getState(), request.getCity())) {
                throw new DuplicateResourceException("Cede", "state and city",
                        request.getState() + ", " + request.getCity());
            }
        }

        existingCede.setEstado(request.getState().trim());
        existingCede.setMunicipio(request.getCity().trim());

        Cede updatedCede = cedeRepository.save(existingCede);
        return mapToResponseDTO(updatedCede);
    }

    public void deleteCede(Long id) {
        if (!cedeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Cede", "id", id);
        }
        cedeRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<CedeResponseDto> searchCedesByState(String state) {
        return cedeRepository.findByEstadoContainingIgnoreCase(state)
                .stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<CedeResponseDto> searchCedesByCity(String city) {
        return cedeRepository.findByMunicipioContainingIgnoreCase(city)
                .stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    private CedeResponseDto mapToResponseDTO(Cede cede) {
        return CedeResponseDto.builder()
                .id(cede.getId())
                .key(cede.getClave())
                .state(cede.getEstado())
                .city(cede.getMunicipio())
                .build();
    }
}

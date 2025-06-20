package utez.edu.mx.almacenes.service;

import utez.edu.mx.almacenes.dto.AlmacenResponseDto;
import utez.edu.mx.almacenes.dto.ClienteRequestDto;
import utez.edu.mx.almacenes.exception.DuplicateResourceException;
import utez.edu.mx.almacenes.exception.ResourceNotFoundException;
import utez.edu.mx.almacenes.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import utez.edu.mx.almacenes.dto.ClienteResponseDto;
import utez.edu.mx.almacenes.model.Cliente;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Transactional
public class ClienteService {

    private final ClienteRepository clientRepository;

    public ClienteResponseDto createClient(ClienteRequestDto request) {
        if (clientRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("Client", "email", request.getEmail());
        }

        if (clientRepository.existsByNumeroTel(request.getPhoneNumber())) {
            throw new DuplicateResourceException("Client", "phone number", request.getPhoneNumber());
        }

        Cliente client = Cliente.builder()
                .nombreCompleto(request.getFullName().trim())
                .numeroTel(request.getPhoneNumber().trim())
                .email(request.getEmail().toLowerCase().trim())
                .build();

        Cliente savedClient = clientRepository.save(client);
        return mapToResponseDTO(savedClient);
    }

    @Transactional(readOnly = true)
    public List<ClienteResponseDto> getAllClients() {
        return clientRepository.findAll()
                .stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ClienteResponseDto getClientById(Long id) {
        Cliente client = clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client", "id", id));
        return mapToResponseDTO(client);
    }

    @Transactional(readOnly = true)
    public ClienteResponseDto getClientByEmail(String email) {
        Cliente client = clientRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Client", "email", email));
        return mapToResponseDTO(client);
    }

    public ClienteResponseDto updateClient(Long id, ClienteRequestDto request) {
        Cliente existingClient = clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client", "id", id));

        if (!existingClient.getEmail().equals(request.getEmail())) {
            if (clientRepository.existsByEmail(request.getEmail())) {
                throw new DuplicateResourceException("Client", "email", request.getEmail());
            }
        }

        if (!existingClient.getNumeroTel().equals(request.getPhoneNumber())) {
            if (clientRepository.existsByNumeroTel(request.getPhoneNumber())) {
                throw new DuplicateResourceException("Client", "phone number", request.getPhoneNumber());
            }
        }

        existingClient.setNombreCompleto(request.getFullName().trim());
        existingClient.setNumeroTel(request.getPhoneNumber().trim());
        existingClient.setEmail(request.getEmail().toLowerCase().trim());

        Cliente updatedClient = clientRepository.save(existingClient);
        return mapToResponseDTO(updatedClient);
    }


    public void deleteClient(Long id) {
        if (!clientRepository.existsById(id)) {
            throw new ResourceNotFoundException("Client", "id", id);
        }
        clientRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<ClienteResponseDto> searchClientsByName(String name) {
        return clientRepository.findByNombreCompletoContainingIgnoreCase(name)
                .stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<AlmacenResponseDto> getAlmacenesByCliente(Long clienteId) {
        Cliente cliente = clientRepository.findById(clienteId)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente", "id", clienteId));

        return cliente.getAlmacenes()
                .stream()
                .map(this::mapAlmacenToResponseDto)
                .collect(Collectors.toList());
    }

    private ClienteResponseDto mapToResponseDTO(Cliente client) {
        return ClienteResponseDto.builder()
                .id(client.getId())
                .fullName(client.getNombreCompleto())
                .phoneNumber(client.getNumeroTel())
                .email(client.getEmail())
                .build();
    }

    private AlmacenResponseDto mapAlmacenToResponseDto(utez.edu.mx.almacenes.model.Almacen almacen) {
        utez.edu.mx.almacenes.dto.CedeResponseDto cedeDto = utez.edu.mx.almacenes.dto.CedeResponseDto.builder()
                .id(almacen.getCede().getId())
                .key(almacen.getCede().getClave())
                .state(almacen.getCede().getEstado())
                .city(almacen.getCede().getMunicipio())
                .build();

        return AlmacenResponseDto.builder()
                .id(almacen.getId())
                .key(almacen.getClave())
                .registrationDate(almacen.getFechaRegistro())
                .salePrice(almacen.getPrecioVenta())
                .rentalPrice(almacen.getPrecioRenta())
                .size(almacen.getSize())
                .cede(cedeDto)
                .build();
    }
}

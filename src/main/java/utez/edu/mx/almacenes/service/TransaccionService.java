package utez.edu.mx.almacenes.service;

import lombok.extern.slf4j.Slf4j;
import utez.edu.mx.almacenes.dto.*;
import utez.edu.mx.almacenes.model.Almacen;
import utez.edu.mx.almacenes.exception.InvalidDataException;
import utez.edu.mx.almacenes.exception.ResourceNotFoundException;
import utez.edu.mx.almacenes.model.Cliente;
import utez.edu.mx.almacenes.repository.AlmacenRepository;
import utez.edu.mx.almacenes.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class TransaccionService {

    private final AlmacenRepository almacenRepository;
    private final ClienteRepository clienteRepository;

    public TransaccionResponseDto procesarTransaccion(TransaccionRequestDto request) {
        Almacen almacen = almacenRepository.findById(request.getAlmacenId())
                .orElseThrow(() -> new ResourceNotFoundException("Almacen", "id", request.getAlmacenId()));

        Cliente cliente = clienteRepository.findById(request.getClienteId())
                .orElseThrow(() -> new ResourceNotFoundException("Cliente", "id", request.getClienteId()));

        if (!almacen.isDisponible()) {
            throw new InvalidDataException("El almacén no está disponible. Ya fue " +
                    (almacen.getVendido() ? "vendido" : "rentado"));
        }

        BigDecimal monto;
        if (request.getEsVenta()) {
            almacen.setVendido(true);
            monto = almacen.getPrecioVenta();
            log.info("Procesando venta del almacén {} al cliente {}", almacen.getClave(), cliente.getNombreCompleto());
        } else {
            almacen.setRentado(true);
            monto = almacen.getPrecioRenta();
            log.info("Procesando renta del almacén {} al cliente {}", almacen.getClave(), cliente.getNombreCompleto());
        }

        almacen.setCliente(cliente);

        Almacen almacenActualizado = almacenRepository.save(almacen);

        return TransaccionResponseDto.builder()
                .almacen(mapAlmacenToResponseDto(almacenActualizado))
                .cliente(mapClienteToResponseDto(cliente))
                .esVenta(request.getEsVenta())
                .fechaTransaccion(LocalDateTime.now())
                .montoTransaccion(monto)
                .tipoTransaccion(request.getEsVenta() ? "VENTA" : "RENTA")
                .build();
    }

    @Transactional(readOnly = true)
    public List<AlmacenResponseDto> getAlmacenesDisponibles() {
        return almacenRepository.findByVendidoFalseAndRentadoFalse()
                .stream()
                .map(this::mapAlmacenToResponseDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<AlmacenResponseDto> getAlmacenesVendidos() {
        return almacenRepository.findByVendidoTrue()
                .stream()
                .map(this::mapAlmacenToResponseDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<AlmacenResponseDto> getAlmacenesRentados() {
        return almacenRepository.findByRentadoTrue()
                .stream()
                .map(this::mapAlmacenToResponseDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<AlmacenResponseDto> getAlmacenesByCliente(Long clienteId) {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente", "id", clienteId));

        return almacenRepository.findByClienteId(clienteId)
                .stream()
                .map(this::mapAlmacenToResponseDto)
                .collect(Collectors.toList());
    }

    public TransaccionResponseDto liberarAlmacen(Long almacenId) {
        Almacen almacen = almacenRepository.findById(almacenId)
                .orElseThrow(() -> new ResourceNotFoundException("Almacen", "id", almacenId));

        if (almacen.isDisponible()) {
            throw new InvalidDataException("El almacén ya está disponible");
        }

        Cliente clienteAnterior = almacen.getCliente();

        almacen.setVendido(false);
        almacen.setRentado(false);
        almacen.setCliente(null);

        Almacen almacenLiberado = almacenRepository.save(almacen);

        log.info("Almacén {} liberado del cliente {}", almacen.getClave(),
                clienteAnterior != null ? clienteAnterior.getNombreCompleto() : "Sin cliente");

        return TransaccionResponseDto.builder()
                .almacen(mapAlmacenToResponseDto(almacenLiberado))
                .cliente(clienteAnterior != null ? mapClienteToResponseDto(clienteAnterior) : null)
                .esVenta(null)
                .fechaTransaccion(LocalDateTime.now())
                .montoTransaccion(BigDecimal.ZERO)
                .tipoTransaccion("LIBERACION")
                .build();
    }

    private AlmacenResponseDto mapAlmacenToResponseDto(Almacen almacen) {
        CedeResponseDto cedeDto = CedeResponseDto.builder()
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

    private ClienteResponseDto mapClienteToResponseDto(Cliente cliente) {
        return ClienteResponseDto.builder()
                .id(cliente.getId())
                .fullName(cliente.getNombreCompleto())
                .phoneNumber(cliente.getNumeroTel())
                .email(cliente.getEmail())
                .build();
    }
}

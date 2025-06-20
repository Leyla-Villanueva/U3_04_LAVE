package utez.edu.mx.almacenes.service;

import mx.edu.utez.almacenes.dto.CedeResponseDto;
import mx.edu.utez.almacenes.dto.AlmacenRequestDto;
import mx.edu.utez.almacenes.dto.AlmacenResponseDto;
import mx.edu.utez.almacenes.models.Cede;
import mx.edu.utez.almacenes.models.Almacen;
import mx.edu.utez.almacenes.models.AlmacenSize;
import mx.edu.utez.almacenes.exception.InvalidDataException;
import mx.edu.utez.almacenes.exception.ResourceNotFoundException;
import mx.edu.utez.almacenes.repository.CedeRepository;
import mx.edu.utez.almacenes.repository.AlmacenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class AlmacenService {

    private final AlmacenRepository warehouseRepository;
    private final CedeRepository cedeRepository;

    public AlmacenResponseDto createWarehouse(AlmacenRequestDto request) {
        Cede cede = cedeRepository.findById(request.getCedeId())
                .orElseThrow(() -> new ResourceNotFoundException("Cede", "id", request.getCedeId()));

        if (request.getRegistrationDate().isAfter(LocalDate.now())) {
            throw new InvalidDataException("Registration date cannot be in the future");
        }

        if (request.getRentalPrice().compareTo(request.getSalePrice()) >= 0) {
            throw new InvalidDataException("Rental price must be less than sale price");
        }

        Almacen warehouse = Almacen.builder()
                .fechaRegistro(request.getRegistrationDate())
                .precioVenta(request.getSalePrice())
                .precioRenta(request.getRentalPrice())
                .size(request.getSize())
                .cede(cede)
                .build();

        Almacen savedWarehouse = warehouseRepository.save(warehouse);

        savedWarehouse.setClave(String.format("%s-A%d",
                savedWarehouse.getCede().getClave(),
                savedWarehouse.getId()));

        savedWarehouse = warehouseRepository.save(savedWarehouse);

        return mapToResponseDTO(savedWarehouse);
    }

    @Transactional(readOnly = true)
    public List<AlmacenResponseDto> getAllWarehouses() {
        return warehouseRepository.findAll()
                .stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public AlmacenResponseDto getWarehouseById(Long id) {
        Almacen warehouse = warehouseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Warehouse", "id", id));
        return mapToResponseDTO(warehouse);
    }

    @Transactional(readOnly = true)
    public AlmacenResponseDto getWarehouseByKey(String key) {
        Almacen warehouse = warehouseRepository.findByClave(key)
                .orElseThrow(() -> new ResourceNotFoundException("Warehouse", "key", key));
        return mapToResponseDTO(warehouse);
    }

    public AlmacenResponseDto updateWarehouse(Long id, AlmacenRequestDto request) {
        Almacen existingWarehouse = warehouseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Warehouse", "id", id));

        boolean cedeChanged = false;

        if (!existingWarehouse.getCede().getId().equals(request.getCedeId())) {
            Cede newCede = cedeRepository.findById(request.getCedeId())
                    .orElseThrow(() -> new ResourceNotFoundException("Cede", "id", request.getCedeId()));
            existingWarehouse.setCede(newCede);
            cedeChanged = true;
        }

        if (request.getRegistrationDate().isAfter(LocalDate.now())) {
            throw new InvalidDataException("Registration date cannot be in the future");
        }

        if (request.getRentalPrice().compareTo(request.getSalePrice()) >= 0) {
            throw new InvalidDataException("Rental price must be less than sale price");
        }

        existingWarehouse.setFechaRegistro(request.getRegistrationDate());
        existingWarehouse.setPrecioVenta(request.getSalePrice());
        existingWarehouse.setPrecioRenta(request.getRentalPrice());
        existingWarehouse.setSize(request.getSize());

        if (cedeChanged) {
            existingWarehouse.setClave(String.format("%s-A%d",
                    existingWarehouse.getCede().getClave(),
                    existingWarehouse.getId()));
        }

        Almacen updatedWarehouse = warehouseRepository.save(existingWarehouse);
        return mapToResponseDTO(updatedWarehouse);
    }

    public void deleteWarehouse(Long id) {
        if (!warehouseRepository.existsById(id)) {
            throw new ResourceNotFoundException("Warehouse", "id", id);
        }
        warehouseRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<AlmacenResponseDto> getWarehousesByCede(Long cedeId) {
        return warehouseRepository.findByCedeId(cedeId)
                .stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<AlmacenResponseDto> getWarehousesBySize(AlmacenSize size) {
        return warehouseRepository.findBySize(size)
                .stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<AlmacenResponseDto> getWarehousesBySalePriceRange(BigDecimal minPrice, BigDecimal maxPrice) {
        if (minPrice.compareTo(maxPrice) > 0) {
            throw new InvalidDataException("Minimum price cannot be greater than maximum price");
        }
        return warehouseRepository.findByPrecioVentaBetween(minPrice, maxPrice)
                .stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    private AlmacenResponseDto mapToResponseDTO(Almacen warehouse) {
        CedeResponseDto cedeDto = CedeResponseDto.builder()
                .id(warehouse.getCede().getId())
                .key(warehouse.getCede().getClave())
                .state(warehouse.getCede().getEstado())
                .city(warehouse.getCede().getMunicipio())
                .build();

        return AlmacenResponseDto.builder()
                .id(warehouse.getId())
                .key(warehouse.getClave())
                .registrationDate(warehouse.getFechaRegistro())
                .salePrice(warehouse.getPrecioVenta())
                .rentalPrice(warehouse.getPrecioRenta())
                .size(warehouse.getSize())
                .cede(cedeDto)
                .build();
    }
}

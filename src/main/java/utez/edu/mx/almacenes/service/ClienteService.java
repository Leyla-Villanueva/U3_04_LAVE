package utez.edu.mx.almacenes.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import utez.edu.mx.almacenes.exception.ResourceNotFoundException;
import utez.edu.mx.almacenes.model.Cliente;
import utez.edu.mx.almacenes.repository.ClienteRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public Cliente createCliente(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    public List<Cliente> getAllClientes() {
        return clienteRepository.findAll();
    }

    public Cliente getClienteById(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con id: " + id));
    }

    public Cliente updateCliente(Long id, Cliente nuevoCliente) {
        Cliente cliente = getClienteById(id);
        cliente.setNombreCompleto(nuevoCliente.getNombreCompleto());
        cliente.setTelefono(nuevoCliente.getTelefono());
        cliente.setCorreoElectronico(nuevoCliente.getCorreoElectronico());
        return clienteRepository.save(cliente);
    }

    public void deleteCliente(Long id) {
        clienteRepository.deleteById(id);
    }
}
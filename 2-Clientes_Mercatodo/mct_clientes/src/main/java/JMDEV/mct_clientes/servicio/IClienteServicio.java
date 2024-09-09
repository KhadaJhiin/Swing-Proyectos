package JMDEV.mct_clientes.servicio;

import JMDEV.mct_clientes.Model.Cliente;

import java.util.List;

public interface IClienteServicio {

    public List<Cliente> listarClientes();

    public Cliente buscarClientePorId(Integer idcliente);

    public void guardarCliente(Cliente cliente);

    public void eliminarCliente(Cliente cliente);
}

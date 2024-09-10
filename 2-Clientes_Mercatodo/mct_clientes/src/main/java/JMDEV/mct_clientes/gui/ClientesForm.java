package JMDEV.mct_clientes.gui;

import JMDEV.mct_clientes.Model.Cliente;
import JMDEV.mct_clientes.servicio.ClienteServicio;
import JMDEV.mct_clientes.servicio.IClienteServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

@Component
public class ClientesForm extends JFrame {
    private JPanel panelPrincipal;
    private JTable clientesTabla;
    private JTextField nombreTexto;
    private JTextField apellidoTexto;
    private JTextField ciudadTexto;
    private JTextField direccionTexto;
    private JTextField telefonoTexto;
    private JTextField puntosTexto;
    private JButton guardarButton;
    private JButton eliminarButton;
    private JButton limpiarButton;
    IClienteServicio clienteServicio;
    private DefaultTableModel tablaModeloClientes;

    @Autowired
    public ClientesForm(ClienteServicio clienteServicio){
        this.clienteServicio = clienteServicio;
        inicarForma();
        guardarButton.addActionListener(e -> guardarCliente ());
    }

    private void inicarForma(){
        setContentPane(panelPrincipal);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900,700);
        setLocationRelativeTo(null);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here

        //We design the table, in the first argument we insert the number of rows, and the second argument the columns

        this.tablaModeloClientes = new DefaultTableModel(0,7);
        String[] cabeceros = {"Id","Nombre","Apellido","Ciudad","Direccion","Telefono","Puntos PC"};
        this.tablaModeloClientes.setColumnIdentifiers(cabeceros);
        this.clientesTabla = new JTable(tablaModeloClientes);

        //We loaded the objets client list to the table
        listClients();
    }

    private void listClients(){
        this.tablaModeloClientes.setRowCount(0);
        List<Cliente> clientes = this.clienteServicio.listarClientes();
        clientes.forEach(cliente -> {
            Object[] renglonCliente = {
                    cliente.getIdcliente(),
                    cliente.getNombre(),
                    cliente.getApellido(),
                    cliente.getCiudad(),
                    cliente.getDireccion(),
                    cliente.getTelefono(),
                    cliente.getPuntosPC()
            };
            this.tablaModeloClientes.addRow(renglonCliente);
        });
    }
    private void guardarCliente(){
        if(nombreTexto.getText().equals("")){
            mostrarMensaje("Proporcione un nombre");
            nombreTexto.requestFocusInWindow();
            return;
        }
        if(apellidoTexto.getText().equals("")){
            mostrarMensaje("Proporcione un apellido");
            apellidoTexto.requestFocusInWindow();
            return;
        }
        if(telefonoTexto.getText().equals("")){
            mostrarMensaje("Proporcione un numero de telefono");
            telefonoTexto.requestFocusInWindow();
            return;
        }

        //We saved the values from formulary
        String nombre = nombreTexto.getText();
        String apellido = apellidoTexto.getText();
        String ciudad = ciudadTexto.getText();
        String direccion= direccionTexto.getText();
        String telefono = telefonoTexto.getText();
        int puntosPC = Integer.parseInt(puntosTexto.getText());

        Cliente cliente = new Cliente();
        cliente.setNombre(nombre);
        cliente.setApellido(apellido);
        cliente.setCiudad(ciudad);
        cliente.setDireccion(direccion);
        cliente.setTelefono(telefono);
        cliente.setPuntosPC(puntosPC);

        this.clienteServicio.guardarCliente(cliente);
        limpiarFormulario();
        listClients();
    }
    private void mostrarMensaje(String mensaje){
        JOptionPane.showMessageDialog(this,mensaje);
    }

    private void limpiarFormulario(){
        nombreTexto.setText("");
        apellidoTexto.setText("");
        ciudadTexto.setText("");
        direccionTexto.setText("");
        telefonoTexto.setText("");
        puntosTexto.setText("");
    }
}

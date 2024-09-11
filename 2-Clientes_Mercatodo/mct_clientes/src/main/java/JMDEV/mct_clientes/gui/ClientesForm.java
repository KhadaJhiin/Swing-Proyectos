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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
    private Integer idCliente;

    @Autowired
    public ClientesForm(ClienteServicio clienteServicio){
        this.clienteServicio = clienteServicio;
        inicarForma();
        guardarButton.addActionListener(e -> guardarCliente ());


        clientesTabla.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                cargarClienteSeleccionado();
            }
        });
        eliminarButton.addActionListener(e -> eliminarCliente());
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

        Cliente cliente = new Cliente(this.idCliente,nombre,apellido,ciudad,direccion,telefono,puntosPC);
        //It is not necessary to pass the attributes by set since they are in the constructor
//        cliente.setIdcliente(this.idCliente);
//        cliente.setNombre(nombre);
//        cliente.setApellido(apellido);
//        cliente.setCiudad(ciudad);
//        cliente.setDireccion(direccion);
//        cliente.setTelefono(telefono);
//        cliente.setPuntosPC(puntosPC);

        this.clienteServicio.guardarCliente(cliente);//Insert or modify depending on the client id attribute
        if(idCliente==null){
            mostrarMensaje("Se agrego cliente");
        }else {
            mostrarMensaje("Se modifico el cliente");
        }
        limpiarFormulario();
        listClients();
    }

    private void cargarClienteSeleccionado(){
        int renglon = clientesTabla.getSelectedRow();
        if(renglon != -1){ //-1 means no log was selected
            String id = clientesTabla.getModel().getValueAt(renglon,0).toString();
            this.idCliente = Integer.parseInt(id);
            String nombre = clientesTabla.getModel().getValueAt(renglon,1).toString();
            this.nombreTexto.setText(nombre);
            String apellido = clientesTabla.getModel().getValueAt(renglon,2).toString();
            this.apellidoTexto.setText(apellido);
            String ciudad = clientesTabla.getModel().getValueAt(renglon,3).toString();
            this.ciudadTexto.setText(ciudad);
            String direccion = clientesTabla.getModel().getValueAt(renglon,4).toString();
            this.direccionTexto.setText(direccion);
            String telefono = clientesTabla.getModel().getValueAt(renglon,5).toString();
            this.telefonoTexto.setText(telefono);
            String puntosPC = clientesTabla.getModel().getValueAt(renglon,6).toString();
            this.puntosTexto.setText(puntosPC);
        }
    }

    private void eliminarCliente(){
        int renglon = clientesTabla.getSelectedRow();
        if (renglon!=-1){
            String idClienteStr = clientesTabla.getModel().getValueAt(renglon,0).toString();
            this.idCliente = Integer.parseInt(idClienteStr);
            Cliente cliente = new Cliente();
            cliente.setIdcliente(this.idCliente);
            clienteServicio.eliminarCliente(cliente);
            mostrarMensaje("Cliente con id: "+this.idCliente+" eliminado");
            limpiarFormulario();
            listClients();
        }else {
            mostrarMensaje("Debe seleccionar un cliente a eliminar");
        }
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
        this.idCliente=null;
        //Deseleccionamos cualquier seleccion realizada en la tabla
        this.clientesTabla.getSelectionModel().clearSelection();
    }
}

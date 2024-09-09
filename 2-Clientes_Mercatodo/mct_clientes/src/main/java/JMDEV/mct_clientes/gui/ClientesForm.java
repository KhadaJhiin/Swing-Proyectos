package JMDEV.mct_clientes.gui;

import JMDEV.mct_clientes.servicio.ClienteServicio;
import JMDEV.mct_clientes.servicio.IClienteServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;

@Component
public class ClientesForm extends JFrame {
    private JPanel panelPrincipal;
    IClienteServicio clienteServicio;

    @Autowired
    public ClientesForm(ClienteServicio clienteServicio){
        this.clienteServicio = clienteServicio;
        inicarForma();
    }

    private void inicarForma(){
        setContentPane(panelPrincipal);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900,700);
        setLocationRelativeTo(null);
    }

}

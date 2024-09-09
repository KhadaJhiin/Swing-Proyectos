package JMDEV.mct_clientes;

import JMDEV.mct_clientes.gui.ClientesForm;
import ch.qos.logback.core.net.server.Client;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import javax.swing.*;

@SpringBootApplication
public class ClientesSwing {
    public static void main(String[] args) {

        //We created de spring factory
        ConfigurableApplicationContext contextoSpring =
                new SpringApplicationBuilder(ClientesSwing.class)
                        .headless(false)
                        .web(WebApplicationType.NONE)
                        .run(args);

        //We created a swing object
        SwingUtilities.invokeLater(()->{
            ClientesForm clientesForm = contextoSpring.getBean(ClientesForm.class);
            clientesForm.setVisible(true);
        });
    }
}

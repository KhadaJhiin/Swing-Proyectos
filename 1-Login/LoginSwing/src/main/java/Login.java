import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login extends JFrame{
    private JPanel panelPrincipal;
    private JTextField usuarioTexto;
    private JPasswordField passwordTexto;
    private JButton enviarBoton;

    public Login(){
        inicializarForma();
        enviarBoton.addActionListener(e -> validar());
    }

    private void inicializarForma(){
        setContentPane(panelPrincipal);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600,400);
        setLocationRelativeTo(null);
    }

    private void validar(){
        String usuario = this.usuarioTexto.getText();
        String password = new String(this.passwordTexto.getPassword());

        if("root".equals(usuario) && "admin".equals(password)){
            mostrarMensaje("Datos correctos, bienvenido");
        }else if("root".equals(usuario)){
            mostrarMensaje("Contraseña incorrecta, intentar de nuevo");
        }else{
            mostrarMensaje("Usuario incorrecto, intentar de nuevo");
        }

    }

    private void mostrarMensaje(String mensaje){
        JOptionPane.showMessageDialog(this, mensaje);

    }

    public static void main(String[] args) {
        Login login = new Login();
        login.setVisible(true);

    }
}



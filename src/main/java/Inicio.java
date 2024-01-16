import javax.swing.*;
import java.io.IOException;

public class Inicio {
    public static void main(String[] args) throws IOException {
        int opcion_menu = -1;
        String[] botones = {" 1. Ver Gatos", "  2. Ver favoritos", " 3. Salir"};

        do {
            //menú principal:
            String opcion = (String) JOptionPane.showInputDialog(null, "Gatitos Java", "Menú Principal", JOptionPane.INFORMATION_MESSAGE,
                    null, botones, botones[0]);
            //validamos qué opción selecciona el usuario:
            for (int i=0;i<botones.length;i++) {
                if (opcion.equals(botones[i])){
                    opcion_menu=i;
                }
            }
            switch (opcion_menu){
                case 0:
                    GatoServicio.verGatos();
                    break;
                case 1:
                    Gato gato = new Gato();
                    GatoServicio.verFavoritos(gato.getApiKey());
                    break;
                default:
                    break;
            }
        }while (opcion_menu != 1);

    }

    /**
     * Este es un repositorio de práctica que sigue un curso de Santiago Bernal, en Platzi.
     *
     * Se sigue tal cual por la claridad de las explicaciones, pero se puede ver su actualización con buenas prácticas en:
     * https://github.com/santiaguf/gatos_app
     *
     * Observación: las claves, contraseñas, keys, etc., no deben quemarse en el código sino que se
     * recomienda manejar un archivo aparte que las coleccione. Ese archivo simplemente se lee de
     * manera local y no se almacena en el repositorio (para no exponer información sensible, a menos
     * que el repositorio sea privado o permita ocultar archivos).
     *
     */
}

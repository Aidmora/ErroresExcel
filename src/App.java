import javax.swing.JOptionPane;

import LecturaArchivo.LecturaArchivos;

public class App {
    private final static String RUTA = "ExcelArchivo";

    public static void main(String[] args) throws Exception {
        LecturaArchivos la = new LecturaArchivos();
        la.LeerArchivos(RUTA);
        int opcion = Integer.parseInt(
                JOptionPane.showInputDialog("MENÚ \n  Escoja una de las siguientes opciones:" + "\n"
                        + "1. Errores en Teléfonos de  los Estudiantes" + "\n"
                        + "2. Eliminar en Calificaciones de los Estudiantes " + "\n"
                        + "3. Salir" + "\n"));
        switch (opcion) {
            case 1:
                la.tratamientoErroresTelefono();
                break;
            case 2:
                la.tratamientoErroresCalificaciones();
                break;
            case 3:
                JOptionPane.showMessageDialog(null, "Fin programa");
                break;
            default:
                JOptionPane.showMessageDialog(null, "Opción Incorrecta");
                break;
        }

    }
}

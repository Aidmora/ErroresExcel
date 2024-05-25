package LecturaArchivo;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import FrameWork.AppException;

public class LecturaArchivos {
    public List<String> mnLines;
    public List<String> telefonoEstudiante = new ArrayList<>();
    public List<String> calificacioneEstudiante = new ArrayList<>();

    public void LeerArchivos(String ExcelArchivo) throws IOException, AppException, SQLException {
        File f = new File(ExcelArchivo);
        if (f.isDirectory()) {
            String[] fileNames = f.list();
            for (String nomArchivo : fileNames) {
                if (nomArchivo.endsWith(".csv")) {
                    String nombreArchivo = ExcelArchivo + "\\" + nomArchivo;
                    mnLines = Files.readAllLines(Paths.get(nombreArchivo), Charset.forName("UTF-8"));
                    for (String line : mnLines) {
                        String[] values = line.split(";");
                        if (values[0].toLowerCase().trim().contains("codigo_est")) {
                            continue;
                        }
                        telefonoEstudiante.add(values[3]);
                        calificacioneEstudiante.add(values[6]);
                    }
                } else {
                    System.out.println(";/ Error en ExcelArchivo: " + ExcelArchivo);
                }
            }
        }
        System.out.println("Se leyo el archivo con exito.");
    }

    public void tratamientoErroresCalificaciones() {
        int errorSinRegistro = contarEspaciosBlancos(calificacioneEstudiante);
        int errorVerificacioNum = verficarRegistroNumero(calificacioneEstudiante);
        String notaValid = identificarNotaValida(calificacioneEstudiante);
        String mensajeError = String
                .format(" Los errores encontrados en el apartado de califcaciones han sido los siguientes:" + "\n"
                        + "1. Existen %d reigstros vacíos " + "\n"
                        + "2. Existen %d teléfonos que no tienen un formato conformado por solo números" + "\n"
                        + "3. %s", errorSinRegistro, errorVerificacioNum, notaValid);
        JOptionPane.showMessageDialog(null, mensajeError);
    }

    private String identificarNotaValida(List<String> calificacioneEstudiante2) {
        int contadorCalificacionE = 0;
        for (String calificacion : calificacioneEstudiante2) {
            if (!(calificacion.equals(""))) {
                int calificacionInt = Integer.parseInt(calificacion);
                if (calificacionInt > 20 || calificacionInt < 0) {
                    contadorCalificacionE++;
                }
            }
        }
        String errorMensaje = String.format(
                "La cantidad de calificación que no están en el intervalo de [0-20] son: %d", contadorCalificacionE);
        return errorMensaje;
    }

    public void tratamientoErroresTelefono() {
        int errorSinRegistro = contarEspaciosBlancos(telefonoEstudiante);
        String errorPrivincia = identificarExistenciaProvincia(telefonoEstudiante);
        String errorLongitud = identificarLongitudTelefono(telefonoEstudiante);
        int errorVerificacioNum = verficarRegistroNumero(telefonoEstudiante);
        String mensajeError = String
                .format(" Los errores encontrados en el apartado de télefonos han sido los siguientes:" + "\n"
                        + "1. Existen %d reigstros vacíos " + "\n"
                        + "2. %s" + "\n"
                        + "3. Existen %d calificaciones que no tienen un formato conformado por solo números" + "\n"
                        + "4. %s", errorSinRegistro, errorPrivincia, errorVerificacioNum, errorLongitud);
        JOptionPane.showMessageDialog(null, mensajeError);

    }

    private int verficarRegistroNumero(List<String> telefonoEstudiante2) {
        int contadorRegistroStr = 0;
        for (String Registro : telefonoEstudiante2) {
            if (!(Registro.equals(""))) {
                try {
                    int numeroTransformar = Integer.parseInt(Registro);
                } catch (NumberFormatException e) {
                    contadorRegistroStr++;
                }
            }

        }
        return contadorRegistroStr;
    }

    private String identificarLongitudTelefono(List<String> telefonoEstudiante2) {
        int contadorLongitud = 0;
        for (String telefono : telefonoEstudiante2) {
            if (!(telefono.equals(""))) {
                if (telefono.length() != 9) {
                    contadorLongitud++;
                }
            }
        }
        String errorEncontrado = String.format(
                "En el archivo de Excel, la cantidad de teléfonos que no cumplen los 9 dígitos son: %d",
                contadorLongitud);
        return errorEncontrado;
    }

    private String identificarExistenciaProvincia(List<String> telefonoEstudiante2) {
        int contadorEProvincia = 0;
        String numero = "";
        for (String telefono : telefonoEstudiante2) {
            if (!(telefono.equals(""))) {
                char primerDigito = telefono.charAt(0);
                String primerDigitoStr = primerDigito + "";
                if (!primerDigitoStr.matches("[2-7]")) {
                    contadorEProvincia++;
                }
            }
        }
        String errorEncontrado = String.format("En el archivo de Excel, la cantidad de teléfonos que no corresponden"
                + " a ninguna provincia del Ecuador son : %d ", contadorEProvincia);
        return errorEncontrado;
    }

    private int contarEspaciosBlancos(List<String> telefonoEstudiante2) {
        int contadorEspacios = 0;
        for (String registro : telefonoEstudiante2) {
            if (registro.equals("")) {
                contadorEspacios++;
            }
        }
        return contadorEspacios;
    }

}

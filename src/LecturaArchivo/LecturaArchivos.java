package LecturaArchivo;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
                        telefonoEstudiante.add(values[3]);
                        calificacioneEstudiante.add(values[6]);
                    }
                } else {
                    System.out.println(";/ Error en ExcelArchivo: " + ExcelArchivo);
                }
            }
        }
        System.out.println("Se leyo el archivo con exito.");
        tratamientoErroresTelefono(telefonoEstudiante);
    }

    private void tratamientoErroresTelefono(List<String> telefonoEstudiante2) {
        String errorSinRegistro = contarEspaciosBlancos(telefonoEstudiante2);
        String errorPrivincia = identificarExistenciaProvincia(telefonoEstudiante2);
        String errorLongitud = identificarLongitudTelefono(telefonoEstudiante2);

    }

    private String identificarLongitudTelefono(List<String> telefonoEstudiante2) {
        return "";
    }

    private String identificarExistenciaProvincia(List<String> telefonoEstudiante2) {
        int contadorEProvincia = 0;
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
                + " a ninguna provincia del ecuador son : %d", contadorEProvincia);
        return errorEncontrado;
    }

    private String contarEspaciosBlancos(List<String> telefonoEstudiante2) {
        int contadorEspacios = 0;
        for (String telefono : telefonoEstudiante2) {
            if (telefono.equals("")) {
                contadorEspacios++;
            }
        }
        String errorEncontrado = String.format("En el archivo de Excel, en los teléfonos correspodientes"
                + " a los estudiantes se encontraron %d registros en blanco", contadorEspacios);
        return errorEncontrado;
    }

}

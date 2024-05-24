import LecturaArchivo.LecturaArchivos;

public class App {
    private final static String RUTA = "ExcelArchivo";

    public static void main(String[] args) throws Exception {

        LecturaArchivos la = new LecturaArchivos();
        la.LeerArchivos(RUTA);

    }
}

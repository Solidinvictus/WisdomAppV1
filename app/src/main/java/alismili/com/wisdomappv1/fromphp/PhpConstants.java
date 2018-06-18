package alismili.com.wisdomappv1.fromphp;

public class PhpConstants {

    public class ServiceCode {
        public static final int HOME = 1;
    }
    // web service url constants
    public class ServiceType {
        public static final String URL_GET_USERS = "http://192.168.0.159/Android/v2/users.php?apicall=getusers";
        public static final String URL_GET_CATEGORIES = "http://192.168.0.159/Android/v2/categories.php?apicall=getcategories";
        public static final String URL_GET_TRASLATETYPES = "http://192.168.0.159/Android/v2/traslatetypes.php?apicall=gettraslatetypes";
        public static final String URL_GET_LANGUAGES = "http://192.168.0.159/Android/v2/languages.php?apicall=getlanguages";
        public static final String URL_GET_TRASLATIONS = "http://192.168.0.159/Android/v2/traslations.php?apicall=gettraslations";
        public static final String URL_GET_CORES ="http://192.168.0.159/Android/v2/cores.php?apicall=getcores";

    }
    // webservice key constants
    public class Params {

        //Cobstantes para usuarios
        public static final String ID_USUARIO = "id_usuario";
        public static final String ALIAS = "alias";
        public static final String EMAIL = "email";
        public static final String PASSWORD = "password";

        //Constantes para categoriascontenidos
        public static final String ID_CATEGORIA = "id_categoria";
        public static final String NOMBRE_CATEGORIA = "nombre_categoria";
        public static final String DESCRIPCION_CATEGORIA = "descripcion_categoria";
        //public static final String POR_DEFECTO = "1";

        //Constantes para tipo_traduccion
        public static final String ID_TIPO_TRADUCCION = "id_tipo_traduccion";
        public static final String ORIGEN_DESTINO = "origen_destino";

        //Constantes para idioma
        public static final String ID_IDIOMA = "id_idioma";
        public static final String IDIOMA = "idioma";

        //Constantes para traducciones
        public static final String ID_TRADUCCION = "id_traduccion";
        public static final String ID_TIPO_TRADUCCION_fk = "id_tipo_traduccion";
        public static final String ID_CONTENIDO_ORIGEN = "id_contenido_origen";
        public static final String ID_CONTENIDO_TRADUCCION = "id_contenido_traduccion";
        public static final String DESCRIPCION_TRADUCCION = "descripcion_traduccion";
        public static final String ES_REVERSIBLE = "es_reversible";


        //Constantes para nucleos
        public static final String ID_NUCLEO = "id_nucleo";
        public static final String CLAVE = "clave";
        public static final String VALOR= "valor";
        public static final String ID_TIPO_TRADUCCION_FK = "id_tipo_traduccion";
        public static final String ID_CATEGORIA_FK  = "id_categoria";
        public static final String ID_USUARIO_FK = "id_usuario";
        public static final String TIPO = "tipo";

    }
}

package src.com.crud.ejemplo.dao;



import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import com.mongodb.*;
import src.com.crud.ejemplo.pojo.Libro;

import static src.com.crud.ejemplo.dao.MongoConector.getConnectionDbAndCollection;


public class LibroDAO {

    private static final String DB = "gestion-libros";
    private static final String COLLECTION = "libros";

    private static LibroDAO INSTANCE = null;
    private static ArrayList<Libro> libros;

    static {
        libros = new ArrayList<>();
    }

    public static synchronized LibroDAO getInstance() {

        if (INSTANCE == null) {
            INSTANCE = new LibroDAO();
        }

        return INSTANCE;
    }


    public boolean crearLibro(Libro l) throws UnknownHostException {

        boolean resul = false;
        BasicDBObject dBObjectLibro;

        DBCollection collection = getConnectionDbAndCollection(DB, COLLECTION);
        long numDocumentos = collection.getCount();
        l.setId(numDocumentos + 1);
        dBObjectLibro = toDBObjectLibro(l);
        collection.insert(dBObjectLibro);

        if (collection.getCount() > numDocumentos) {
            resul = true;
        }

        return resul;
    }

    private BasicDBObject toDBObjectLibro(Libro l) {

        // Creamos una instancia BasicDBObject
        BasicDBObject dBObjectLibro = new BasicDBObject();

        dBObjectLibro.append("id", l.getId());
        dBObjectLibro.append("titulo", l.getTitulo());
        dBObjectLibro.append("precio", l.getPrecio());
        dBObjectLibro.append("isbn", l.getIsbn());
        dBObjectLibro.append("editorial", l.getEditorial());


        return dBObjectLibro;
    }

    // Transformo un objecto que me da MongoDB a un Objecto Java
    private Libro deMongoaJava(BasicDBObject toDBObjectLibro) {
        Libro l = new Libro();
        l.setId(toDBObjectLibro.getLong("id"));
        l.setTitulo(((toDBObjectLibro.getString("titulo") == null) ? String.valueOf(' ') : toDBObjectLibro.getString("titulo")));
        l.setIsbn(((toDBObjectLibro.getString("isbn") == null) ? String.valueOf(' ') : toDBObjectLibro.getString("isbn")));
        l.setPrecio((toDBObjectLibro.getInt("precio")));
        l.setEditorial(((toDBObjectLibro.getString("editorial") == null) ? String.valueOf(' ') : toDBObjectLibro.getString("editorial")));

        return l;
    }

    public List<Libro> listar() throws UnknownHostException {
        libros = new ArrayList<>();

        // PASO 3: Obtenemos una coleccion para trabajar con ella
        DBCollection collection = src.com.crud.ejemplo.dao.MongoConector.getConnectionDbAndCollection(DB, COLLECTION);
        int numDocumentos = (int) collection.getCount();
        System.out.println("Número de documentos en la colección libros: " + numDocumentos + "\n");

        // Busco todos los documentos de la colección y los imprimo
        try (DBCursor cursor = collection.find()) {
            while (cursor.hasNext()) {
                libros.add(deMongoaJava((BasicDBObject) cursor.next()));

            }
        }
        return libros;
    }

    public boolean eliminar(int id) throws UnknownHostException {
        boolean resul = false;
        DBCollection collection = src.com.crud.ejemplo.dao.MongoConector.getConnectionDbAndCollection(DB, COLLECTION);
        long numDocumentos = collection.getCount();

        DBObject findDoc = new BasicDBObject("id", id);
        collection.remove(findDoc);

        if (collection.getCount() < numDocumentos) {
            resul = true;
        }

        return resul;
    }


    public Libro buscarPorId(int id) throws UnknownHostException {

        DBCollection collection = src.com.crud.ejemplo.dao.MongoConector.getConnectionDbAndCollection(DB, COLLECTION);
        DBObject findDoc = new BasicDBObject("id", new BasicDBObject("id", id));


        // return deMongoaJava((BasicDBObject) findDoc);
        System.out.println(findDoc);
        return (Libro) findDoc;
    }


    public boolean modificar(Libro lOriginal, Libro lActualizacion) throws UnknownHostException {
        /**
         * Para la actualizacion collection.update pide 2 objetos el orginal y el nuevo objeto modificado
         */
        System.out.println((lOriginal.getId()));
        System.out.println(lActualizacion.toString());
        DBCollection collection = MongoConector.getConnectionDbAndCollection(DB, COLLECTION) ;
        System.out.println(lOriginal.getId());
        System.out.println(lActualizacion);
        System.out.println(lOriginal.toString());
        System.out.println(lActualizacion.toString());
        DBObject lOriginalObj = toDBObjectLibro(lOriginal);
        DBObject lActualizacionObj = toDBObjectLibro(lActualizacion) ;


        DBObject findDoc = new BasicDBObject("id", lOriginal.getId());

        System.out.println(findDoc);
        collection.update(findDoc,lActualizacionObj);
        //collection.update({id:0},{titulo:'php'});
//        DBCursor cursor = collection.find(lOriginalObj);
//
//       while(cursor.hasNext()){
//           System.out.println(cursor.next());
//       }
        //collection.find({"id": lOriginalObj});

        return false;
    }


    public void actualizarTest(Libro lOriginal, Libro lActualizacion) throws UnknownHostException {
        // find hosting = hostB, and update the clients to 110

        DBCollection collection = src.com.crud.ejemplo.dao.MongoConector.getConnectionDbAndCollection(DB, COLLECTION);

        BasicDBObject newDocument = new BasicDBObject();
        newDocument.put("id", lActualizacion.getId());
        newDocument.put("titulo", lActualizacion.getTitulo());
        newDocument.put("editorial", lActualizacion.getEditorial());
        newDocument.put("isbn", lActualizacion.getIsbn());
        newDocument.put("precio", lActualizacion.getPrecio());


        BasicDBObject searchQuery = new BasicDBObject().append("id", lOriginal.getId());

        collection.update(searchQuery, newDocument);
    }
}

package src.com.crud.ejemplo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.UnknownHostException;
import java.util.ArrayList;


import src.com.crud.ejemplo.dao.LibroDAO;
import src.com.crud.ejemplo.pojo.Libro;


public class Main {

    private final static int CREAR_LIBRO = 1;
    private final static int LISTAR_LIBROs = 2;
    private final static int MODFICAR_LIBRO = 3;
    private final static int ELIMINAR_LIBRO = 4;
    private final static String CREADO_EXITO = "Libro dado de alta correctamente";
    private static Libro l = new Libro();
    private static LibroDAO librosDao = new LibroDAO();

    public static void main(String[] args) {

        librosDao = LibroDAO.getInstance();



        try {

            opcionesPrincipales();




        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static void opcionesPrincipales() throws UnknownHostException{


        int opcion;
        opcion = opcionPrincipal();
        switch (opcion) {

            case CREAR_LIBRO:
                crearLibro();
                break;
            case LISTAR_LIBROs:
                listarLibros();
                break;
            case MODFICAR_LIBRO:
                modificaLibro();
                break;
            case ELIMINAR_LIBRO:
                eliminarLibro();
                break;
        }

        opcionPrincipal();


    }
    private static void eliminarLibro() throws UnknownHostException {
        int id;
        try {
            InputStreamReader isr = new InputStreamReader(System.in);
            System.out.println("**************Introduzca el id del libro que quiere eliminar*********************");
            BufferedReader br = new BufferedReader(isr);
            id = Integer.parseInt(br.readLine()) ;

            librosDao.eliminar(id);

        }catch (IOException e){
            e.printStackTrace();
        }

        opcionesPrincipales();
    }

    private static void modificaLibro() throws UnknownHostException{

        int id ;
        String titulo;
        int precio;
        String editorial;
        String isbn;

        Libro libroModificar = new Libro();
        Libro lOriginal = new Libro();
        try {

            InputStreamReader isr = new InputStreamReader(System.in);
            System.out.println("**************Introduzca el id del libro que quiere modificar*********************");
            BufferedReader br = new BufferedReader(isr);
            id = Integer.parseInt(br.readLine());

            System.out.println(lOriginal);

            isr = new InputStreamReader(System.in);
            System.out.println("**************Introduzca el titulo del libro*********************");
            br = new BufferedReader(isr);
            titulo = br.readLine();


            isr = new InputStreamReader(System.in);
            System.out.println("**************Introduzca el precio del libro*********************");
            br = new BufferedReader(isr);
            precio = Integer.parseInt(br.readLine());

            isr = new InputStreamReader(System.in);
            System.out.println("**************Introduzca la editorial del libro*********************");
            br = new BufferedReader(isr);
            editorial = br.readLine();

            isr = new InputStreamReader(System.in);
            System.out.println("**************Introduzca el codigo isbn*********************");
            br = new BufferedReader(isr);
            isbn = br.readLine();

            lOriginal.setId(id);
            libroModificar.setTitulo(titulo);
            libroModificar.setEditorial(editorial);
            libroModificar.setIsbn(isbn);
            libroModificar.setPrecio(precio);

            //librosDao.modificar(lOriginal , libroModificar );
            librosDao.actualizarTest(lOriginal,libroModificar);



        }catch (IOException e){
            e.printStackTrace();
        }
        opcionesPrincipales();

    }

    private static void listarLibros() throws UnknownHostException {
        ArrayList<Libro> libros = (ArrayList<Libro>) librosDao.listar();
        libros.forEach(l -> System.out.println(l.toString()));
        opcionesPrincipales();

    }

    private static void crearLibro() throws UnknownHostException{
        String titulo;
        int precio = 0;
        String isbn;
        String editorial;

        try {
            InputStreamReader isr = new InputStreamReader(System.in);
            System.out.println("**************Introduzca el titulo*********************");
            BufferedReader br = new BufferedReader(isr);
            titulo = br.readLine();

            System.out.println("**************Introduzca el codigo isbn*********************");
            br = new BufferedReader(isr);
            isbn = br.readLine();

            System.out.println("**************Introduzca el precio *********************");
            br = new BufferedReader(isr);
            try {
                precio = Integer.parseInt(br.readLine());
            } catch (Exception e) {
                System.out.println("Debe introducir un numero en el precio del libro");
                crearLibro();
            }


            System.out.println("**************Introduzca la editorial*********************");
            br = new BufferedReader(isr);
            editorial = br.readLine();

            l.setTitulo(titulo);
            l.setIsbn(isbn);
            l.setPrecio(precio);
            l.setEditorial(editorial);
            boolean resul = librosDao.crearLibro(l);

            if (resul) {
                System.out.println(CREADO_EXITO);
            }


        } catch (Exception e) {
            e.printStackTrace();
            opcionesPrincipales();


        } finally {
            opcionesPrincipales();
        }
    }

    private static int opcionPrincipal() {
        int opcion = 0;
        System.out.println("**************Gestor de libros*********************");
        System.out.println("**************Elija una opcion*********************");
        System.out.println("**************Crear libro: Introducza 1*********************");
        System.out.println("**************Listar libros: Introduzca 2*********************");
        System.out.println("**************Modificar libros: Introduza 3*********************");
        System.out.println("**************Eliminar libros: Introduza 4*********************");
        System.out.println("Introduzca una opcion");
        try {
            InputStreamReader isr = new InputStreamReader(System.in);
            BufferedReader br = new BufferedReader(isr);
            opcion = Integer.parseInt(br.readLine());


        } catch (Exception e) {

            if (e.getMessage().contains("For input string")) {
                System.out.println("Por favor introduzca una opcion valida");
            }

        }
        return opcion;
    }
}

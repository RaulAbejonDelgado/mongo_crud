package src.com.crud.ejemplo.pojo;

public class Libro {

    private static int contador;
    private long id;
    private String titulo;
    private int precio;
    private String isbn;
    private String editorial;

    public Libro() {
        super();
        this.id =0;
        this.titulo = "";
        this.precio = 0;
        this.isbn = "";
        this.editorial = "";
    }


    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getEditorial() {
        return editorial;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    @Override
    public String toString() {
        return "Libro{" +
                "id="+ this.id + '\'' +
                "titulo='" + this.titulo + '\'' +
                ", precio=" + this.precio +
                ", isbn='" + this.isbn + '\'' +
                ", editorial='" + this.editorial + '\'' +
                '}';
    }
}

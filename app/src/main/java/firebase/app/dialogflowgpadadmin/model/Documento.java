package firebase.app.dialogflowgpadadmin.model;

public class Documento {

    private String uid;
    private String Titulo;
    private String Descripcion;
    private String Imagen;

    //construvtor vacio
    public Documento() {
    }

    //get and setter
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getTitulo() {
        return Titulo;
    }

    public void setTitulo(String titulo) {
        Titulo = titulo;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public String getImagen() {
        return Imagen;
    }

    public void setImagen(String imagen) {
        Imagen = imagen;
    }

    //to string titulo


    @Override
    public String toString()
    {
        return Titulo;
    }
}

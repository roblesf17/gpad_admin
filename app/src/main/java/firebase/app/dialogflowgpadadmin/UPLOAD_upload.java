package firebase.app.dialogflowgpadadmin;

public class UPLOAD_upload
{

    private String titulo;

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    private String imagen;

    private String descripcion;

    public UPLOAD_upload()
    {

    }


    public UPLOAD_upload ( String name , String imageUrl , String Descripcion)
    {
        if (name.trim().equals( "" ))
        {
            name = "No Name";
        }
        titulo =  name ;
        imagen = imageUrl ;
        descripcion = Descripcion;
    }




}

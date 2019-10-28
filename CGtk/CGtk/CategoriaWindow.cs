using System;
using System.Data;

namespace CGtk
{
    public partial class CategoriaWindow : Gtk.Window
    {

        public CategoriaWindow(Categoria categoria) :
               base(Gtk.WindowType.Toplevel) {
            this.Build();

            if (categoria != null)
                entryNombre.Text = categoria.Nombre;

            buttonAceptar.Clicked += (sender, e) => Guardar();
            buttonCancelar.Clicked += (sender, e) => this.Destroy();
        }


        public void Guardar() {
            if (entryNombre.Text != null) {
                CategoriaDao.Save(new Categoria(0, entryNombre.Text));
                this.Destroy();
            }

        }

    }
}

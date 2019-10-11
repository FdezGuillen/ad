using System;
namespace CGtk
{
    public partial class CategoriaWindow : Gtk.Window
    {
        public CategoriaWindow() :
                base(Gtk.WindowType.Toplevel) {
            this.Build();
        }
    }
}


// This file has been generated by the GUI designer. Do not modify.
namespace CGtk
{
	public partial class CategoriaWindow
	{
		private global::Gtk.VBox vbox2;

		private global::Gtk.Table table1;

		private global::Gtk.Entry entry1;

		private global::Gtk.Label labelNombre;

		private global::Gtk.HBox hbox1;

		private global::Gtk.Button buttonCancelar;

		private global::Gtk.Button buttonAceptar;

		protected virtual void Build()
		{
			global::Stetic.Gui.Initialize(this);
			// Widget CGtk.CategoriaWindow
			this.Name = "CGtk.CategoriaWindow";
			this.Title = global::Mono.Unix.Catalog.GetString("CategoriaWindow");
			this.WindowPosition = ((global::Gtk.WindowPosition)(4));
			// Container child CGtk.CategoriaWindow.Gtk.Container+ContainerChild
			this.vbox2 = new global::Gtk.VBox();
			this.vbox2.Name = "vbox2";
			this.vbox2.Spacing = 6;
			// Container child vbox2.Gtk.Box+BoxChild
			this.table1 = new global::Gtk.Table(((uint)(1)), ((uint)(2)), false);
			this.table1.Name = "table1";
			this.table1.RowSpacing = ((uint)(6));
			this.table1.ColumnSpacing = ((uint)(6));
			// Container child table1.Gtk.Table+TableChild
			this.entry1 = new global::Gtk.Entry();
			this.entry1.CanFocus = true;
			this.entry1.Name = "entry1";
			this.entry1.IsEditable = true;
			this.entry1.InvisibleChar = '•';
			this.table1.Add(this.entry1);
			global::Gtk.Table.TableChild w1 = ((global::Gtk.Table.TableChild)(this.table1[this.entry1]));
			w1.LeftAttach = ((uint)(1));
			w1.RightAttach = ((uint)(2));
			w1.YOptions = ((global::Gtk.AttachOptions)(4));
			// Container child table1.Gtk.Table+TableChild
			this.labelNombre = new global::Gtk.Label();
			this.labelNombre.Name = "labelNombre";
			this.labelNombre.LabelProp = global::Mono.Unix.Catalog.GetString("Nombre: ");
			this.table1.Add(this.labelNombre);
			global::Gtk.Table.TableChild w2 = ((global::Gtk.Table.TableChild)(this.table1[this.labelNombre]));
			w2.XOptions = ((global::Gtk.AttachOptions)(4));
			w2.YOptions = ((global::Gtk.AttachOptions)(4));
			this.vbox2.Add(this.table1);
			global::Gtk.Box.BoxChild w3 = ((global::Gtk.Box.BoxChild)(this.vbox2[this.table1]));
			w3.Position = 0;
			w3.Expand = false;
			w3.Fill = false;
			// Container child vbox2.Gtk.Box+BoxChild
			this.hbox1 = new global::Gtk.HBox();
			this.hbox1.Name = "hbox1";
			this.hbox1.Spacing = 6;
			// Container child hbox1.Gtk.Box+BoxChild
			this.buttonCancelar = new global::Gtk.Button();
			this.buttonCancelar.CanFocus = true;
			this.buttonCancelar.Name = "buttonCancelar";
			this.buttonCancelar.UseUnderline = true;
			this.buttonCancelar.Label = global::Mono.Unix.Catalog.GetString("Cancelar");
			this.hbox1.Add(this.buttonCancelar);
			global::Gtk.Box.BoxChild w4 = ((global::Gtk.Box.BoxChild)(this.hbox1[this.buttonCancelar]));
			w4.Position = 0;
			w4.Expand = false;
			w4.Fill = false;
			// Container child hbox1.Gtk.Box+BoxChild
			this.buttonAceptar = new global::Gtk.Button();
			this.buttonAceptar.CanFocus = true;
			this.buttonAceptar.Name = "buttonAceptar";
			this.buttonAceptar.UseUnderline = true;
			this.buttonAceptar.Label = global::Mono.Unix.Catalog.GetString("Aceptar");
			this.hbox1.Add(this.buttonAceptar);
			global::Gtk.Box.BoxChild w5 = ((global::Gtk.Box.BoxChild)(this.hbox1[this.buttonAceptar]));
			w5.Position = 1;
			w5.Expand = false;
			w5.Fill = false;
			this.vbox2.Add(this.hbox1);
			global::Gtk.Box.BoxChild w6 = ((global::Gtk.Box.BoxChild)(this.vbox2[this.hbox1]));
			w6.Position = 1;
			w6.Expand = false;
			w6.Fill = false;
			this.Add(this.vbox2);
			if ((this.Child != null))
			{
				this.Child.ShowAll();
			}
			this.DefaultWidth = 400;
			this.DefaultHeight = 300;
			this.Show();
		}
	}
}

using System;
using Gtk;
using CGtk;

public partial class MainWindow : Gtk.Window
{
    public MainWindow() : base(Gtk.WindowType.Toplevel) {
        Build();

        treeView.AppendColumn("id", new CellRendererText(), "text", 0);
        treeView.AppendColumn("nombre", new CellRendererText(), "text", 1);
        ListStore listStore = new ListStore(typeof(string), typeof(string));
        treeView.Model = listStore;
        listStore.AppendValues("1", "cat 1");
        listStore.AppendValues("2", "cat 2");

        newAction.Activated += (sender, e) => new CategoriaWindow();

        quitAction.Activated += (sender, e) => Application.Quit();

    }

    protected void OnDeleteEvent(object sender, DeleteEventArgs a) {
        Application.Quit();
        a.RetVal = true;
    }
}

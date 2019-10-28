using System;
using Gtk;
using CGtk;
using System.Data;
using MySql.Data.MySqlClient;
using Serpis.Ad;
using System.Collections.Generic;

public partial class MainWindow : Gtk.Window
{

    ListStore listStore;

    public MainWindow() : base(Gtk.WindowType.Toplevel) {
        App.Instance.DbConnection = new MySqlConnection(
            "server=localhost;database=dbprueba;user=root;password=sistemas;ssl-mode=none"
        );
        App.Instance.DbConnection.Open();
        Build();

        TreeViewHelper.Fill(treeView, new string[] { "Id", "Nombre" }, CategoriaDao.GetAll());
       
        newAction.Activated += (sender, e) => new CategoriaWindow(null);
       
        editAction.Activated += (sender, e) => {
            object id = TreeViewHelper.GetId(treeView);
            Categoria categoria = CategoriaDao.Load(id);
            new CategoriaWindow(categoria);
        };

        deleteAction.Activated += (sender, e) => {
            object id = TreeViewHelper.GetId(treeView);
            CategoriaDao.Delete(id);
        };

        refreshAction.Activated += (sender, e) => CargarDatos();

        quitAction.Activated += (sender, e) => Cerrar();

        actualizarEstados();
        treeView.Selection.Changed += (sender, e) => actualizarEstados();

    }

    public void CargarDatos() {
        TreeViewHelper.Fill(treeView, new string[] { "Id", "Nombre" }, CategoriaDao.GetAll());
    }


    public void Cerrar() {
       App.Instance.DbConnection.Close();
        Application.Quit();
    }

    private void actualizarEstados() {
        bool hasSelectedRows = treeView.Selection.CountSelectedRows() > 0;
        editAction.Sensitive = hasSelectedRows;
        deleteAction.Sensitive = hasSelectedRows;
    }
}

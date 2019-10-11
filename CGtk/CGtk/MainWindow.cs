using System;
using Gtk;
using CGtk;
using System.Data;
using MySql.Data.MySqlClient;

public partial class MainWindow : Gtk.Window
{
    static IDbConnection dbConnection = new MySqlConnection(
                "server=localhost;database=dbprueba;user=root;password=sistemas;ssl-mode=none"
                );

    public MainWindow() : base(Gtk.WindowType.Toplevel) {
        Build();

        treeView.AppendColumn("id", new CellRendererText(), "text", 0);
        treeView.AppendColumn("nombre", new CellRendererText(), "text", 1);
        ListStore listStore = new ListStore(typeof(string), typeof(string));
        treeView.Model = listStore;

        //Conexion base de datos
        dbConnection.Open();

        IDbCommand dbCommand = dbConnection.CreateCommand();
        dbCommand.CommandText = "select * from categoria";
        IDataReader dataReader = dbCommand.ExecuteReader();

        while (dataReader.Read()) {
            listStore.AppendValues(dataReader["id"].ToString(), dataReader["nombre"]);
        }

        dataReader.Close();
        dbConnection.Close();
        newAction.Activated += (sender, e) => new CategoriaWindow();

        quitAction.Activated += (sender, e) => Application.Quit();

    }

    protected void OnDeleteEvent(object sender, DeleteEventArgs a) {
        Application.Quit();
        a.RetVal = true;
    }
}

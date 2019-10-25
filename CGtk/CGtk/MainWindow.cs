using System;
using Gtk;
using CGtk;
using System.Data;
using MySql.Data.MySqlClient;
using Serpis.Ad;

public partial class MainWindow : Gtk.Window
{
    static IDbConnection dbConnection = new MySqlConnection(
                "server=localhost;database=dbprueba;user=root;password=sistemas;ssl-mode=none"
                );
    ListStore listStore;

    public MainWindow() : base(Gtk.WindowType.Toplevel) {
        Build();

        treeView.AppendColumn("id", new CellRendererText(), "text", 0);
        treeView.AppendColumn("nombre", new CellRendererText(), "text", 1);

        listStore = new ListStore(typeof(string), typeof(string));
        treeView.Model = listStore;

        //Conexion base de datos
        dbConnection.Open();
        CargarDatos();
       
        newAction.Activated += (sender, e) => new CategoriaWindow(null);

        bool rowsSelected = treeView.Selection.CountSelectedRows() > 0;
        editAction.Sensitive = rowsSelected;
       
        editAction.Activated += (sender, e) => {
            TreeSelection selectedRow = treeView.Selection;
            selectedRow.GetSelected(out TreeIter treeIter);
            System.Object obj = treeView.Model.GetValue(treeIter, 0);
            ulong id = (ulong) obj.GetType().GetProperty("id").GetValue(obj);
            String nombre = obj.GetType().GetProperty("nombre").GetValue(obj).ToString();
            Categoria categoria = new Categoria(id, nombre);
            new CategoriaWindow(categoria);

        };

        treeView.Selection.Changed += (sender, e) => CargarDatos();

        refreshAction.Activated += (sender, e) => CargarDatos();

        quitAction.Activated += (sender, e) => Cerrar();

    }

    public void CargarDatos() {

        listStore.Clear();
      
        IDbCommand dbCommand = dbConnection.CreateCommand();
        dbCommand.CommandText = "select * from categoria";
        IDataReader dataReader = dbCommand.ExecuteReader();

        while (dataReader.Read()) {
            listStore.AppendValues(dataReader["id"].ToString(), dataReader["nombre"]);
        }
        dataReader.Close();
    }

    public static void Save(Categoria categoria) {
        if (categoria.Id == 0)
            insert(categoria);
        else
            update(categoria);
    }
    

    private static void insert(Categoria categoria) {
        IDbCommand dbCommand = dbConnection.CreateCommand();
        dbCommand.CommandText = "insert into categoria(nombre) values(@nombre)";
        try {

            DbCommandHelper.AddParameter(dbCommand, "nombre", categoria.Nombre);
            dbCommand.ExecuteNonQuery();
        }
        catch (Exception e) {
            Console.WriteLine("ERROR. {0}", e);
        }
    }

    private static void update(Categoria categoria) {
        IDbCommand dbCommand = dbConnection.CreateCommand();
        dbCommand.CommandText = "update categoria set = @nombre where id = @id";
        try {
            DbCommandHelper.AddParameter(dbCommand, "nombre", categoria.Nombre);
            DbCommandHelper.AddParameter(dbCommand, "id", categoria.Id);
            dbCommand.ExecuteNonQuery();
        }
        catch (Exception e) {
            Console.WriteLine("ERROR. {0}", e);
        }
    }

    public void Cerrar() {
        dbConnection.Close();
        Application.Quit();
    }

    protected void OnDeleteEvent(object sender, DeleteEventArgs a) {
        Application.Quit();
        a.RetVal = true;
    }
}

using System;
using System.Data;
using MySql.Data.MySqlClient;
using Serpis.Ad;

namespace CArticulo
{
    class MainClass
    {
        static IDbConnection dbConnection = new MySqlConnection(
                "server=localhost;database=dbprueba;user=root;password=sistemas;ssl-mode=none"
                );

        public static void Main(string[] args) {
            Console.WriteLine("Acceso a dbprueba");

            dbConnection.Open();
            Menu();
            //InsertValue();
            //ShowAll();
            //ShowMetaInfo();
            dbConnection.Close();
        }

        public static void Menu() {
            Console.WriteLine("****GESTIÓN DE ARTÍCULOS****");
            Console.WriteLine("1.- Crear nuevo artículo\n"
                + "2.- Modificar artículo\n"
                + "3.- Eliminar artículo\n"
                + "4.- Consultar artículo\n" +
                    "5.- Listar artículos \n" +
                    "0.- Salir");
            int entero = readInteger("Seleccione una opción: ");

            switch (entero) {
                case 5:
                    listar();
                    break;
            }
        }

        public static int readInteger(String label) {
            int entero = -1;

            while (entero == -1) {
                try {
                    Console.WriteLine(label);
                    String linea = Console.ReadLine();
                    entero = Int32.Parse(linea);
                }
                catch {
                    Console.WriteLine("Entrada no válida. Por favor, vuelve a intentarlo.\n");
                }
            }

            return entero;
        }

        public static void listar() {
            IDbCommand dbCommand = dbConnection.CreateCommand();
            dbCommand.CommandText = "select * from articulo";
            IDataReader dataReader = dbCommand.ExecuteReader();

            while (dataReader.Read()) {
                Console.WriteLine("id={0} nombre={1} precio={2} id categoria={3}", dataReader["id"], dataReader["nombre"], 
                    dataReader["precio"], dataReader["idCategoria"]);
            }

            dataReader.Close();
        }

        public static void InsertValue() {
            IDbCommand dbCommand = dbConnection.CreateCommand();
            string nombre = "nuevo " + DateTime.Now;
            string commandText = String.Format("insert into categoria(nombre) values ('{0}')", nombre);
            dbCommand.CommandText = commandText;
            dbCommand.ExecuteNonQuery();

        }

        public static void ShowMetaInfo() {
            IDbCommand dbCommand = dbConnection.CreateCommand();
            dbCommand.CommandText = "select * from categoria";
            IDataReader dataReader = dbCommand.ExecuteReader();
            Console.WriteLine("FieldCount={0}", dataReader.FieldCount);
            for (int i = 0; i < dataReader.FieldCount; i++)
                Console.WriteLine("Field {0,3} = {1,-15} Type= {2}", i, dataReader.GetName(i),
                    dataReader.GetFieldType(i));

            dataReader.Close();
        }
    }
}

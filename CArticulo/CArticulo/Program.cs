﻿using System;
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
                    "6. - Listar categorías \n " +
                    "0.- Salir");
            int entero = readInteger("Seleccione una opción: ");

            switch (entero) {
                case 1:
                    Nuevo();
                    break;

                case 2:
                    Modificar();
                    break;

                case 3:
                    Eliminar();
                    break;

                case 4:
                    Consultar();
                    break;

                case 5:
                    Listar("articulo");
                    break;

                case 6:
                    Listar("categoria");
                    break;

                    //todo: listarCategoria
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

        public static Decimal readDecimal(String label) {
            Decimal dec = -1;

            while (dec == -1) {
                try {
                    Console.WriteLine(label);
                    String linea = Console.ReadLine();
                    dec = Decimal.Parse(linea);
                }
                catch {
                    Console.WriteLine("Entrada no válida. Por favor, vuelve a intentarlo.\n");
                }
            }
            return dec;
        }


        public static void Listar(string tabla) {
            IDbCommand dbCommand = dbConnection.CreateCommand();
            dbCommand.CommandText = "select * from " + tabla;
            IDataReader dataReader = dbCommand.ExecuteReader();

            while (dataReader.Read()) {
                if (tabla.Equals("articulo"))
                    Console.WriteLine("id={0} nombre={1} precio={2} id categoria={3}", dataReader["id"], dataReader["nombre"], 
                        dataReader["precio"], dataReader["idCategoria"]);
                else if (tabla.Equals("categoria"))
                    Console.WriteLine("id={0} nombre={1} ", dataReader["id"], dataReader["nombre"]);
            }

            dataReader.Close();
        }

        public static void Nuevo() {
            string nombre;
            Decimal precio;
            int idCategoria;

            IDbCommand dbCommand = dbConnection.CreateCommand();
            dbCommand.CommandText = "insert into articulo(nombre, precio, idCategoria) values(@nombre, @precio, @idCategoria)";

            try {
                Console.WriteLine("Nombre del artículo: ");
                nombre = Console.ReadLine();

                precio = readDecimal("Precio del artículo: ");

                idCategoria = readInteger("ID de la categoría: ");

                DbCommandHelper.AddParameter(dbCommand, "nombre", nombre);
                DbCommandHelper.AddParameter(dbCommand, "precio", precio);
                DbCommandHelper.AddParameter(dbCommand, "idCategoria", idCategoria);

                dbCommand.ExecuteNonQuery();

            }
            catch (Exception e){
                Console.WriteLine("ERROR. {0}", e);
            }

        }

        public static void Modificar() {

            int id;
            string nombre;
            Decimal precio;
            int idCategoria;

            IDbCommand dbCommand = dbConnection.CreateCommand();
            dbCommand.CommandText = "update articulo " +
            	"set nombre=@nombre, precio=@precio, idCategoria=@idCategoria " +
            	"where id = @id";

            try {
                id = readInteger("ID del artículo a modificar: ");

                Console.WriteLine("Datos actuales del artículo: ");
                bool articuloExists = Consultar(id);

                if (!articuloExists)
                    throw new Exception();

                Console.WriteLine("Nombre del artículo: ");
                nombre = Console.ReadLine();

                precio = readDecimal("Precio del artículo: ");

                idCategoria = readInteger("ID de la categoría: ");

                DbCommandHelper.AddParameter(dbCommand, "nombre", nombre);
                DbCommandHelper.AddParameter(dbCommand, "precio", precio);
                DbCommandHelper.AddParameter(dbCommand, "idCategoria", idCategoria);
                DbCommandHelper.AddParameter(dbCommand, "id", id);

                dbCommand.ExecuteNonQuery();

            }
            catch (Exception e) {
                Console.WriteLine("ERROR. {0}", e);
            }

        }

        public static void Eliminar() {

            int id;

            IDbCommand dbCommand = dbConnection.CreateCommand();
            dbCommand.CommandText = "delete from articulo where id = @id";

            try {
                id = readInteger("ID del artículo a eliminar: ");

                Console.WriteLine("Datos del artículo: ");
                bool articuloExists = Consultar(id);

                if (!articuloExists)
                    throw new Exception();
                    
                DbCommandHelper.AddParameter(dbCommand, "id", id);

                Console.WriteLine("¿Seguro que quieres eliminar este artículo? (Sí/No)");
                string opcion = Console.ReadLine().ToLower();

                if (opcion[0] == 's') {
                    dbCommand.ExecuteNonQuery();
                    Console.WriteLine("Artículo eliminado.");
                }
                else {
                    Console.WriteLine("Operación cancelada.");
                }

            }
            catch (FormatException) {
                Console.WriteLine("Entrada incorrecta. Por favor, vuelve a intentarlo.");
            }
            catch (Exception e) {
                Console.WriteLine("ERROR. {0}", e);
            }

        }

        public static bool Consultar(int? id = null) {

            bool recordExists = false;
            IDbCommand dbCommand = dbConnection.CreateCommand();
            dbCommand.CommandText = "select * from articulo where id=@id";

            try {
                if (id is null)
                    id = readInteger("ID del artículo a modificar: ");

                DbCommandHelper.AddParameter(dbCommand, "id", id);

                IDataReader dataReader = dbCommand.ExecuteReader();
                if (dataReader.Read()) {
                    Console.WriteLine("id={0} nombre={1} precio={2} id categoria={3}", dataReader["id"], dataReader["nombre"],
                        dataReader["precio"], dataReader["idCategoria"]);
                    recordExists = true;
                }
                else {
                    throw new Exception("No existe ningún artículo con este ID.");
                }
                dataReader.Close();
            }
            catch (Exception e) {
                Console.WriteLine("ERROR. {0}", e);
            }
            return recordExists;
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

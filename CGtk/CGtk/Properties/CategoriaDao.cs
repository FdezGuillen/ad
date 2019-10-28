using System;
using System.Collections;
using System.Collections.Generic;
using System.Data;
using Serpis.Ad;
namespace CGtk
{
    public class CategoriaDao
    {

        public static IEnumerable GetAll() {
            IList<Categoria> categorias = new List<Categoria>();

            IDbCommand dbCommand = App.Instance.DbConnection.CreateCommand();
            dbCommand.CommandText = "select * from categoria order by id";
            IDataReader dataReader = dbCommand.ExecuteReader();
            while (dataReader.Read()) {
                categorias.Add(new Categoria((ulong)dataReader["id"], (string)dataReader["nombre"]));
            }
            dataReader.Close();
            return categorias;
        }

        private static string selectSql = "select * from categoria where id = @id";
        public static Categoria Load(object id) {
            Categoria categoria = new Categoria();
            IDbCommand dbCommand = App.Instance.DbConnection.CreateCommand();
            dbCommand.CommandText = selectSql;
            DbCommandHelper.AddParameter(dbCommand, "id", id);
            IDataReader dataReader = dbCommand.ExecuteReader();
            dataReader.Read();
            categoria.Id = (ulong)id;
            categoria.Nombre = (string)dataReader["nombre"];
            dataReader.Close();
            return categoria;
        }

        public static void Save(Categoria categoria) {
            if (categoria.Id == 0) {
                insert(categoria);
            }
            else {
                update(categoria);
            }
        }

        private static void insert(Categoria categoria) {
            IDbCommand dbCommand = App.Instance.DbConnection.CreateCommand();
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
            IDbCommand dbCommand = App.Instance.DbConnection.CreateCommand();
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

        private static string deleteSql = "delete from categoria where id=@id";
        public static void Delete(object id) {
            IDbCommand dbCommand = App.Instance.DbConnection.CreateCommand();
            dbCommand.CommandText = deleteSql;
            try {
                DbCommandHelper.AddParameter(dbCommand, "id", id);
                dbCommand.ExecuteNonQuery();
            }
            catch (Exception e) {
                Console.WriteLine("ERROR. {0}", e);
            }
        }
    }
}
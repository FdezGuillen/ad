using System;


namespace Serpis.Ad
{
    public class ConsoleHelper
    {
        public static int ReadInteger(String label) {
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

        public static Decimal ReadDecimal(String label) {
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

    }
}

using System;
using System.Collections.Generic;

namespace Serpis.Ad
{
    public class Menu
    {
        private bool exit = false;
        private IList<string> labels = new List<string>();
        private IDictionary<string, Action> options = new Dictionary<string, Action>();
        string menuLabel;

        private Menu (string menulabel){
            this.menuLabel = menulabel;
        }


        public static Menu Create (string menuLabel){
            return new Menu(menuLabel);
        }

        public Menu Add(string label, Action action){
            labels.Add(label);
            string option = label.Substring(0, 1).ToUpper();
            options.Add(option, action);
            return this;
        }

        public Menu ExitWhen(string label){
            labels.Add(label);
            string option = label.Substring(0, 1).ToUpper();
            options.Add(option, () => exit = true);
            return this;
        }

        public void Loop(){
            while(!exit){
                foreach (string label in labels)
                    Console.WriteLine(label);
                string option = Console.ReadLine();
                if (options.ContainsKey(option))
                    options[option]();
                else
                    Console.WriteLine("ERROR");
            }
        }

    }
}

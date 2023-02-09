using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using Utils.Language;
using Utils.ObjectComponents;

namespace Utils
{
    public static class InitializeComponents
    {
        public static void LoadComboBox<L>(this ComboBox cbo, L list, string display, string value)
        {
            Utils.Utils.ToWait(true);
            cbo.DataSource = list;
            cbo.DisplayMember = display;
            cbo.ValueMember = value;
            cbo.SelectedIndex = 0;
            Utils.Utils.ToWait(false);
        }

        public static void CarregaGrid<L>(this DataGridView dgv, BindingNavigator bn, List<L> data)
        {
            Utils.Utils.ToWait(true);
            BindingSource bs = new BindingSource();
            bs.DataSource = data;
            bn.BindingSource = bs;
            dgv.DataSource = bs;

            EventsComponents.FormatGrid<L>(dgv);

            Utils.Utils.ToWait(false);
        }


        public static void LoadComboBoxStatus(this ComboBox cbo)
        {
            Utils.Utils.ToWait(true);
            var list = new List<ComboBoxObject>();

            list.Add(new ComboBoxObject { Id = -1, Name = ResourceLanguage.All });
            list.Add(new ComboBoxObject { Id = 1, Name = ResourceLanguage.Actives });
            list.Add(new ComboBoxObject { Id = 0, Name = ResourceLanguage.Inactives });

            cbo.DropDownStyle = ComboBoxStyle.DropDownList;

            cbo.DataSource = list;
            cbo.DisplayMember = "Name";
            cbo.ValueMember = "Id";

            cbo.SelectedIndex = 0;
            Utils.Utils.ToWait(false);
        }

        public static void LoadComboBoxCreateUpdateForm(this ComboBox cbo)
        {
            Utils.Utils.ToWait(true);
            var list = new List<ComboBoxObject>();
            
            list.Add(new ComboBoxObject { Id = 1, Name = ResourceLanguage.Active });
            list.Add(new ComboBoxObject { Id = 0, Name = ResourceLanguage.Inactive});

            cbo.DropDownStyle = ComboBoxStyle.DropDownList;

            cbo.DataSource = list;
            cbo.DisplayMember = "Name";
            cbo.ValueMember = "Id";

            cbo.SelectedIndex = 0;
            Utils.Utils.ToWait(false);
        }


    }
}

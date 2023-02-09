using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using Utils.Annotations;
using Utils.Enums;
using Utils.Language;

namespace Utils
{
    public static class EventsComponents
    {
        public static void NewRecord<F>(this Button btn, Button buttonUpdate)
        {
            Utils.Utils.ToWait(true);
            btn.Click += (object sender, EventArgs e) => {
                Utils.Utils.ToWait(true);
                var formNew = new Dictionary<string, object>();
                formNew.Add("action", ResourceLanguage.Create);
                var vclass = typeof(F);
                var ctor = vclass.GetConstructor(new[] { typeof(Dictionary<string, object>) });
                var instance = ctor.Invoke(new object[] { formNew });
                Utils.Utils.ToWait(false);
                ((Form)instance).ShowDialog();
                buttonUpdate.PerformClick();               
            };
            Utils.Utils.ToWait(false);
        }

        public static void UpdateDataDoubleClickGrid<F>(this DataGridView dgv, Button buttonUpdate)
        {
            Utils.Utils.ToWait(true);
            dgv.DoubleClick += (object sender, EventArgs e) => {
                if (dgv.RowCount > 0)
                {
                    if (MessageBox.Show(string.Format(ResourceLanguage.ExecuteAction, ResourceLanguage.Update), ResourceLanguage.Update, MessageBoxButtons.YesNo, MessageBoxIcon.Question, MessageBoxDefaultButton.Button1) == DialogResult.Yes)
                    {
                        Utils.Utils.ToWait(true);
                        var formAlter = new Dictionary<string, object>();
                        formAlter.Add("action", ResourceLanguage.Update.ToLower());
                        foreach (DataGridViewCell cell in dgv.CurrentRow.Cells)
                        {
                            formAlter.Add(cell.OwningColumn.DataPropertyName, cell.Value);
                        }
                        var vclass = typeof(F);
                        var ctor = vclass.GetConstructor(new[] { typeof(Dictionary<string, object>) });
                        var instance = ctor.Invoke(new object[] { formAlter });
                        Utils.Utils.ToWait(false);
                        ((Form)instance).ShowDialog();
                        buttonUpdate.PerformClick();
                    }
                }
            };
            Utils.Utils.ToWait(false);
        }


        public static void DeleteItemGrid<C>(this Button btn, DataGridView dgv, string nemeCellId, string nameCellStatus, string nameMethod, Button updateButton)
        {
            Utils.Utils.ToWait(true);
            btn.Click += (object sender, EventArgs e) => {
                if (dgv.RowCount > 0)
                {
                    Utils.Utils.ToWait(true);
                    var status = dgv.CurrentRow.Cells[nameCellStatus].Value.ToString().Equals(ResourceLanguage.Active) ? true : false;

                    var nameAction = (status ? ResourceLanguage.Inactivate : ResourceLanguage.Activate);
                    Utils.Utils.ToWait(false);
                    if (MessageBox.Show(string.Format(ResourceLanguage.ExecuteAction, nameAction), nameAction, MessageBoxButtons.YesNo, MessageBoxIcon.Question, MessageBoxDefaultButton.Button1) == DialogResult.Yes)
                    {
                        Utils.Utils.ToWait(true);
                        var id = dgv.CurrentRow.Cells[nemeCellId].Value.ToString();
                        var vclass = typeof(C);
                        var action = vclass.GetMethod(nameMethod);
                        object classInstance = Activator.CreateInstance(vclass, null);
                        action.Invoke(classInstance, new object[] { id });
                        updateButton.PerformClick();
                        btn.Text = (status ? ResourceLanguage.Activate : ResourceLanguage.Inactivate);
                        Utils.Utils.ToWait(false);
                    }
                }
            };
            Utils.Utils.ToWait(false);
        }

        public static void UpdateDescriptionButtonDelete(this DataGridView dgv, Button btn, string nameCellStatus)
        {
            Utils.Utils.ToWait(true);
            if (dgv.Rows.Count == 0)
                return;
            var status = dgv.CurrentRow.Cells[nameCellStatus].Value.ToString().Equals(ResourceLanguage.Active) ? true : false;
            btn.Text = (status ? ResourceLanguage.Inactivate : ResourceLanguage.Activate);

            dgv.Click += (object sender, EventArgs e) => {
                if (dgv.RowCount > 0)
                {
                    Utils.Utils.ToWait(true);
                    var vstatus = dgv.CurrentRow.Cells[nameCellStatus].Value.ToString().Equals(ResourceLanguage.Active) ? true : false;
                    btn.Text = (vstatus ? ResourceLanguage.Inactivate : ResourceLanguage.Activate);
                    Utils.Utils.ToWait(false);
                }
            };
            Utils.Utils.ToWait(false);
        }

        public static void FormatGrid<T>(DataGridView dgv)
        {
            foreach (var prop in typeof(T).GetProperties())
            {
                foreach (FormatGrid item in prop.GetCustomAttributes(typeof(FormatGrid), false))
                {
                    if (item.Visible)
                    {
                        if(item.Order != 99)
                        {                       
                            dgv.Columns[prop.Name].DisplayIndex = item.Order;
                        }
                        

                        if (item.Width > 0)
                        {
                            dgv.Columns[prop.Name].Width = item.Width;
                        }
                        else
                        {
                            dgv.Columns[prop.Name].AutoSizeMode = DataGridViewAutoSizeColumnMode.Fill;
                        }

                        dgv.Columns[prop.Name].HeaderText = item.Name;

                        switch (item.Alignment)
                        {
                            case EnumAlignment.Right:
                                dgv.Columns[prop.Name].DefaultCellStyle.Alignment = DataGridViewContentAlignment.BottomRight;
                                break;
                            case EnumAlignment.Center:
                                dgv.Columns[prop.Name].DefaultCellStyle.Alignment = DataGridViewContentAlignment.BottomCenter;
                                break;
                            default:
                                dgv.Columns[prop.Name].DefaultCellStyle.Alignment = DataGridViewContentAlignment.BottomLeft;
                                break;
                        }
                    }
                    else
                    {
                        dgv.Columns[prop.Name].Visible = false;
                    }               
                }
            }

            dgv.AllowUserToAddRows = false;
            dgv.AllowUserToDeleteRows = false;
            dgv.AllowUserToOrderColumns = false;
            dgv.AllowUserToResizeColumns = false;
            dgv.AllowUserToResizeRows = false;
            dgv.MultiSelect = false;
            dgv.RowHeadersVisible = false;
            dgv.SelectionMode = DataGridViewSelectionMode.FullRowSelect;
            dgv.ReadOnly = true;
        }
    }
}

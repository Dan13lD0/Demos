using System;
using System.Collections.Generic;
using System.Globalization;
using System.Linq;
using System.Security.Cryptography;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace Utils.Utils
{
    public static class Utils
    {
        public static void FixWidthHeightMainForm(this Form form, int width, int height)
        {
            form.Top = 0;
            form.Left = 0;
            form.Width = width;
            form.Height = height - 25;
        }

        public static void ShowForm(this Form form, Panel panel)
        {
            Application.UseWaitCursor = true;
            form.TopLevel = false;
            panel.Controls.Add(form);
            form.Show();
            form.Activate();
            Application.UseWaitCursor = false;
        }

        public static void EnterExecuteTab(this KeyPressEventArgs e)
        {
            if (e.KeyChar == Convert.ToChar(13))
            { SendKeys.Send("{Tab}"); }
        }

        public static void JustOnlyLetters(this KeyPressEventArgs e)
        {
            if (!char.IsLetter(e.KeyChar) && !char.IsControl(e.KeyChar) && e.KeyChar != 32)
            { e.Handled = true; }
        }

        public static void JustOnlyNumbers(this KeyPressEventArgs e)
        {
            if (!char.IsDigit(e.KeyChar) && !char.IsControl(e.KeyChar) && e.KeyChar != 32)
            { e.Handled = true; }
        }

        public static void LettersAndNumbers(this KeyPressEventArgs e)
        {
            if (!char.IsDigit(e.KeyChar) && !char.IsLetter(e.KeyChar) && !char.IsControl(e.KeyChar) && e.KeyChar != 32)
            {
                e.Handled = true;
            }
        }

        public static void JustOnlyOneComma(this KeyPressEventArgs e, object sender)
        {
            if (CultureInfo.CurrentCulture.Name.ToString().Contains("en"))
            {
                if (e.KeyChar == '.')
                {
                    if (e.KeyChar == '.' && (sender as Control).Text.IndexOf('.') > -1)
                    {
                        e.Handled = true;
                    }
                }
                else
                {
                    if (!char.IsDigit(e.KeyChar) && !char.IsControl(e.KeyChar))
                    { e.Handled = true; }
                }
            }
            else
            {
                if (e.KeyChar == ',')
                {
                    if (e.KeyChar == ',' && (sender as Control).Text.IndexOf(',') > -1)
                    {
                        e.Handled = true;
                    }
                }
                else
                {
                    if (!char.IsDigit(e.KeyChar) && !char.IsControl(e.KeyChar))
                    { e.Handled = true; }
                }
            }

        }

        public static void ToWait( bool status)
        {
            Application.UseWaitCursor = status;
        }

    }
}

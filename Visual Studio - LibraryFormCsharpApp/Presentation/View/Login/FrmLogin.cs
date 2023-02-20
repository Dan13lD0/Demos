using Bll;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace Presentation.View.Login
{
    public partial class FrmLogin : Form
    {
       
        public FrmLogin()
        {
            InitializeComponent();
#if DEBUG == true
            txtUser.Text = "admin";
            txtPassword.Text = "123456";
#endif
        }

        private void btnExitApplication_Click(object sender, EventArgs e)
        {
            Application.Exit();
        }

        private void CleandFields()
        {
            txtPassword.Clear();
            txtUser.Clear();
            txtUser.Focus();
        }
        private void btnLogin_Click(object sender, EventArgs e)
        {
            if(string.IsNullOrEmpty(txtUser.Text) || string.IsNullOrEmpty(txtPassword.Text))
            {
                MessageBox.Show("Usuário ou senha não foi informado!", "Advise", MessageBoxButtons.OK, MessageBoxIcon.Information);
                txtUser.Focus();
            }
            else
            {
                LoginBll _bll = new LoginBll();
                var user = _bll.ValidateUser(txtUser.Text, txtPassword.Text);
                if(user == null)
                {
                    MessageBox.Show("Usuário não encontrado!", "Advise", MessageBoxButtons.OK, MessageBoxIcon.Information);
                    CleandFields();
                }
                else
                {
                    var main = new FrmMain(user.Id.ToString(), user.Name);
                    this.Hide();
                    main.ShowDialog();
                    CleandFields();

                }
            }
        }
    }
}

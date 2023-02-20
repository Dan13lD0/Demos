using Presentation.View.Author;
using Presentation.View.Book;
using Presentation.View.Category;
using Presentation.View.Client;
using Presentation.View.User;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using Utils.Utils;

namespace Presentation
{
    public partial class FrmMain : Form
    {
        public FrmMain(string userId,string userName)
        {
            InitializeComponent();
        }

        private void userToolStripMenuItem_Click(object sender, EventArgs e)
        {
            new FrmListAuthor().ShowForm(panel1);
        }

        private void clientToolStripMenuItem_Click(object sender, EventArgs e)
        {
            new FrmListClient().ShowForm(panel1);
        }

        private void categoryToolStripMenuItem_Click(object sender, EventArgs e)
        {
            new FrmListCategory().ShowForm(panel1);
        }

        private void bookToolStripMenuItem_Click(object sender, EventArgs e)
        {
            new FrmListBook().ShowForm(panel1);
        }

        private void authorToolStripMenuItem_Click(object sender, EventArgs e)
        {
            new FrmListUser().ShowForm(panel1);
        }

        private void txtUserName_Click(object sender, EventArgs e)
        {

        }
    }
}

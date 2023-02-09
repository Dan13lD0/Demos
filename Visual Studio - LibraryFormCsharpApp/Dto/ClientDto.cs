using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Utils.Annotations;
using Utils.Enums;

namespace Dto
{
    [Table("Client")]
    public class ClientDto : GlobalDto
    {
        [FormatGrid(Order = 1, Visible = true, Width = 0, Alignment = EnumAlignment.Left, Name = "Name")]
        public string Name { get; set; }
        [FormatGrid(Order = 2, Visible = true, Width = 150, Alignment = EnumAlignment.Left, Name = "RG")]
        public string RG { get; set; }
        [FormatGrid(Order = 3, Visible = true, Width = 150, Alignment = EnumAlignment.Left, Name = "CPF")]
        public string CPF { get; set; }
        [FormatGrid(Order = 4, Visible = true, Width = 150, Alignment = EnumAlignment.Center, Name = "Sex")]
        public string Sex { get; set; }
        [FormatGrid(Order = 5, Visible = true, Width = 150, Alignment = EnumAlignment.Center, Name = "Born")]
        public DateTime? Birthday { get; set; }
    }
}

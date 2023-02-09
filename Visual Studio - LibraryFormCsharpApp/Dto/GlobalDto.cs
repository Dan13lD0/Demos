using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Utils.Annotations;
using Utils.Enums;

namespace Dto
{
    public class GlobalDto
    {
        [Key]
        [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
        [FormatGrid(Order = 0, Visible = true, Width = 150, Alignment = EnumAlignment.Center, Name = "Code")]
        public Int64 Id { get; set; }

        [FormatGrid(Visible = false)]
        public bool Status { get; set; }
        [NotMapped]
        [FormatGrid(Order = 99, Visible = true, Width = 100, Alignment = EnumAlignment.Center, Name = "Status")]
        public string StatusDescription { get { return Status ? "Active" : "Inactive"; } }
    }
}

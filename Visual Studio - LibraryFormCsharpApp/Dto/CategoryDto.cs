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
    [Table("Category")]
    public class CategoryDto : GlobalDto
    {
        [FormatGrid(Order = 1, Visible = true, Width = 0, Alignment = EnumAlignment.Left, Name = "Name")]
        public string Name { get; set; }
    }
}

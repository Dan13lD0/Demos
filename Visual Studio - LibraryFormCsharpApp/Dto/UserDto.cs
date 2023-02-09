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
    [Table("User")]
    public  class UserDto: GlobalDto
    {
        [FormatGrid(Order = 1, Visible = true, Width = 0, Alignment = EnumAlignment.Left, Name = "Name")]
        public string Name { get; set; }
        [FormatGrid(Order = 2, Visible = true, Width = 120, Alignment = EnumAlignment.Left, Name = "Login")]
        public string Login { get; set; }
        [FormatGrid(Visible = false)]
        public string Password { get; set; }
        [FormatGrid(Order = 3, Visible = true, Width = 0, Alignment = EnumAlignment.Left, Name = "Mail")]
        public string Mail { get; set; }

    }
}

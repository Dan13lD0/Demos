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
    [Table("Book")]
    public class BookDto:GlobalDto
    {
        [FormatGrid(Order = 1, Visible = true, Width = 0, Alignment = EnumAlignment.Left, Name = "Name")]
        public string Name { get; set; }
        [ForeignKey("Author")]
        [FormatGrid(Visible = false)]
        public Int64 AuthorId { get; set; }
        [NotMapped]
        [FormatGrid(Order = 2, Visible = true, Width = 0, Alignment = EnumAlignment.Left, Name = "Author")]
        public string NameAuthor { get { return Author != null ? Author.Name : ""; } }
        [FormatGrid(Visible = false)]
        public AuthorDto Author { get; set; }
    }
}

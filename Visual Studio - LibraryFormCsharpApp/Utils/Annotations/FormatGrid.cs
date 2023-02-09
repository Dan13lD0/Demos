using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Utils.Enums;

namespace Utils.Annotations
{
    public class FormatGrid : Attribute
    {
        public int Order { get; set; }
        public bool Visible { get; set; }
        public int Width { get; set; }
        public string Name { get; set; }
        public EnumAlignment Alignment { get; set; }
    }
}

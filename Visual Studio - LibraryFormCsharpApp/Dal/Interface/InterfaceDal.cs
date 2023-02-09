using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Dal.Interface
{
    interface InterfaceDal<D>
    {
        List<D> GetList(D value,bool? status);

        D GetOneItem(D value);

        bool ExistsItem(D value);

        D CreateItem(D value);

        D UpdateItem(D value);

        void EnableDisableItem(D value);
    }
}

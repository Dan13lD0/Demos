using Dal.Dal.Dao;
using Dal.Interface;
using Dto;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Dal
{
    public class ClientDal : InterfaceDal<ClientDto>
    {
        public ClientDto CreateItem(ClientDto value)
        {
            using (var conn = new ContextSqlServer())
            {
                var insert = new ClientDto();

                insert.Name = value.Name;
                insert.RG = value.RG;
                insert.CPF = value.CPF;
                insert.Sex = value.Sex;
                insert.Birthday = value.Birthday;
                insert.Status = true;

                conn.Client.Add(insert);
                conn.SaveChanges();

                return value;
            }
        }

        public void EnableDisableItem(ClientDto value)
        {
            using (var conn = new ContextSqlServer())
            {
                var delete = conn.Client.FirstOrDefault(i => i.Id.Equals(value.Id));
                if (delete != null)
                {
                    delete.Status = value.Status;
                    conn.SaveChanges();
                }

            }
        }

        public bool ExistsItem(ClientDto value)
        {
            using (var conn = new ContextSqlServer())
            {
                return conn.Client.Where(i => i.Id != value.Id
                && i.Name.Equals(value.Name)
                && i.RG.Equals(value.RG)
                && i.CPF.Equals(value.CPF)).ToList().Count > 0 ? true : false;
            }
        }

        public List<ClientDto> GetList(ClientDto value, bool? status)
        {
            using (var conn = new ContextSqlServer())
            {
                return conn.Client.Where(i => i.Name.Contains(value.Name)
                      && i.RG.Contains(value.RG)
                      && i.CPF.Contains(value.CPF)
                      && i.Sex.Contains(value.Sex)).ToList();
            }
        }

        public ClientDto GetOneItem(ClientDto value)
        {
            using (var conn = new ContextSqlServer())
            {
                return conn.Client.FirstOrDefault(i => i.Id.Equals(value.Id));
            }
        }

        public ClientDto UpdateItem(ClientDto value)
        {
            using (var conn = new ContextSqlServer())
            {
                var update = conn.Client.FirstOrDefault(i => i.Id.Equals(value.Id));
                if (update == null)
                    return null;

                update.Name = value.Name;
                update.RG = value.RG;
                update.CPF = value.CPF;
                update.Sex = value.Sex;
                update.Birthday = value.Birthday;
                update.Status = value.Status;

                conn.SaveChanges();

                return update;
            }
        }
    }
}

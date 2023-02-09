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
    public class AuthorDal : InterfaceDal<AuthorDto>
    {
        public AuthorDto CreateItem(AuthorDto value)
        {
            var insert = new AuthorDto();
            insert.Name = value.Name;
            insert.Status = true;
            using (var conn = new ContextSqlServer())
            {
                conn.Author.Add(insert);
                conn.SaveChanges();
                return insert;
            }
        }

        public void EnableDisableItem(AuthorDto value)
        {
            using (var conn = new ContextSqlServer())
            {
                var delete = conn.Author.FirstOrDefault(i => i.Id.Equals(value.Id));
                if (delete != null)
                {
                    delete.Status = value.Status;
                    conn.SaveChanges();
                }

            }
        }

        public bool ExistsItem(AuthorDto value)
        {
            using (var conn = new ContextSqlServer())
            {
                return conn.Author.Where(i => i.Id != value.Id
                && i.Name.Equals(value.Name)).ToList().Count > 0 ? true : false;
            }
        }

        public List<AuthorDto> GetList(AuthorDto value, bool? status)
        {
            using (var conn = new ContextSqlServer())
            {
                if (status != null)
                {
                    return conn.Author.Where(i => i.Name.Contains(value.Name) && i.Status == (bool)status).ToList();
                }
                else
                {
                    return conn.Author.Where(i => i.Name.Contains(value.Name)).ToList();
                }
            }

        }

        public AuthorDto GetOneItem(AuthorDto value)
        {
            using (var conn = new ContextSqlServer())
            {
                return conn.Author.FirstOrDefault(i => i.Id == value.Id); ;
            }
        }

        public AuthorDto UpdateItem(AuthorDto value)
        {
            using (var conn = new ContextSqlServer())
            {
               var update = conn.Author.FirstOrDefault(i => i.Id == value.Id); ;

                if(update != null)
                {
                    update.Name = value.Name;
                    update.Status = value.Status;
                }

                return update;
            }
        }
    }
}

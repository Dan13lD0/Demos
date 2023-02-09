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
    public class BookDal : InterfaceDal<BookDto>
    {
        public BookDto CreateItem(BookDto value)
        {
            var insert = new BookDto();

            insert.Name = value.Name;
            insert.AuthorId = value.AuthorId;
            insert.Status = true;
            using (var conn = new ContextSqlServer())
            {
                conn.Book.Add(insert);
                conn.SaveChanges();
            }
            return insert;
        }

        public void EnableDisableItem(BookDto value)
        {
            using (var conn = new ContextSqlServer())
            {
                var delete = conn.Book.FirstOrDefault(i => i.Id.Equals(value.Id));
                if (delete != null)
                {
                    delete.Status = value.Status;
                    conn.SaveChanges();
                }

            }
        }

        public bool ExistsItem(BookDto value)
        {
            using (var conn = new ContextSqlServer())
            {
                return conn.Client.Where(i => i.Id != value.Id
                && i.Name.Equals(value.Name)).ToList().Count > 0 ? true : false;
            }
        }

        public List<BookDto> GetList(BookDto value, bool? status)
        {
            using (var conn = new ContextSqlServer())
            {
                if(status != null)
                {
                    return conn.Book.Where(i => i.AuthorId == (value.AuthorId > 0 ? value.AuthorId : i.AuthorId) && i.Name.Contains(value.Name) && i.Status == (bool)status).ToList();
                }
                else
                {
                    return conn.Book.Where(i => i.AuthorId == (value.AuthorId > 0 ? value.AuthorId : i.AuthorId) && i.Name.Contains(value.Name)).ToList();

                }
            }
        }

        public BookDto GetOneItem(BookDto value)
        {
            using (var conn = new ContextSqlServer())
            {
                return conn.Book.FirstOrDefault(i => i.Id.Equals(value.Id));
            }
        }

        public BookDto UpdateItem(BookDto value)
        {
            using (var conn = new ContextSqlServer())
            {
                var update = conn.Book.FirstOrDefault(i=> i.Id == value.Id);
                if(update != null)
                {
                    update.Name = value.Name;
                    update.AuthorId= value.AuthorId;
                }
                conn.SaveChanges();
                return update;
            }
        }
    }
}

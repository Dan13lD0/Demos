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
    public class CategoryDal : InterfaceDal<CategoryDto>
    {
        public CategoryDto CreateItem(CategoryDto value)
        {
            var insert = new CategoryDto
            {
                Name = value.Name,
                Status = true
            };

            using (var conn = new ContextSqlServer())
            {
                conn.Category.Add(insert);
                conn.SaveChanges();
            }

            return value;
        }

        public void EnableDisableItem(CategoryDto value)
        {
            using (var conn = new ContextSqlServer())
            {

                var update = conn.Category.FirstOrDefault(i => i.Id == value.Id);
                if (update != null)
                {
                    update.Status = value.Status;
                    conn.SaveChanges();
                }
            }
        }

        public bool ExistsItem(CategoryDto value)
        {
            using (var conn = new ContextSqlServer())
            {
                var exist = conn.Category.Where(i => i.Id != value.Id && i.Name.Equals(value.Name)).ToList();
                if (exist.Count > 0)
                    return true;
                return false;
            }
        }

        public List<CategoryDto> GetList(CategoryDto value, bool? status)
        {
            using (var conn = new ContextSqlServer())
            {
                if (status != null)
                {
                    return conn.Category.Where(i => i.Name.Contains(value.Name) && i.Status.Equals((bool)status)).ToList();
                }
                else
                {
                    return conn.Category.Where(i => i.Name.Contains(value.Name)).ToList();
                }
            }
        }

        public CategoryDto GetOneItem(CategoryDto value)
        {
            using (var conn = new ContextSqlServer())
            {
                return conn.Category.FirstOrDefault(i => i.Id == value.Id);
            }
        }

        public CategoryDto UpdateItem(CategoryDto value)
        {
            using (var conn = new ContextSqlServer())
            {
                var update = conn.Category.FirstOrDefault(i => i.Id == value.Id);
                if (update != null)
                {
                    update.Name = value.Name;
                    update.Status = value.Status;
                    conn.SaveChanges();
                }
            }
            return value;
        }
    }
}

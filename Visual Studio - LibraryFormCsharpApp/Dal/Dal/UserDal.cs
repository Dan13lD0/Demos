using Dal.Dal.Dao;
using Dal.Interface;
using Dto;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Dal.Dal
{
    public class UserDal : InterfaceDal<UserDto>
    {
        public UserDto CreateItem(UserDto value)
        {
            var insert = new UserDto
            {
                Name = value.Name,
                Login = value.Login,
                Password = value.Password,
                Mail = value.Mail,
                Status = true
            };

            using (var conn = new ContextSqlServer())
            {
                conn.User.Add(insert);
                conn.SaveChanges();
            }

            return insert;
        }

        public void EnableDisableItem(UserDto value)
        {
            using (var conn = new ContextSqlServer())
            {

                var update = conn.User.FirstOrDefault(i => i.Id == value.Id);
                if (update != null)
                {
                    update.Status = value.Status;
                    conn.SaveChanges();
                }
            }
        }

        public bool ExistsItem(UserDto value)
        {
            using (var conn = new ContextSqlServer())
            {
                return conn.User.Count(i => i.Id != value.Id && i.Name.Equals(value.Name) && i.Login.Equals(value.Login))>0?true:false;
            }
        }

        public List<UserDto> GetList(UserDto value, bool? status)
        {
            try
            {
                using (var conn = new ContextSqlServer())
                {
                    if (status != null)
                    {
                        return conn.User.Where(i => i.Name.Contains(value.Name) && i.Login.Contains(value.Login) && i.Mail.Contains(value.Mail) && i.Status.Equals((bool)status)).ToList();
                    }
                    else
                    {
                        return conn.User.Where(i => i.Name.Contains(value.Name) && i.Login.Contains(value.Login) && i.Mail.Contains(value.Mail)).ToList();
                    }
                }
            }
            catch (Exception ex)
            {

                throw;
            }
        }

        public UserDto GetOneItem(UserDto value)
        {
            using (var conn = new ContextSqlServer())
            {
                return conn.User.FirstOrDefault(i => i.Id == value.Id);
            }
        }

        public UserDto UpdateItem(UserDto value)
        {
            using (var conn = new ContextSqlServer())
            {
                var update = conn.User.FirstOrDefault(i => i.Id == value.Id);
                if (update != null)
                {
                    update.Name = value.Name;
                    update.Login = value.Login;
                    update.Password = value.Password;
                    update.Status = value.Status;
                    update.Mail = value.Mail;
                    conn.SaveChanges();
                }
            }
            return value;
        }
    }
}

using Dal.Dal.Dao;
using Dto;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Dal
{
    public class LoginDal
    {
        public UserDto ValidateUser(UserDto user)
        {
            using (var conn = new ContextSqlServer())
            {
                return conn.User.Where(i => i.Login.Equals(user.Login) && i.Password.Equals(user.Password) && i.Status.Equals(true)).FirstOrDefault();
            }
        }
    }
}

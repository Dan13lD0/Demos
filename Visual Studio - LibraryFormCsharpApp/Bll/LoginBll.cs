using Dal;

using Dto;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Utils.Utils;

namespace Bll
{

    public class LoginBll
    {
        private LoginDal _dal;

        public LoginBll()
        {
            _dal = new LoginDal();
        }

        public UserDto ValidateUser(string login, string password)
        {
            return _dal.ValidateUser(new UserDto { Login = login, Password = password.Encrypt() });
        }
    }
}

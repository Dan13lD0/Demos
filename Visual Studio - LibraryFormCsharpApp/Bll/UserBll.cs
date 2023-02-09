using Dal.Dal;
using Dto;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Utils.Language;
using Utils.Utils;

namespace Bll
{
    public class UserBll
    {
        private UserDal _dal;
        private UserDto user;
        public UserBll()
        {
            _dal = new UserDal();
            user = new UserDto();
        }

        public List<UserDto> ListUsers(string name, string login, string mail, string status)
        {
            bool? vStatus = null;


            if (status.Equals(ResourceLanguageGlobal.Active.ToString()))
            {
                vStatus = true;
            }
            else if (status.Equals(ResourceLanguage.Inactive.ToString()))
            {
                vStatus = false;
            }
            else
            {
                vStatus = null;
            }

            user.Name = name;
            user.Login = login;
            user.Mail = mail;

            return _dal.GetList(user, vStatus);
        }

        public UserDto GetUser(string id)
        {
            if (Int64.TryParse(id, out var userId))
            {
                var user = _dal.GetOneItem(new UserDto { Id = userId }); ;
                user.Password = user.Password.Decrypt();
                return user;
            }

            user.Id = 0;

            return user;
        }

        public UserDto CreateUpdateUser(string id, string name, string login, string password, string mail, string status, out string msg, out bool success)
        {
            msg = "";
            success = true;

            try
            {

                Int64 userId = 0;
                Int64.TryParse(id, out userId);

                user.Id = userId;

                if (string.IsNullOrEmpty(name))
                {
                    msg = ResourceLanguageUser.UserNameNotFound;
                    success = false;
                    return null;
                }

                user.Name = name;

                if (string.IsNullOrEmpty(login))
                {
                    msg = ResourceLanguageUser.UserLoginNotFound;
                    success = false;
                    return null;
                }

                user.Login = login;

                if (string.IsNullOrEmpty(password))
                {
                    msg = ResourceLanguageUser.UserPasswordNotFound;
                    success = false;
                    return null;
                }

                user.Password = password.Encrypt(); ;

                if (string.IsNullOrEmpty(mail))
                {
                    msg = ResourceLanguageUser.UserMailNotFound;
                    success = false;
                    return null;
                }

                user.Mail = mail;

                var vStatus = true;
                if (status == ResourceLanguage.Active)
                {
                    vStatus = true;
                }
                else
                {
                    vStatus = false;

                }

                user.Status = vStatus;

                if (_dal.ExistsItem(user))
                {
                    msg = ResourceLanguageUser.UserExists;
                    return null;
                }

                if (userId > 0)
                {
                    msg = ResourceLanguageUser.UserUpdated;
                    return _dal.UpdateItem(user);
                }
                else
                {
                    msg = ResourceLanguageUser.UserCreated;
                    return _dal.CreateItem(user);
                }
            }
            catch (Exception ex)
            {
                msg = ex.Message;
                return null;
            }
        }

        public void EnableDisableUser(string id)
        {
            if (Int64.TryParse(id, out var userId))
            {
                var user = _dal.GetOneItem(new UserDto { Id = userId});
                if (user != null)
                {
                    if (user.Status)
                    {
                        user.Status = false;
                    }
                    else
                    {
                        user.Status = true;
                    }
                    _dal.EnableDisableItem(user);
                }
            }

        }
    }
}

using Dal;
using Dto;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Utils.Language;

namespace Bll
{
    public class AuthorBll
    {
        private AuthorDal _dal;
        private AuthorDto author;

        public AuthorBll()
        {
            _dal = new AuthorDal(); ;
            author = new AuthorDto(); ;
        }

        public List<AuthorDto> ListAuthor(string name, string status)
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

            author.Name = name;


            return _dal.GetList(author, vStatus);
        }

        public AuthorDto GetAuthor(string id)
        {
            if (Int64.TryParse(id, out var authorId))
            {
                var _author = _dal.GetOneItem(new AuthorDto { Id = authorId });
                return _author;
            }

            author.Id = 0;

            return author;
        }

        public AuthorDto CreateUpdateAuthor(string id, string name, string status, out string msg, out bool success)
        {
            msg = "";
            success = true;

            try
            {

                Int64 authorId = 0;
                Int64.TryParse(id, out authorId);

                author.Id = authorId;

                if (string.IsNullOrEmpty(name))
                {
                    msg = "Nome da author não encontrado!";
                    success = false;
                    return null;
                }

                author.Name = name;

                var vStatus = true;

                if (status == ResourceLanguage.Active)
                {
                    vStatus = true;
                }
                else
                {
                    vStatus = false;

                }

                author.Status = vStatus;

                if (_dal.ExistsItem(author))
                {
                    msg = "Author já existe!";
                    return null;
                }

                if (authorId > 0)
                {
                    msg = "Author atualizada com sucesso!";
                    return _dal.UpdateItem(author);
                }
                else
                {
                    msg = "Author criada com sucesso!";
                    return _dal.CreateItem(author);
                }
            }
            catch (Exception ex)
            {
                msg = ex.Message;
                return null;
            }
        }

        public void EnableDisableAuthor(string id)
        {
            if (Int64.TryParse(id, out var authorId))
            {
                var category = _dal.GetOneItem(new AuthorDto { Id = authorId });
                if (category != null)
                {
                    if (category.Status)
                    {
                        category.Status = false;
                    }
                    else
                    {
                        category.Status = true;
                    }
                    _dal.EnableDisableItem(category);
                }
            }

        }
    }
}

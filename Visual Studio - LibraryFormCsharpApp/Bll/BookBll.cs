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
    public class BookBll
    {
        private BookDal _dal;
        private BookDto book;

        public BookBll()
        {
            _dal = new BookDal();
            book = new BookDto();
        }

        public List<BookDto> ListBook(string name, string author, string status)
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

            book.Name = name;

            Int64 idAuthor = 0;
            Int64.TryParse(author, out idAuthor);

            book.AuthorId = idAuthor;

            return _dal.GetList(book, vStatus);
        }

        public BookDto GetBook(string id)
        {
            if (Int64.TryParse(id, out var bookId))
            {
                var _book = _dal.GetOneItem(new BookDto { Id = bookId });
                return _book;
            }

            book.Id = 0;

            return book;
        }

        public BookDto CreateUpdateBook(string id, string name, string author,string status, out string msg, out bool success)
        {
            msg = "";
            success = true;

            try
            {

                Int64 bookId = 0;
                Int64.TryParse(id, out bookId);

                book.Id = bookId;

                if (string.IsNullOrEmpty(name))
                {
                    msg = "Nome da book não encontrado!";
                    success = false;
                    return null;
                }

                book.Name = name;

                Int64 authorId = 0;
                if(!Int64.TryParse(author,out authorId))
                {
                    msg = "Author não selecionado!";
                    success = false;
                    return null;
                }
                

                var vStatus = true;

                if (status == ResourceLanguage.Active)
                {
                    vStatus = true;
                }
                else
                {
                    vStatus = false;

                }

                book.Status = vStatus;

                if (_dal.ExistsItem(book))
                {
                    msg = "book já existe!";
                    return null;
                }

                if (authorId > 0)
                {
                    msg = "book atualizada com sucesso!";
                    return _dal.UpdateItem(book);
                }
                else
                {
                    msg = "book criada com sucesso!";
                    return _dal.CreateItem(book);
                }
            }
            catch (Exception ex)
            {
                msg = ex.Message;
                return null;
            }
        }

        public void EnableDisableBook(string id)
        {
            if (Int64.TryParse(id, out var authorId))
            {
                var book = _dal.GetOneItem(new BookDto { Id = authorId });
                if (book != null)
                {
                    if (book.Status)
                    {
                        book.Status = false;
                    }
                    else
                    {
                        book.Status = true;
                    }
                    _dal.EnableDisableItem(book);
                }
            }

        }
    }
}

using Dal;
using Dal.Dal;
using Dto;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Utils.Language;

namespace Bll
{
    public class CategoryBll
    {
        private CategoryDal _dal;
        private CategoryDto category;



        public CategoryBll()
        {
            _dal = new CategoryDal();
            category = new CategoryDto();
        }

        public List<CategoryDto> ListCategory(string name, string status)
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

            category.Name = name;


            return _dal.GetList(category, vStatus);
        }

        public CategoryDto GetCategory(string id)
        {
            if (Int64.TryParse(id, out var categoryId))
            {
                var category = _dal.GetOneItem(new CategoryDto { Id= categoryId }); 
                return category;
            }

            category.Id = 0;

            return category;
        }

        public CategoryDto CreateUpdateCategory(string id, string name, string description, string status, out string msg, out bool success)
        {
            msg = "";
            success = true;

            try
            {

                Int64 categoryId = 0;
                Int64.TryParse(id, out categoryId);

                category.Id = categoryId;

                if (string.IsNullOrEmpty(name))
                {
                    msg = "Nome da categoria não encontrado!";
                    success = false;
                    return null;
                }

                category.Name = name;

                var vStatus = true;

                if (status == ResourceLanguage.Active)
                {
                    vStatus = true;
                }
                else
                {
                    vStatus = false;

                }

                category.Status = vStatus;

                if (_dal.ExistsItem(category))
                {
                    msg = "Categoria já existe!";
                    return null;
                }

                if (categoryId > 0)
                {
                    msg = "Categoria atualizada com sucesso!";
                    return _dal.UpdateItem(category);
                }
                else
                {
                    msg = "Categoria criada com sucesso!";
                    return _dal.CreateItem(category);
                }
            }
            catch (Exception ex)
            {
                msg = ex.Message;
                return null;
            }
        }

        public void EnableDisableCategory(string id)
        {
            if (Int64.TryParse(id, out var categoryId))
            {
                var category = _dal.GetOneItem(new CategoryDto { Id= categoryId });
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

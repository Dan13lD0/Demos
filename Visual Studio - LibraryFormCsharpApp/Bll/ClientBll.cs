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
    public class ClientBll
    {
        private ClientDal _dal;
        private ClientDto _client;

        public ClientBll()
        {
            _dal = new ClientDal();
            _client = new ClientDto();
        }

        public List<ClientDto> ListClient(string name, string rg, string cpf, string sex, string status)
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

            _client.Name = name;
            _client.RG = rg;
            _client.CPF = cpf;
            _client.Sex = sex;


            return _dal.GetList(_client, vStatus);
        }

        public ClientDto GetClient(string id)
        {
            if (Int64.TryParse(id, out var clientId))
            {
                _client.Id = clientId;
                var client = _dal.GetOneItem(_client); ;
                return client;
            }

            _client.Id = 0;

            return _client;
        }

        public ClientDto CreateUpdateClient(string id, string name, string rg, string cpf, string birthday, string sex, string status, out string msg, out bool success)
        {
            msg = "";
            success = true;

            try
            {

                Int64 clientId = 0;
                Int64.TryParse(id, out clientId);

                _client.Id = clientId;

                if (string.IsNullOrEmpty(name))
                {
                    msg = "Nome do cliente não encontrado!";
                    success = false;
                      return null;
                }

                _client.Name = name;
                _client.RG = rg;
                _client.CPF = cpf;
                _client.Sex = sex;

                if (DateTime.TryParse(birthday, out var birth))
                {
                    _client.Birthday = birth;
                }
                else
                {
                    _client.Birthday = null;
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

                _client.Status = vStatus;

                if (_dal.ExistsItem(_client))
                {
                    msg = "Cliente já existe!";
                      return null;
                }

                if (clientId > 0)
                {
                    msg = "Cliente atualizado com sucesso!";
                    return _dal.UpdateItem(_client);
                }
                else
                {
                    msg = "Cliente criado com sucesso!";
                    return _dal.CreateItem(_client);
                }
            }
            catch (Exception ex)
            {
                msg = ex.Message;
                  return null;
            }
        }

        public void EnableDisableClient(string id)
        {
            if (Int64.TryParse(id, out var clientId))
            {
                _client.Id = clientId;
                var client = _dal.GetOneItem(_client);
                if (client != null)
                {
                    if (client.Status)
                    {
                        client.Status = false;
                    }
                    else
                    {
                        client.Status = true;
                    }
                    _dal.EnableDisableItem(client);
                }
            }

        }
    }
}

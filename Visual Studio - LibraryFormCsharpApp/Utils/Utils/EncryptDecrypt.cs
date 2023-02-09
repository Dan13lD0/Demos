using System;
using System.Collections.Generic;
using System.Linq;
using System.Security.Cryptography;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace Utils.Utils
{
    public static class EncryptDecrypt
    {
        private static TripleDESCryptoServiceProvider TripleDes = new TripleDESCryptoServiceProvider();
        private static MD5CryptoServiceProvider MD5 = new MD5CryptoServiceProvider();
        private const string Key = "salmo139";

        public static Byte[] MD5Hash(string value)
        {
            Byte[] byteArray = ASCIIEncoding.ASCII.GetBytes(value);
            return MD5.ComputeHash(byteArray);
        }
        public static string Encrypt(this string value)
        {
            Utils.ToWait(true);
            TripleDes.Key = EncryptDecrypt.MD5Hash(Key);
            TripleDes.Mode = CipherMode.ECB;
            Byte[] buffer = ASCIIEncoding.ASCII.GetBytes(value);
            Utils.ToWait(false);
            return Convert.ToBase64String(TripleDes.CreateEncryptor().TransformFinalBlock(buffer, 0, buffer.Length));       
        }
        public static string Decrypt(this string value)
        {
            Utils.ToWait(true);
            TripleDes.Key = EncryptDecrypt.MD5Hash(Key);
            TripleDes.Mode = CipherMode.ECB;
            Byte[] buffer = Convert.FromBase64String(value);
            Utils.ToWait(false);
            return ASCIIEncoding.ASCII.GetString(TripleDes.CreateDecryptor().TransformFinalBlock(buffer, 0, buffer.Length));            
        }
    }
}

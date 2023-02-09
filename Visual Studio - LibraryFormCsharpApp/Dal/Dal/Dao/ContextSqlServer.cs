using Dto;
using System;
using System.Collections.Generic;
using System.Data.Entity;
using System.Data.Entity.ModelConfiguration.Conventions;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Utils.Utils;

namespace Dal.Dal.Dao
{
    internal class ContextSqlServer : DbContext
    {
        public ContextSqlServer() : base("Data Source=(local);Initial Catalog=LibraryApp;Integrated Security=True")
        {
            Database.SetInitializer<ContextSqlServer>(new CreateDatabaseIfNotExists<ContextSqlServer>());
            Database.SetInitializer<ContextSqlServer>(new DropCreateDatabaseIfModelChanges<ContextSqlServer>());

            InitializeInformationDefault();
        }


        protected override void OnModelCreating(DbModelBuilder modelBuilder)
        {
            modelBuilder.Conventions.Remove<OneToManyCascadeDeleteConvention>();

        }

        public DbSet<UserDto> User { get; set; }
        public DbSet<AuthorDto> Author { get; set; }
        public DbSet<BookDto> Book { get; set; }
        public DbSet<ClientDto> Client { get; set; }        
        public DbSet<CategoryDto> Category { get; set; }


        #region DefaultInformations

        private void InitializeInformationDefault()
        {
            CreateAdmin();
        }
        private void CreateAdmin()
        {
            if (User.Count() > 0)
                return;
            User.Add(new UserDto
            {
                Login = "admin",
                Name = "admin",
                Mail = "admin@gmail.com",
                Password = "123456".Encrypt(),
                Status = true
            });
            SaveChanges();
        }

        #endregion
    }
}

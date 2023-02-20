using Dto;
using System;
using System.Collections.Generic;
using System.Data.Entity;
using System.Data.Entity.ModelConfiguration.Conventions;
using System.Globalization;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Utils.Utils;

namespace Dal.Dal.Dao
{
    internal class ContextSqlServer : DbContext
    {
        public ContextSqlServer() : base("Data Source=.;Initial Catalog=LibraryApp;Integrated Security=True")
        {
            Database.SetInitializer<ContextSqlServer>(new CreateDatabaseIfNotExists<ContextSqlServer>());
            Database.SetInitializer<ContextSqlServer>(new DropCreateDatabaseIfModelChanges<ContextSqlServer>());
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

 

        #endregion
    }
}

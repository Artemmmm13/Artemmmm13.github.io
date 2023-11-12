using HW4.Models;
using Microsoft.AspNetCore.Identity.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore;

namespace HW4.Data;

public class ApplicationDbContext : IdentityDbContext
{
    public DbSet<User> Users { get; set; }
    public DbSet<UserEncryptedTextMapping> UserEncryptedTextMappings { get; set; }
    
    public ApplicationDbContext(DbContextOptions<ApplicationDbContext> options): base(options)
    {
    }
    
}
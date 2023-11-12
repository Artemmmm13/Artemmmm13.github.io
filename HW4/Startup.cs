using HW4.Data;
using HW4.Models;
using Microsoft.AspNetCore.Identity;

namespace HW4;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.AspNetCore.Mvc.Razor.RuntimeCompilation;


public class Startup
{
    public void ConfigureServices(IServiceCollection services)
    {
        services.AddControllersWithViews().AddRazorRuntimeCompilation();
        services.AddIdentity<User, IdentityRole<Guid>>()
            .AddEntityFrameworkStores<ApplicationDbContext>()
            .AddDefaultTokenProviders();

    }
}
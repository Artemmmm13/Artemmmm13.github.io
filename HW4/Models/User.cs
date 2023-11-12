using System.ComponentModel.DataAnnotations;

namespace HW4.Models;

public class User
{
    public Guid UserId { get; set; }

    [MaxLength(128)]
    [MinLength(1)]
    [Required]
    public string? UserName { get; set; }
    
    [Required]
    [EmailAddress]
    public string? Email { get; set; }
    
    [Required]
    [DataType(DataType.Password)]
    public string? Password { get; set; }
    
    public Boolean? IsUserAdmin { get; set; }
}
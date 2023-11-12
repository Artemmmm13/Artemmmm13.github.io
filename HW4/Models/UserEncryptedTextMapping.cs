using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using Microsoft.EntityFrameworkCore;

namespace HW4.Models;

[PrimaryKey("UserToEncryptedTextMappingId")]
public class UserEncryptedTextMapping
{
    public Guid UserToEncryptedTextMappingId { get; set; }
    
    [Required]
    [ForeignKey("text_creator_id")]
    public Guid TextCreatorId { get; set; }
    
    [MinLength(2)]
    [MaxLength(50)]
    [Required]
    public string? EncryptedText { get; set; }
}